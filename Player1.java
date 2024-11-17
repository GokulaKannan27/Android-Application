package com.example.beat_box;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Player1 extends Fragment {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private Button playPauseButton;
    private ImageView likeButton, downloadButton;
    private TextView startTime, endTime;
    private String songUrl;
    private boolean isPlaying = false;
    private boolean isLiked = false;
    private TextView songTitle;

    //private static final String ARG_TITLE="key";
    private static final String ARG_URL="keys";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_main, container, false);

        songTitle = view.findViewById(R.id.song_title);
        playPauseButton = view.findViewById(R.id.play_pause_button);
        seekBar = view.findViewById(R.id.seek_bar);
        startTime = view.findViewById(R.id.start_time);
        endTime = view.findViewById(R.id.end_time);
        likeButton = view.findViewById(R.id.like_button);
        downloadButton = view.findViewById(R.id.download_button);

        Bundle b=getArguments();
        // Retrieve song URL and title passed to this fragment
        if (b!= null) {

            songUrl = b.getString(ARG_URL);
            Toast.makeText(getContext(),songUrl,Toast.LENGTH_SHORT).show();
            String title = getArguments().getString("keys");
            songTitle.setText(title);
            Log.d("Player1", "Song URL: " + songUrl);
        }

        playPauseButton.setOnClickListener(v -> togglePlayPause());
        likeButton.setOnClickListener(v -> toggleLike());
        downloadButton.setOnClickListener(v -> downloadSong());

        // Initialize MediaPlayer and prepare for playback
        initializeMediaPlayer();

        return view;
    }

    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            //songUrl="https://github.com/GokulaKannan27/Hackthon-Accus-for-Buccus/raw/refs/heads/main/Aaruyire.mp3";
            mediaPlayer.setDataSource(songUrl);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(mp -> {
                seekBar.setMax(mp.getDuration());
                endTime.setText(formatTime(mp.getDuration()));
                playPauseButton.setEnabled(true);
                Log.d("Player1","MediPlayer Prepared: "+mp.getDuration());
                // progressBar.setVisibility(View.GONE);
            });

            mediaPlayer.setOnCompletionListener(mp -> resetPlayer());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) mediaPlayer.seekTo(progress);
                    startTime.setText(formatTime(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"Error initializing media player",Toast.LENGTH_LONG).show();
        }
    }

    private void togglePlayPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playPauseButton.setText("Play");
        } else {
            mediaPlayer.start();
            playPauseButton.setText("Pause");
            updateSeekBar();
        }
        isPlaying =mediaPlayer.isPlaying();
    }

    private void updateSeekBar() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            startTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
            handler.postDelayed(this::updateSeekBar, 1000);
        }
    }

    private void resetPlayer() {
        playPauseButton.setText("Play");
        isPlaying = false;
        mediaPlayer.seekTo(0);
        seekBar.setProgress(0);
        startTime.setText("00:00");
    }

    private void toggleLike() {
        isLiked = !isLiked;
        likeButton.setImageResource(isLiked ? R.drawable.homeicon : R.drawable.homeicon);
        // Save to favorites logic (e.g., database or SharedPreferences)
        Toast.makeText(getContext(), isLiked ? "Added to favorites" : "Removed from favorites", Toast.LENGTH_SHORT).show();
    }

    private void downloadSong() {
        // Implement download functionality here
        Toast.makeText(getContext(), "Downloading song...", Toast.LENGTH_SHORT).show();
    }

    private String formatTime(int milliseconds) {
        int minutes = (milliseconds / 1000) / 60;
        int seconds = (milliseconds / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacksAndMessages(null);
    }
}