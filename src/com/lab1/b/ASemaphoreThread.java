package com.lab1.b;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ASemaphoreThread extends Thread {

    public static final int MIN_TARGET = 10;
    public static final int MAX_TARGET = 90;

    private final JSlider slider;
    private final int value;
    private final AtomicInteger semaphore;
    private final int target;
    private boolean isStopped = false;

    public ASemaphoreThread(JSlider slider, int value, AtomicInteger semaphore, int target) {
        this.slider = slider;
        this.value = value;
        this.semaphore = semaphore;
        this.target = target;
    }

    public void finish() {
        isStopped = true;
    }

    @Override
    public void run() {
        synchronized (slider) {
            while (!isStopped && slider.getValue() != target) {
                slider.setValue(slider.getValue() + value);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(target == MAX_TARGET) {
                semaphore.set(0);
            } else {
                semaphore.set(1);
            }
        }
    }
}
