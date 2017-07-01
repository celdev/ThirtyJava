package com.celdev.thirtyjava.model.scoring;

import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSet;
import com.celdev.thirtyjava.model.scoring.dicehelpers.MinimalDice;

import java.util.Set;

public class DiceSetCounter {

    public int getMaxScoreOfAllDiceSetPermutations(Set<DiceSet> permutations, ScoringMode scoringMode) {
        int topScore = 0;
        int targetScore = scoringMode.getScore();
        for (DiceSet diceSet : permutations) {
            int diceSetScore = count(diceSet, targetScore);
            if (diceSetScore > topScore) {
                topScore = diceSetScore;
            }
        }
        return topScore;
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

}
