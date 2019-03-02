package com.example.multiplayertictactoe;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginUserId;
    private EditText loginPassword;

    private Button  login;
    private ImageButton faceBookLogin;
    private ImageButton gmailLogin;
    private ImageButton instagramLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide the actionbar
//        getSupportActionBar().hide();

        loginUserId = (EditText) findViewById(R.id.loginUserIdTextView);
        loginPassword = (EditText) findViewById(R.id.loginUserPassword);

        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);

        faceBookLogin = (ImageButton) findViewById(R.id.loginFacebookButton);
        gmailLogin = (ImageButton) findViewById(R.id.loginGmailButton);
        instagramLogin = (ImageButton) findViewById(R.id.loginInstagramButton);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == login.getId()) {
            Intent intent = new Intent(MainActivity.this, GameSelectionActivity.class);
            startActivity(intent);

//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getApplicationContext());
//            LayoutInflater layoutInflater = this.getLayoutInflater();
//            View dialogView = layoutInflater.inflate(R.layout.player1_winning_layout, null);
//            dialogBuilder.setView(dialogView);
//
//            AlertDialog alertDialog = dialogBuilder.create();
//            alertDialog.show();
        }
    }
}
