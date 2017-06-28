package com.celdev.thirtyjava.model;

import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.io.Serializable;


public class GameScoring implements Serializable {

    private final ScoringMode scoringMode;
    private final int score;

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

    public String getGameScoringAsStorableObject() {
        return scoringMode.name() + ";" + score + "&";
    }

    public GameScoring(String createFromString) throws LoadingException {
        String[] split = createFromString.split(";");
        scoringMode = ScoringMode.scoringModeFromNameString(split[0]);
        score = Integer.parseInt(split[1]);
    }
}
