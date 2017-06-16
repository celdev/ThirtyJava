package com.celdev.thirtyjava.view;

import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.DiceColor;

class DiceViewTranslatorImpl implements DiceViewTranslator {
    @Override
    public int diceValueToDrawable(Dice dice, DiceViewState viewState) {
        switch (viewState) {
            case NOT_ROLLED:
                return DiceDrawableRepository.valueAndColorToDrawable(dice.getValue(), DiceColor.GRAY);
            case SAVE_VALUE:
                return DiceDrawableRepository.valueAndColorToDrawable(dice.getValue(), DiceColor.RED);
            case ROLLING:
            case HAS_VALUE:
            default:
                return DiceDrawableRepository.valueAndColorToDrawable(dice.getValue(), DiceColor.WHITE);

        }
    }
}
