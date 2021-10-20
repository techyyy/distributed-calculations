package com.lab2.a;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FixedThreadPool {

    private static final Logger LOG = Logger.getLogger(FixedThreadPool.class.getName());

    private final int size;

    private final Worker[] workers;

    private final LinkedBlockingQueue<Runnable> taskQueue;

    private class Worker extends Thread {

        private boolean isStopped = false;

        public void setStopped() {
            isStopped = true;
        }

        public void run() {
            Runnable task;
            while (!isStopped) {
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty()) {
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            LOG.log(Level.SEVERE, e.getMessage());
                        }
                    }
                    task = taskQueue.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    LOG.log(Level.SEVERE, e.getMessage());
                }
            }
        }
    }

    public FixedThreadPool(int size) {
        this.size = size;
        taskQueue = new LinkedBlockingQueue<>();
        workers = new Worker[size];

        for (int i = 0; i < size; i++) {
            workers[i] = new Worker();
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    public void shutdown() throws InterruptedException {
        for (int i = 0; i < size; i++) {
            workers[i].setStopped();
        }
    }
}