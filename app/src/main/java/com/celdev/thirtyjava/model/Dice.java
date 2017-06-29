package com.celdev.thirtyjava.model;

import java.io.Serializable;

public class Dice implements Serializable {

    private int value;

    public Dice(int value) {
        this.value = value;
    }

    public void roll(Roller roller) {
        value = roller.rollDice();
    }

    public int getValue() {
        return value;
    }
}
