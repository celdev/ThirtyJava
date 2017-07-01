package com.celdev.thirtyjava.gameactivity;


import com.celdev.thirtyjava.helpers.DiceCheckboxViewState;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.util.List;

/** This class contains the method which the
 *  View, Presenter and the Repository should implement
 *
 *  For comments about what each method does, check the implementations
 * */
interface GameActivityMVP {

    interface View {

        void newRound();

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

        GameApplicationState createGameApplicationState(List<DiceCheckboxViewState> diceCheckboxViewStates, GameApplicationState.GameViewState gameViewState);

        GameScoring[] getScorings();

        void injectGameState(GameApplicationState gameState);
    }

    interface GameRepository {

        boolean finishRound();

        boolean finishGame();

        ScoringMode[] getAvailableScoringModes();

        void saveScoring(List<Dice> dices, ScoringMode scoringMode);

        int getThrowCount();

        void incrementThrowCount();

        void incrementRound();

        void injectGameScorings(GameScoring[] gameScorings);

        GameScoring[] getGameScorings();

        int getRoundCount();

        void injectGameState(GameApplicationState gameState);

        void newGame();
    }


}
