package com.celdev.thirtyjava.model.scoring.dicehelpers;

import com.celdev.thirtyjava.model.Dice;

public class MinimalDice {

    private final int value;

    public MinimalDice(Dice dice) {
        value = dice.getValue();
    }

    public MinimalDice(char diceChar) {;
        value = diceChar - 64;
    }

    public int getValue() {
        return value;
    }

    public String getValueAsString() {
        return "" + (char)(64+value);
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
