package com.charodi.notebookgames.SOS;

import java.util.Objects;
import java.util.Random;

import android.os.Handler;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Canvas;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.charodi.notebookgames.R;
import com.charodi.notebookgames.RoomDetails;
import com.charodi.notebookgames.TicTacToe.PlayArena;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SOSGame extends View {

    public int TOTAL_NUMBERS;

    int rowCount;
    int columnCount;
    private final String player1;
    private final String player2;
    private final Paint paintTool;
    private final SOSCell[][] elements;
    private SOSCell element ;
    boolean gameOver = false;
    String mode = "null";
    String otherPlayer = "null";
    DatabaseReference reff, reff1;
    PlayArena playArena;
    String player;

    public SOSGame(Context context, String player1, String player2, String mode, int count, boolean isComputer, boolean isOnline, String player) {
        super(context);

        this.player1 = player1;
        this.player2 = player2;
        this.mode = mode;
        this.rowCount = count;
        this.columnCount = count;
        this.player = player;
        Log.e("Abhi", "Player " + player);
        this.setBackgroundResource(R.drawable.my_background4);

        paintTool = new Paint();
        this.paintTool.setARGB(255, 0, 0, 0);
        this.paintTool.setAntiAlias(true);
        this.paintTool.setStyle(Style.STROKE);
        this.paintTool.setStrokeWidth(1);

        this.TOTAL_NUMBERS = rowCount * columnCount;
        element = new SOSCell();
        playArena = new PlayArena();

        int cellHeight = this.getWidth() / rowCount;
        int cellWidth = this.getWidth() / columnCount;


        elements = new SOSCell[rowCount][columnCount];
        element.isComputer = isComputer;
        element.isOnline = isOnline;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                elements[i][j] = new SOSCell(cellHeight * j, cellWidth * i);
            }
        }
        SOSBoard newBoard = new SOSBoard(rowCount, columnCount);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                elements[i][j].number = newBoard.elements[i][j];
            }
        }

        if (player.equalsIgnoreCase("player1")) {
            otherPlayer = "Player2";
        } else {
            otherPlayer = "Player1";
        }

        //if(roomDetails.getTurn().equals(player) && roomDetails.getX_value() >= 0 && roomDetails.getY_value()>=0) {
        //   refreshData(roomDetails.getX_value(), roomDetails.getY_value(), roomDetails.getText());
        //}
        /*Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {*/


        reff = FirebaseDatabase.getInstance().getReference().child("Rooms").child("Abhi").child("PlayArena");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Log.e("SOS1", "x_Val: ");

                    String textVal = snapshot.child("text").getValue().toString();
                    int x_Val = Integer.parseInt(snapshot.child("x_value").getValue().toString());
                    int y_Val = Integer.parseInt(snapshot.child("y_value").getValue().toString());
                    String turnVal = snapshot.child("turn").getValue().toString();

                    Log.e("SOS1", "x_Val: " + x_Val + " y_Val: " + y_Val);
                    if (x_Val >= 0 && y_Val >= 0) {
                        refreshData(x_Val, y_Val, textVal);
                    }
                }

            }

            @Override

            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean getData() {

        return true;
    }

    private void refreshData(int x, int y, String text) {
        if(elements[y][x].status == SOSCell.NOT_SELECTED_CELL) {
            elements[y][x].status = SOSCell.SELECTED_CELL;       // To change the status of selected cell to 1
            element.countOfSelectedCell++;
            if (text.equals("S")) {
                elements[y][x].text = "S";
                element.value = 'S';
            }
            if (text.equals("O")) {
                elements[y][x].text = "O";
                element.value = 'O';
            }
            check(x, y);
            Log.e("SOS","Testttt  X: "+x +" Y: "+y);
            this.invalidate();
        }
    }
    /*
        This method is to draw the playing activity on every page refresh
        args    canvas      ;
        1. Creating m*m square tables; m = selected no. of square size
        2. Striking out the particular cell in particular direction based on options
        3. Creating Borders for player details or S,O for highlighting the box
        4. Creating a player details box for name and scorecard
        return ; it will not return anything
     */
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j].draw(canvas, getResources(), j, i, this.getWidth()/elements.length, (this.getWidth()/elements[0].length), elements[i][j].text,this.getHeight()/2 - this.getWidth()/2, this.rowCount);
                if (!elements[i][j].options.equals("0"))
                    elements[i][j].drawStrikeThrough(canvas, j, i, this.getWidth()/elements.length, (this.getWidth()/elements[0].length), elements[i][j].options, this.getHeight()/2 - this.getWidth()/2);
            }
        }
        element.drawSnO(canvas, getResources(), this.getWidth(), this.getHeight());
        element.drawPlayers(canvas, this.getWidth(), this.getHeight(), player1, player2);
        int cellHeight = this.getWidth()/rowCount;
        int cellWidth = this.getWidth()/columnCount;

        for (int i = 0; i <= columnCount; i++) {
            canvas.drawLine(cellWidth * i, this.getHeight()/2 - this.getWidth()/2, cellWidth * i, this.getWidth()+this.getHeight()/2 - this.getWidth()/2, paintTool);
        }
        for (int i = 0; i <= rowCount; i++) {
            canvas.drawLine(0, (cellHeight * i) + (this.getHeight()/2 - this.getWidth()/2), this.getWidth(), (cellHeight * i) + (this.getHeight()/2 - this.getWidth()/2), paintTool);
        }

        super.onDraw(canvas);
    }

    /*
        This method is to select a S,O or any cell from the table and also if the computer plays it calls another method called SelectA Number
        args    event
        1. For multiplayer, it gives option to select S or O box and then select a particular cell to add a value
        2. For Computer, it checks for a SOS if not then, it will select a random value on a random cell
        return  true / false ; based on the condition
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (!gameOver) {
                if (element.isComputer && element.player % 2 == 0) {      // To wait for computer to complete it's cell Selection
                    return false;
                }
                //if (element.isOnline && !roomDetails.getTurn().equals(player)) {
                  //  return false;
                //}

                int x_aux = (int) (event.getX() / (this.getWidth() / rowCount));
                int y_aux = (int) ((event.getY() - (this.getHeight() / 4)) / (this.getWidth() / columnCount));

                if (event.getX() >= (this.getWidth() / 2 + this.getWidth() / 9)
                        && event.getX() <= (this.getWidth() / 2 + this.getWidth() / 3)
                        && event.getY() >= this.getWidth() / 2 + this.getWidth() + 150
                        && event.getY() <= (this.getWidth() + this.getWidth() / 2 + 390)) {
                    element.value = SOSCell.VALUE_O;            // To select O box on the screen
                    this.invalidate();
                }

                if (event.getX() >= (this.getWidth() / 9) && event.getX() <= (this.getWidth() / 3)
                        && event.getY() >= this.getWidth() / 2 + this.getWidth() + 150
                        && event.getY() <= (this.getWidth() + this.getWidth() / 2 + 390)) {
                    element.value = SOSCell.VALUE_S;            // To select S box on the screen
                    this.invalidate();
                }

                if (event.getY() > (this.getHeight() / 2 - this.getWidth() / 2)
                        && event.getY() < (this.getHeight() / 2 + this.getWidth() / 2)
                        && elements[y_aux][x_aux].status == SOSCell.NOT_SELECTED_CELL) {
                    elements[y_aux][x_aux].status = SOSCell.SELECTED_CELL;       // To change the status of selected cell to 1
                    element.countOfSelectedCell++;
                    if (SOSCell.VALUE_S == element.value)
                        elements[y_aux][x_aux].text = "S";
                    else
                        elements[y_aux][x_aux].text = "O";
                    check(x_aux, y_aux);
                    if (elements[y_aux][x_aux].options.equals("0")) {       //If no SOS found then move to next player
                        element.player++;
                    }
                    Log.e("SOS","Y_aux: "+y_aux +"X_aux: "+x_aux);
                    this.invalidate();
                    if(element.isOnline) {
                        updateDatabase(x_aux, y_aux);
                    }
                    /*if (element.isComputer && !isGameOver() && elements[y_aux][x_aux].options.equals("0")) {  //If computer plays and SOS not found for previous player
                        Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            selectANumber();                            // To select a random cell with S or O and with a click gap of 2 seconds
                            isGameOver();
                        }, 2000L);
                    } else {
                        isGameOver();
                    }*/
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return false;
    }

    private void updateDatabase(int x_aux, int y_aux) {

        String players;
        reff = FirebaseDatabase.getInstance().getReference().child("Rooms").child("Abhi");
        if(player.equals("Player1")) {
            players = "Player2";
        } else {
            players = "Player1";
        }

        String text = String.valueOf(element.value);
        playArena.setText(text);
        playArena.setTurn(players);
        playArena.setX_value(x_aux);
        playArena.setY_value(y_aux);
        reff.child("PlayArena").setValue(playArena);
/*
        String text = String.valueOf(element.value);
        reff.child("x_value").setValue(x_aux);
        reff.child("y_value").setValue(y_aux);
        reff.child("text").setValue(text);

        Log.e("SOS","y_aux: "+y_aux +" x_aux: "+x_aux);
        if(player.equals("Player1")) {
            reff.child("turn").setValue("Player2");
        } else {
            reff.child("turn").setValue("Player1");
        }*/
    }

    public void check(int x_aux, int y_aux) {
        int leftHorizontal      = count('A',y_aux,x_aux,-1,0);
        int rightHorizontal     = count('A',y_aux,x_aux,1,0);
        int upperVertical       = count('A',y_aux,x_aux,0,-1);
        int lowerVertical       = count('A',y_aux,x_aux,0,1);
        int upperLeftDiagonal   = count('A',y_aux,x_aux,-1,-1);
        int lowerRightDiagonal  = count('A',y_aux,x_aux,1,1);
        int upperRightDiagonal  = count('A',y_aux,x_aux,-1,1);
        int lowerLeftDiagonal   = count('A',y_aux,x_aux,1,-1);

        if (SOSCell.VALUE_S == element.value) {
            if (leftHorizontal >= 2 || rightHorizontal >= 2) {
                if (leftHorizontal >= 2) {
                    elements[y_aux - 1][x_aux].options += "1";
                    elements[y_aux - 2][x_aux].options += "1";
                }
                if (rightHorizontal >= 2) {
                    elements[y_aux + 1][x_aux].options += "1";
                    elements[y_aux + 2][x_aux].options += "1";
                }
                elements[y_aux][x_aux].options += "1";
                updateScore();
            }
            if (upperVertical >= 2 || lowerVertical >= 2) {
                if (upperVertical >= 2) {
                    elements[y_aux][x_aux - 1].options += "2";
                    elements[y_aux][x_aux - 2].options += "2";
                }
                if (lowerVertical >= 2) {
                    elements[y_aux][x_aux + 1].options += "2";
                    elements[y_aux][x_aux + 2].options += "2";
                }
                elements[y_aux][x_aux].options += "2";
                updateScore();
            }
            if (upperLeftDiagonal >= 2 || lowerRightDiagonal >= 2) {
                if (upperLeftDiagonal >= 2) {
                    elements[y_aux - 1][x_aux - 1].options += "3";
                    elements[y_aux - 2][x_aux - 2].options += "3";
                }
                if (lowerRightDiagonal >= 2) {
                    elements[y_aux + 1][x_aux + 1].options += "3";
                    elements[y_aux + 2][x_aux + 2].options += "3";
                }
                elements[y_aux][x_aux].options += "3";
                updateScore();
            }
            if (upperRightDiagonal >= 2 || lowerLeftDiagonal >= 2) {
                if (upperRightDiagonal >= 2) {
                    elements[y_aux - 1][x_aux + 1].options += "4";
                    elements[y_aux - 2][x_aux + 2].options += "4";
                }
                if (lowerLeftDiagonal >= 2) {
                    elements[y_aux + 1][x_aux - 1].options += "4";
                    elements[y_aux + 2][x_aux - 2].options += "4";
                }
                elements[y_aux][x_aux].options += "4";
                updateScore();
            }
        }

        if (SOSCell.VALUE_O == element.value) {
            if (leftHorizontal + rightHorizontal + 1 >= 3) {
                if (leftHorizontal >= 1) {
                    elements[y_aux - 1][x_aux].options += "1";
                }
                if (rightHorizontal >= 1) {
                    elements[y_aux + 1][x_aux].options += "1";
                }
                elements[y_aux][x_aux].options += "1";
                updateScore();
            }
            if (upperVertical + lowerVertical + 1 >= 3) {
                if (upperVertical >= 1) {
                    elements[y_aux][x_aux - 1].options += "2";
                }
                if (lowerVertical >= 1) {
                    elements[y_aux][x_aux + 1].options += "2";
                }
                elements[y_aux][x_aux].options += "2";
                updateScore();
            }
            if (upperLeftDiagonal + lowerRightDiagonal + 1 >= 3) {
                if (upperLeftDiagonal >= 1) {
                    elements[y_aux - 1][x_aux - 1].options += "3";
                }
                if (lowerRightDiagonal >= 1) {
                    elements[y_aux + 1][x_aux + 1].options += "3";
                }
                elements[y_aux][x_aux].options += "3";
                updateScore();
            }
            if (upperRightDiagonal + lowerLeftDiagonal + 1 >= 3) {
                if (upperRightDiagonal >= 1) {
                    elements[y_aux - 1][x_aux + 1].options += "4";
                }
                if (lowerLeftDiagonal >= 1) {
                    elements[y_aux + 1][x_aux - 1].options += "4";
                }
                elements[y_aux][x_aux].options += "4";
                updateScore();
            }
        }

    }

    public void updateScore() {
        if(element.isOnline) {
            //....
        } else {
            if (element.player % 2 == 0) {
                element.scorePlayer2++;
            } else {
                element.scorePlayer1++;
            }
        }
    }

    public int count(char type, int x, int y, int dx, int dy) { //0,6,1,0
        int count = 0;
        x += dx;
        y += dy;

        if(element.player % 2 != 0) {
            if(SOSCell.VALUE_S == element.value) {
                count = count_S(x, y, dx, dy);
            }
            if(SOSCell.VALUE_O == element.value) {
                count = count_O(x, y, dx, dy);
            }
        } else {
            if (type == 'S' || (type == 'A' && SOSCell.VALUE_S == element.value)) {
                count = count_S(x, y, dx, dy);
            }
            if (type == 'O' || (type == 'A' && SOSCell.VALUE_O == element.value)) {
                count = count_O(x, y, dx, dy);
            }
        }

        return count;
    }

    private int count_S(int x, int y, int dx, int dy) {
        int count   = 0;
        int i       = 2;

        while (x >= 0 && x < rowCount && y >= 0 && y < columnCount && i > 0) {
            if (i % 2 != 0 && Objects.equals(elements[x][y].text, "S")) {
                count++;
            }
            if (i % 2 == 0 && Objects.equals(elements[x][y].text, "O")) {
                count++;
            }

            x += dx;
            y += dy;
            i--;
        }

        return count;
    }

    private int count_O(int x, int y, int dx, int dy) {
        int count   = 0;
        int i       = 1;

        while (x >= 0 && x < rowCount && y >= 0 && y < columnCount && i > 0) {
            if (Objects.equals(elements[x][y].text, "S")) {
                count++;
            }
            x += dx;
            y += dy;
            i--;
        }
        return count;
    }

    private boolean isGameOver(){
        if(element.countOfSelectedCell == this.TOTAL_NUMBERS) {
            gameOver = true;
            if(element.scorePlayer1 > element.scorePlayer2) {
                SOSApplication.result = SOSApplication.WIN;
            } else if (element.scorePlayer2 > element.scorePlayer1) {
                SOSApplication.result = SOSApplication.FAIL;
            } else {
                SOSApplication.result = SOSApplication.TIE;
            }
            showResultPage();
        }
        return gameOver;
    }

    private void showResultPage(){
        Intent intent = new Intent(this.getContext().getApplicationContext(), ResultActivity.class);
        if(SOSApplication.result == SOSApplication.WIN){
            intent.putExtra("winner", this.player1);
        } else if(SOSApplication.result == SOSApplication.FAIL){
            intent.putExtra("winner", this.player2);
        } if(SOSApplication.result == SOSApplication.TIE) {
            intent.putExtra("winner", "draw");
        }
        intent.putExtra("playerOne", player1);
        intent.putExtra("playerTwo", player2);
        intent.putExtra("count", rowCount);
        intent.putExtra("isComputer", element.isComputer);
        intent.putExtra("mode", mode);
        this.getContext().startActivity(intent);
    }

    private void selectANumber() {
        int selectedNumber = 0;
        int bound = 0;
        String randomStr = "";
        if (mode.equalsIgnoreCase("Hard")) {
            bound = 1;
        }
        if (mode.equalsIgnoreCase("Medium")) {
            bound = 3;
        }
        if (mode.equalsIgnoreCase("Easy")) {
            bound = 4;
        }

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(bound);
        if(randomInt == 0) {
            selectedNumber = checkForSOS();
        }


        if (selectedNumber == 0) {
            int[] notSelectedNumbers = new int[TOTAL_NUMBERS];
            int count = 0;
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    if (elements[i][j].status == SOSCell.NOT_SELECTED_CELL) {
                        notSelectedNumbers[count] = elements[i][j].number;
                        count++;
                    }
                }
            }

            if (count > 0) {
                Random randomGenerator1 = new Random();
                int randomInt1 = randomGenerator1.nextInt(count);
                selectedNumber = notSelectedNumbers[randomInt1];
                randomStr = String.valueOf(randomGenerator1.nextBoolean());
            }
        }

        int posX = 0, posY = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (elements[i][j].number == selectedNumber) {
                    posX = i;
                    posY = j;
                }
            }
        }

        if (randomStr.equals("true")) {
            element.value = SOSCell.VALUE_S;
            elements[posX][posY].text = "S";
        }
        if (randomStr.equals("false")) {
            element.value = SOSCell.VALUE_O;
            elements[posX][posY].text = "O";
        }

        elements[posX][posY].status = SOSCell.SELECTED_CELL;
        element.countOfSelectedCell++;
        check(posY, posX);

        isGameOver();
        this.invalidate();
        if (elements[posX][posY].options.equals("0")) {
            element.player++;
        } else {
            Handler handler = new Handler();
            handler.postDelayed(this::selectANumber, 2000);
        }

    }

    private int checkForSOS() {
        for (int x_aux = 0; x_aux < rowCount; x_aux++) {
            for (int y_aux = 0; y_aux < columnCount; y_aux++) {
                if (elements[x_aux][y_aux].status == SOSCell.NOT_SELECTED_CELL) {

                    int leftHorizontal      = count('S', x_aux, y_aux, -1, 0);
                    int rightHorizontal     = count('S', x_aux, y_aux, 1, 0);
                    int upperVertical       = count('S', x_aux, y_aux, 0, -1);
                    int lowerVertical       = count('S', x_aux, y_aux, 0, 1);
                    int upperLeftDiagonal   = count('S', x_aux, y_aux, -1, -1);
                    int lowerRightDiagonal  = count('S', x_aux, y_aux, 1, 1);
                    int upperRightDiagonal  = count('S', x_aux, y_aux, -1, 1);
                    int lowerLeftDiagonal   = count('S', x_aux, y_aux, 1, -1);

                    if (leftHorizontal == 2 || rightHorizontal == 2
                            || upperVertical == 2 || lowerVertical == 2
                            || upperLeftDiagonal == 2 || lowerRightDiagonal == 2
                            || upperRightDiagonal == 2 || lowerLeftDiagonal == 2) {
                        element.value = SOSCell.VALUE_S;
                        elements[x_aux][y_aux].text = "S";
                        return elements[x_aux][y_aux].number;
                    }

                    int leftHorizontal1     = count('O', x_aux, y_aux, -1, 0);
                    int rightHorizontal1    = count('O', x_aux, y_aux, 1, 0);
                    int upperVertical1      = count('O', x_aux, y_aux, 0, -1);
                    int lowerVertical1      = count('O', x_aux, y_aux, 0, 1);
                    int upperLeftDiagonal1  = count('O', x_aux, y_aux, -1, -1);
                    int lowerRightDiagonal1 = count('O', x_aux, y_aux, 1, 1);
                    int upperRightDiagonal1 = count('O', x_aux, y_aux, -1, 1);
                    int lowerLeftDiagonal1  = count('O', x_aux, y_aux, 1, -1);

                    if (leftHorizontal1 + rightHorizontal1 == 2
                            || upperVertical1 + lowerVertical1 == 2
                            || upperLeftDiagonal1 + lowerRightDiagonal1 == 2
                            || upperRightDiagonal1 + lowerLeftDiagonal1 == 2) {
                        element.value = SOSCell.VALUE_O;
                        elements[x_aux][y_aux].text = "O";
                        return elements[x_aux][y_aux].number;
                    }
                }
            }
        }
        return 0;
    }

}
