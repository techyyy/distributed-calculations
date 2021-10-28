package com.lab3.c;

public class Smoking {

    public static void main(String[] args) throws InterruptedException  {
        Thread t1 = new Thread(new Smoker("Larry", Material.TOBACCO));
        Thread t2 = new Thread(new Smoker("Harry", Material.PAPER));
        Thread t3 = new Thread(new Smoker("Barry", Material.LIGHTER));

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(10*1000);

        t1.interrupt();
        t2.interrupt();
        t3.interrupt();
    }
}
