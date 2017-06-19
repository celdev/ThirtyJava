package com.celdev.thirtyjava.gameactivity;

import com.celdev.thirtyjava.model.Dice;

import java.util.List;

public interface GameActivityMVP {

    interface View {
        void doDicePlay();

        List<Dice> getDices();

        void onFinishRound();

        void onFinishGame();

        void newDiceThrow();

        int getDiceValueSetCount();

        void updateThrowText(int throwNumber);

        void updateRoundText(int roundNumber);

        void showRoundResults();

        void startNewRound();

        void DEBUG_TOAST(String msg);
    }

    interface Presenter {

        void onDicePlay();

        void onRoundFinish();

        void onGameFinish();

        int getThrowCount();

        int getRoundCount();

        void startNewRound();

    }


}
