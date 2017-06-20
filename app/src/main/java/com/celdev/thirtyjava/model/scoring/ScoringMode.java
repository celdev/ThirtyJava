package com.celdev.thirtyjava.model.scoring;

public enum ScoringMode {

    LOW(-1),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11),
    TWELEVE(12);


    private int score;

    ScoringMode(int score) {
        this.score = score;
    }


    public static ScoringMode intToScoringMode(int number) {
        for (ScoringMode scoringMode : values()) {
            if (number == scoringMode.score) {
                return scoringMode;
            }
        }
        return LOW;
    }

}
