package com.lab2.c;

import lombok.ToString;

import java.security.SecureRandom;

@ToString
public class Monk {
    public final int capacity;
    public final Monastery monastery;

    public Monk(Monastery monastery) {
        this.monastery = monastery;
        SecureRandom random = new SecureRandom();
        capacity = random.nextInt(100);
    }
}
