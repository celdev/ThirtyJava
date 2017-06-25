package com.celdev.thirtyjava.model.scoring;

import com.celdev.thirtyjava.model.Dice;

import java.util.List;

public class ScoreCounterImpl implements ScoreCounter {


    /** Returns the sum of the dices, if the sum isn't equal to the scoring mode
     *  i.e. sum = 6 and scoring mode = 7 then the combination can't be made
     *  and an IllegalArgumentException will be thrown
     * */
    @Override
    public int calculateScore(List<Dice> dices, ScoringMode scoringMode) throws IllegalArgumentException {
        int score = 0;
        for (Dice dice : dices) {
            score += dice.getValue();
        }
        if (score > scoringMode.getScore()) {
            throw new IllegalArgumentException();
        }
        return score;
    }


    @Override
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
