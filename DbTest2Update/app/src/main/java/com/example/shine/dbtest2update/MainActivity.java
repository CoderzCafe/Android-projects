package com.example.shine.dbtest2update;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shine.dbtest2update.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Database db;
    private EditText idText, nameText, ageText;
    private RadioGroup genderRadio;
    private RadioButton radioButton;
    private Button add, show, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        idText = (EditText) findViewById(R.id.idText);
        nameText = (EditText) findViewById(R.id.nameText);
        ageText = (EditText) findViewById(R.id.ageText);

        genderRadio = (RadioGroup) findViewById(R.id.genderRadioGroup);

        add = (Button) findViewById(R.id.addButton);
        show = (Button) findViewById(R.id.showDataButton);
        update = (Button) findViewById(R.id.updateButton);

        add.setOnClickListener(this);
        show.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /** collect all data **/
        String id = idText.getText().toString();
        String name = nameText.getText().toString();
        String age = ageText.getText().toString();
        int selectedGenderId = genderRadio.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedGenderId);

        /** add button action **/
        if (v.getId() == R.id.addButton) {
            long rowId = db.insertData(name, age, radioButton.getText().toString());
            if (rowId == -1) {
                Toast.makeText(this, "Data not inserted...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data is inserted...", Toast.LENGTH_SHORT).show();
                nameText.setText("");
                ageText.setText("");
                genderRadio.clearCheck();
            }
        } else if (v.getId() == R.id.showDataButton) {
                /** show all the data **/
                Cursor cursor = db.showAllData();

                if (cursor.getCount() == 0) {
                    Toast.makeText(this, "There is no data in the database", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    StringBuffer stringBuffer = new StringBuffer();
                    while (cursor.moveToNext()) {
                        stringBuffer.append("ID :"+cursor.getInt(0)+"\n");
                        stringBuffer.append("Name :" +cursor.getString(1)+"\n");
                        stringBuffer.append("Age :" +cursor.getString(2)+"\n");
                        stringBuffer.append("Gender :" +cursor.getString(3)+"\n\n");
                    }
                    showData("All data", stringBuffer);
                }
        } else if (v.getId() == R.id.updateButton) {
            /** update the data **/
            boolean isUpdate = db.updateData(id, name, age, radioButton.getText().toString());
            if (isUpdate == true) {
                idText.setText("");
                nameText.setText("");
                ageText.setText("");
                genderRadio.clearCheck();
                Toast.makeText(this, "Data is updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data not updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showData(String title, StringBuffer data) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(data.toString());
        alert.setCancelable(true);
        alert.show();
    }
}
