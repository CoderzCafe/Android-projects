package com.example.shine.dbtest1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseHelper databaseHelper;

    private EditText name;
    private EditText age;
    private RadioGroup genderRadio;
    private RadioButton radioButton;
    private Button add;
    private Button showButton;

    private TextView showTheDataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        name = (EditText) findViewById(R.id.nameText);
        age = (EditText) findViewById(R.id.ageText);
        genderRadio = (RadioGroup) findViewById(R.id.genderRadioGroup);
        add = (Button) findViewById(R.id.addButton);
        showButton = (Button) findViewById(R.id.showDataButton);

        showTheDataText = (TextView) findViewById(R.id.showDataText);

        add.setOnClickListener(this);
        showButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String studentName = name.getText().toString();
        String studentAge = age.getText().toString();

        //  get the data from radio button
        int selectedId = genderRadio.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
//        String studentGender = radioButton.getText().toString();    // problem

        if (v.getId() == R.id.addButton) {
            long rowId = databaseHelper.insertData(studentName, studentAge, radioButton.getText().toString());

            if (rowId == -1) {
                Toast.makeText(this, "Data is not inserted", Toast.LENGTH_SHORT).show();
            } else {
                name.setText("");
                age.setText("");
                genderRadio.clearCheck();
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            }
        }

        if (v.getId() == R.id.showDataButton) {
            Cursor cursor = databaseHelper.showData();

            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Nothing to show.!", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()) {
                stringBuffer.append("ID : " +String.valueOf(cursor.getInt(0))+"\n");
                stringBuffer.append("Name : " +cursor.getString(1) +"\n");
                stringBuffer.append("Age : " +cursor.getString(2) +"\n");
                stringBuffer.append("Gender : " +cursor.getString(3) +"\n");
            }
            showData("Result set \n", stringBuffer.toString());
        }
    }

    private void showData(String title, String data) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(data);
        alert.setCancelable(true);
        alert.show();
    }
}
