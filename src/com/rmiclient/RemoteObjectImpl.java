package com.rmiclient;

import com.rmiclient.io.RMIInputStream;
import com.rmiclient.io.InputStreamImpl;
import com.rmiclient.io.RMIOutputStream;
import com.rmiclient.io.OutputStreamImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class RemoteObjectImpl extends UnicastRemoteObject implements RemoteObject {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_MAX_THREAD_COUNT = 5;

    private static final Vector<Thread> pendingCommandThreads = new Vector<>();
    private static final Vector<Thread> runningCommandThreads = new Vector<>();


    public RemoteObjectImpl() throws RemoteException {
    }

    @Override
    public String ping() {
        return "Hello world!";
    }


    @Override
    public String runCommand(String command, String[] envp)
            throws RemoteException {

        CommandThread t = new CommandThread(command, envp);
        try {

            if (getActiveThreadCount() < getMaxThreadCount()) {
                runningCommandThreads.add(t);
                t.start();
            } else {
                pendingCommandThreads.add(t);
                System.out.println("Queued (thread: " + t.getName() + "): " + command);
            }
            t.join();

        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }

        return t.getResults();
    }

    public OutputStream getOutputStream(File f) throws IOException {
        System.out.println("Upload file: " + f.getName());
        return new RMIOutputStream(new OutputStreamImpl(new FileOutputStream(f)));
    }

    public InputStream getInputStream(File f) throws IOException {
        System.out.println("Download file: " + f.getName());
        return new RMIInputStream(new InputStreamImpl(new FileInputStream(f)));
    }

    public static int getPendingThreadCount() {
        return pendingCommandThreads.size();
    }

    public static int getActiveThreadCount() {
        return runningCommandThreads.size();
    }

    protected int getMaxThreadCount() {
        return DEFAULT_MAX_THREAD_COUNT;
    }


    class CommandThread extends Thread {
        private final String command;
        private final String[] envp;
        private final StringBuffer results = new StringBuffer();


        public CommandThread(String command, String[] envp) {
            super();
            this.command = command;
            this.envp = envp;
        }

        public void run() {
            long startTime = System.currentTimeMillis();

            if (RemoteObjectImpl.getActiveThreadCount() > 1) {
                try {
                    sleep((long) (Math.random() * 2000));
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

            try {
                System.out.println("Running (thread: " + this.getName() + ") : " + command);
                Process cmdProcess = Runtime.getRuntime().exec(command, envp);
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(cmdProcess.getInputStream()));
                BufferedReader stdError = new BufferedReader(new
                        InputStreamReader(cmdProcess.getErrorStream()));
                String s;
                while ((s = stdInput.readLine()) != null) {
                    results.append(s);
                }
                while ((s = stdError.readLine()) != null) {
                    results.append(s);
                }

            } catch (IOException e) {
                results.append(e.getMessage());
            } finally {
                long endTime = System.currentTimeMillis();
                runningCommandThreads.remove(this);
                System.out.println("Completed (thread: " + this.getName() + ") in " + (endTime - startTime) + " ms");
                if (getPendingThreadCount() > 0 &&
                        getActiveThreadCount() < getMaxThreadCount()) {
                    Thread t = pendingCommandThreads.remove(0);
                    runningCommandThreads.add(t);
                    t.start();
                }
            }
        }

        public String getResults() {
            return results.toString();
        }

    }
}
