package com.charodi.notebookgames.SOS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.charodi.notebookgames.MainActivity;
import com.charodi.notebookgames.R;
import com.charodi.notebookgames.RoomDetails;
import com.charodi.notebookgames.TicTacToe.PlayArena;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlayOnline extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int count = 0;
    RoomDetails roomDetails;
    PlayArena playArena;

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_online);

        reff= FirebaseDatabase.getInstance().getReference().child("Rooms");
        EditText playerOne = findViewById(R.id.playerOne);
        Spinner spinner = findViewById(R.id.spinnerValues);
        Button createGameButton = findViewById(R.id.createGameButton);
        Button joinGameButton = findViewById(R.id.joinGameButton);

        roomDetails = new RoomDetails();
        playArena = new PlayArena();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.combinations,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getPlayerOneName = playerOne.getText().toString();

                if (getPlayerOneName.isEmpty()) {
                    Toast.makeText(PlayOnline.this, "Please enter player name", Toast.LENGTH_SHORT).show();
                } else {

                    roomDetails.setPlayer1(getPlayerOneName);
                    roomDetails.setPlayer2("null");
                    roomDetails.setBox(count);
                    roomDetails.setRoomName("Abhi");
                    reff.child("Abhi").setValue(roomDetails);

                    playArena.setText("S");
                    playArena.setTurn("Player1");
                    playArena.setX_value(-1);
                    playArena.setY_value(-1);
                    reff.child("Abhi").child("PlayArena").setValue(playArena);

                    Intent intent = new Intent(PlayOnline.this, WaitingActivity.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("playerTwo", "Computer");
                    intent.putExtra("count", count);
                    intent.putExtra("isComputer", false);
                    intent.putExtra("mode", "null");
                    intent.putExtra("isOnline", true);
                    intent.putExtra("player", "Player1");
                    startActivity(intent);
                }
            }
        });

        joinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getPlayerOneName = playerOne.getText().toString();

                if (getPlayerOneName.isEmpty()) {
                    Toast.makeText(PlayOnline.this, "Please enter player name", Toast.LENGTH_SHORT).show();
                } else {

                    reff.child("Abhi").child("player2").setValue(getPlayerOneName);

                    Intent intent = new Intent(PlayOnline.this, WaitingActivity.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("playerTwo", "Computer");
                    intent.putExtra("count", count);
                    intent.putExtra("isComputer", false);
                    intent.putExtra("mode", "null");
                    intent.putExtra("isOnline", true);
                    intent.putExtra("player", "Player2");
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        count = i + 5;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PlayOnline.this, MainActivity.class);
        startActivity(intent);
    }

}