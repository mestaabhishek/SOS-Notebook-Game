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

public class AddPlayer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    int count = 0;
    String mode = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        EditText playerOne = findViewById(R.id.playerOne);
        RadioButton modeEasy = findViewById(R.id.modeEasy);
        RadioButton modeHard = findViewById(R.id.modeHard);
        Spinner spinner = findViewById(R.id.spinnerValues);
        Button startGameButton = findViewById(R.id.startGameButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.combinations,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        modeEasy.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((RadioButton)v).setChecked(true);
                ((RadioButton)v).setSelected(true);
                mode = "Easy";
            }
        });

        modeHard.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((RadioButton)v).setChecked(true);
                ((RadioButton)v).setSelected(true);
                mode = "Hard";
            }
        });

        if(modeHard.isChecked()) {
            mode = "Hard";
        }
        if(modeEasy.isChecked()) {
            mode = "Easy";
        }

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getPlayerOneName = playerOne.getText().toString();

                if (getPlayerOneName.isEmpty()) {
                    Toast.makeText(AddPlayer.this, "Please enter player name", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddPlayer.this, SOSActivity.class);
                    intent.putExtra("playerOne", getPlayerOneName);
                    intent.putExtra("playerTwo", "Computer");
                    intent.putExtra("count", count);
                    intent.putExtra("isComputer", true);
                    intent.putExtra("mode", mode);
                    intent.putExtra("isOnline", false);
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
        Intent intent = new Intent(AddPlayer.this, MainActivity.class);
        startActivity(intent);
    }

}