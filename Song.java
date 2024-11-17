package com.example.beat_box;

public class Song {
    private String title;
    private String url;

    public Song(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public  String getUrl() {
        return url;
    }
}
