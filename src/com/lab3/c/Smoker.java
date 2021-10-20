package com.lab3.c;

public class Smoker implements Runnable{

    public static final int TOTAL_COUNT = 3;

    private static final Barrier BARRIER = new Barrier(TOTAL_COUNT, new Dealer());
    private static final Semaphore SEMAPHORE = new Semaphore();

    private final Material has;
    private final String name;

    public Smoker(String name, Material has) {
        this.has = has;
        this.name = name;
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            SEMAPHORE.acquire();
            int missing = 0;
            if (has == Material.values()[missing]) {
                System.out.println(this.name +" is smoking.");
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    System.err.println("Interrupted");
                    return;
                }
            }
            SEMAPHORE.release();

            if(!Thread.currentThread().isInterrupted())
                BARRIER.await();
        }
    }
}
