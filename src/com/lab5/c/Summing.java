package com.lab5.c;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Summing {
    private final int ARRAY_SIZE = 5;
    private final List<ArrayThread> threads;
    private final CyclicBarrier barrier;
    private boolean exit = false;
    private List<Integer> sums = new ArrayList<>();

    private class ArrayThread extends Thread {
        List<Integer> array = new ArrayList<>(ARRAY_SIZE);
        int id;

        public ArrayThread(int id) {
            for (int i = 0; i < ARRAY_SIZE; ++i) {
                int MAX_NUMBER = 4;
                array.add(new Random().nextInt(MAX_NUMBER));
            }

            this.id = id;
        }

        public int getSum() {
            int sum = 0;

            for (int i : array) {
                sum += i;
            }

            return sum;
        }

        private void changeRandomValue(boolean add) {
            int i = new Random().nextInt(ARRAY_SIZE);

            System.out.printf("#%d changed array\n", id);

            if (add) {
                array.set(i, array.get(i) + 1);
            }else {
                array.set(i, array.get(i) - 1);
            }
        }

        @Override
        public void run() {
            while (!exit) {
                try {
                    barrier.await();
                    if (!exit) {
                        List<Integer> sumsCopy = new ArrayList<>(sums);
                        int mySum = sumsCopy.get(id);
                        sumsCopy.sort(Comparator.naturalOrder());
                        if (mySum == sumsCopy.get(0)){
                            changeRandomValue(true);
                        }else if(mySum == sumsCopy.get(1)){
                            changeRandomValue(new Random().nextBoolean());
                        }else{
                            changeRandomValue(false);
                        }
                    }

                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Summing() {
        threads = new ArrayList<>();
        barrier = new CyclicBarrier(3, ()->{
            sums = new ArrayList<>();
            System.out.println("Updating sums");
            for (int i = 0; i < 3; ++i) {
                sums.add(threads.get(i).getSum());
                System.out.printf("%d ",sums.get(i));
            }
            System.out.println();
            if (sums.get(0).equals(sums.get(1)) && sums.get(1).equals(sums.get(2))) {
                exit = true;
            }
        });

        for (int i = 0; i < 3; ++i) {
            threads.add(new ArrayThread(i));
        }
    }

    public void run() {
        for (int i = 0; i < 3; ++i) {
            threads.get(i).start();
        }
        for (int i = 0; i < 3; ++i) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Random().setSeed(System.nanoTime());
        Summing main = new Summing();
        main.run();
    }
}
