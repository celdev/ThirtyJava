package com.celdev.thirtyjava.gameactivity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.celdev.thirtyjava.R;
import com.celdev.thirtyjava.model.scoring.ScoringMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ScoringModeViewController {

    @BindView(R.id.scoring_state_table)
    TableLayout scoringStateTable;

    @BindView(R.id.scoring_state_text_low)
    TextView low;
    @BindView(R.id.scoring_state_text_4)
    TextView four;
    @BindView(R.id.scoring_state_text_5)
    TextView five;
    @BindView(R.id.scoring_state_text_6)
    TextView six;
    @BindView(R.id.scoring_state_text_7)
    TextView seven;
    @BindView(R.id.scoring_state_text_8)
    TextView eight;
    @BindView(R.id.scoring_state_text_9)
    TextView nine;
    @BindView(R.id.scoring_state_text_10)
    TextView ten;
    @BindView(R.id.scoring_state_text_11)
    TextView eleven;
    @BindView(R.id.scoring_state_text_12)
    TextView twelve;



    ScoringModeViewController(Activity activity) {
        ButterKnife.bind(this, activity);
        scoringStateTable.setVisibility(View.VISIBLE);
    }

    void hideScoringModeState() {
        scoringStateTable.setVisibility(View.INVISIBLE);
    }

    void showScoringModeState() {
        scoringStateTable.setVisibility(View.VISIBLE);
    }


    void injectAvailableScoringModes(ScoringMode[] availableScoringModes) {
        List<ScoringMode> all = Arrays.asList(ScoringMode.values());
        List<ScoringMode> available = new ArrayList<>(Arrays.asList(availableScoringModes));
        for (ScoringMode scoringMode : all) {
            if (!available.contains(scoringMode)) {
                removeScoringMode(scoringMode);
            }
        }
    }

    void removeScoringMode(ScoringMode scoringMode) {
        switch (scoringMode) {
            case LOW:
                low.setVisibility(View.INVISIBLE);
                break;
            case FOUR:
                four.setVisibility(View.INVISIBLE);
                break;
            case FIVE:
                five.setVisibility(View.INVISIBLE);
                break;
            case SIX:
                six.setVisibility(View.INVISIBLE);
                break;
            case SEVEN:
                seven.setVisibility(View.INVISIBLE);
                break;
            case EIGHT:
                eight.setVisibility(View.INVISIBLE);
                break;
            case NINE:
                nine.setVisibility(View.INVISIBLE);
                break;
            case TEN:
                ten.setVisibility(View.INVISIBLE);
                break;
            case ELEVEN:
                eleven.setVisibility(View.INVISIBLE);
                break;
            case TWELEVE:
                twelve.setVisibility(View.INVISIBLE);
                break;
        }
    }
}