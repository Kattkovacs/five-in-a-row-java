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

        while (!inputValidation(inputArr)) {
            input = getInput();
            inputArr = input.split("", 2);
        }
        ;

        String rowHeaders = getRowHeaders();
        String[] colHeaders = getColHeaders();
        int rowIndex = rowHeaders.indexOf(inputArr[0].toUpperCase());
        int colIndex = Integer.parseInt(inputArr[1]) - 1;
        int[] coordinates = {rowIndex, colIndex};
        return coordinates;
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

    public static String getInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter a coordinate:");
        String coordinate = scan.next();
        return coordinate;
    }

    public boolean inputValidation(String[] inputArr) {
        String rowHeaders = getRowHeaders();
        String[] colHeaders = getColHeaders();
        try {
            int rowIndex = rowHeaders.indexOf(inputArr[0].toUpperCase());
            int colIndex = Integer.parseInt(inputArr[1]) - 1;
            int[] coordinates = {rowIndex, colIndex};
            if (!rowHeaders.contains(inputArr[0].toUpperCase())) {
                System.out.println(inputArr[0] + " is an invalid row coordinate!");
                return false;
            }
            if (!Arrays.asList(colHeaders).contains(inputArr[1])) {
                System.out.println(inputArr[1] + " is an invalid col coordinate!");
                return false;
            }
            if (this.board[rowIndex][colIndex] != 0) {
                System.out.println("This coordinate is already used!");
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            //System.err.println(e.getMessage());
            System.out.println(inputArr[1] + " is an invalid col coordinate!");
            return false;
        }
    }

    public void mark(int player, int row, int col) {
        this.board[row][col] = player;
    }

    public static int[][] getArray(int[][] board) {
        int nDiagonals = board.length + board[0].length -1;
        int[][] diagonalsArray = new int[nDiagonals][];

//        int[] diagonalArray = new int[];

        for (int i = 0; i < board.length ; i++) {

            for (int j = 0; j < board[0].length; j++) {

                int index = 0;
                for (int k = 0; k < nDiagonals; k++) {
                    diagonalsArray[k][index] = board[i][j];
                    index++;
                }
            }
        }

//        [[0], [0,0], [0,0,0,], [0,0], [0]]
//        int[] diagonalArray = new int[];
        return diagonalsArray;
    }

    public static boolean hasWonArray(int[] array, int player, int howMany) {
        int counter = 0;
        for (int value : array) {
            if (value == player) {
                counter++;
                if (counter == howMany) {
                    return true;
                }
            } else {
                counter = 0;
            }
        }
        return false;
    }


    public boolean hasWon(int player, int howMany) {
        hasWonArray(this.board[0], player, howMany);
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
        for (int colId = 1; colId <= nCols; colId++) {
            System.out.printf(numberFormat, colId);
        }
        System.out.println();

        /** Print row id and row content for each row */
        char rowId = 'A';
        for (int[] row : this.board) {
            System.out.printf(stringFormat, rowId);
            for (int num : row) {
                char sign;
                switch (num) {
                    case 1:
                        sign = 'X';
                        break;
                    case 2:
                        sign = 'O';
                        break;
                    default:
                        sign = '.';
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
        int player = 1;
        int round = 1;
        while (round <= 3) {
            printBoard();
            int[] coordinates = getMove(player);
            int row = coordinates[0];
            int col = coordinates[1];
            mark(player, row, col);
            player = player == 1 ? 2 : 1;
            round++;
        }
    }
}
