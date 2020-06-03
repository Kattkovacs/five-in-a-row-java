package com.codecool.fiveinarow;

//import javax.xml.bind.SchemaOutputResolver;
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
        String[] inputArr = input.split("", 2);

        while (!inputValidation(inputArr)){
            input = getInput();
            inputArr = input.split("", 2);
        };

        String rowHeaders = getRowHeaders();
        String[] colHeaders = getColHeaders();
        int rowIndex = rowHeaders.indexOf(inputArr[0].toUpperCase());
        int colIndex = Integer.parseInt(inputArr[1]) - 1;
        int[] coordinates = {rowIndex, colIndex};
        return coordinates;
    }

    public String getRowHeaders() {
        String rowHeaders ="";
        char c = 'A';
        for (int i=0; i< this.board.length; i++) {
            rowHeaders += c;
            c++;
        }
        return rowHeaders;
    }

    public String[] getColHeaders() {
        String[] colHeaders = new String[this.board[0].length];
        int id = 1;
        for (int i = 0; i < this.board[0].length; i++) {
            colHeaders[i] = Integer.toString(id);
            id++;
        }
        return colHeaders;
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

    public boolean inputValidation(String[] inputArr){
        String rowHeaders = getRowHeaders();
        String[] colHeaders = getColHeaders();
        int rowIndex = rowHeaders.indexOf(inputArr[0].toUpperCase());
        int colIndex = Integer.parseInt(inputArr[1]) - 1;
        int[] coordinates = {rowIndex, colIndex};

        if(!rowHeaders.contains(inputArr[0].toUpperCase())){
            System.out.println(inputArr[0] + " is an invalid row coordinate!");
            return false;
        }
        if(!Arrays.asList(colHeaders).contains(inputArr[1])){
            System.out.println(inputArr[1] + " is an invalid col coordinate!");
            return false;
        }
        if(this.board[rowIndex][colIndex] != 0){
            System.out.println("This coordinate is already used!");
            return false;
        }

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
