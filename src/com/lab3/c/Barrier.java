package com.lab3.c;

public class Barrier {

    private int threadsCount = 2;
    private int finishedThreads = 0;
    private Runnable commonRunnable = null;

    public Barrier() {
        super();
    }

    public Barrier(int numThreads) {
        super();
        this.threadsCount = numThreads;
    }

    public Barrier(int numThreads, Runnable commonRunnable) {
        super();
        this.threadsCount = numThreads;
        this.commonRunnable = commonRunnable;
    }

    public synchronized void await(){
        ++finishedThreads;

        try {
            if(finishedThreads== threadsCount)
                this.overcomeBarrier();
            else {
                this.wait();
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted");
        }
    }

    private synchronized void overcomeBarrier(){
        if(commonRunnable != null) {
            Thread th = new Thread(commonRunnable);
            th.start();
            try {
                th.join();
            } catch (InterruptedException e) {
                return;
            }
        }
        finishedThreads = 0;
        this.notifyAll();
    }
}
