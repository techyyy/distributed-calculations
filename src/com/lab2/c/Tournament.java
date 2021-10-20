package com.lab2.c;

public class Tournament {

    public final static int ROUNDS_COUNT = 3;

    public static void main(String[] args) {
        Monk[] monks = new Monk[(int) Math.pow(2, ROUNDS_COUNT)];

        for(int i = 0; i < monks.length; i += 2) {
            monks[i] = new Monk(Monastery.GUAN_YIN);
        }

        for(int i = 1; i < monks.length; i += 2) {
            monks[i] = new Monk(Monastery.GUAN_YANG);
        }
        for(int i = 0; i < monks.length; i++) {
            System.out.println(i + "." + monks[i]);
        }

        Contest contest = new Contest(monks,0,monks.length - 1);
        Integer winner = contest.compute();

        System.out.println("The winner is " + winner + "." + monks[winner].monastery);
        System.out.println(monks[winner].monastery + " monastery gets to keep the Bodhisattva statue.");
    }
}
