package com.charodi.notebookgames.SOS;

import android.graphics.Color;
import android.graphics.Point;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.*;
import android.util.Log;

import com.charodi.notebookgames.R;

public class SOSCell extends Point{

    public static final int NOT_SELECTED_CELL = 0;
    public static final int SELECTED_CELL = 1;

    public static final char VALUE_S = 'S';
    public static final char VALUE_O = 'O';

    int number;
    String text;
    int status;
    String options;

    int player;
    int scorePlayer1;
    int scorePlayer2;
    int countOfSelectedCell;
    boolean isComputer;
    boolean isOnline;
    
    char value;

    public final Rect selectedCell = new Rect();
    public final Rect sBoxSelection = new Rect();
    public final Rect oBoxSelection = new Rect();
    public final RectF player1Selection = new RectF();
    public final RectF player2Selection = new RectF();
    public final RectF ScoreBackGround1 = new RectF();
    public final RectF ScoreBackGround2 = new RectF();


    public SOSCell(int x, int y) {
        super(x, y);
        status = NOT_SELECTED_CELL;
        options = "0" ;
    }
    public SOSCell() {
        value = VALUE_S;
        player = 1;
        scorePlayer1 = 0;
        scorePlayer2 = 0;
        countOfSelectedCell = 0;
    }

    public void draw(Canvas g, Resources res, int x, int y, int w, int h, String Text, int z, int cellCount) {
        int textSize = 80 - (5 * (cellCount-5));

        Paint paintTool = new Paint();
        paintTool.setAntiAlias(true);
        paintTool.setTextSize(textSize);
        paintTool.setStyle(Style.FILL);
        paintTool.setTextAlign(Paint.Align.CENTER);
        paintTool.setARGB(255, 0, 0, 0);

        selectedCell.set(x * w, (y * h + z),
                (x * w + w), (y * h + h + z));

        Bitmap bmpClosedWindow = BitmapFactory.decodeResource(res, R.drawable.wooden_square);
        Bitmap bmpBrokenWindow = BitmapFactory.decodeResource(res, R.drawable.wooden_broken_square);
        g.drawBitmap(bmpClosedWindow, null, selectedCell, null);

        if(status == 1){
            Paint selected = new Paint();
            selected.setColor(Color.GREEN);

            paintTool.setColor(Color.YELLOW);
            paintTool.setTextAlign(Paint.Align.CENTER);

            g.drawBitmap(bmpBrokenWindow, null, selectedCell, null);
            g.drawText(Text, (float) ((x*w)+(w/2)), (float) ((y*h)+(2*w/3)+z), paintTool);

        } else if(status == 2) {
            Paint selected = new Paint();
            selected.setColor(Color.BLUE);
            g.drawLine((float) ((x*w)+(w/2)), (float) ((y * h + z)+(w/2)),
                    (float) ((x * w + w)+(w/2)), (float) ((y * h + h + z)+(w/2)), selected);

            paintTool.setARGB(255, 255, 0, 0);
        }
    }
    public void drawSnO(Canvas canvas, Resources res, int belowAreaEnd, int belowAreaBottom) {

        int belowAreaTop = belowAreaBottom/2 + belowAreaEnd/2;
        int belowAreaStart = 0;
        int belowAreaHeight = belowAreaBottom - belowAreaTop;
        int outerArea;
        int squareArea;

        if (belowAreaEnd/2 < belowAreaHeight) {
            outerArea = (belowAreaEnd / 2) / 5;
            squareArea = 3 * outerArea;

            sBoxSelection.set(belowAreaStart + outerArea, belowAreaTop + (belowAreaHeight/2) - (squareArea/2),
                    belowAreaEnd/2 - outerArea, belowAreaTop + (belowAreaHeight/2) + (squareArea/2));
            oBoxSelection.set(belowAreaEnd/2 + outerArea, belowAreaTop + (belowAreaHeight/2) - (squareArea/2),
                    belowAreaEnd - outerArea, belowAreaTop + (belowAreaHeight/2) + (squareArea/2));
        } else {
            Log.e("SOS", "inside else");
            outerArea = belowAreaHeight/5;
            squareArea = 3 * outerArea;

            sBoxSelection.set((belowAreaEnd/4) - (squareArea/2), belowAreaTop + outerArea,
                    (belowAreaEnd/4) + (squareArea/2), belowAreaBottom - outerArea);
            oBoxSelection.set((belowAreaEnd/4 * 3) - (squareArea/2), belowAreaTop + outerArea,
                    (belowAreaEnd/4 * 3) + (squareArea/2), belowAreaBottom - outerArea);

        }

        if(value == VALUE_S) {
            Bitmap bmpImageS = BitmapFactory.decodeResource(res, R.drawable.wooden_broken_square);
            canvas.drawBitmap(bmpImageS, null, sBoxSelection, null);

            Bitmap bmpImageO = BitmapFactory.decodeResource(res, R.drawable.wooden_square);
            canvas.drawBitmap(bmpImageO, null, oBoxSelection, null);
        }
        if(value == VALUE_O) {
            Bitmap bmpImageS = BitmapFactory.decodeResource(res, R.drawable.wooden_square);
            canvas.drawBitmap(bmpImageS, null, sBoxSelection, null);

            Bitmap bmpImageO = BitmapFactory.decodeResource(res, R.drawable.wooden_broken_square);
            canvas.drawBitmap(bmpImageO, null, oBoxSelection, null);
        }
    }

