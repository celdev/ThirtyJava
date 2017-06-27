package com.celdev.thirtyjava.model.scoring;

import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSet;
import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSetCreator;
import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSetPermutationCreator;

import java.util.List;
import java.util.Set;

public class ScoreCounter {


    public int calculateScore(List<Dice> dices, ScoringMode scoringMode){
        DiceSet diceSet = new DiceSetCreator(dices).getDiceSet();
        Set<DiceSet> permutations = new DiceSetPermutationCreator(diceSet).createAndGetPermutations();
        return new DiceSetCounter().getMaxScoreOfAllDiceSetPermutations(permutations,scoringMode);
    }

    public int calculateLowScore(List<Dice> dices) {
        int score = 0;
        for (Dice dice : dices) {
            if (dice.getValue() <= 3) {
                score += dice.getValue();
            }
        }
        return score;
    }
}
