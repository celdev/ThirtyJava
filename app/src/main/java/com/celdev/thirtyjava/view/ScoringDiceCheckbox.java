package com.celdev.thirtyjava.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.celdev.thirtyjava.model.Dice;

public class ScoringDiceCheckbox extends DiceCheckbox {
    public ScoringDiceCheckbox(Context context) {
        super(context);
    }

    public ScoringDiceCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setState(DiceViewState viewState) {
        super.setState(viewState);
    }

    @Override
    public void injectDice(Dice dice) {
        super.injectDice(dice);
    }

    public boolean isSaveCombinationState() {
        return super.getViewState().equals(DiceViewState.MAKE_DICE_COMBINATION);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void rollDice() {
        return;
    }

    @Override
    public boolean isSaveValue() {
        return super.isSaveValue();
    }

    @Override
    public void onNewThrow() {
        return;
    }

    @Override
    public void onLastThrow() {
        return;
    }

    @Override
    public void onNewRound() {
        return;
    }
}
