package com.example.materialtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar_layout);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /** toolbar menu items select **/
        int itemId = item.getItemId();

        if (itemId == R.id.settingMenu) {
            Toast.makeText(this, "You are click on the setting", Toast.LENGTH_SHORT).show();
        }

        if (itemId == R.id.forwardNavigateMenu) {
            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
