package com.example.viewdatafromfirebasedb;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView artistListView;
    private List<DataModel> artistList;

    private DatabaseReference databaseReference;
    private DatabaseReference databaseTracks;

    private List<Track> trackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        databaseReference = FirebaseDatabase.getInstance().getReference("artists");

        artistListView = (ListView) findViewById(R.id.listView);
        artistList = new ArrayList<>();
        trackList = new ArrayList<>();

        artistListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel = artistList.get(position);
                showDetails(dataModel.getArtistId(), dataModel.getArtistName(), dataModel.getArtistGenre());

                return false;
            }
        });
    }

    private void showDetails(String artistId, String artistName, String artistGen) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.artist_details, null);
        alertBuilder.setView(dialogView);

        alertBuilder.setTitle("Artist details");
        final AlertDialog alert = alertBuilder.create();
        alert.show();

        final TextView artistNameText = dialogView.findViewById(R.id.artistNameText);
        final TextView artistGenreType = dialogView.findViewById(R.id.artistGenreTypeText);
        final ListView trackListView = dialogView.findViewById(R.id.artistTrackListView);

        artistNameText.setText(artistName);
        artistGenreType.setText(artistGen);

        /** database **/
        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(artistId);
        ArrayAdapter<Track> adapter = new ArrayAdapter<Track>(this, R.layout.tracklist_layout);
        trackListView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataModelSnapshot : dataSnapshot.getChildren()) {
                    DataModel dataModel = dataModelSnapshot.getValue(DataModel.class);
                    artistList.add(dataModel);
                }

                ArtistListAdapter adapter = new ArtistListAdapter(MainActivity.this, artistList);
                artistListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                trackList.clear();
                for (DataSnapshot trackSnapshot: dataSnapshot.getChildren()) {
                    Track track = trackSnapshot.getValue(Track.class);
                    trackList.add(track);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
