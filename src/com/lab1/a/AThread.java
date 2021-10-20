package com.lab1.a;

import lombok.AllArgsConstructor;

import javax.swing.*;

@AllArgsConstructor
class AThread extends Thread {

    private final JSlider slider;
    private final int value;

    @Override
    public void run() {
        while (true) {
            synchronized (slider) {
                slider.setValue(slider.getValue() + value);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
