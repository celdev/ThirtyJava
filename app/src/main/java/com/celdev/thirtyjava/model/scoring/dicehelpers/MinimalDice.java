package com.celdev.thirtyjava.model.scoring.dicehelpers;

import com.celdev.thirtyjava.model.Dice;

/** This class represents a immutable dice
 *  which can be created by passing in an Dice
 *  or passing in a char
 *  If the char-parameter constructor is used
 *  then the value of the dice will be as following
 *  A -> 1
 *  B -> 2
 *  C -> 3
 *  D -> 4
 *  E -> 5
 *  F -> 6
 *  ...
 *  This makes it possible for me to create sets of (Minimal)Dice by
 *  converting a String (e.g.) "AACDEF" into 6 dice with the values
 *  [1] [1] [3] [4] [5] [6]
 * */
public class MinimalDice {

    private final int value;

    MinimalDice(Dice dice) {
        value = dice.getValue();
    }

    MinimalDice(char diceChar) {
        value = diceChar - 64;
    }

    public int getValue() {
        return value;
    }

    /** Returns the value converted into a letter (1=A, 2=B,...,6=F)
     * */
    String getValueAsString() {
        return "" + (char)(64+value);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && value == ((MinimalDice) o).value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
