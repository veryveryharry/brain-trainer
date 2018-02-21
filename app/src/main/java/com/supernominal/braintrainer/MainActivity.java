package com.supernominal.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {

    final int GAME_DURATION_SECONDS = 30;

    int correctProblems = 0;
    int attemptedProblems = 0;
    int correctButton;
    int summand1;
    int summand2;

    TextView scoreTextView;
    TextView problemTextView;
    GridLayout gridLayout;
    Button goButton;
    ConstraintLayout gameConstraintLayout;
    TextView timerTextView;
    TextView finalScoreTextView;

    public void startGame(View view ) {

        goButton.setVisibility(View.INVISIBLE);
        gameConstraintLayout.setVisibility(View.VISIBLE);
        finalScoreTextView.setVisibility(View.INVISIBLE);

        new CountDownTimer(GAME_DURATION_SECONDS * 1000 + 100, 1000) {
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l/1000) + "s");
            }

            @Override
            public void onFinish() {
                gameConstraintLayout.setVisibility(View.INVISIBLE);
                goButton.setText("Play again!");
                goButton.setVisibility(View.VISIBLE);
                finalScoreTextView.setVisibility(View.VISIBLE);
                finalScoreTextView.setText(scoreTextView.getText());
                correctProblems = 0;
                attemptedProblems = 0;
                updateScore();
            }
        }.start();

        generateQuestion();
    }

    public void checkAnswer(View view) {

        Button button = (Button) view;
        String tag = (String) button.getTag();

        if (Integer.parseInt(tag) == correctButton) {
            correctProblems++;
        }
        attemptedProblems++;

        updateScore();

        generateQuestion();

    }

    public void generateQuestion() {

        summand1 = getRandomNumber(1,99);
        summand2 = getRandomNumber(1,99);
        problemTextView.setText(String.valueOf(summand1) + " + " + String.valueOf(summand2));

        int buttonCount = gridLayout.getChildCount();
        correctButton = getRandomNumber(0,buttonCount - 1);

        ArrayList<Integer> answersArray = new ArrayList<Integer>(asList(0,0,0,0));
        answersArray.set(correctButton, summand1 + summand2);

        for (int i = 0; i < buttonCount; i++) {

            Button child = (Button) gridLayout.getChildAt(i);

            if (i != correctButton) {

                Integer newRandom = getRandomNumber(2, 198);
                while (Arrays.asList(answersArray).contains(newRandom)) {
                    newRandom = getRandomNumber(2, 198);
                }

                answersArray.set(i, newRandom);
                child.setText(newRandom.toString());

            } else {

                child.setText(answersArray.get(i).toString());
            }
        }
    }

    public void updateScore() {

        scoreTextView.setText(String.valueOf(correctProblems) + "/" + String.valueOf(attemptedProblems));

    }

    public int getRandomNumber(int min, int max) {

        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        problemTextView = (TextView) findViewById(R.id.problemTextView);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        goButton = (Button) findViewById(R.id.goButton);
        gameConstraintLayout = (ConstraintLayout) findViewById(R.id.gameConstraintLayout);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        finalScoreTextView = (TextView) findViewById((R.id.finaScoreTextView));

    }
}
