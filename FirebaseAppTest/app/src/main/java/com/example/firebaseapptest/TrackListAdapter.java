package com.example.firebaseapptest;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class TrackListAdapter extends ArrayAdapter<Track> {

    private Activity context;
    private List<Track> trackList;

    public TrackListAdapter(Activity context, List<Track> trackList) {
        super(context, R.layout.tracks_layout_list, trackList);
        this.context = context;
        this.trackList = trackList;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View trackListItemView = inflater.inflate(R.layout.tracks_layout_list, null, true);

        TextView trackName = (TextView) trackListItemView.findViewById(R.id.trackNameText);
        TextView trackRating = (TextView) trackListItemView.findViewById(R.id.trackRatingText);

        Track track = trackList.get(position);
        trackName.setText(track.getTrackName());
        trackRating.setText(String.valueOf(track.getTrackRating()));

        return trackListItemView;
    }
}
