package com.charodi.notebookgames.SOS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.charodi.notebookgames.R;
import com.charodi.notebookgames.RoomDetails;
import com.charodi.notebookgames.TicTacToe.PlayArena;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WaitingActivity extends AppCompatActivity {

    DatabaseReference reff;
    RoomDetails roomDetails;
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        String getPlayerName = getIntent().getStringExtra("player");

        reff= FirebaseDatabase.getInstance().getReference().child("Rooms");
        roomDetails = new RoomDetails();

        if (!isNetworkAvailable(WaitingActivity.this)) {
            Toast.makeText(WaitingActivity.this, "CHECK YOUR INTERNET CONNECTIVITY...", Toast.LENGTH_SHORT).show();
        }

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                reff.orderByChild("roomName").equalTo("Abhi").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            String player1 = snapshot.child("Abhi").child("player1").getValue().toString();
                            String player2 = snapshot.child("Abhi").child("player2").getValue().toString();
                            int count = Integer.parseInt(snapshot.child("Abhi").child("box").getValue().toString());
                            Log.e("SOS","Player1: "+player1+"Plyer2: "+player2);
                            if (!player1.equals("null") && !player2.equals("null")) {
                                Intent intent = new Intent(WaitingActivity.this, SOSActivity.class);
                                intent.putExtra("playerOne", player1);
                                intent.putExtra("playerTwo", player2);
                                intent.putExtra("count", count);
                                intent.putExtra("isComputer", false);
                                intent.putExtra("mode", "null");
                                intent.putExtra("isOnline", true);
                                intent.putExtra("player", getPlayerName);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(WaitingActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                handler.postDelayed(this, 2000);
            }
        },2000);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }
    private boolean isNetworkAvailable(WaitingActivity mainActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }
}