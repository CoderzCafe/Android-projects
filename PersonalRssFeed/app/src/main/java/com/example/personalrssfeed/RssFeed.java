package com.example.personalrssfeed;

public class RssFeed {
    public int id;
    public String rssFeedTitle;
    public String rssFeedAddress;

    public RssFeed(String rssFeedTitle, String rssFeedAddress) {
        this.rssFeedTitle = rssFeedTitle;
        this.rssFeedAddress = rssFeedAddress;
    }

    public RssFeed(int id, String rssFeedTitle, String rssFeedAddress) {
        this.id = id;
        this.rssFeedTitle = rssFeedTitle;
        this.rssFeedAddress = rssFeedAddress;
    }
}