    public void drawPlayers(Canvas g, int x, int y, String Player1, String Player2) {

        player1Selection.set((float) x/10 -10, (float) ((y/2 - x/2) - y/5 - 10),
                (float) (x/2 - x/10 +10), (float) y/5 + 10);
        player2Selection.set((float) (x/2 + x/10 -10), (float) ((y/2 - x/2) - y/5 - 10),
                (float) (x/2 - x/10 + x/2 + 10), (float) y/5 + 10);
        ScoreBackGround1.set((float) x/6, (float) ((y/2 - x/2) - y/8), (float) (x/2 - x/6), (float) y/5);
        ScoreBackGround2.set((float) (x/2 + x/6), (float) ((y/2 - x/2) -y/8), (float) (x/2 + x/2 - x/6), (float)y/5);

        Paint selected2 = new Paint();
        selected2.setColor(Color.BLACK);

        if(player % 2 != 0) {
            g.drawRect(player1Selection,selected2);
        }
        else {
            g.drawRect(player2Selection,selected2);
        }

        Paint selected = new Paint();
        selected.setColor(Color.RED);
        selected.setStyle(Style.FILL);

        Paint paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setStyle(Style.FILL);
        paintText.setTextSize(60);
        paintText.setTextAlign(Paint.Align.CENTER);

        Paint selected1 = new Paint();
        selected1.setColor(Color.rgb(160, 32, 240));
        selected1.setStyle(Style.FILL);

        player1Selection.set((float) x/10, (float) ((y/2 - x/2) - y/5),
                (float) (x/2 - x/10), (float) y/5);
        player2Selection.set((float) (x/2 + x/10), (float) ((y/2 - x/2) - y/5),
                (float) (x/2 - x/10 + x/2), (float) y/5);

        g.drawRect(player1Selection,selected);
        g.drawRect(player2Selection,selected1);

        g.drawText(Player1, (float) x/4, (float) ((y/2 - x/2) - y/6), paintText);
        g.drawText(Player2, (float) (x/4 + x/2), (float) ((y/2 - x/2) - y/6), paintText);

        g.drawRect(ScoreBackGround1, paintText);
        g.drawRect(ScoreBackGround2, paintText);

        paintText.setColor(Color.WHITE);
        paintText.setTextSize(80);

        g.drawText(String.valueOf(scorePlayer1), (float) x/4, (float) ((y/2 - x/2) - y/12), paintText);
        g.drawText(String.valueOf(scorePlayer2), (float) (x/4 + x/2), (float) ((y/2 - x/2) - y/12), paintText);

    }

    public void drawStrikeThrough(Canvas canvas, int x, int y, int w, int h, String options, int z) {

        Paint selected5 = new Paint();
        selected5.setColor(Color.RED);
        selected5.setStyle(Style.FILL);
        selected5.setStrokeWidth(5);

        if(options.contains("1")) {
            canvas.drawLine((float) (x * w + w/2), (float) (y * h + z),
                    (float) (x * w + w/2), (float) (y * h + h + z), selected5);
        }
        if(options.contains("2")) {
            canvas.drawLine((float) (x * w), (float) (y * h + z + h/2),
                    (float) (x * w + w), (float) (y * h + h/2 + z), selected5);
        }
        if(options.contains("3")) {
            canvas.drawLine((float) (x * w), (float) (y * h + z),
                    (float) (x * w + w), (float) (y * h + h + z), selected5);
        }
        if(options.contains("4")) {
            canvas.drawLine((float) (x * w + h), (float) (y * h + z),
                    (float) (x * w), (float) (y * h + h + z), selected5);
        }

    }

}