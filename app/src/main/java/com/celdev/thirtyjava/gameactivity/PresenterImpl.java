package com.celdev.thirtyjava.gameactivity;

import android.os.Bundle;

import com.celdev.thirtyjava.gameactivity.GameActivityMVP.GameRepository;
import com.celdev.thirtyjava.helpers.DiceCheckboxViewState;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.util.List;

class PresenterImpl implements GameActivityMVP.Presenter {

    private GameActivityMVP.View view;

    private GameRepository gameRepository;

    public PresenterImpl(GameActivityMVP.View view, GameRepository gameRepository) {
        this.view = view;
        this.gameRepository = gameRepository;
    }


    @Override
    public void finishRound() {
        gameRepository.incrementRound();
        if (gameRepository.finishGame()) {
            view.finishGame();
        } else {
            view.newRound();
        }
    }

    @Override
    public void newThrow() {
        gameRepository.incrementThrowCount();
        if (gameRepository.finishRound()) {
            view.finishRound();
        } else {
            view.newThrow();
        }
    }

    @Override
    public void saveScore(ScoringMode scoringMode, List<Dice> dices) {
        gameRepository.saveScoring(dices, scoringMode);
    }

    @Override
    public int getThrowState() {
        return gameRepository.getThrowCount();
    }

    @Override
    public int getRoundState() {
        return gameRepository.getRoundCount();
    }

    @Override
    public ScoringMode[] getAvailableScoringModes() {
        return gameRepository.getAvailableScoringModes();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        gameRepository.saveGameState();
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        gameRepository.loadGameState();
    }

    @Override
    public void injectGameState(GameApplicationState gameState) {
        gameRepository.injectGameState(gameState);
    }

    @Override
    public GameApplicationState createGameApplicationState(List<DiceCheckboxViewState> diceCheckboxViewStates, GameApplicationState.GameViewState gameViewState) {
        return new GameApplicationState(
                diceCheckboxViewStates.get(0),
                diceCheckboxViewStates.get(1),
                diceCheckboxViewStates.get(2),
                diceCheckboxViewStates.get(3),
                diceCheckboxViewStates.get(4),
                diceCheckboxViewStates.get(5),
                gameRepository.getGameScorings(),
                gameRepository.getRoundCount(),
                gameRepository.getThrowCount(),
                gameRepository.getAvailableScoringModes(),
                gameViewState
        );
    }

    @Override
    public GameScoring[] getScorings() {
        return gameRepository.getGameScorings();
    }

}
