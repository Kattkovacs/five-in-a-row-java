package com.codecool.fiveinarow;


import static com.codecool.fiveinarow.Game.hasWonArray;

public class FiveInARow {

    public static void main(String[] args) {

//        Game game = new Game(5, 11);
//        game.enableAi(1);
//        game.enableAi(2);
//        game.play(5);
        int[] arr = {1, 2, 0, 1, 1, 1, 1, 2};
        System.out.println(hasWonArray(arr, 1, 3));
    }
}
