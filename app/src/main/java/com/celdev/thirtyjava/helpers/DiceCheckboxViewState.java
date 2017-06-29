package com.celdev.thirtyjava.helpers;

import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.view.DiceViewState;

import java.io.Serializable;

public class DiceCheckboxViewState implements Serializable {

    private final Dice dice;
    private final DiceViewState diceViewState;

    public DiceCheckboxViewState(Dice dice, DiceViewState diceViewState) {
        this.dice = dice;
        this.diceViewState = diceViewState;
    }

    public Dice getDice() {
        return dice;
    }

    public DiceViewState getDiceViewState() {
        return diceViewState;
    }
}
