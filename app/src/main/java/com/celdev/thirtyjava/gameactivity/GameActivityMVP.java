package com.celdev.thirtyjava.gameactivity;

import android.os.Bundle;

import com.celdev.thirtyjava.helpers.DiceCheckboxViewState;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.ScoringMode;
import com.celdev.thirtyjava.view.DiceViewState;

import java.util.List;

public interface GameActivityMVP {

    interface View {

        void setState(List<Dice> dices, List<DiceViewState> diceViewStates, ScoringMode[] availableScoringModes);

        void newRound();

        void firstThrow();

        void newThrow();

        void finishRound();

        void finishGame();

    }

    interface Presenter {

        void finishRound();

        void newThrow();

        void saveScore(ScoringMode scoringMode, List<Dice> dices);

        int getThrowState();

        int getRoundState();

        ScoringMode[] getAvailableScoringModes();

        void onSaveInstanceState(Bundle outState);

        void onRestoreInstanceState(Bundle bundle);

        GameApplicationState createGameApplicationState(List<DiceCheckboxViewState> diceCheckboxViewStates, GameApplicationState.GameViewState gameViewState);

        GameScoring[] getScorings();

        void injectGameState(GameApplicationState gameState);
    }

    interface GameRepository {

        boolean finishRound();

        boolean finishGame();

        void saveGameState();

        void loadGameState();

        ScoringMode[] getAvailableScoringModes();

        void saveScoring(List<Dice> dices, ScoringMode scoringMode);

        GameScoring[] loadScoring();

        int getThrowCount();

        void incrementThrowCount();

        void incrementRound();

        void injectGameScorings(GameScoring[] gameScorings);

        GameScoring[] getGameScorings();

        int getRoundCount();

        void injectGameState(GameApplicationState gameState);
    }


}
