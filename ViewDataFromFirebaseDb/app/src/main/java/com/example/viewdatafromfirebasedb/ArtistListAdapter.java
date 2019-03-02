package com.example.viewdatafromfirebasedb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class ArtistListAdapter extends ArrayAdapter<DataModel> {

    private Activity context;
    private List<DataModel> list;

    public ArtistListAdapter(Activity context, List<DataModel> list) {
        super(context, R.layout.artist_list_layout, list);
        this.context = context;
        this.list = list;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.artist_list_layout, null, true);

        TextView idText = listItemView.findViewById(R.id.artistId);
        TextView nameText = listItemView.findViewById(R.id.artistName);
        TextView genText = listItemView.findViewById(R.id.artistGen);

        DataModel artist = list.get(position);

        idText.setText(artist.getArtistId());
        nameText.setText(artist.getArtistName());
        genText.setText(artist.getArtistGenre());

        return listItemView;
    }
}
