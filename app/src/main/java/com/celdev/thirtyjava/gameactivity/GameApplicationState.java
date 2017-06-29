package com.celdev.thirtyjava.gameactivity;

import com.celdev.thirtyjava.helpers.DiceCheckboxViewState;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class GameApplicationState implements Serializable {

    private DiceCheckboxViewState dice1, dice2, dice3, dice4, dice5, dice6;
    private GameScoring[] gameScorings;
    private int roundCount;
    private int throwCount;
    private ScoringMode[] availableScoringModes;
    private GameViewState gameViewState;

    public GameApplicationState(DiceCheckboxViewState dice1, DiceCheckboxViewState dice2,
                                DiceCheckboxViewState dice3, DiceCheckboxViewState dice4,
                                DiceCheckboxViewState dice5, DiceCheckboxViewState dice6,
                                GameScoring[] gameScorings,
                                int roundCount, int throwCount,
                                ScoringMode[] availableScoringModes, GameViewState gameViewState) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.dice3 = dice3;
        this.dice4 = dice4;
        this.dice5 = dice5;
        this.dice6 = dice6;
        this.gameScorings = gameScorings;
        this.roundCount = roundCount;
        this.throwCount = throwCount;
        this.availableScoringModes = availableScoringModes;
        this.gameViewState = gameViewState;
    }

    public enum GameViewState {

        ROLL,
        SET_SCORE

    }

    public GameViewState getGameViewState() {
        return gameViewState;
    }

    public List<DiceCheckboxViewState> getDiceCheckboxViewStateList(){
        return Arrays.asList(dice1, dice2, dice3, dice4, dice5, dice6);
    }

    public DiceCheckboxViewState getDice1() {
        return dice1;
    }

    public DiceCheckboxViewState getDice2() {
        return dice2;
    }

    public DiceCheckboxViewState getDice3() {
        return dice3;
    }

    public DiceCheckboxViewState getDice4() {
        return dice4;
    }

    public DiceCheckboxViewState getDice5() {
        return dice5;
    }

    public DiceCheckboxViewState getDice6() {
        return dice6;
    }

    public GameScoring[] getGameScorings() {
        return gameScorings;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public int getThrowCount() {
        return throwCount;
    }

    public ScoringMode[] getAvailableScoringModes() {
        return availableScoringModes;
    }
}
