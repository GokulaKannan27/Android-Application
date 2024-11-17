package com.example.beat_box;

import java.util.ArrayList;
import java.util.List;

public class LikedSongsManager {
    private static LikedSongsManager instance;
    private List<Song> likedSongs;

    private LikedSongsManager() {
        likedSongs = new ArrayList<>();
    }

    public static synchronized LikedSongsManager getInstance() {
        if (instance == null) {
            instance = new LikedSongsManager();
        }
        return instance;
    }

    public List<Song> getLikedSongs() {
        return likedSongs;
    }

    public void addSong(Song song) {
        if (!likedSongs.contains(song)) {
            likedSongs.add(song);
        }
    }

    public void removeSong(Song song) {
        likedSongs.remove(song);
    }
}
