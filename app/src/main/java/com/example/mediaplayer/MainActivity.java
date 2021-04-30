package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.button);
        seekBar = findViewById(R.id.seekbar);


        mediaPlayer = new MediaPlayer();
       // mediaPlayer.create(MainActivity.this,R.raw.master_theme);



        try {
            mediaPlayer.setDataSource("https://buildappswithpaulo.com/music/watch_me.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }


        MediaPlayer.OnPreparedListener preparedListener;
        preparedListener=new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(final MediaPlayer playerM){
                seekBar.setMax(playerM.getDuration());

                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (playerM.isPlaying()){
                            mediaPlayer.pause();
                            playButton.setText(R.string.play_text);
                        }else{
                            playerM.start();
                            playButton.setText(R.string.pause_text);
                        }
                    }
                });

            }
        };


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int duration = mp.getDuration();
                Toast.makeText(MainActivity.this, "Duration: "+ ((duration/1000)/60), Toast.LENGTH_SHORT).show();
            }
        });

        mediaPlayer.setOnPreparedListener(preparedListener);
        mediaPlayer.prepareAsync();



       seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if (fromUser){
                   mediaPlayer.seekTo(progress);
               }
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });






  }

//    private void playMusic() {
//        if (mediaPlayer!= null){
//
//        }
//    }
//
//    private void pauseMusic() {
//        if (mediaPlayer != null){
//
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
        mediaPlayer.release();
    }
}