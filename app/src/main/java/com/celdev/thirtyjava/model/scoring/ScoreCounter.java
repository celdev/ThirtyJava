package com.celdev.thirtyjava.model.scoring;

import com.celdev.thirtyjava.model.Dice;

import java.util.List;

public interface ScoreCounter {

    int calculateScore(List<Dice> dices, ScoringMode scoringMode) throws IllegalArgumentException;

    int calculateLowScore(List<Dice> dices) throws IllegalArgumentException;

}
