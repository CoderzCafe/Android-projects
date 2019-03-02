package com.example.firebaseapptest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "artistname";
    public static final String ARTIST_ID = "artistid";

    private Button sendButton;
    private EditText nameText;
    private Spinner genSpinner;

    //  instance of firebase realtime database
    private DatabaseReference artistDatabaseReference;

    private ListView artistListView;

    private List<Artist> artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** firebase database references **/
        artistDatabaseReference = FirebaseDatabase.getInstance().getReference("artists");

        sendButton = (Button) findViewById(R.id.sendDataButton);
        nameText = (EditText) findViewById(R.id.enterNameText);
        genSpinner = (Spinner) findViewById(R.id.spinnerText);

        artistListView = (ListView) findViewById(R.id.artistListView);
        artistList = new ArrayList<>();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** add data to the database **/
                addText();
            }
        });

        artistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistList.get(position);
                Intent intent = new Intent(MainActivity.this, AddTrackActivity.class);

                intent.putExtra(ARTIST_ID, artist.getArtistId());
                intent.putExtra(ARTIST_NAME, artist.getArtistName());

                startActivity(intent);
            }
        });

        artistListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Artist artist = artistList.get(position);
                showUpdateDialog(artist.getArtistId(), artist.getArtistName());
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        artistDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                artistList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()) {
                    Artist artist = artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);
                }

                ArtistListAdapter adapter = new ArtistListAdapter(MainActivity.this, artistList);
                artistListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    /** update dialog **/
    private void showUpdateDialog(final String artistId, String artistName) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.update_dialog, null);

        alertBuilder.setView(dialogView);

        alertBuilder.setTitle("Updating artist : " +artistId);
        final AlertDialog alert = alertBuilder.create();
        alert.show();

        final EditText updateNameText = dialogView.findViewById(R.id.editTextName);
        final Spinner updateSpinner = dialogView.findViewById(R.id.updateSpinner);
        final Button updateButton = dialogView.findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = updateNameText.getText().toString().trim();
                String genre = updateSpinner.getSelectedItem().toString();
                if (TextUtils.isEmpty(name)) {
                    updateNameText.setError("Name is required");
                }
                boolean check = updateArtist(artistId, name, genre);
                if (check == true) {
                    alert.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "there is problem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /** add data to the databse **/
    private void addText() {
        String name = nameText.getText().toString();
        String gen = genSpinner.getSelectedItem().toString();

        /** get the unique key from the database **/
        String id = artistDatabaseReference.push().getKey();

        Artist artist = new Artist(id, name, gen);

        /** send data to the database **/
        artistDatabaseReference.child(id).setValue(artist);
        Toast.makeText(this, "Artist is added", Toast.LENGTH_SHORT).show();

        nameText.setText("");

    }

    private boolean updateArtist(String id, String name, String gen) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artists");
        Artist artist = new Artist(id, name, gen);
        databaseReference.child(id).setValue(artist);
        Toast.makeText(this, "Artist is updated successfully..", Toast.LENGTH_SHORT).show();
        return true;
    }
}
