package com.charodi.notebookgames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.charodi.notebookgames.SOS.AddPlayer;
import com.charodi.notebookgames.SOS.AddPlayers;
import com.charodi.notebookgames.SOS.PlayOnline;
import com.charodi.notebookgames.SOS.QuitGameDialog;
import com.charodi.notebookgames.SOS.SOSActivity;

public class MainActivity extends AppCompatActivity {


    /**
     *
     */
    @Override
    public void onBackPressed() {
        String message = "Do you really want to quit the game?";
        QuitGameDialog quitGameDialog = new QuitGameDialog(MainActivity.this, message, MainActivity.this);
        quitGameDialog.setCancelable(false);
        quitGameDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playVideo();

        LinearLayout layoutComputer, layoutFriend, layoutOnline;

        layoutComputer = findViewById(R.id.playWithComputer);
        layoutFriend = findViewById(R.id.playWithFriend);
        layoutOnline = findViewById(R.id.playOnline);

        layoutComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddPlayer.class);
                startActivity(i);
            }
        });

        layoutFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddPlayers.class);
                startActivity(i);
            }
        });

        layoutOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PlayOnline.class);
                startActivity(i);
            }
        });

    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        playVideo();
    }

    public void playVideo() {
        final VideoView videoView = findViewById(R.id.videoView3);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.home_background);
        videoView.setVideoURI(uri);
        videoView.requestFocus();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });

        videoView.start();
    }

    public void closeGame() {
        Toast.makeText(this,"Closed", Toast.LENGTH_SHORT).show();
    }
}