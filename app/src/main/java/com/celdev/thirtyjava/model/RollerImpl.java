package com.celdev.thirtyjava.model;


import java.util.Random;

public class RollerImpl implements Roller {
    @Override
    public int rollDice() {
        return new Random().nextInt(6) + 1;
    }



}
