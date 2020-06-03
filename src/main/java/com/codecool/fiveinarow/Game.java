package com.codecool.fiveinarow;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Arrays;
import java.util.Scanner;

public class Game implements GameInterface {

    private int[][] board;

    public Game(int nRows, int nCols) {
        int[][] newBoard = new int[nRows][nCols];

        /** Print the rows of the board - ONLY FOR TEST PURPOSES! */
        for (int[] row : newBoard) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("------ End of test section ------");

        setBoard(newBoard);

    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getMove(int player) {
        String input = getInput();
        String[] inputArr = input.split("");
        inputValidation(inputArr);
        String rowHeaders = "";
        for (char c = 'A'; c < this.board[0].length; c++) {
            rowHeaders = new StringBuilder().append(c).toString();
        }
        System.out.println(rowHeaders);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int rowIndex = alphabet.indexOf(inputArr[0]);
        int colIndex = Integer.parseInt(inputArr[1]) - 1;
        int[] coordinates = {rowIndex, colIndex};
        return coordinates;
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public static String getInput(){

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a coordinate:");
        String coordinate = scan.next();
        return coordinate;
    }

    public static boolean inputValidation(String[] inputArr){
        return true;
    }

    public void mark(int player, int row, int col) {
    }

    public boolean hasWon(int player, int howMany) {
        return false;
    }

    public boolean isFull() {
        return false;
    }

    public void printBoard() {
        int nCols = this.board[0].length;
        int aligner = 4;
        String stringFormat = "%" + aligner + "s";
        String numberFormat = "%" + aligner + "d";
        String blank = " ";

        /** Print header for columns */
        System.out.printf(stringFormat, blank);
        for (int colId=1; colId <= nCols; colId++) {
            System.out.printf(numberFormat, colId);
        }
        System.out.println();

        /** Print row id and row content for each row */
        char rowId = 'A';
        for (int[] row : this.board) {
            System.out.printf(stringFormat, rowId);
            for (int num: row) {
                char sign;
                switch (num) {
                    case 1:
                        sign = 'X';
                        break;
                    case 2:
                        sign = 'O';
                        break;
                    default: sign = '.';
                }
                System.out.printf(stringFormat, sign);
            }
            System.out.println();
            rowId++;
        }
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
        printBoard();
        getMove(1);
    }
}
