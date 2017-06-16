package com.celdev.thirtyjava.model;

public class DiceFactoryImpl extends DiceFactory {



    public DiceFactoryImpl(Roller roller) {
        super(roller);
    }

    @Override
    public Dice createDice() {
        return new Dice(getRoller().rollDice());
    }
}
