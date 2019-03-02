package com.example.personalrssfeed;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class EditRssFeedActivity extends AppCompatActivity {
    private List<RssFeed> feeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rss_feed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        feeds = new Database(this).getRssFeeds();

        RssFeedAdapter adapter = new RssFeedAdapter(this, feeds);

        final ListView listView = (ListView) findViewById(R.id.editRssListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //  write alert dialog
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EditRssFeedActivity.this);
                alertBuilder.setTitle("Remove feed");
                alertBuilder.setMessage("Are you sure want to remove this feed?");

                final int positionRemove = position;

                alertBuilder.setNegativeButton("No", null);
                alertBuilder.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int which) {
                        RssFeed selectedFeed = feeds.get(positionRemove);

                        new Database(EditRssFeedActivity.this).deleteRssFeed(selectedFeed);
                        feeds = new Database(EditRssFeedActivity.this).getRssFeeds();
                        //  clear from list
                        RssFeedAdapter adapterNew = new RssFeedAdapter(EditRssFeedActivity.this, feeds);;
                        listView.setAdapter(adapterNew);
                    }
                });
                alertBuilder.show();
            }
        });
    }

}
