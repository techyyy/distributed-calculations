package com.rmiclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObject extends Remote {

    String ping() throws RemoteException;

    String runCommand(String command, String[] envp) throws RemoteException;

    OutputStream getOutputStream(File f) throws IOException;

    InputStream getInputStream(File f) throws IOException;
}