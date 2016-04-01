package at.walternative.connectfour;

import android.util.Log;

import java.util.Random;

/**
 * Created by grego on 18.03.2016.
 */
public class Game {

    private static final int EMPTY = 0;
    private static final int NUM_COLS = 7;
    private static final int NUM_ROWS = 6;

    private int[][] gameState = new int[NUM_COLS][NUM_ROWS];
    private int gameCounter = 0;

    public Player getCurrentPlayer() {
        if (gameCounter % 2 > 0) {
            return Player.RED;
        } else {
            return Player.YELLOW;
        }
    }

    public boolean move(int column) {

        for (int i = 0; i < gameState[column].length; i++) {
            if (gameState[column][i] == EMPTY) {
                gameState[column][i] = getCurrentPlayer().getRepresentationValue();

                return true;
            }
        }

        return false;
    }

    // this should explicitly be called after successful render
    public void incrementGameCounter() {
        gameCounter++;
    }

    public int computerMove() {
        while (true) {
            Random random = new Random();

            int max = 6;
            int min = 0;

            int randomCol = random.nextInt(max - min + 1) + min;

            if (move(randomCol)) {
                return randomCol;
            }
        }
    }

    public void evaluateGameState() {
        // the possible occurences of tuples with size four in a row
        // are limited by the column size
        for (int r = 0; r < NUM_ROWS; r++) {
            for (int i = 0; i < (NUM_COLS - 4); i++) {
                Player winner = evaluateRowHorizontally(r, i);
                if (winner != null) {
                    Log.i("INFO", "The winner is: " + winner);
                }
            }
        }
    }

    public Player evaluateRowHorizontally(int rowIndex, int startColIndex) {

        // get initial field value for the first column of this iteration
        // if it is unset there can not be 4 rows connected by the same player
        int fieldValue = gameState[startColIndex][rowIndex];
        if (fieldValue == EMPTY)
            return null;

        // offset of one as the first step is already done
        for (int i = 1; i < 4; i++) {
            if (gameState[startColIndex + i][rowIndex] != fieldValue)
                return null;
            else
                fieldValue = gameState[startColIndex + i][rowIndex];
        }

        switch (fieldValue) {
            case 1:
                return Player.RED;
            case 2:
                return Player.YELLOW;
            default:
                throw new IllegalStateException("This should be impossible");
        }
    }

    public enum Player {
        RED(1),
        YELLOW(2);

        private int representationValue;

        Player(int representationValue) {
            this.representationValue = representationValue;
        }

        public int getRepresentationValue() {
            return representationValue;
        }
    }
}
