package com.celdev.thirtyjava.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.celdev.thirtyjava.helpers.Utils;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.DiceFactory;
import com.celdev.thirtyjava.model.DiceFactoryImpl;
import com.celdev.thirtyjava.model.Roller;
import com.celdev.thirtyjava.model.RollerImpl;

public class DiceCheckbox extends android.support.v7.widget.AppCompatCheckBox {

    private DiceCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHeight((int) Utils.convertDipToPixel(128,context));
        setWidth((int) Utils.convertDipToPixel(128,context));
    }

    public DiceCheckbox(Context context) {
        this(context,null,-1);
    }

    public DiceCheckbox(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    private static final int MAX_ROLLS = 30;

    private Roller roller = new RollerImpl();
    private Dice dice = new DiceFactoryImpl(roller).createDice();
    private DiceViewState viewState = DiceViewState.NOT_ROLLED;
    private DiceViewTranslator translator = new DiceViewTranslatorImpl();


    public void setState(DiceViewState viewState) {
        this.viewState = viewState;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        switch (viewState) {
            case ROLLING:
                drawAsRolling(canvas);
                break;
            default:
                drawDice(canvas);
        }
    }

    private void drawDice(Canvas canvas) {
        int drawable = translator.diceValueToDrawable(dice, viewState);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), drawable), 0, 0, new Paint());
    }

    private int rollDiceViewCounter = 0;

    private void drawAsRolling(Canvas canvas) {
        rollDiceViewCounter++;
        dice.roll(roller);
        int drawableId = translator.diceValueToDrawable(dice, viewState);
        Bitmap diceImage = BitmapFactory.decodeResource(getResources(), drawableId);
        if (diceImage != null) {
            canvas.drawBitmap(diceImage, 0, 0, new Paint());
        }
        if (rollDiceViewCounter >= MAX_ROLLS) {
            rollDiceViewCounter = 0;
            setState(DiceViewState.HAS_VALUE);
        }
    }


}
