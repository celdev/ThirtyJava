package com.celdev.thirtyjava.gameactivity;

import com.celdev.thirtyjava.model.Constants;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.GameRound;

import java.util.ArrayList;
import java.util.List;

class PresenterImpl implements GameActivityMVP.Presenter {

    private GameActivityMVP.View view;

    private int roundCount = 1;
    private int throwCount = 1;
    private List<GameRound> gameRounds;

    public PresenterImpl(GameActivityMVP.View view) {
        this.view = view;
        gameRounds = new ArrayList<>();
    }

    @Override
    public void onDicePlay() {
        throwCount++;
        updateViewGUI();
        if (view.getDiceValueSetCount() == view.getDices().size()) {
            view.showRoundResults();
        } else if (throwCount > Constants.THROWS_IN_ROUND) {
            view.showRoundResults();
        } else {
            view.newDiceThrow();
        }
    }

    @Override
    public void startNewRound() {
        throwCount = 1;
        view.startNewRound();
    }

    private void updateViewGUI() {
        view.updateRoundText(roundCount);
        view.updateThrowText(throwCount);
    }

    @Override
    public void onRoundFinish() {
        saveRound(roundCount,view.getDices());
        roundCount++;
        updateViewGUI();
        if (roundCount > Constants.ROUNDS_IN_GAME) {
            view.onFinishGame();
        } else {
            view.newDiceThrow();
        }
    }

    private void saveRound(int roundCount, List<Dice> dices) {
        gameRounds.add(new GameRound(roundCount, dices));
        startNewRound();
    }

    @Override
    public void onGameFinish() {
        view.DEBUG_TOAST("Finished Game");
    }

    @Override
    public int getThrowCount() {
        return throwCount;
    }

    @Override
    public int getRoundCount() {
        return roundCount;
    }
}
