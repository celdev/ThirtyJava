package com.celdev.thirtyjava.gameactivity;

import android.app.Application;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.celdev.thirtyjava.R;

import com.celdev.thirtyjava.gameactivity.GameApplicationState.GameViewState;
import com.celdev.thirtyjava.helpers.DiceCheckboxViewState;
import com.celdev.thirtyjava.model.Constants;
import com.celdev.thirtyjava.model.Dice;
import com.celdev.thirtyjava.model.scoring.ScoringMode;
import com.celdev.thirtyjava.view.DiceCheckbox;
import com.celdev.thirtyjava.view.DiceViewState;
import com.celdev.thirtyjava.view.ScoringModeArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity implements GameActivityMVP.View, AdapterView.OnItemSelectedListener {

    @BindViews({R.id.dice_1, R.id.dice_2, R.id.dice_3, R.id.dice_4, R.id.dice_5, R.id.dice_6})
    List<DiceCheckbox> diceCheckboxes;

    @BindView(R.id.throw_text)
    TextView throwTextView;

    @BindView(R.id.round_text)
    TextView roundTextView;

    @BindView(R.id.continueRound)
    Button continueButton;

    @BindView(R.id.rollBtn)
    Button rollButton;

    @BindView(R.id.scoringChoiceSpinner)
    Spinner scoringSpinner;

    private GameActivityMVP.Presenter presenter;

    private ScoringMode chosenScoringMode = null;
    private ScoringModeViewController scoringModeViewController;

    private GameViewState gameViewState = GameViewState.ROLL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        presenter = new PresenterImpl(this, new GameRepositoryImpl(this));
        updateGUI();
        scoringSpinner.setOnItemSelectedListener(this);
        scoringModeViewController = new ScoringModeViewController(this);
    }

    @OnClick(R.id.rollBtn)
    public void rollDices(View view) {
        rollButton.setEnabled(false);
        for (DiceCheckbox diceCheckbox : diceCheckboxes) {
            diceCheckbox.rollDice();
        }
        presenter.newThrow();
    }

    @OnClick(R.id.continueRound)
    public void continueGame(View view) {
        if (chosenScoringMode == null) {
            Toast.makeText(this, R.string.chooseScoringMode, Toast.LENGTH_SHORT).show();
            return;
        }
        continueButton.setVisibility(View.INVISIBLE);
        scoringSpinner.setVisibility(View.INVISIBLE);
        presenter.saveScore(chosenScoringMode, getDices());
        presenter.finishRound();
        scoringModeViewController.removeScoringMode(chosenScoringMode);
        chosenScoringMode = null;
        continueButton.setEnabled(false);
        gameViewState = GameViewState.ROLL;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            GameApplicationState gameState = (GameApplicationState) savedInstanceState.getSerializable("gamestate");
            if (gameState != null) {
                List<DiceCheckboxViewState> diceCheckboxViewStateList = gameState.getDiceCheckboxViewStateList();
                for (int i = 0; i < diceCheckboxViewStateList.size(); i++) {
                    diceCheckboxes.get(i).injectDice(diceCheckboxViewStateList.get(i).getDice());
                    diceCheckboxes.get(i).setState(diceCheckboxViewStateList.get(i).getDiceViewState());
                }
                presenter.injectGameState(gameState);
                scoringModeViewController.injectAvailableScoringModes(gameState.getAvailableScoringModes());
                switch (gameState.getGameViewState()) {
                    case ROLL:
                        newThrow();
                        break;
                    case SET_SCORE:
                        finishRound();
                        break;
                }
            }
        }
    }


    @Override
    public void setState(List<Dice> dices, List<DiceViewState> diceViewStates, ScoringMode[] availableScoringModes) {
        for (int i = 0; i < dices.size(); i++) {
            DiceCheckbox diceCheckbox = diceCheckboxes.get(i);
            diceCheckbox.injectDice(dices.get(i));
            diceCheckbox.setState(diceViewStates.get(i));
        }
        scoringModeViewController.injectAvailableScoringModes(availableScoringModes);
        updateGUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        GameApplicationState gameApplicationState = presenter.createGameApplicationState(getDiceCheckboxViewStates(), gameViewState);
        outState.putSerializable("gamestate", gameApplicationState);
    }

    private List<DiceCheckboxViewState> getDiceCheckboxViewStates() {
        List<DiceCheckboxViewState> diceCheckboxViewStates = new ArrayList<>();
        for (DiceCheckbox diceCheckbox : diceCheckboxes) {
            diceCheckboxViewStates.add(new DiceCheckboxViewState(diceCheckbox.getDice(), diceCheckbox.getViewState()));
        }
        return diceCheckboxViewStates;
    }

    private List<Dice> getDices() {
        List<Dice> dices = new ArrayList<>();
        for (DiceCheckbox diceCheckbox : diceCheckboxes) {
            dices.add(diceCheckbox.getDice());
        }
        return dices;
    }

    @Override
    public void newRound() {
        for (DiceCheckbox diceCheckbox : diceCheckboxes) {
            diceCheckbox.setState(DiceViewState.NOT_ROLLED);
        }
        scoringModeViewController.showScoringModeState();
        newThrow();
        updateGUI();
    }

    @Override
    public void firstThrow() {
        rollButton.setEnabled(true);
        updateGUI();
    }

    @Override
    public void newThrow() {
        rollButton.setEnabled(true);
        updateGUI();
    }

    @Override
    public void finishRound() {
        Log.d("tag", "finishRound: ");
        populateAndShowScoringSpinner();
        continueButton.setVisibility(View.VISIBLE);
        scoringModeViewController.hideScoringModeState();
        gameViewState = GameViewState.SET_SCORE;
    }



    @Override
    public void finishGame() {
        Intent intent = new Intent(this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.GAME_SCORINGS, presenter.getScorings());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void populateAndShowScoringSpinner() {
        scoringSpinner.setAdapter(new ScoringModeArrayAdapter(this, android.R.layout.simple_spinner_item, presenter.getAvailableScoringModes()));
        scoringSpinner.setVisibility(View.VISIBLE);
    }

    private void updateGUI() {
        roundTextView.setText(getString(R.string.round_number,presenter.getRoundState()));
        throwTextView.setText(getString(R.string.throw_number,presenter.getThrowState()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ScoringMode scoringMode = (ScoringMode) scoringSpinner.getItemAtPosition(position);
        Log.d("tag", scoringMode.name());
        chosenScoringMode = scoringMode;
        continueButton.setEnabled(true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
