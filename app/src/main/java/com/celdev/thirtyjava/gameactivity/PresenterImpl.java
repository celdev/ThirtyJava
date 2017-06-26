package com.celdev.thirtyjava.gameactivity;

import com.celdev.thirtyjava.gameactivity.GameActivityMVP.GameRepository;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.util.ArrayList;
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

}
