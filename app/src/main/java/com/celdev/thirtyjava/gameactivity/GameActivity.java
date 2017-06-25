package com.celdev.thirtyjava.gameactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.celdev.thirtyjava.R;

import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.view.DiceCheckbox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements GameActivityMVP.View {

    @BindViews({R.id.dice_1, R.id.dice_2, R.id.dice_3, R.id.dice_4, R.id.dice_5, R.id.dice_6})
    List<DiceCheckbox> diceCheckboxes;

    @BindView(R.id.throw_text)
    TextView throwTextView;

    @BindView(R.id.round_text)
    TextView roundTextView;

    @BindView(R.id.continueRound)
    Button continueRoundButton;

    @BindView(R.id.rollBtn)
    Button rollButton;

    private GameActivityMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        presenter = new PresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRoundText(presenter.getRoundCount());
        updateThrowText(presenter.getThrowCount());
    }

    @OnClick(R.id.rollBtn)
    public void rollDice(View view) {
        doDicePlay();
    }

    @OnClick(R.id.continueRound)
    public void continueRound(View view) {
        onFinishRound();
        doDicePlay();
    }



    @Override
    public void doDicePlay() {
        for (DiceCheckbox dcb : diceCheckboxes) {
            dcb.rollDice();
        }
        presenter.onDicePlay();
    }

    @Override
    public void newDiceThrow() {
        for (DiceCheckbox dcb : diceCheckboxes) {
            dcb.onNewThrow();
        }
    }

    @Override
    public void startNewRound() {
        continueRoundButton.setEnabled(false);
        rollButton.setEnabled(true);
    }

    @Override
    public void showRoundResults() {
        continueRoundButton.setEnabled(true);
        rollButton.setEnabled(false);
        for (DiceCheckbox dcb : diceCheckboxes) {
            dcb.onLastThrow();
        }
    }

    @Override
    public List<Dice> getDices() {
        List<Dice> dices = new ArrayList<>();
        for (DiceCheckbox diceCheckbox : diceCheckboxes) {
            dices.add(diceCheckbox.getDice());
            Log.d("gameacivity", "dice values: " + diceCheckbox.getDice().getValue());
        }
        return dices;
    }

    @Override
    public void onFinishRound() {
        presenter.onRoundFinish();
        for (DiceCheckbox dcb : diceCheckboxes) {
            dcb.onNewRound();
        }
    }

    @Override
    public void onFinishGame() {
        DEBUG_TOAST("finish game");
    }

    @Override
    public int getDiceValueSetCount() {
        int count = 0;
        for (DiceCheckbox dcb : diceCheckboxes) {
            if (dcb.isSaveValue()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void updateThrowText(int throwNumber) {
        throwTextView.setText(getString(R.string.throw_number,throwNumber));
    }

    @Override
    public void updateRoundText(int roundNumber) {
        roundTextView.setText(getString(R.string.round_number,roundNumber));
    }

    @Override
    public void DEBUG_TOAST(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
