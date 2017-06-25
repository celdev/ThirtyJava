package com.celdev.thirtyjava.model.scoring.dicehelpers;

import com.celdev.thirtyjava.model.Dice;

public class MinimalDice {

    private final int value;

    public MinimalDice(Dice dice) {
        value = dice.getValue();
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MinimalDice that = (MinimalDice) o;

        return value == that.value;

    }

    @Override
    public int hashCode() {
        return value;
    }
}
