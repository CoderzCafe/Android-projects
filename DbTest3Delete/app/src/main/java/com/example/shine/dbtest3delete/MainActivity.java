package com.example.shine.dbtest3delete;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDatabase myDatabase;
    private EditText idText, nameText, ageText;
    private RadioGroup genderGroup;
    private RadioButton radioButton;
    private Button addButton, showButton, updateButton, deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = new MyDatabase(this);
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();

        idText = (EditText) findViewById(R.id.idText);
        nameText = (EditText) findViewById(R.id.nameText);
        ageText = (EditText) findViewById(R.id.ageText);
        genderGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);

        addButton = (Button) findViewById(R.id.addButton);
        showButton = (Button) findViewById(R.id.showAllDataButton);
        updateButton = (Button) findViewById(R.id.updateDataButton);
        deleteButton = (Button) findViewById(R.id.deleteDataButton);

        addButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String id = idText.getText().toString().trim();
        String name = nameText.getText().toString().trim();
        String age = ageText.getText().toString().trim();
        int genderSelectedId = genderGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(genderSelectedId);

        /** inserted data to database **/
        if (v.getId() == R.id.addButton) {
            long rowId = myDatabase.insertData(name, age, radioButton.getText().toString());
            if (rowId < 0 ) {
                Toast.makeText(this, "Data is not inserted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data is inserted...", Toast.LENGTH_SHORT).show();
                nameText.setText("");
                ageText.setText("");
                genderGroup.clearCheck();
            }
        } else if (v.getId() == R.id.showAllDataButton) {
            /** show add data from database **/
            Cursor cursor = myDatabase.showAllData();
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "There is no data in the database", Toast.LENGTH_SHORT).show();
            }

            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()) {
                stringBuffer.append("ID :" +cursor.getInt(0)+"\n");
                stringBuffer.append("Name :" +cursor.getString(1)+"\n");
                stringBuffer.append("Age :" +cursor.getString(2)+"\n");
                stringBuffer.append("Gender :" +cursor.getString(3)+"\n\n");
            }
            showData("All data", stringBuffer);

        } else if (v.getId() == R.id.updateDataButton) {
            /** update data **/
            int rowId = myDatabase.updateData(id, name, age, radioButton.getText().toString());
            if (rowId > 0) {
                Toast.makeText(this, "Data is updated", Toast.LENGTH_SHORT).show();
                nameText.setText("");
                idText.setText("");
                ageText.setText("");
                genderGroup.clearCheck();
            } else {
                Toast.makeText(this, "Data is not updated", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.deleteDataButton) {
            /** delete data **/
            int rowId = myDatabase.deleteData(id);
            if (rowId > 0) {
                idText.setText("");
                Toast.makeText(this, id+"'s data is deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, id+"'s data is not deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showData(String title, StringBuffer data) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(data.toString());
        alert.show();
    }
}
