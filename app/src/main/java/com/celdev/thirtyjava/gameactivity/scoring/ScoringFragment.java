package com.celdev.thirtyjava.gameactivity.scoring;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.celdev.thirtyjava.R;
import com.celdev.thirtyjava.gameactivity.GameActivityMVP;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.view.DiceViewState;
import com.celdev.thirtyjava.view.ScoringDiceCheckbox;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoringFragment extends DialogFragment implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    @BindViews({R.id.scoring_dice_1,R.id.scoring_dice_2,R.id.scoring_dice_3,
            R.id.scoring_dice_4,R.id.scoring_dice_5,R.id.scoring_dice_6,})
    List<ScoringDiceCheckbox> diceCheckboxes;

    @BindView(R.id.finish_set_value_button)
    Button finishSetScoreButton;

    @BindView(R.id.choose_dice_button)
    Button makeDiceCombinationButton;

    @BindView(R.id.choiceSpinner)
    Spinner choiceSpinner;

    @BindView(R.id.total_score_text)
    TextView totalScoreText;

    @BindView(R.id.marked_score_text)
    TextView markedScoreText;

    private int totalScore = 0;

    private GetDiceCallback diceCallback;
    private GameActivityMVP.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scoring, container, false);
        ButterKnife.bind(this, view);
        ArrayAdapter<CharSequence> scoringAdapter = ArrayAdapter.createFromResource(getContext(), R.array.scoring_choices, android.R.layout.simple_spinner_item);
        scoringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choiceSpinner.setAdapter(scoringAdapter);
        choiceSpinner.setOnItemSelectedListener(this);
        for (ScoringDiceCheckbox sdcb : diceCheckboxes) {
            sdcb.setOnCheckedChangeListener(this);
        }
        injectDices(diceCallback.getDices());
        return view;
    }


    public void injectGetDiceCallbackAndPresenter(GetDiceCallback getDiceCallback, GameActivityMVP.Presenter presenter) {
        this.diceCallback = getDiceCallback;
        this.presenter = presenter;
    }

    private void injectDices(List<Dice> dices) {
        for(int i = 0; i < dices.size(); i++) {
            Dice dice = dices.get(i);
            Log.d("scoring", "injectDices: index = " +  i + " dice value = " + dice.getValue());
            diceCheckboxes.get(i).injectDice(dice);
            diceCheckboxes.get(i).setState(DiceViewState.NOT_ROLLED);
        }
    }




    @OnClick(R.id.finish_set_value_button)
    public void finishSetScoreButton(View view) {
        this.dismiss();
    }



    public ScoringFragment() {
        // Required empty public constructor
    }

    public static ScoringFragment newInstance() {
        return new ScoringFragment();
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            calculateLowScore();
        } else {
            calculateScore(position + 3);
        }

    }

    private void calculateScore(int i) {
        //TODO
    }

    private void calculateLowScore() {
        int score = 0;
        for (ScoringDiceCheckbox diceCheckbox : diceCheckboxes) {
            int diceValue = diceCheckbox.getDice().getValue();
            if (diceValue <= 3) {
                score += diceValue;
            }
        }
        totalScore = score;
        updateTotalScore();
    }

    private void updateTotalScore() {
        totalScoreText.setText(getString(R.string.total_score, totalScore));
        markedScoreText.setText("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            ScoringDiceCheckbox sdcb = (ScoringDiceCheckbox) buttonView;
            sdcb.setState(DiceViewState.MAKE_DICE_COMBINATION);
        }

        reCalculateScore();
    }

    private void reCalculateScore() {
        int score = 0;
        for (ScoringDiceCheckbox diceCheckbox : diceCheckboxes) {
            if (diceCheckbox.isSaveCombinationState()) {
                score += diceCheckbox.getDice().getValue();
            }
        }

    }
}
