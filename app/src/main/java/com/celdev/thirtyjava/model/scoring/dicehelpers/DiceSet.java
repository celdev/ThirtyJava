package com.celdev.thirtyjava.model.scoring.dicehelpers;

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
