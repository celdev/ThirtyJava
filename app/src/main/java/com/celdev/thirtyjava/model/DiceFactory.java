package com.celdev.thirtyjava.model;

public abstract class DiceFactory {


    private Roller roller;

    public DiceFactory(Roller roller) {
        this.roller = roller;
    }

    abstract Dice createDice();

    Roller getRoller() {
        return roller;
    }
}
