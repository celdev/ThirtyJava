package com.celdev.thirtyjava.gameactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.celdev.thirtyjava.R;
import com.celdev.thirtyjava.gameactivity.GameActivityMVP.GameRepository;
import com.celdev.thirtyjava.model.Constants;
import com.celdev.thirtyjava.model.GameScoring;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.result_low)
    TextView low;
    @BindView(R.id.result_4)
    TextView resultFour;
    @BindView(R.id.result_5)
    TextView resultFive;
    @BindView(R.id.result_6)
    TextView resultSix;
    @BindView(R.id.result_7)
    TextView resultSeven;
    @BindView(R.id.result_8)
    TextView resultEight;
    @BindView(R.id.result_9)
    TextView resultNine;
    @BindView(R.id.result_10)
    TextView resultTen;
    @BindView(R.id.result_11)
    TextView resultEleven;
    @BindView(R.id.result_12)
    TextView resultTwelve;
    @BindView(R.id.total_score_text)
    TextView totalScoreText;
    @BindView(R.id.start_new_game_button)
    Button startNewGameButton;

    private GameRepository gameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        gameRepository = new GameRepositoryImpl(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d("tag", "onCreate: budle is not null");
            gameRepository.injectGameScorings((GameScoring[])bundle.getSerializable(Constants.GAME_SCORINGS));
        } else {
            Log.d("tag", "onCreate: bundle is null");
        }
    }

    @OnClick(R.id.start_new_game_button)
    public void startNewGame(View view) {
        gameRepository.newGame();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLatestScores(gameRepository.getGameScorings());
    }

    private void showLatestScores(GameScoring[] scorings) {
        int total = 0;
        for (GameScoring scoring : scorings) {
            total += scoring.getScore();
            switch (scoring.getScoringMode()) {
                case LOW:
                    low.setText(scoring.getScoreAsString());
                    break;
                case FOUR:
                    resultFour.setText(scoring.getScoreAsString());
                    break;
                case FIVE:
                    resultFive.setText(scoring.getScoreAsString());
                    break;
                case SIX:
                    resultSix.setText(scoring.getScoreAsString());
                    break;
                case SEVEN:
                    resultSeven.setText(scoring.getScoreAsString());
                    break;
                case EIGHT:
                    resultEight.setText(scoring.getScoreAsString());
                    break;
                case NINE:
                    resultNine.setText(scoring.getScoreAsString());
                    break;
                case TEN:
                    resultTen.setText(scoring.getScoreAsString());
                    break;
                case ELEVEN:
                    resultEleven.setText(scoring.getScoreAsString());
                    break;
                case TWELEVE:
                    resultTwelve.setText(scoring.getScoreAsString());
                    break;
            }
        }
        totalScoreText.setText(getString(R.string.total_score, total));
    }
}
