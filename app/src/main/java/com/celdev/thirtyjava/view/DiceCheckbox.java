package com.celdev.thirtyjava.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.celdev.thirtyjava.helpers.Utils;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.DiceFactoryImpl;
import com.celdev.thirtyjava.model.Roller;
import com.celdev.thirtyjava.model.RollerImpl;

public class DiceCheckbox extends android.support.v7.widget.AppCompatCheckBox implements DiceViewInterface, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String TAG = "dicecheckbox";

    private static final int DEF_STYLE_VALUE = 0;

    private boolean canChangeState = true;

    private DiceCheckbox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHeight((int) Utils.convertDipToPixel(89,context));
        setWidth((int) Utils.convertDipToPixel(89,context));
    }


    public DiceCheckbox(Context context) {
        this(context,null, DEF_STYLE_VALUE);
    }

    public DiceCheckbox(Context context, AttributeSet attrs) {
        this(context, attrs, DEF_STYLE_VALUE);
    }

    private static final int MAX_ROLLS = 30;

    private Roller roller = new RollerImpl();
    private Dice dice = new DiceFactoryImpl(roller).createDice();
    private DiceViewState viewState = DiceViewState.NOT_ROLLED;
    private DiceViewTranslator translator = new DiceViewTranslatorImpl();

    public DiceViewState getViewState() {
        return viewState;
    }

    public void setState(DiceViewState viewState) {
        if (canChangeState) {
            this.viewState = viewState;
            invalidate();
        }
    }

    public void injectDice(Dice dice) {
        this.dice = dice;
        invalidate();
    }

    /** Sets the Listeners for this DiceCheckbox
     *  so that the player can save the value of the dice
     **/
    private void setListeners() {
        setOnClickListener(this);
        setOnCheckedChangeListener(this);
    }

    /** Unsets the Listeners for this DiceCheckbox
     *  so that the player can't save the value of the dice while the
     *  dice is rolling
     * */
    private void unsetListeners() {
        setOnCheckedChangeListener(null);
        setOnClickListener(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (viewState) {
            case NOT_ROLLED:
                unsetListeners();
                drawDice(canvas);
                break;
            case ROLLING:
                unsetListeners();
                drawAsRolling(canvas);
                break;
            default:
                setListeners();
                drawDice(canvas);
        }
    }

    /** Sets the state Rolling unless the DiceViewState
     *  is set to SAVE_VALUE (in which case the dice
     *  shouldn't be rolled
     * */
    @Override
    public void rollDice() {
        if (viewState.equals(DiceViewState.SAVE_VALUE)) {
            return;
        } else {
            setState(DiceViewState.ROLLING);
        }
    }

    @Override
    public boolean isSaveValue() {
        return viewState.equals(DiceViewState.SAVE_VALUE);
    }

    /** Draws the current dice on the views Canvas
     *  which dice (color) is drawn depends on the DiceViewState
     *  the DiceCheckbox currently has.
     * */
    private void drawDice(Canvas canvas) {
        int drawable = translator.diceValueToDrawable(dice, viewState);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), drawable), 0, 0, new Paint());
    }


    /** Rolls the dice in this view MAX_ROLLS times
     *  and shows the value of the roll each time.
     *  When finished rolling the state is set to HAS_VALUE instead
     * */
    private int rollDiceViewCounter = 0;
    private void drawAsRolling(Canvas canvas) {
        rollDiceViewCounter++;
        dice.roll(roller);
        int drawableId = translator.diceValueToDrawable(dice, viewState);
        Bitmap diceImage = BitmapFactory.decodeResource(getResources(), drawableId);
        if (diceImage != null) {
            canvas.drawBitmap(diceImage, 0, 0, new Paint());
            invalidate();
        }
        if (rollDiceViewCounter >= MAX_ROLLS) {
            rollDiceViewCounter = 0;
            setState(DiceViewState.HAS_VALUE);
        }
    }
    
    @Override
    public Dice getDice() {
        return dice;
    }

    @Override
    public void setCantChangeState() {
        canChangeState = false;
    }

    @Override
    public void onNewThrow() {
        if (viewState.equals(DiceViewState.SAVE_VALUE)) {
            return;
        }
        setState(DiceViewState.ROLLING);
    }

    @Override
    public void onLastThrow() {
        setState(DiceViewState.NOT_ROLLED);
        canChangeState = false;
    }

    @Override
    public void onNewRound() {
        canChangeState = true;
        setState(DiceViewState.NOT_ROLLED);
    }

    /** Doesn't do anything but if not present
     *  the onCheckedChanged-listener below doesn't work
     * */
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "insideOnChanged");
        if (isChecked) {
            setState(DiceViewState.SAVE_VALUE);
        } else {
            setState(DiceViewState.HAS_VALUE);
        }
    }
}
