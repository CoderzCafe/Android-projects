package com.example.shine.sharedpreferencetest1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userName, password;
    private Button loginButton;
    private CheckBox savePasswordButton;
    private TextView showName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showName = (TextView) findViewById(R.id.showName);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        savePasswordButton = (CheckBox) findViewById(R.id.savePassword);
        savePasswordButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.loginButton) {
            String name = userName.getText().toString();
            String pawrd = password.getText().toString();
            if (!name.isEmpty() && !pawrd.isEmpty()) {
                showName.setText(name + "\n" + pawrd);
            }
            userName.setText("");
            password.setText("");
        } if (savePasswordButton.isChecked()) {
            if (!userName.getText().toString().isEmpty() || !password.getText().toString().isEmpty()) {
                /** shared preference **/
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nameKey", userName.getText().toString());
                editor.putString("pwdKey", password.getText().toString());
                editor.commit();
            }
        } if (this.getClass().equals(MainActivity.class)) {
            if (savePasswordButton.isChecked()) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
                String name = sharedPreferences.getString("nameKey", "Can't find the name");
                String pwd = sharedPreferences.getString("pwdKey", "Can't find the password");

                userName.setText(name);
                password.setText(pwd);
            }
        }

    }
}
