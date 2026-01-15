package com.charodi.notebookgames.SOS;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.charodi.notebookgames.MainActivity;
import com.charodi.notebookgames.R;

public class ResultActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String winnerName = getIntent().getStringExtra("winner");
        String playerOneName = getIntent().getStringExtra("playerOne");
        String playerTwoName = getIntent().getStringExtra("playerTwo");
        int count = getIntent().getIntExtra("count", 5);
        String gameMode = getIntent().getStringExtra("mode");
        boolean isComputer = getIntent().getBooleanExtra("isComputer", true);

        final Button playAgain = (Button) findViewById(R.id.play);
        final TextView winner = (TextView) findViewById(R.id.winnerName);

        playVideo();
        winner.setText(winnerName);

        playAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                Intent intent = new Intent(ResultActivity.this, SOSActivity.class);
                intent.putExtra("playerOne", playerOneName);
                intent.putExtra("playerTwo", playerTwoName);
                intent.putExtra("count", count);
                intent.putExtra("isComputer", isComputer);
                intent.putExtra("mode", gameMode);
                startActivity(intent);
            }
        });
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        // Perform action on clicks
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
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
        final VideoView videoView = findViewById(R.id.backgdVideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.result_video);
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
}
