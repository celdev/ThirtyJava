package com.celdev.thirtyjava.model.scoring.dicehelpers;

import com.celdev.thirtyjava.model.Dice;

import java.util.List;

/** This class represents a set of 6 dices
 *  Since this class and the MinimalDice class overrides
 *  the hashcode and equals method this class helps with reducing
 *  the amount of permutations to check for the score
 *
 *  i.e given the permutations of (e.g) the 6 dice with the
 *  face values of [1] [2] [2] [3] [4] [5]
 *
 *  Some of the permutations will be duplicate since
 *  the permutation method will create
 *  e.g. [1][3][4][5][2(a)][2(b)] and [1][3][4][5][2(b)][2(a)]
 *
 *  using this class and a Set<DiceSet> this will be reduced to one entry
 *  in the Set
 *
 *  The maximum DiceSets to check for score will be 6! (=720)
 *  and this will only be the case when every dice have a different value
 *  given two duplicate dice values (e.g. [1] [1] [2] [2] [4] [5])
 *  only 120 DiceSets will have their maximum score calculated
 * */
public class DiceSet {

    private final MinimalDice dice1, dice2, dice3, dice4, dice5, dice6;
    private final MinimalDice[] dices;

    public DiceSet(MinimalDice dice1, MinimalDice dice2, MinimalDice dice3, MinimalDice dice4, MinimalDice dice5, MinimalDice dice6) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.dice3 = dice3;
        this.dice4 = dice4;
        this.dice5 = dice5;
        this.dice6 = dice6;
        dices = new MinimalDice[]{dice1, dice2, dice3, dice4, dice5, dice6};
    }

    public DiceSet(String diceSetAsString) {
        MinimalDice[] dices = new MinimalDice[6];
        for (int i = 0; i < diceSetAsString.length(); i++) {
            dices[i] = new MinimalDice(diceSetAsString.charAt(i));
        }
        this.dices = dices;
        dice1 = dices[0];
        dice2 = dices[1];
        dice3 = dices[2];
        dice4 = dices[3];
        dice5 = dices[4];
        dice6 = dices[5];
    }

    public DiceSet(List<Dice> diceList) {
        MinimalDice[] dices = new MinimalDice[6];
        for (int i = 0; i < diceList.size(); i++) {
            dices[i] = new MinimalDice(diceList.get(i));
        }
        this.dices = dices;
        dice1 = dices[0];
        dice2 = dices[1];
        dice3 = dices[2];
        dice4 = dices[3];
        dice5 = dices[4];
        dice6 = dices[5];
    }

    public String getDiceSetAsCharString(){
        String ret = "";
        for (MinimalDice minimalDice : dices) {
            ret += minimalDice.getValueAsString();
        }
        return ret;
    }


    public MinimalDice[] getDices() {
        return dices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiceSet diceSet = (DiceSet) o;

        if (!dice1.equals(diceSet.dice1)) return false;
        if (!dice2.equals(diceSet.dice2)) return false;
        if (!dice3.equals(diceSet.dice3)) return false;
        if (!dice4.equals(diceSet.dice4)) return false;
        if (!dice5.equals(diceSet.dice5)) return false;
        return dice6.equals(diceSet.dice6);

    }

    @Override
    public int hashCode() {
        int result = dice1.hashCode();
        result = 31 * result + dice2.hashCode();
        result = 31 * result + dice3.hashCode();
        result = 31 * result + dice4.hashCode();
        result = 31 * result + dice5.hashCode();
        result = 31 * result + dice6.hashCode();
        return result;
    }
}
