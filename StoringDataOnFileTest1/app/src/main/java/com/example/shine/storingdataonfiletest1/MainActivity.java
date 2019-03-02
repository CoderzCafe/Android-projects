package com.example.shine.storingdataonfiletest1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText text;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.diaryText);
        save = (Button) findViewById(R.id.saveButton);
        readFromFile();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = text.getText().toString();
                if (!txt.isEmpty()) {
                    writeToFile(txt);
                    Toast.makeText(MainActivity.this, "Data save successfully..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter your text and" +
                            " the click the save button", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void writeToFile(String txt) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = openFileOutput("dairy.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(txt.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            FileInputStream fileInputStream = openFileInput("dairy.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuffer stringBuffer = new StringBuffer();

            while((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line +"\n");
            }
            text.setText(stringBuffer.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
