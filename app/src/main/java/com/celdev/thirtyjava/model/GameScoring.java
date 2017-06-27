package com.celdev.thirtyjava.model;

import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.io.Serializable;


public class GameScoring implements Serializable {

    private ScoringMode scoringMode;
    private int score;

    public GameScoring(ScoringMode scoringMode, int score) {
        this.scoringMode = scoringMode;
        this.score = score;
    }

    public ScoringMode getScoringMode() {
        return scoringMode;
    }

    public int getScore() {
        return score;
    }

    public String getScoreAsString() {
        return "" + score;
    }
}
