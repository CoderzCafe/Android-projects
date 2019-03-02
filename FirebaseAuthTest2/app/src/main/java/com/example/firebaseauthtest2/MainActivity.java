package com.example.firebaseauthtest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userId;
    private EditText password;

    private Button signinButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(getApplicationContext());

        userId = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        signinButton = (Button) findViewById(R.id.signinButton);
        signinButton.setOnClickListener(this);
        signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == userId.getId()) {
            System.out.println("Hello");
        }

        if (v.getId() == signupButton.getId()) {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        }

    }
}
