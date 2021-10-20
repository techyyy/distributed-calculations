package com.lab2.a;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

class BeeGroup implements Runnable {

    private final int[] lane;

    BeeGroup(int[] lane) {
        this.lane = lane;
    }

    @SneakyThrows
    @Override
    public void run() {
        TimeUnit.SECONDS.sleep(1);
        for (int j : lane) {
            if (j == 1) {
                System.out.println("found");
                break;
            }
        }
    }
}
