package com.celdev.thirtyjava.model;

import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.util.List;

public class GameScoring {

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
}
