package com.example.firebaseapptest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    private TextView artistNameText;
    private EditText trackNameText;
    private Button addButton;
    private SeekBar seekBarRating;

    private ListView listViewTrack;

    private DatabaseReference databaseTracks;

    private List<Track> listTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        artistNameText = (TextView) findViewById(R.id.textViewArtistName);
        trackNameText = (EditText) findViewById(R.id.trackNameText);
        addButton = (Button) findViewById(R.id.addDataButton);
        seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);

        listViewTrack = (ListView) findViewById(R.id.tackListView);
        listTrack = new ArrayList<>();

        /** get the artist id and name from the MainActivity **/
        Intent intent = getIntent();
        String artistId = intent.getStringExtra(MainActivity.ARTIST_ID);
        String artistName = intent.getStringExtra(MainActivity.ARTIST_NAME);

        artistNameText.setText(artistName);

        /** create nodes on the database based on the particular id **/
        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(artistId);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTracks();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listTrack.clear();
                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren()) {
                    Track track = trackSnapshot.getValue(Track.class);
                    listTrack.add(track);
                }

                TrackListAdapter adapter = new TrackListAdapter(AddTrackActivity.this, listTrack);
                listViewTrack.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveTracks() {
        String trackName = trackNameText.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if (!TextUtils.isEmpty(trackName)) {
            String id = databaseTracks.push().getKey();

            Track track = new Track(id, trackName, rating);

            databaseTracks.child(id).setValue(track);
            Toast.makeText(this, "Track is inserted successfully", Toast.LENGTH_SHORT).show();
            trackNameText.setText("");
            seekBarRating.setProgress(0);

        } else {
            Toast.makeText(this, "Please insert the track name", Toast.LENGTH_SHORT).show();
        }
    }
}
