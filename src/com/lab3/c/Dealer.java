package com.lab3.c;

import java.security.SecureRandom;

public class Dealer implements Runnable{

    private static final SecureRandom RANDOM = new SecureRandom();

    public void run() {
        int missing = RANDOM.nextInt(Smoker.TOTAL_COUNT);
        System.out.println("Provided " + Material.values()[missing]+'.');
    }

}