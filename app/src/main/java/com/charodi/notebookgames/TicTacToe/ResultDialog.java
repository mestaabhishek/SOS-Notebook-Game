package com.charodi.notebookgames.TicTacToe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.charodi.notebookgames.R;

public class ResultDialog extends Dialog {

    private final String message;
    private final TicTacToeGame ticTacToeGame;

    public ResultDialog(@NonNull Context context, String message, TicTacToeGame ticTacToeGame) {
        super(context);
        this.message = message;
        this.ticTacToeGame = ticTacToeGame;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog);

        TextView messageText = findViewById(R.id.messageText);
        Button startAgainButton = findViewById(R.id.startAgainButton);

        messageText.setText(message);

        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ticTacToeGame.restartMatch();
                dismiss();
            }
        });
    }
}