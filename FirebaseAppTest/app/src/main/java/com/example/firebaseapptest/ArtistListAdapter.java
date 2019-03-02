package com.example.firebaseapptest;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class ArtistListAdapter extends ArrayAdapter<Artist> {

    private Activity context;
    private List<Artist> artistList;

    public ArtistListAdapter(Activity context, List<Artist> artistList) {
        super(context, R.layout.artist_layout_list, artistList);

        this.context = context;
        this.artistList = artistList;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listItemView = layoutInflater.inflate(R.layout.artist_layout_list, null, true);

        TextView nameText = (TextView) listItemView.findViewById(R.id.artistNameText);
        TextView genText = (TextView) listItemView.findViewById(R.id.artistGenText);

        Artist artist = artistList.get(position);

        nameText.setText(artist.getArtistName());
        genText.setText(artist.getGen());
        return listItemView;
    }
}
