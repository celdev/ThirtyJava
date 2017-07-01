package com.celdev.thirtyjava.gameactivity;

import com.celdev.thirtyjava.helpers.DiceCheckboxViewState;
import com.celdev.thirtyjava.model.GameScoring;
import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/** This class' responsibility is to save the state of the application
 *  This is done by saving all data that is needed to recreate the state of the application
 *
 *  The information needed is
    *      * The value of each dice and their DiceViewState
 *              - needed to recreate the DiceCheckboxes
 *
    *      * The round and throw count
    *      * The recorded scores
    *      * The available Scoring modes
 *              - needed to recreate the GameRepository
 *
    *      * The GameViewState
 *              - Needed to determind which state the activity should be in
 *                (Roll the dice or set the score)
 * */
class GameApplicationState implements Serializable {

    private DiceCheckboxViewState dice1, dice2, dice3, dice4, dice5, dice6;
    private GameScoring[] gameScorings;
    private int roundCount;
    private int throwCount;
    private ScoringMode[] availableScoringModes;
    private GameViewState gameViewState;

    GameApplicationState(DiceCheckboxViewState dice1, DiceCheckboxViewState dice2,
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

    enum GameViewState {
        ROLL,
        SET_SCORE
    }

    GameViewState getGameViewState() {
        return gameViewState;
    }

    List<DiceCheckboxViewState> getDiceCheckboxViewStateList(){
        return Arrays.asList(dice1, dice2, dice3, dice4, dice5, dice6);
    }

    GameScoring[] getGameScorings() {
        return gameScorings;
    }

    int getRoundCount() {
        return roundCount;
    }

    int getThrowCount() {
        return throwCount;
    }

    ScoringMode[] getAvailableScoringModes() {
        return availableScoringModes;
    }
}
