package com.example.personalrssfeed;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RssFeedAdapter extends ArrayAdapter<RssFeed> {
    private Context context;

    public RssFeedAdapter(Context context, List<RssFeed> feeds) {
        super(context, 0, feeds);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RssFeed feed = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rss_edit_feed_item_row, parent, false);
        }

        TextView feedTitle = (TextView) convertView.findViewById(R.id.rssEditFeedTitleTextView);
        TextView feedAddress = (TextView) convertView.findViewById(R.id.rssEditFeedAddressTextView);

        feedTitle.setText(feed.rssFeedTitle);
        feedAddress.setText(feed.rssFeedAddress);

        return convertView;
    }
}
