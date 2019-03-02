package com.example.personalrssfeed;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;

public class MainActivity extends AppCompatActivity {

    ArrayList<RssItem> rssItems;
    List<RssFeed> rssFeeds;

    private int feedCount;
    private int retriveFeedCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fab.getContext(), AddFeedActivity.class);
                startActivity(intent);
            }
        });

        rssItems = new ArrayList<RssItem>();
        rssFeeds = new Database(this).getRssFeeds();
        feedCount = rssFeeds.size();
        retriveFeedCount = 0;

        for (int i=0; i<rssFeeds.size(); i++) {
            GetFeedItems(rssFeeds.get(i).rssFeedAddress);
        }
    }

    public void GetFeedItems(String feedAddress) {
        try {
            SimpleRss2Parser parser = new SimpleRss2Parser(feedAddress,
                    new SimpleRss2ParserCallback() {
                        @Override
                        public void onFeedParsed(List<RSSItem> items) {
                            for(int i = 0; i < items.size(); i++){
                                RssItem item = new RssItem();
                                item.setTile(items.get(i).getTitle());
                                item.setDescription(items.get(i).getDescription());
                                item.setLink(items.get(i).getLink());
                                Log.d("Item received", items.get(i).getTitle());
                                rssItems.add(item);
                            }
                            PopulateListView();

                        }
                        @Override
                        public void onError(Exception ex) {
//                            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                            PopulateListView();
                        }
                    });
            parser.parseAsync();
        } catch (Exception e) {
            Toast.makeText(this, "Address not found or "+e.getMessage(), Toast.LENGTH_LONG).show();
            PopulateListView();
        }
    }

    public void PopulateListView() {
        retriveFeedCount++;
        if (retriveFeedCount == feedCount) {
            Log.d("Feeds receive", "got all feeds");
            ListView listView = (ListView) findViewById(R.id.rssFeedItemListView);
            listView.setAdapter(new FeedItemAdapter(getApplicationContext(), rssItems));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, RssItemViewActivity.class);

                    RssItem item = rssItems.get(position);

                    intent.putExtra("url", item.getLink().toString());
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(MainActivity.this, EditRssFeedActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
