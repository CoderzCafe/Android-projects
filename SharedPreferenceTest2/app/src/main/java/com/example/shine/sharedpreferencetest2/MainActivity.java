package com.example.shine.sharedpreferencetest2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView scoreBoard;
    private Button increaseButton, decreaseButton;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        scoreBoard = (TextView) findViewById(R.id.score);
        increaseButton = (Button) findViewById(R.id.increaseButton);

        if (loadTheScore() != 0) {
            scoreBoard.setText("Score :" +loadTheScore());
        }

        increaseButton.setOnClickListener(this);
        decreaseButton = (Button) findViewById(R.id.decreaseButton);
        decreaseButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.increaseButton) {
            score = score + 10;
            scoreBoard.setText("Score : " +score);
            saveTheScore(score);

        } if (v.getId() == R.id.decreaseButton) {
            score = score - 10;
            scoreBoard.setText("Score : " +score);
            saveTheScore(score);
        }
    }

    private void saveTheScore(int score) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("gameScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastScore", score);
        editor.commit();
    }

    private int loadTheScore() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("gameScore", Context.MODE_PRIVATE);
        int score = sharedPreferences.getInt("lastScore", 0);
        return score;
    }
}
