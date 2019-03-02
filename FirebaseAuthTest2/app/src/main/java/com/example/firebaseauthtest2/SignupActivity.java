package com.example.firebaseauthtest2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailText;
    private EditText password;

    private Button signupButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseApp.initializeApp(getApplicationContext());

        emailText = (EditText) findViewById(R.id.emailEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        signupButton = (Button) findViewById(R.id.signupButton);
        signupButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == signupButton.getId()) {

            registerUser();

        }
    }

    private void registerUser() {
        String email = emailText.getText().toString().trim();
        String pwd = password.getText().toString().trim();

        if (email.isEmpty()) {
            emailText.setError("Email required");
            emailText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email");
            emailText.requestFocus();
            return;
        }

        if (pwd.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (pwd.length() < 6) {
            password.setError("Your password is less than 6");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User registered successful", Toast.LENGTH_SHORT).show();
                            //  clear those fields
                            emailText.setText("");
                            password.setText("");
                        }
                    }
                });
    }
}
