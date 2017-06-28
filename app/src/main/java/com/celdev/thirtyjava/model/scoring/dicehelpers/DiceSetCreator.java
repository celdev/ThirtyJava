package com.celdev.thirtyjava.model.scoring.dicehelpers;

import com.celdev.thirtyjava.model.Dice;

import java.util.List;

/** This class is responsible for creating a DiceSet-object from
 *  a List of Dice objects
 * */
public class DiceSetCreator {

    private DiceSet diceSet;

    public DiceSetCreator(List<Dice> diceList) {
        if (diceList.size() != 6) {
            throw new IllegalArgumentException("Have to have 6 dices");
        }

    }

    public DiceSet getDiceSet() {
        return diceSet;
    }
}
