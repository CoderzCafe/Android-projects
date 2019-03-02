package com.example.multiplayertictactoe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class GameLocalActivity extends AppCompatActivity {

    private Menu menu;
    private int activePlayer = 0;   //  o is for O

    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};  //  2 means unplayed
    private int[][] winningLocaiton = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                                        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                                        {0, 4, 8}, {2, 4, 6}};
    private boolean gameOver = false;
    private TextView player1Score;
    private int score1 = 0;
    private TextView player2Score;
    private int score2 = 0;

    private LinearLayout winnerLayout;
    private ImageView winnerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_local);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        winnerLayout = (LinearLayout) findViewById(R.id.winnerWindowLayout);
//        winnerLayout.setVisibility(INVISIBLE);

        player1Score = (TextView) findViewById(R.id.gameScoreTextViewPlayer1);
        player2Score = (TextView) findViewById(R.id.gameScoreTextViewPlayer2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settingLeaderboardMenuItem) {
            Intent intent = new Intent(getApplicationContext(), LeaderboardActivity.class);
            startActivity(intent);
        } if (item.getItemId() == R.id.settingGamesettingMenuItem) {
            Intent intent = new Intent(getApplicationContext(), GameSettingActivity.class);
            startActivity(intent);
        } if (item.getItemId() == R.id.settingLogoutMenuItem) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    public void gameLogic(View view) {

        ImageView tappedView = (ImageView) view;
        int tappedLocation = Integer.parseInt(view.getTag().toString());

        if (gameState[tappedLocation] == 2 && !gameOver) {

            gameState[tappedLocation] = activePlayer;

            tappedView.setTranslationY(-300f);

            if (activePlayer == 0) {
                tappedView.setImageResource(R.drawable.p1);
                activePlayer = 1;
            } else if (activePlayer == 1) {
                tappedView.setImageResource(R.drawable.p2);
                activePlayer = 0;
            }

            tappedView.animate().translationYBy(300f).setDuration(500);
        }

        for (int[] winningPosition : winningLocaiton) {

            if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                && gameState[winningPosition[0]] != 2) {

                if (activePlayer == 0) {
                    Toast.makeText(getApplicationContext(), "X is winner", Toast.LENGTH_SHORT).show();
                } if (activePlayer == 1) {
                    Toast.makeText(getApplicationContext(), "O is winner", Toast.LENGTH_LONG).show();
                }

                gameOver = true;

            }
        }
    }
}
