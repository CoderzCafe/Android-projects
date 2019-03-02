package com.example.shine.sharedpreferencetest3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout lineareLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineareLayout = (LinearLayout) findViewById(R.id.linearLayout);

        if (getTheLoadColor() != getResources().getColor(R.color.colorAccent)) {
            lineareLayout.setBackgroundColor(getTheLoadColor());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.redColor) {
            lineareLayout.setBackgroundColor(getResources().getColor(R.color.red));
            storeColor(getResources().getColor(R.color.red));
        } if (item.getItemId() == R.id.greenColor) {
            lineareLayout.setBackgroundColor(getResources().getColor(R.color.green));
            storeColor(getResources().getColor(R.color.green));
        } if (item.getItemId() == R.id.blueColor) {
            lineareLayout.setBackgroundColor(getResources().getColor(R.color.blue));
            storeColor(getResources().getColor(R.color.blue));
        }

        return super.onOptionsItemSelected(item);
    }

    private void storeColor(int color) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("storeBgColor", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("bgColor", color);
        editor.commit();
    }

    private int getTheLoadColor() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("storeBgColor", Context.MODE_PRIVATE);
        int color = sharedPreferences.getInt("bgColor", getResources().getColor(R.color.colorAccent));
        return color;
    }
 }
