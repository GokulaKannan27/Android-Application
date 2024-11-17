package com.example.beat_box;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songs;

    public SongAdapter(List<Song> songs) {
        this.songs = songs;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);

        holder.songTitle.setText(song.getTitle());
        holder.likeButton.setOnClickListener(v -> {
            if (!isLiked(song)) {
                LikedSongsManager.getInstance().addSong(song);
                holder.likeButton.setColorFilter(Color.RED); // Change color to indicate liked
                Toast.makeText(v.getContext(), "Added to Liked Songs", Toast.LENGTH_SHORT).show();
            } else {
                LikedSongsManager.getInstance().removeSong(song);
                holder.likeButton.setColorFilter(Color.GRAY); // Default color
                Toast.makeText(v.getContext(), "Removed from Liked Songs", Toast.LENGTH_SHORT).show();
            }
        });

        holder.likeButton.setColorFilter(isLiked(song) ? Color.RED : Color.GRAY); // Update button state
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    private boolean isLiked(Song song) {
        return LikedSongsManager.getInstance().getLikedSongs().contains(song);
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle;
        ImageView likeButton;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.song_title);
            likeButton = itemView.findViewById(R.id.like_button);
        }
    }
}
