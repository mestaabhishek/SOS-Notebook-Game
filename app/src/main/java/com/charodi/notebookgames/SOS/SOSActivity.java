package com.charodi.notebookgames.SOS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.charodi.notebookgames.MainActivity;
import com.charodi.notebookgames.R;
import com.charodi.notebookgames.R.id;
import com.charodi.notebookgames.TicTacToe.ResultDialog;
import com.charodi.notebookgames.TicTacToe.TicTacToeGame;

public class SOSActivity extends Activity {
    /** Called when the activity is first created. */

    private SOSGame game;
    String getPlayerName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");
        int count = getIntent().getIntExtra("count", 5);
        String gameMode = getIntent().getStringExtra("mode");
        boolean isComputer = getIntent().getBooleanExtra("isComputer", false);
        boolean isOnline = getIntent().getBooleanExtra("isOnline", false);
        if(isOnline) {
            getPlayerName = getIntent().getStringExtra("player");
        }

        game = new SOSGame(this, getPlayerOneName, getPlayerTwoName, gameMode, count, isComputer, isOnline, getPlayerName);
        setContentView(game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");
        int count = getIntent().getIntExtra("count", 5);
        String gameMode = getIntent().getStringExtra("mode");
        boolean isComputer = getIntent().getBooleanExtra("isComputer", false);
        boolean isOnline = getIntent().getBooleanExtra("isOnline", false);
        if(isOnline) {
            getPlayerName = getIntent().getStringExtra("player");
        }

        game = new SOSGame(this, getPlayerOneName, getPlayerTwoName, gameMode, count, isComputer, isOnline, getPlayerName);
        this.setContentView(game);
        return true;
    }

    private void newGame(){
        String getPlayerOneName = getIntent().getStringExtra("playerOne");
        String getPlayerTwoName = getIntent().getStringExtra("playerTwo");
        int count = getIntent().getIntExtra("count", 5);
        String gameMode = getIntent().getStringExtra("mode");
        boolean isComputer = getIntent().getBooleanExtra("isComputer", false);
        boolean isOnline = getIntent().getBooleanExtra("isOnline", false);
        if(isOnline) {
            getPlayerName = getIntent().getStringExtra("player");
        }

        game = new SOSGame(this, getPlayerOneName, getPlayerTwoName, gameMode, count, isComputer, isOnline, getPlayerName);
        this.setContentView(game);
    }

    /**
     *
     */
    @Override
    public void onBackPressed() {
        String message = "Do you really want to quit this match?";
        QuitGameDialog quitGameDialog = new QuitGameDialog(SOSActivity.this, message, SOSActivity.this);
        quitGameDialog.setCancelable(false);
        quitGameDialog.show();
    }

    public void closeMatch() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
