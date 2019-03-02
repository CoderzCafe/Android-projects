package com.example.sqlitedbwithlistview;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText idText, nameText;
    private Button saveButton, showButton, updateButton, deleteButton;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        idText = (EditText) findViewById(R.id.idText);
        nameText = (EditText) findViewById(R.id.nameText);

        saveButton = (Button) findViewById(R.id.saveButton);
        showButton = (Button) findViewById(R.id.showButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String id = idText.getText().toString();
        String name = nameText.getText().toString();

        if (v.getId() == R.id.saveButton) {
            /** save button ***/
            long rowId = database.insertData(name);
            if (rowId > 0) {
                Toast.makeText(this, "Data is inserted", Toast.LENGTH_SHORT).show();
                nameText.setText("");
                idText.setText("");
            } else {
                Toast.makeText(this, "Data is not inserted", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.showButton) {
            /** show button **/
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);

        } else if (v.getId() == R.id.updateButton) {
            /** udpate button **/
            long rowId = database.update(id, name);
            if (rowId < 0) {
                Toast.makeText(this, "Data can not be updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data is updated", Toast.LENGTH_SHORT).show();
                idText.setText("");
                nameText.setText("");
            }

        } else if (v.getId() == R.id.deleteButton) {
            /** delete button **/
            long rowId = database.delete(id);
            if (rowId < 0) {
                Toast.makeText(this, "Data is not deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data is deleted", Toast.LENGTH_SHORT).show();
                idText.setText("");
            }
        }
    }
}
