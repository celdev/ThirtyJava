package com.celdev.thirtyjava.gameactivity;

import android.content.Context;

import com.celdev.thirtyjava.model.Constants;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameRepositoryImpl implements GameActivityMVP.GameRepository {

    private Context context;
    private int throwCount = 1;
    private int roundCount = 1;
    private GameScoring[] gameScorings = new GameScoring[10];
    private List<ScoringMode> scoringModes = new ArrayList<>();

    public GameRepositoryImpl(Context context) {
        this.context = context;
        scoringModes.addAll(Arrays.asList(ScoringMode.values()));
    }

    @Override
    public boolean finishRound() {
        return throwCount > Constants.THROWS_IN_ROUND;
    }

    @Override
    public boolean finishGame() {
        return roundCount > Constants.ROUNDS_IN_GAME;
    }

    @Override
    public void saveGameState() {
        //todo
    }

    @Override
    public void loadGameState() {
        //todo
    }

    @Override
    public int getThrowCount() {
        return throwCount;
    }

    @Override
    public void incrementThrowCount() {
        throwCount++;
    }

    @Override
    public void incrementRound() {
        roundCount++;
        throwCount = 1;
    }


    @Override
    public int getRoundCount() {
        return roundCount;
    }

    @Override
    public ScoringMode[] getAvailableScoringModes() {
        return scoringModes.toArray(new ScoringMode[scoringModes.size()]);
    }

    @Override
    public void saveScoring(List<Dice> dices, ScoringMode scoringMode) {
        scoringModes.remove(scoringMode);
    }

    @Override
    public GameScoring[] loadScoring() {
        return new GameScoring[0];
    }
}
