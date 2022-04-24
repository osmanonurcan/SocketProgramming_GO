/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author osman
 */
public class deneme {

    static int size = 4;
    static int[][] board = new int[size][size];
    static int myColor = 2;
    static int[][] checkTable = new int[size][size];

    //siyah 1
    //beyaz 2
    //no check 0
    public static void main(String[] args) {

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board[r][c] = 0;
            }
        }
        board[1][1] = 1;
        board[1][2] = 1;
        board[2][1] = 1;
        board[2][2] = 1;
        int[] scores = score(board, myColor);
        System.out.println(scores[0]);
        

    }

    public static int[] score(int[][] board, int myColor) {

        int myScore = 0;
        int enemyScore = 0;
        

        isCaptured(size / 2, size / 2, myScore);

        return new int[]{myScore, enemyScore};
    }
    
    public static boolean isFullcheckTable(){
        
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if(checkTable[r][c] == 0){
                    return false;
                }
            }
        }
        return true;
    
    }

    public static boolean isCaptured(int row, int col, int myScore) {
        boolean right, bottom, left, top = false;
        if(isFullcheckTable()){
            return false;
        }
        
        
        if (checkTable[row][col] == 1) {
            
            return true;
        }
        if (checkTable[row][col] == 2) {
            

            return false;
        }
        if (checkTable[row][col] == 3) {
            
            return true;
        }

        if (row == 0 || row == size - 1 || col == 0 || col == size - 1) { //check border
            checkTable[row][col] = 2;
            if (col != size - 1 && checkTable[row][col+1] == 0) {
                right = isCaptured(row, col + 1, myScore); //check right
            }
            if (row != size - 1&& checkTable[row+1][col] == 0) {
                bottom = isCaptured(row + 1, col, myScore); //check bottom
            }
            if (col != 0&& checkTable[row][col-1] == 0) {
                left = isCaptured(row, col - 1, myScore); //check left
            }
            if (row != 0&& checkTable[row-1][col] == 0) {
                top = isCaptured(row - 1, col, myScore); //check top
            }
            return false;

        } else {
            if (board[row][col] == myColor) {
                checkTable[row][col] = 3;
                right = isCaptured(row, col + 1, myScore); //check right
                bottom = isCaptured(row + 1, col, myScore); //check bottom
                left = isCaptured(row, col - 1, myScore); //check left
                top = isCaptured(row - 1, col, myScore); //check top
                return true;
            } else {
                right = isCaptured(row, col + 1, myScore); //check right
                bottom = isCaptured(row + 1, col, myScore); //check bottom
                left = isCaptured(row, col - 1, myScore); //check left
                top = isCaptured(row - 1, col, myScore); //check top
                if (right == true && bottom == true && left == true && top == true) {
                    checkTable[row][col] = 1;
                    myScore++;
                    return true;
                } else {
                    checkTable[row][col] = 2;
                    return false;
                }
            }
        }

    }
}
