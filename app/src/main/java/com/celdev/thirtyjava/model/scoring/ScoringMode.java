package com.celdev.thirtyjava.model.scoring;

import com.celdev.thirtyjava.R;

public enum ScoringMode {

    LOW(-1, R.string.scoring_low),
    FOUR(4, R.string.scoring_4),
    FIVE(5, R.string.scoring_5),
    SIX(6, R.string.scoring_6),
    SEVEN(7, R.string.scoring_7),
    EIGHT(8, R.string.scoring_8),
    NINE(9, R.string.scoring_9),
    TEN(10, R.string.scoring_10),
    ELEVEN(11, R.string.scoring_11),
    TWELEVE(12, R.string.scoring_12);


    private int score;
    private int spinnerNameStringId;

    ScoringMode(int score, int spinnerNameStringId) {
        this.score = score;
        this.spinnerNameStringId = spinnerNameStringId;
    }

    public static ScoringMode intToScoringMode(int number) {
        for (ScoringMode scoringMode : values()) {
            if (number == scoringMode.score) {
                return scoringMode;
            }
        }
        return LOW;
    }

    public int getSpinnerNameStringId() {
        return spinnerNameStringId;
    }

    public int getScore() {
        return score;
    }
}
