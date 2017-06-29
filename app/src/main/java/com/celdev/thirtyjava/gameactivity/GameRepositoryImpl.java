package com.celdev.thirtyjava.gameactivity;

import android.content.Context;
import android.content.SharedPreferences;

import com.celdev.thirtyjava.model.Constants;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.DiceSetCounter;
import com.celdev.thirtyjava.model.scoring.ScoreCounter;
import com.celdev.thirtyjava.model.scoring.ScoringMode;
import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSet;
import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSetCreator;
import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSetPermutationCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameRepositoryImpl implements GameActivityMVP.GameRepository {

    private static final String PREF_KEY = "celdev.thirtyjava.game";
    private static final String GAME_SCORING_BASE_KEY = "celdev.thirtyjava.scoring";

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
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < gameScorings.length; i++) {
            GameScoring gameScoring = gameScorings[i];
            if (gameScoring != null) {
                editor.putString(GAME_SCORING_BASE_KEY + "_" + i, gameScoring.getGameScoringAsStorableObject());
            }
        }
        editor.putInt(GAME_SCORING_BASE_KEY.concat(".roundnr"), roundCount);
        editor.putInt(GAME_SCORING_BASE_KEY.concat(".thrownr"), throwCount);
        editor.apply();
    }

    @Override
    public void loadGameState() {
        //todo
    }

    @Override
    public void injectGameState(GameApplicationState gameState) {
        throwCount = gameState.getThrowCount();
        roundCount = gameState.getRoundCount();
        gameScorings = gameState.getGameScorings();
        scoringModes = new ArrayList<>(Arrays.asList(gameState.getAvailableScoringModes()));
        gameScorings = gameState.getGameScorings();
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
    public void injectGameScorings(GameScoring[] gameScorings) {
        this.gameScorings = gameScorings;
    }

    @Override
    public GameScoring[] getGameScorings() {
        return this.gameScorings;
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
        int scoringIndex = scoringMode.getScore() - 3;
        if (scoringIndex < 0) {
            scoringIndex = 0;
        }
        gameScorings[scoringIndex] = new GameScoring(scoringMode, new ScoreCounter().calculateScore(dices, scoringMode));
    }

    @Override
    public GameScoring[] loadScoring() {
        return new GameScoring[0];
    }
}
