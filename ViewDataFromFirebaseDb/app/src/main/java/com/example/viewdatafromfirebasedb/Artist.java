package com.example.viewdatafromfirebasedb;

public class Artist {
    private String artistId;
    private String artistName;
    private String artistGen;

    public Artist() {

    }

    public Artist(String artistId, String artistName, String artistGen) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGen = artistGen;
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

    public String getArtistGen() {
        return artistGen;
    }

    public void setArtistGen(String artistGen) {
        this.artistGen = artistGen;
    }
}
