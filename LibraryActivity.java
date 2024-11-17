package com.example.beat_box;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LibraryActivity extends Fragment {
    private RecyclerView rc;
    private TextView likedSongs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library_main, container, false);

        rc = view.findViewById(R.id.recycle);
        rc.setVisibility(View.GONE); // Initially hide RecyclerView

        likedSongs = view.findViewById(R.id.liked_songs);
        likedSongs.setOnClickListener(v -> openLikedSongs());

        return view;
    }

    private void openLikedSongs() {
        List<Song> likedSongs = LikedSongsManager.getInstance().getLikedSongs();

        if (likedSongs.isEmpty()) {
            Toast.makeText(getContext(), "No liked songs yet!", Toast.LENGTH_SHORT).show();
            return;
        }

        rc.setVisibility(View.VISIBLE);
        RecyclerView.Adapter adapter = new SongAdapter(likedSongs);
        rc.setAdapter(adapter);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
