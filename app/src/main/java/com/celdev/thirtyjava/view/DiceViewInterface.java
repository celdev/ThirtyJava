package com.celdev.thirtyjava.view;

import com.celdev.thirtyjava.model.Dice;

interface DiceViewInterface {

    Dice getDice();

    void setCantChangeState();

    void onNewThrow();

    void rollDice();

    boolean isSaveValue();

    void onNewRound();

    void onLastThrow();
}
