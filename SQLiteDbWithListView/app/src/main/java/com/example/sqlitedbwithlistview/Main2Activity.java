package com.example.sqlitedbwithlistview;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        db = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listViewData);

        loadData();
    }

    public void loadData() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor cursor = db.showAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data in database", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                arrayList.add(String.valueOf(cursor.getInt(0)) +"\t"+cursor.getString(1));
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, R.id.textViewId, arrayList);
        listView.setAdapter(adapter);
    }
}
