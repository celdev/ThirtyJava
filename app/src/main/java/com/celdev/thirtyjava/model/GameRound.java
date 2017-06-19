package com.celdev.thirtyjava.model;

import java.util.List;

public class GameRound {

    private int roundNr;
    private List<Dice> dices;


    public GameRound(int roundNr, List<Dice> dices) {
        this.roundNr = roundNr;
        this.dices = dices;
    }
}
