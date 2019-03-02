package com.example.firebaseauthtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailText;
    private EditText passwordText;
    private Button signUpButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseApp.initializeApp(getApplicationContext());

        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        signUpButton = (Button) findViewById(R.id.signUpButton2ndActivity);
        signUpButton.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        /** firebase auth instance **/
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /** back to sign in activity -> MainActivity **/
    public void signInAction(View view) {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == signUpButton.getId()) {
            registerUser();
        }
    }

    private void registerUser() {
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

        progressBar.setVisibility(View.VISIBLE);
        /** creating new user with firebase auth **/
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Your registration is completed", Toast.LENGTH_SHORT).show();
                } else {
//                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                        Toast.makeText(getApplicationContext(), "User is already exists", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
//                    }

                    boolean check = !task.getResult().getUser().isAnonymous();
                    if (!check) {
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "User is already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
