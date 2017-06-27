package com.celdev.thirtyjava.gameactivity;

import android.content.Context;

import com.celdev.thirtyjava.model.Constants;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.DiceSetCounter;
import com.celdev.thirtyjava.model.scoring.ScoreCounter;
import com.celdev.thirtyjava.model.scoring.ScoringMode;
import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSetCreator;
import com.celdev.thirtyjava.model.scoring.dicehelpers.DiceSetPermutationCreator;

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
        if (scoringMode.equals(ScoringMode.LOW)) {
            gameScorings[scoringIndex] = new GameScoring(scoringMode, new DiceSetCounter().calculateDiceSetAsLow(new DiceSetCreator(dices).getDiceSet()));
        } else {
            gameScorings[scoringIndex] = new GameScoring(scoringMode,
                    new DiceSetCounter().getMaxScoreOfAllDiceSetPermutations(
                            new DiceSetPermutationCreator(new DiceSetCreator(dices).getDiceSet()).createAndGetPermutations(), scoringMode));
        }
    }

    @Override
    public GameScoring[] loadScoring() {
        return new GameScoring[0];
    }
}
