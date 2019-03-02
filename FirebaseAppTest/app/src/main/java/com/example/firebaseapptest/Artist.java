package com.example.firebaseapptest;

public class Artist {
    private String artistId;
    private String artistName;
    private String gen;

    public Artist() {
        // default constructor
    }

    public Artist(String artistId, String artistName, String gen) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.gen = gen;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }
}
