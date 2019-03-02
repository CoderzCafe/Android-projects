package com.example.firebaseauthtest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailText;
    private EditText passwordText;
    private Button signinButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);

        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        progressBar = (ProgressBar) findViewById(R.id.progressBarMainActivity);
        signinButton = (Button) findViewById(R.id.signinButtonMainActivity);
        signinButton.setOnClickListener(this);

        /** firebaseauth instance **/
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(getApplicationContext());
    }

    /** signup action on textview **/
    public void signUpAction(View view) {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signinButtonMainActivity) {
            userAuthLogin();
        }
    }

    private void userAuthLogin() {

        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (email.isEmpty()) {
            emailText.setError("Email is required");
            emailText.requestFocus();
            return;
        } if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            /** check the email is valid or not **/
            emailText.setError("Please enter a valid email address");
            emailText.requestFocus();
            return;
        } if (password.isEmpty()) {
            passwordText.setError("Password is required");
            passwordText.requestFocus();
            return;
        } if (password.length() < 6) {
            passwordText.setError("Minimum length of password should be 6");
            passwordText.requestFocus();
            return;
        }

        /** progress bar visibility **/
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    TOAST_SHORT_LENGTH(getApplicationContext(), task.getException().toString());
                }
            }
        });
    }

    public static void TOAST_SHORT_LENGTH(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
