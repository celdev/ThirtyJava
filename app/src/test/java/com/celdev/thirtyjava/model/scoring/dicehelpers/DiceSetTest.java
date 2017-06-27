package com.celdev.thirtyjava.model.scoring.dicehelpers;

import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.scoring.DiceSetCounter;
import com.celdev.thirtyjava.model.scoring.ScoringMode;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class DiceSetTest {

    @Test
    public void testDiceToString() {
        assertEquals("ABCDEF", createDiceSetWithValues(1, 2, 3, 4, 5, 6).getDiceSetAsCharString());
        assertEquals("AAAAAA",createDiceSetWithValues(1,1,1,1,1,1).getDiceSetAsCharString());
        assertEquals("ABABAB",createDiceSetWithValues(1,2,1,2,1,2).getDiceSetAsCharString());
    }

    @Test
    public void testDiceToStringError() {
        assertNotEquals("ABCDEF", createDiceSetWithValues(1, 1, 1, 1, 1, 1).getDiceSetAsCharString());
        assertNotEquals("AAAAAA",createDiceSetWithValues(1,1,2,1,1,1).getDiceSetAsCharString());
        assertNotEquals("ABABAB",createDiceSetWithValues(1,2,1,3,1,2).getDiceSetAsCharString());
    }

    @Test
    public void testPermutations() {
        Set<DiceSet> diceSets = new DiceSetPermutationCreator(createDiceSetWithValues(1, 2, 3, 4, 5, 6)).createAndGetPermutations();
        assertEquals(diceSets.size(), 720);
        Set<DiceSet> diceSets2 = new DiceSetPermutationCreator(createDiceSetWithValues(1, 1, 3, 4, 5, 6)).createAndGetPermutations();
        assertEquals(diceSets2.size(), 360);
        Set<DiceSet> diceSets3 = new DiceSetPermutationCreator(createDiceSetWithValues(1, 1, 1, 4, 5, 6)).createAndGetPermutations();
        assertEquals(diceSets3.size(), 120);
        Set<DiceSet> diceSets4 = new DiceSetPermutationCreator(createDiceSetWithValues(1, 1, 1, 1, 5, 6)).createAndGetPermutations();
        assertEquals(diceSets4.size(), 30);
        Set<DiceSet> diceSets5 = new DiceSetPermutationCreator(createDiceSetWithValues(1, 1, 1, 1, 1, 6)).createAndGetPermutations();
        assertEquals(diceSets5.size(), 6);
        Set<DiceSet> diceSets6 = new DiceSetPermutationCreator(createDiceSetWithValues(1, 1, 1, 1, 1, 1)).createAndGetPermutations();
        assertEquals(diceSets6.size(), 1);
    }

    @Test
    public void testScoring() {
        Set<DiceSet> diceSets = new DiceSetPermutationCreator(createDiceSetWithValues(1, 2, 3, 4, 5, 6)).createAndGetPermutations();
        DiceSetCounter diceSetCounter = new DiceSetCounter();
        int maxScoreOfAllDiceSetPermutations = diceSetCounter.getMaxScoreOfAllDiceSetPermutations(diceSets, ScoringMode.SIX);
        assertEquals(18, maxScoreOfAllDiceSetPermutations);
        Set<DiceSet> diceSets2 = new DiceSetPermutationCreator(createDiceSetWithValues(1, 1, 1, 2, 4, 4)).createAndGetPermutations();
        int maxScoreOfAllDiceSetPermutations1 = diceSetCounter.getMaxScoreOfAllDiceSetPermutations(diceSets2, ScoringMode.FIVE);
        assertEquals(10, maxScoreOfAllDiceSetPermutations1);
    }

    @Test
    public void testScoring2() {
        assertEquals(24, createDiceSetAndGetMaxScore(createDiceSetWithValues(2, 2, 4, 4, 6, 6), ScoringMode.TWELEVE));
        assertEquals(36, createDiceSetAndGetMaxScore(createDiceSetWithValues(6, 6, 6, 6, 6, 6), ScoringMode.TWELEVE));
        assertEquals(6, createDiceSetAndGetMaxScore(createDiceSetWithValues(1, 1, 1, 1, 1, 1), ScoringMode.SIX));
        assertEquals(0, createDiceSetAndGetMaxScore(createDiceSetWithValues(1, 1, 1, 1, 1, 1), ScoringMode.SEVEN));
    }

    private int createDiceSetAndGetMaxScore(DiceSet diceSet, ScoringMode scoringMode) {
        return new DiceSetCounter().getMaxScoreOfAllDiceSetPermutations(new DiceSetPermutationCreator(diceSet).createAndGetPermutations(), scoringMode);
    }


    private DiceSet createDiceSetWithValues(int dice1, int dice2, int dice3, int dice4, int dice5, int dice6) {
        return new DiceSet(
                createMinimalDice(dice1),
                createMinimalDice(dice2),
                createMinimalDice(dice3),
                createMinimalDice(dice4),
                createMinimalDice(dice5),
                createMinimalDice(dice6)
        );
    }

    private MinimalDice createMinimalDice(int value) {
        return new MinimalDice(new Dice(value));
    }

}