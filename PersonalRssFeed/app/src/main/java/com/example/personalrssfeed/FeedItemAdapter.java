package com.example.personalrssfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FeedItemAdapter extends ArrayAdapter<RssItem> {
    private Context context;

    public FeedItemAdapter(Context context, ArrayList<RssItem> items) {
        super(context, 0, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RssItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rss_feed_item_row, parent, false);
        }

        TextView largeLetter = (TextView) convertView.findViewById(R.id.rssFeedItemLetterTextView);
        TextView title = (TextView) convertView.findViewById(R.id.rssFeedItemTitleTextView);
        TextView description = (TextView) convertView.findViewById(R.id.rssFeedItemDescriptionTextView);

        largeLetter.setText(item.getTile().substring(0, 1));
        title.setText(item.getTile());
        description.setText(item.getDescription());

        return convertView;
    }
}
