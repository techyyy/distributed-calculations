package com.lab4.a;

public class ReadWriteLock {
    private Integer readLock = 0;
    private Integer writeLock = 0;

    public void readLock() {
        while (readLock == 1) {
            try {
                this.wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (readLock) {
            readLock = 1;
        }
    }

    public void readUnlock() {
        synchronized (readLock) {
            readLock = 0;
        }
    }

    public void writeLock() {
        readLock();
        while (writeLock == 1) {
            try {
                this.wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (writeLock) {
            writeLock = 1;
        }
    }

    public void writeUnlock() {
        synchronized (this) {
            readLock = 0;
            writeLock = 0;
        }
    }
}
