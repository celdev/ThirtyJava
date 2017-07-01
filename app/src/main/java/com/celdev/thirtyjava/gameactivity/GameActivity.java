package com.celdev.thirtyjava.gameactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** This class is the Activity for playing the game
 *  This class implements the View functionality which is responsible for
 *  altering the view elements, i.e. showing or hiding a button
 *
 *  This activity is made up of two groups of components
 *  group 1:    6 dice
 *  group 2:    button for rolling the dices
 *              table for showing which types of scores to set that isn't set already
 *              spinner for choosing which score type to set
 *              button for setting the dice to a certain score type
 *      All elements of the second group isn't present at the same time
 *      The visibility of the view elements can be divided into 2 groups
 *
 *      Rolling mode:   shows the "roll button" and the table with available scoring types
 *      Set Score mode: show the spinner with available scoring types and the
 *                      set score button. The rolling button is disabled and
 *                      the table with available scoring types isn't visible
 *
 *  The Presenter for this activity (the class PresenterImpl) is responsible for
 *  much of the control of the flow of actions in the Activity
 * */
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


    /** Creates the layout from the layout-file (landscape or vertical)
     *  initializes the ButterKnife-plugin (which saves me from a lot of boring findViewById(int)
     *  Sets the listener of the spinner to this (which implements OnItemSelectedListener)
     *  and initializes the presenter and the controller of the scoring type table.
     * */
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

    /** This method is executed when the roll button is clicked
     *  Disables the roll button so that the user doesn't accidentally roll multiple times
     *  calls the rollDice method of the DiceCheckbox class which rolls the dice if the
     *  dice's state isn't set to save the dice value.
     *
     *  Then tells the presenter that a newThrow has been made
     * */
    @OnClick(R.id.rollBtn)
    public void rollDices(View view) {
        rollButton.setEnabled(false);
        for (DiceCheckbox diceCheckbox : diceCheckboxes) {
            diceCheckbox.rollDice();
        }
        presenter.newThrow();
    }

    /** Overrides the back-button functionality which
     *  prevents the activity from being killed when the button is pressed
     *  and instead makes the button have the same behaviour as the home button
     * */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /** This method is called when the set score button is clicked
     *
     *  When the user picks a scoring mode from the spinner the
     *  instance variable chosenScoringMode is set the chosen ScoringMode
     *
     *  hides some views that isn't needed anymore when the score have been saved
     *  tells the presenter that a round has been finished
     *
     *  removes the chosen scoring mode from the scoring mode table
     *  sets the GameViewState to Rolling
     * */
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

    /** This method is called when the app is restored
     *  the state of the application is stored in a GameApplicationState-object which holds
     *  all necessary information for recreating the state of the application
     *  For more information see the GameApplicationState class
     * */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            GameApplicationState gameState = (GameApplicationState) savedInstanceState.getSerializable(Constants.BUNDLE_GAME_STATE);
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


    /** This method is called after the application is being stopped
     *  This method tells the presenter to create the GameApplicationState-object which is then
     *  saved with the gamestate-key in the Bundle
     *  which can then be used when restoring the activities state
     * */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        GameApplicationState gameApplicationState = presenter.createGameApplicationState(getDiceCheckboxViewStates(), gameViewState);
        outState.putSerializable(Constants.BUNDLE_GAME_STATE, gameApplicationState);
    }

    /** Creates and returns a list of the DiceCheckboxViewState of all the DiceCheckboxes in the activity
     * */
    private List<DiceCheckboxViewState> getDiceCheckboxViewStates() {
        List<DiceCheckboxViewState> diceCheckboxViewStates = new ArrayList<>();
        for (DiceCheckbox diceCheckbox : diceCheckboxes) {
            diceCheckboxViewStates.add(new DiceCheckboxViewState(diceCheckbox.getDice(), diceCheckbox.getViewState()));
        }
        return diceCheckboxViewStates;
    }

    /** Creates and return a list of all the Dice of the DiceCheckboxes in the activity
     * */
    private List<Dice> getDices() {
        List<Dice> dices = new ArrayList<>();
        for (DiceCheckbox diceCheckbox : diceCheckboxes) {
            dices.add(diceCheckbox.getDice());
        }
        return dices;
    }

    /** This method is called when the presenter
     *  has determined that a new round is to be played
     *
     *  Sets the state of the DiceCheckboxes to NOT_ROLLED (white color dice)
     *  Also calls newThrow which sets the state of the View so that the
     *  dices can be rolled
     * */
    @Override
    public void newRound() {
        for (DiceCheckbox diceCheckbox : diceCheckboxes) {
            diceCheckbox.setState(DiceViewState.NOT_ROLLED);
        }
        scoringModeViewController.showScoringModeState();
        newThrow();
    }

    /** This method is called when the state of the view
     *  should be set to allow for the user to roll the dices
     *  Also updates the values of the GUI (the round and throw count)
     * */
    @Override
    public void newThrow() {
        rollButton.setEnabled(true);
        updateGUI();
    }

    /** This method is called when the view state of the activity should be
     *  set to allow the user to choose a scoring type and save the scores of the dice
     *  Hides the scoring table and shows the spinner and the set score button
     *  sets the gameViewState variable to SetScore which is used to
     *  determind which state the activity should be in if the activity is restored from
     *  OnRestoreInstanceState()
     * */
    @Override
    public void finishRound() {
        populateAndShowScoringSpinner();
        continueButton.setVisibility(View.VISIBLE);
        scoringModeViewController.hideScoringModeState();
        gameViewState = GameViewState.SET_SCORE;
    }

    /** This method is called when the game is finished
     *  and the results should be shown
     *  Starts the ResultActivity with the bundle contaning the
     *  recorded scores that the player has achieved
     *
     *  destroyes this instance of the activity by calling finish()
     *  since there isn't any reason for the player to be able to go back
     *  to the game after the game is finished
     * */
    @Override
    public void finishGame() {
        Intent intent = new Intent(this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.GAME_SCORINGS, presenter.getScorings());
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    /** populates the spinner with the available scoring modes and sets the spinner to be visible
     * */
    private void populateAndShowScoringSpinner() {
        scoringSpinner.setAdapter(new ScoringModeArrayAdapter(this, android.R.layout.simple_spinner_item, presenter.getAvailableScoringModes()));
        scoringSpinner.setVisibility(View.VISIBLE);
    }

    /** Updates the round number and the throw count GUI text
     * */
    private void updateGUI() {
        roundTextView.setText(getString(R.string.round_number,presenter.getRoundState()));
        throwTextView.setText(getString(R.string.throw_number,presenter.getThrowState()));
    }

    /** This method is called when the user makes a choice in the Spinner
     *  sets the chosenScoringMode instance variable to the chosen mode so that
     *  the correct mode is used when calculating the score when clicking the
     *  set score button (which this method also enables)
     * */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        chosenScoringMode = (ScoringMode) scoringSpinner.getItemAtPosition(position);
        continueButton.setEnabled(true);
    }

    /** This method must be here since it is part of the OnItemSelected interface which this class
     *  implements
     *
     *  does nothing.
     * */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
