package com.celdev.thirtyjava.view;

import com.celdev.thirtyjava.model.Dice;

interface DiceViewTranslator {

    int diceValueToDrawable(Dice dice, DiceViewState viewState);

}
