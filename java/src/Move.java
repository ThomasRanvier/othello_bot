/**
 * This class is used to represent a move of the Othello game.
 */
public class Move {
    public int x, y, value;
    public boolean is_pass_move;

    /**
     * Class constructor that takes a boolean argument.
     * @param is_pass_move A boolean true if the move is a pass move, false otherwise.
     */
    public Move(boolean is_pass_move) {
        this.is_pass_move = is_pass_move;
    }

    /**
     * Class constructor that takes an integer argument.
     * @param value An integer that corresponds to the value of the move.
     */
    public Move(int value) {
        this.value = value;
    }

    /**
     * Class constructor that takes two arguments.
     * @param x Coordinate x.
     * @param y Coordinate y.
     */
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
        this.is_pass_move = false;
        this.value = 0;
    }

    /**
     * Override of the toString function to print the move formatted in (y + 1, x + 1).
     * @return The string to print.
     */
    @java.lang.Override
    public java.lang.String toString() {
        if (this.is_pass_move)
            return "pass";
        return "(" + (this.y + 1) + "," + (this.x + 1) + ")";
    }
}