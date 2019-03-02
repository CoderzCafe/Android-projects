package com.example.personalrssfeed;

import java.net.URL;

public class RssItem {

    private String tile;
    private String description;
    private URL link;

    public RssItem(String tile, String description, URL link) {
        this.tile = tile;
        this.description = description;
        this.link = link;
    }

    public RssItem() {

    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }
}
