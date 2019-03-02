package com.example.signinsignuptest1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabase database;
    private EditText userNameText, passwordText;
    private Button signInButton, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new MyDatabase(this);
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        userNameText = (EditText) findViewById(R.id.userNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        signInButton = (Button) findViewById(R.id.signinButton);
        signupButton = (Button) findViewById(R.id.signupButton1);

        signInButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String userName = userNameText.getText().toString();
        String password = passwordText.getText().toString();

        if (v.getId() == R.id.signinButton) {
            /** signin button **/
            Boolean result = database.matchUser(userName, password);

            if (result == true) {
                Toast.makeText(this, userName+" welcome......", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Signup please", Toast.LENGTH_LONG).show();
            }


        } else if (v.getId() == R.id.signupButton1) {
            /** signup button **/
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        }
    }
}
