package com.celdev.thirtyjava.model.scoring.dicehelpers;

import com.celdev.thirtyjava.model.Dice;

import java.util.List;

public class DiceSetCreator {

    private DiceSet diceSet;

    public DiceSetCreator(List<Dice> diceList) {
        if (diceList.size() != 6) {
            throw new IllegalArgumentException("Have to have 6 dices");
        }
        MinimalDice[] dices = new MinimalDice[6];
        for (int i = 0; i < diceList.size(); i++) {
            dices[i] = new MinimalDice(diceList.get(i));
        }
        diceSet = new DiceSet(dices[0], dices[1], dices[2], dices[3], dices[4], dices[5]);
    }

    public DiceSet getDiceSet() {
        return diceSet;
    }
}
