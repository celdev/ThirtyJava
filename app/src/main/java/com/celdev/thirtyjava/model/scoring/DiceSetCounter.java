package com.celdev.thirtyjava.model.scoring;

import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSet;
import com.celdev.thirtyjava.model.scoring.dicehelpers.MinimalDice;

public class DiceSetCounter {

    public int getScoreOfDiceSet(DiceSet diceSet, ScoringMode scoringMode) {
        int scoringModeInt = scoringMode.getScore();
        if (scoringModeInt == -1) {
            return calculateDiceSetAsLow(diceSet);
        } else {
            return count(diceSet, scoringModeInt);
        }
    }

    private int count(DiceSet diceSet, int targetScore) {
        int score = 0;
        int tempscore = 0;
        MinimalDice[] dices = diceSet.getDices();
        for (MinimalDice minimalDice : dices) {
            tempscore += minimalDice.getValue();
            if (tempscore == targetScore) {
                score += targetScore;
                tempscore = 0;
            } else if (tempscore > targetScore) {
                break;
            }
        }
        return score;

    }

    private int calculateDiceSetAsLow(DiceSet diceSet) {
        int score = 0;
        for (MinimalDice dice : diceSet.getDices()) {
            if (dice.getValue() <= 3) {
                score += dice.getValue();
            }
        }
        return score;
    }

}
