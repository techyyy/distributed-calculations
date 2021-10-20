package com.lab3.c;

public class Semaphore {
    private boolean isNotBlocked = true;

    public synchronized void acquire() {
        if(isNotBlocked) {
            isNotBlocked = false;
        } else {
            try {
                this.wait();
            } catch (InterruptedException e) {
                isNotBlocked = true;
            }
        }
    }

    public synchronized void release() {
        isNotBlocked = true;
        this.notify();
    }
}
