package com.charodi.notebookgames.SOS;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.charodi.notebookgames.MainActivity;
import com.charodi.notebookgames.R;

public class QuitGameDialog extends Dialog {

    private final SOSActivity sosActivity;
    private final MainActivity mainActivity;
    private final String message;

    public QuitGameDialog(@NonNull Context context, String message, SOSActivity sosActivity) {
        super(context);
        this.message = message;
        this.sosActivity = sosActivity;
        this.mainActivity = null;
    }

    public QuitGameDialog(@NonNull Context context, String message, MainActivity mainActivity) {
        super(context);
        this.message = message;
        this.sosActivity = null;
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit_game_dialog);

        TextView messageText = findViewById(R.id.messageText);
        Button yesButton = findViewById(R.id.yesButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        messageText.setText(message);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sosActivity != null)
                    sosActivity.closeMatch();
                if (mainActivity != null)
                    mainActivity.closeGame();
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}