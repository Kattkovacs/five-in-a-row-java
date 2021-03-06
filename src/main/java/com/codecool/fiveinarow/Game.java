package com.codecool.fiveinarow;

//import javax.xml.bind.SchemaOutputResolver;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.*;

public class Game implements GameInterface {

    private int[][] board;

    public Game(int nRows, int nCols) {
        int[][] newBoard = new int[nRows][nCols];
        setBoard(newBoard);
    }

    public int[][] getBoard() {
        return board;
    }

    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getMove(int player) {
        String input = getInput();
        if (input.toLowerCase().equals("quit") || input.toLowerCase().equals("q")) {
            quitGame();
            return null;
        } else {
            String[] inputArr = input.split("", 2);

            while (!inputValidation(inputArr, player)) {
                input = getInput();
                inputArr = input.split("", 2);
            }
            String rowHeaders = getRowHeaders();
            String[] colHeaders = getColHeaders();
            int rowIndex = rowHeaders.indexOf(inputArr[0].toUpperCase());
            int colIndex = Integer.parseInt(inputArr[1]) - 1;
            int[] coordinates = {rowIndex, colIndex};
            return coordinates;
        }
    }

    public String getRowHeaders() {
        String rowHeaders = "";
        char c = 'A';
        for (int i = 0; i < this.board.length; i++) {
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

    public void displayWhoTurns(int player) {
        if (player == 1) {
            System.out.println("\n" + RED_BOLD_BRIGHT + "'X' turns!" + RESET);
        } else {
            System.out.println("\n" + BLUE_BOLD_BRIGHT + "'O' turns!" + RESET);
        }
    }

    public static String getInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n"+ YELLOW_BRIGHT + "Enter a coordinate (e.g.: A1):" + RESET);
        String coordinate = scan.next();
        return coordinate;
    }

    public boolean inputValidation(String[] inputArr, int player) {
        String rowHeaders = getRowHeaders();
        String[] colHeaders = getColHeaders();
        try {
            int rowIndex = rowHeaders.indexOf(inputArr[0].toUpperCase());
            int colIndex = Integer.parseInt(inputArr[1]) - 1;
            int[] coordinates = {rowIndex, colIndex};
            if (!rowHeaders.contains(inputArr[0].toUpperCase())) {
                refreshScreen();
                printBoard();
                displayWhoTurns(player);
                System.out.println("\n" + RED_BRIGHT + inputArr[0].toUpperCase() + inputArr[1].toUpperCase() + " is an invalid coordinate!" + RESET);
                return false;
            }
            if (!Arrays.asList(colHeaders).contains(inputArr[1])) {
                refreshScreen();
                printBoard();
                displayWhoTurns(player);
                System.out.println("\n" + RED_BRIGHT+ inputArr[0].toUpperCase() + inputArr[1].toUpperCase() + " is an invalid coordinate!" + RESET);
                return false;
            }
            if (this.board[rowIndex][colIndex] != 0) {
                refreshScreen();
                printBoard();
                displayWhoTurns(player);
                System.out.println("\n" + RED_BRIGHT + "Coordinate '" + inputArr[0].toUpperCase() + inputArr[1].toUpperCase() + "' is already used!" + RESET);
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            //System.err.println(e.getMessage());
            refreshScreen();
            printBoard();
            displayWhoTurns(player);
            System.out.println("\n" + RED_BRIGHT + inputArr[0].toUpperCase() + inputArr[1].toUpperCase() + " is an invalid coordinate!" + RESET);
            return false;
        }
    }

    public void mark(int player, int row, int col) {
        this.board[row][col] = player;
    }

    public int[] getTargetRow(int[] coordinates) {
        int [] targetRow = board[coordinates[0]];
        return targetRow;
    }

    public int[] getTargetCol(int[] coordinates) {
        int targetColIndex = coordinates[1];
        int arraySize = board.length;
        int [] targetCol = new int[arraySize];

        for (int i=0; i<targetCol.length; i++) {
            targetCol[i] = board[i][targetColIndex];
        }
        return targetCol;
    }

    public int[] getRightDownDiagonal(int[] coordinates){
        int lastRowIndex = this.board.length -1;
        int lastColIndex = this.board[0].length -1;
        int targetRow = coordinates[0];
        int targetCol = coordinates[1];
        int offset = Math.min(targetRow, targetCol);
        int starterRow = targetRow - offset;
        int starterCol = targetCol - offset;
        int arraySize = Math.min(lastRowIndex-starterRow, lastColIndex-starterCol) + 1;
        int[] rightDownDiagonal = new int[arraySize];

        for (int i=0; i<rightDownDiagonal.length; i++) {
            rightDownDiagonal[i] = this.board[starterRow+i][starterCol+i];
        }
        return rightDownDiagonal;
    }

    public int[] getLeftDownDiagonal(int[] coordinates){
        int lastRowIndex = this.board.length -1;
        int lastColIndex = this.board[0].length -1;
        int targetRow = coordinates[0];
        int targetCol = coordinates[1];
        int offset = Math.min(targetRow, lastColIndex-targetCol);
        int starterRow = targetRow - offset;
        int starterCol = targetCol + offset;
        int arraySize = Math.min(lastRowIndex-starterRow, starterCol) + 1;
        int[] leftDownDiagonal = new int[arraySize];

        for (int i=0; i<leftDownDiagonal.length; i++) {
            leftDownDiagonal[i] = this.board[starterRow+i][starterCol-i];
        }
        return leftDownDiagonal;
    }

    public int[][] getArraysForCheckWon(int[] coordinates) {
        int[][] arraysForCheckWon = new int[4][];
        arraysForCheckWon[0] = getTargetRow(coordinates);
        arraysForCheckWon[1] = getTargetCol(coordinates);
        arraysForCheckWon[2] = getRightDownDiagonal(coordinates);
        arraysForCheckWon[3] = getLeftDownDiagonal(coordinates);

        return arraysForCheckWon;
    }

    public boolean hasWon(int[][] array, int player, int howMany) {
        for (int[] innerArr : array) {
            int counter = 0;
            for (int value : innerArr) {
                if (value == player) {
                    counter++;
                    if (counter == howMany) {
                        return true;
                    }
                } else {
                    counter = 0;
                }
            }
        }
        return false;
    }

    public boolean isFull() {
        for (int[] row : this.board) {
            for (int num : row) {
                if (num == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        int nCols = this.board[0].length;
        int aligner = 4;
        String stringFormat = "%" + aligner + "s";
        String numberFormat = "%" + aligner + "d";
        String blank = " ";

        /** Print header for columns */
        System.out.printf(stringFormat, blank);
        for (int colId = 1; colId <= nCols; colId++) {
            System.out.printf(numberFormat, colId);
        }
        System.out.println();

        /** Print row id and row content for each row */
        char rowId = 'A';
        for (int[] row : this.board) {
            System.out.printf(stringFormat, rowId);
            for (int num : row) {
                String sign;
                switch (num) {
                    case 1:
                        sign = RED_BOLD_BRIGHT + "   X" + RESET;
                        break;
                    case 2:
                        sign = BLUE_BOLD_BRIGHT + "   O" + RESET;
                        break;
                    default:
                        sign = ".";
                }
                System.out.printf(stringFormat, sign);
            }
            System.out.println();
            rowId++;
        }
    }

    public void printResult(int player) {
        if (player == 1) {
            System.out.println("\n" + RED_BOLD_BRIGHT + "X won!" + RESET);
        } else if (player == 2){
            System.out.println("\n" + BLUE_BOLD_BRIGHT + "O won!" + RESET);
        } else {
            System.out.println("\n" + YELLOW_BRIGHT + "It's a tie!" + RESET);
        }
    }

    public void enableAi(int player) {
    }

    public static void refreshScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void play(int howMany) {
        refreshScreen();
        int player = 1;
        while (true) {
            printBoard();
            displayWhoTurns(player);
            int[] coordinates = getMove(player);
            int row = coordinates[0];
            int col = coordinates[1];
            refreshScreen();
            mark(player, row, col);
            int[][] arraysForCheckWon = getArraysForCheckWon(coordinates);
            if (hasWon(arraysForCheckWon, player, howMany)){
                refreshScreen();
                printBoard();
                printResult(player);
                quitGame();
            }
            if (isFull()){
                refreshScreen();
                printBoard();
                printResult(3);
                quitGame();
            }
            player = player == 1 ? 2 : 1;
        }
    }

    public void quitGame(){
            System.out.println("\n" + YELLOW_BRIGHT + "Thanks for playing! Bye!" + RESET + "\n");
            System.exit(0);
    }

}
