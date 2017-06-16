package com.celdev.thirtyjava.view;

import com.celdev.thirtyjava.R;
import com.celdev.thirtyjava.model.DiceColor;

public class DiceDrawableRepository {

    public enum GRAY_DICE {
        ONE(R.drawable.grey1),
        TWO(R.drawable.grey2),
        THREE(R.drawable.grey3),
        FOUR(R.drawable.grey4),
        FIVE(R.drawable.grey5),
        SIX(R.drawable.grey6);

        private int drawable;

        GRAY_DICE(int drawable) {
            this.drawable = drawable;
        }
    }

    public enum WHITE_DICE {
        ONE(R.drawable.white1),
        TWO(R.drawable.white2),
        THREE(R.drawable.white3),
        FOUR(R.drawable.white4),
        FIVE(R.drawable.white5),
        SIX(R.drawable.white6);

        private int drawable;

        WHITE_DICE(int drawable) {
            this.drawable = drawable;
        }
    }

    public enum RED_DICE {
        ONE(R.drawable.red1),
        TWO(R.drawable.red2),
        THREE(R.drawable.red3),
        FOUR(R.drawable.red4),
        FIVE(R.drawable.red5),
        SIX(R.drawable.red6);

        private int drawable;

        RED_DICE(int drawable) {
            this.drawable = drawable;
        }
    }

    public static int valueAndColorToDrawable(int value, DiceColor diceColor) throws IllegalArgumentException {
        switch (diceColor) {
            case RED:
                return RED_DICE.values()[value - 1].drawable;
            case GRAY:
                return GRAY_DICE.values()[value - 1].drawable;
            case WHITE:
            default:
                return WHITE_DICE.values()[value - 1].drawable;
        }
    }
}
