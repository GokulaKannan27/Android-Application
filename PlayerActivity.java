package com.example.beat_box;

import static android.content.Intent.getIntent;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;

public class PlayerActivity extends Fragment {

    private ImageView img;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private ImageButton playPauseButton;
    private ImageView likeButton, downloadButton;
    private TextView startTime, endTime;
    private String songUrl;
    private boolean isPlaying = false;
    private boolean isLiked = false;
    private TextView songTitle;

    //private static final String ARG_TITLE="key";
    private static final String ARG_URL="key";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_main, container, false);

        img=view.findViewById(R.id.imageview3);
        songTitle = view.findViewById(R.id.song_title);
        playPauseButton = view.findViewById(R.id.play_pause_button);
        seekBar = view.findViewById(R.id.seek_bar);
        startTime = view.findViewById(R.id.start_time);
        endTime = view.findViewById(R.id.end_time);
        likeButton = view.findViewById(R.id.like_button);
        downloadButton = view.findViewById(R.id.download_button);


        Bundle b=getArguments();
        img.setImageResource(b.getInt("image"));
        // Retrieve song URL and title passed to this fragment
        if (b!= null) {

            songUrl = b.getString(ARG_URL);
            // Toast.makeText(getContext(),songUrl,Toast.LENGTH_SHORT).show();
            String title = getArguments().getString("key");
            songTitle.setText(getArguments().getString("title"));
            Log.d("PlayerActivity", "Song URL: " + songUrl);
        }

        playPauseButton.setOnClickListener(v -> togglePlayPause());
        likeButton.setOnClickListener(v -> toggleLike());
        downloadButton.setOnClickListener(v -> downloadSong());

        // Initialize MediaPlayer and prepare for playback
        initializeMediaPlayer();

        return view;
    }

    private void initializeMediaPlayer() {
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
        mediaPlayer = new MediaPlayer();
        try {
            //songUrl="https://github.com/GokulaKannan27/Hackthon-Accus-for-Buccus/raw/refs/heads/main/Aaruyire.mp3";
            mediaPlayer.setDataSource(songUrl);
            mediaPlayer.prepareAsync();
            //mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(mp -> {
                seekBar.setMax(mp.getDuration());
                endTime.setText(formatTime(mp.getDuration()));
                playPauseButton.setEnabled(true);
                Log.d("PlayerActivity","MediPlayer Prepared: "+mp.getDuration());
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
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                playPauseButton.setImageResource(R.drawable.play);
            } else {
                mediaPlayer.start();
                playPauseButton.setImageResource(R.drawable.pause);
                updateSeekBar();
            }
            isPlaying = mediaPlayer.isPlaying();
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            startTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
            handler.postDelayed(this::updateSeekBar, 1000);
        }
    }

    private void resetPlayer() {
        if(mediaPlayer!=null){
            mediaPlayer.seekTo(0);
            seekBar.setProgress(0);
            startTime.setText("00:00");
            playPauseButton.setImageResource(R.drawable.pause);
            isPlaying = false;
        }
      /*  playPauseButton.setText("Play");
        isPlaying = false;
        mediaPlayer.seekTo(0);
        seekBar.setProgress(0);
        startTime.setText("00:00");*/
    }

    private void toggleLike() {
        isLiked = !isLiked;
        likeButton.setImageResource(isLiked ? R.drawable.fav2: R.drawable.fav3);

        // Save to favorites logic (e.g., database or SharedPreferences)
        Toast.makeText(getContext(), isLiked ? "Added to favorites" : "Removed from favorites", Toast.LENGTH_SHORT).show();
    }

    private void downloadSong() {

        if (songUrl != null) {
            DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(songUrl);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            // Set destination path
            String fileName = "downloaded_song.mp3"; // You can make this dynamic
            File destination = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), fileName);
            request.setDestinationUri(Uri.fromFile(destination));

            // Set visibility and status
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle("Downloading Song");

            // Enqueue the download
            downloadManager.enqueue(request);

            Toast.makeText(getContext(), "Downloading song...", Toast.LENGTH_SHORT).show();

            // Optionally, save the path to the Library (SQLite or Firebase)
            //saveSongToLibrary(destination.getAbsolutePath());
        }
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