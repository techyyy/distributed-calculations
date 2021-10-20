package com.lab1.a;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Menu {

    private static final String CURRENT_THREAD_PRIORITY = "Thread %1$s priority: %2$s";

    private final JPanel panel;
    private JSlider slider;
    private final AThread threadOne;
    private final AThread threadTwo;
    private final JLabel threadOneLabel;
    private final JLabel threadTwoLabel;

    private JButton buttonInitializer(String title, ActionListener listener) {
        JButton button = new JButton(title);
        button.addActionListener(listener);
        Font newButtonFont = new Font(button.getFont().getName(), Font.BOLD, button.getFont().getSize());
        button.setFont(newButtonFont);
        return button;
    }

    private JSlider sliderInitializer() {
        JSlider slider = new JSlider(0, 100);
        slider.setValue(50);
        slider.setEnabled(false);
        return slider;
    }

    private void panelInitializer() {
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100,100,100));
        panel.setLayout(new GridLayout(3, 3, 10, 10));
        slider = sliderInitializer();
        panel.add(slider);
        panel.add(buttonInitializer("Start", e -> new Thread(() -> {
            threadOne.start();
            threadTwo.start();
        }).start()));
        panel.add(new JLabel(""));
        panel.add(buttonInitializer("Min", e -> { threadOne.setPriority(Thread.MIN_PRIORITY);
                                                    threadOneLabel.setText(String.format(CURRENT_THREAD_PRIORITY, "1", "1"));}));
        panel.add(buttonInitializer("Max", e -> { threadOne.setPriority(Thread.MIN_PRIORITY);
                                                    threadOneLabel.setText(String.format(CURRENT_THREAD_PRIORITY, "1", "10"));}));
        panel.add(threadOneLabel);
        panel.add(buttonInitializer("Min", e -> { threadTwo.setPriority(Thread.MIN_PRIORITY);
                                                    threadTwoLabel.setText(String.format(CURRENT_THREAD_PRIORITY, "2", "1"));}));
        panel.add(buttonInitializer("Max", e -> { threadTwo.setPriority(Thread.MIN_PRIORITY);
                                                    threadTwoLabel.setText(String.format(CURRENT_THREAD_PRIORITY, "2", "10"));}));
        panel.add(threadTwoLabel);

    }

    private void frameInitializer(JFrame frame, JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private Menu() {
        JFrame frame = new JFrame();
        panel = new JPanel();

        threadOneLabel = new JLabel("Thread 1 priority: 5");
        threadTwoLabel = new JLabel("Thread 2 priority: 5");

        panelInitializer();
        frameInitializer(frame, panel);

        threadOne = new AThread(slider, -1);
        threadTwo = new AThread(slider, 1);
    }

    public static void main(String[] args) {
        new Menu();
    }
}