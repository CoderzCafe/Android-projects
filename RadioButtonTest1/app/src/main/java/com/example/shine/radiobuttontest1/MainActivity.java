package com.example.shine.radiobuttontest1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private Button show;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        show = (Button) findViewById(R.id.showButton);
        textView = (TextView) findViewById(R.id.showSelectedDataText);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selected);
                textView.setText(radioButton.getText());
            }
        });
    }
}
