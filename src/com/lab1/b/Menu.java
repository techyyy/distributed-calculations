package com.lab1.b;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;

public class Menu {
    private final JFrame frame;
    private final JPanel panel;

    private JSlider slider;

    private ASemaphoreThread threadOne;
    private ASemaphoreThread threadTwo;

    private static final AtomicInteger semaphore = new AtomicInteger(0);

    private JButton buttonInitializer(String title, ActionListener listener) {
        JButton button = new JButton(title);
        button.addActionListener(listener);
        Font newButtonFont = new Font(button.getFont().getName(), Font.BOLD, button.getFont().getSize());
        button.setFont(newButtonFont);
        return button;
    }

    private JSlider sliderInitializer() {
        JSlider slider = new JSlider(10, 90);
        slider.setValue(45);
        slider.setEnabled(false);
        return slider;
    }

    private void panelInitializer() {
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100,100,100));
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        slider = sliderInitializer();
        panel.add(slider);
        panel.add(new JLabel(""));
        panel.add(buttonInitializer("Start 1", e -> {
            if(semaphore.get() != 0) {
                JOptionPane.showMessageDialog(frame, "Cannot start the thread right now!");
            } else {
                threadOne = new ASemaphoreThread(slider, -1, semaphore, ASemaphoreThread.MIN_TARGET);
                threadOne.start();
            }
        }));
        panel.add(buttonInitializer("Start 2", e -> {
            if(semaphore.get() != 1) {
                JOptionPane.showMessageDialog(frame, "Cannot start the thread right now!");
            } else {
                threadTwo = new ASemaphoreThread(slider, 1, semaphore, ASemaphoreThread.MAX_TARGET);
                threadTwo.start();
            }
        }));
        panel.add(buttonInitializer("Stop 1", e -> threadOne.finish()));
        panel.add(buttonInitializer("Stop 2", e -> threadTwo.finish()));

    }

    private void frameInitializer(JFrame frame, JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private Menu() {
        frame = new JFrame();
        panel = new JPanel();

        panelInitializer();
        frameInitializer(frame, panel);
    }

    public static void main(String[] args) {
        new Menu();
    }
}