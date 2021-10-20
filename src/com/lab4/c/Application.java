package com.lab4.c;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Application {

    private static final String SITE_A = "A";
    private static final String SITE_B = "B";
    private static final String SITE_C = "C";
    private static final String SITE_D = "d";

    public static void main(String... args) throws InterruptedException {

        TrafficScheduler trafficScheduler = new TrafficScheduler();
        TransportManager transportManager = new TransportManager(new ReentrantReadWriteLock(false), trafficScheduler);

        transportManager.addBusStop(SITE_A);
        transportManager.addBusStop(SITE_B);
        transportManager.addBusStop(SITE_C);
        transportManager.addBusStop(SITE_D);

        transportManager.addFlight(SITE_A, SITE_B, 10);
        transportManager.addFlight(SITE_C, SITE_B, 20);
        transportManager.addFlight(SITE_C, SITE_D, 30);
        transportManager.addFlight(SITE_A, SITE_C, 40);

        transportManager.changeFlightPrice(SITE_A, SITE_C, 50);
        System.out.println("Flight price " + SITE_C + " - " + SITE_D + " is " +
                transportManager.getFlightPrice(SITE_C, SITE_D));
        transportManager.deleteFlight(SITE_C, SITE_A);
        transportManager.deleteBusStop(SITE_B);
    }
}
