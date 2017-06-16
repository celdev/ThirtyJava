package com.celdev.thirtyjava.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RollerImplTest {

    @Test
    public void testRoller(){
        Roller roller = new RollerImpl();
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < 6) {
            int roll = roller.rollDice();
            numbers.add(roll);
            assertTrue(roll > 0 && roll <= 6);
        }
        assertEquals(Arrays.toString(numbers.toArray()), "[1, 2, 3, 4, 5, 6]");
    }
}