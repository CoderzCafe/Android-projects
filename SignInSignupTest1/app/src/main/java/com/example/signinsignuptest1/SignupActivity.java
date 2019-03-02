package com.example.signinsignuptest1;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabase myDatabase;
    private EditText nameText, emailText, userNameText, passwordText;
    private Button signupButton;
    private UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        myDatabase = new MyDatabase(this);
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();

        nameText = (EditText) findViewById(R.id.nameText);
        emailText = (EditText) findViewById(R.id.emailText);
        userNameText = (EditText) findViewById(R.id.userNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);

        userDetails = new UserDetails();

        signupButton = (Button) findViewById(R.id.signupButton2);
        signupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String userName = userNameText.getText().toString();
        String password = passwordText.getText().toString();

        userDetails.setName(name);
        userDetails.setEmail(email);
        userDetails.setUserName(userName);
        userDetails.setPassword(password);

        if (v.getId() == R.id.signupButton2) {
            long rowId = myDatabase.insertData(userDetails);
            if (rowId < -1) {
                Toast.makeText(this, "Data not inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data is inserted..", Toast.LENGTH_SHORT).show();
                nameText.setText("");
                emailText.setText("");
                userNameText.setText("");
                passwordText.setText("");
            }
        }
    }
}
