package at.walternative.connectfour;

import java.util.Random;

/**
 * Created by grego on 18.03.2016.
 */
public class Game {

    private static final int EMPTY = 0;

    private int[][] gameState = new int[7][6];
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

                gameCounter++;
                return true;
            }
        }

        return false;
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
