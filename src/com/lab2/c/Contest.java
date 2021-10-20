package com.lab2.c;

import lombok.AllArgsConstructor;

import java.util.concurrent.RecursiveTask;

@AllArgsConstructor
public class Contest extends RecursiveTask<Integer> {

    private final Monk[] monks;
    private final int leftFighter;
    private final int rightFighter;

    @Override
    protected Integer compute() {
        if (rightFighter - leftFighter > 2) {
            int middle = (leftFighter + rightFighter)/2;

            Contest firstBattle = new Contest(monks, leftFighter, middle);
            firstBattle.fork();

            Contest secondBattle = new Contest(monks, middle, rightFighter);
            secondBattle.fork();

            Integer firstContestant = firstBattle.join();
            Integer secondContestant = secondBattle.join();

            return fight(firstContestant, secondContestant);
        } else {
            return fight(leftFighter, leftFighter + 1);
        }
    }

    private Integer fight(Integer m1, Integer m2) {
        return monks[m1].capacity > monks[m2].capacity ? m1 : m2;
    }
}
