package com.celdev.thirtyjava.view;

import com.celdev.thirtyjava.model.Dice;

public interface DiceViewTranslator {

    int diceValueToDrawable(Dice dice, DiceViewState viewState);

}
