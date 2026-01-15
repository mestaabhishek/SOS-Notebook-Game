package com.charodi.notebookgames.SOS;

import java.util.Random;

public class SOSBoard {
    int[][] elements;
    String[][] elementStr;

    public SOSBoard(int rowCount, int columnCount){
        elements = new int[rowCount][columnCount];
        elementStr = new String[rowCount][columnCount];

        int count =1;

        for(int i=0; i < rowCount; i++){
            for(int j=0; j < columnCount; j++){
                elements[i][j] = count;
                count++;
            }
        }
    }
}
