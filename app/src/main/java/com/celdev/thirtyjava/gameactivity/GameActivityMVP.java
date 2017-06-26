package com.celdev.thirtyjava.gameactivity;

import android.content.Context;

import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.util.List;

public interface GameActivityMVP {

    interface View {

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

        int getRoundCount();

    }


}
