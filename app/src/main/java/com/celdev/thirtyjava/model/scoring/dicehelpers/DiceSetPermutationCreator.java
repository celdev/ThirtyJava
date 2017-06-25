package com.celdev.thirtyjava.model.scoring.dicehelpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiceSetPermutationCreator {

    private DiceSet diceSet;

    private Set<DiceSet> permutations = new HashSet<>();

    public DiceSetPermutationCreator(DiceSet diceSet) {
        this.diceSet = diceSet;
    }

    public DiceSetPermutationCreator createPermutations() {


        return this;
    }

    private static class Permuter {

        /** Generates all the permutations of the string passed as a
         *  parameter by recursively separating the last char of the string
         *  and recursively inserting it in all possible points in the
         *  rest of the string
         * */
        private static List<String> permutation(String s) {
            List<String> result = new ArrayList<>();
            if (s.length() == 1) {
                result.add(s);
            } else if (s.length() > 1) {
                String last = s.substring(s.length() - 1);
                String rest = s.substring(0, s.length() - 1);
                result = merge(permutation(rest), last);
            }
            return result;
        }

        /** Merges the String c-parameter into every item in the list-List
         *  in every possible place
         *  so if the List contains AB and BA and the String c = C
         *  then the result which this method returns will contain
         *  ABC, ACB, CAB, BAC, BCA, CBA
         * */
        private static List<String> merge(List<String> basesToAddLastChar, String c) {
            List<String> result = new ArrayList<>();
            for (String s : basesToAddLastChar) {
                for (int i = 0; i <= s.length(); i++) {
                    String ps = new StringBuffer(s).insert(i, c).toString();
                    result.add(ps);
                }
            }
            return result;
        }
    }
}
