package com.example.multiplayertictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class GameSelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton singlePlayerButton;
    private ImageButton offlineMultiPlayerButton;
    private ImageButton onlineMultiPlayerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        //  hide the actionbar
//        getSupportActionBar().hide();

        singlePlayerButton = (ImageButton) findViewById(R.id.gameSinglePlayerButton);
        singlePlayerButton.setOnClickListener(this);

        offlineMultiPlayerButton = (ImageButton) findViewById(R.id.gameOfflineMultiPlayerButton);
        offlineMultiPlayerButton.setOnClickListener(this);

        onlineMultiPlayerButton = (ImageButton) findViewById(R.id.gameOnlineMultiPlayerButton);
        onlineMultiPlayerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == singlePlayerButton.getId()) {
            //  single playing the game
            singlePlayerFunction();

        } else if (v.getId() == offlineMultiPlayerButton.getId()) {
            Toast.makeText(this, "This feature is under construction", Toast.LENGTH_SHORT).show();
//            offlineMultiplayerFunction();

        } else if (v.getId() == onlineMultiPlayerButton.getId()) {
            Toast.makeText(this, "This feature is under construction", Toast.LENGTH_SHORT).show();
//            onlineMultiPlayerFunction();
        }
    }

    private void singlePlayerFunction() {
        Intent intent = new Intent(GameSelectionActivity.this, GameLocalActivity.class);
        startActivity(intent);
    }

    private void offlineMultiplayerFunction() {

    }

    private void onlineMultiPlayerFunction() {

    }
}
