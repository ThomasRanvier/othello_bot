/**
 * Class that is used to evaluate a state of the game.
 */
public class Evaluator {
    private char player;

    /**
     * Class constructor that takes the played player as argument.
     * @param player The player that the bot plays.
     */
    public Evaluator(char player) {
        this.player = player;
    }

    /**
     * The function that evaluates the game state.
     * @param position The actual position of the game.
     * @return A value higher if the player is in a good position, lower otherwise.
     */
    public int evaluate(Position position) {
        char[][] grid = position.getGrid();
        int result = 0;
        for(int x = 0; x < position.SIZE; x++) {
            for(int y = 0; y < position.SIZE; y++) {
                if (grid[x][y] != 'E') {
                    int value = computeValue(x, y, position.SIZE);
                    result += grid[x][y] == this.player ? value : -value;
                }
            }
        }
        return result;
    }

    /**
     * The function that evaluates the value of the selected cell.
     * @param x Coordinate x.
     * @param y Coordinate y.
     * @param size Size of the grid.
     * @return The computed value.
     */
    private int computeValue(int x, int y, int size) {
        int result = 0;
        if (x == 0)
            result += 2;
        if (y == 0)
            result += 2;
        if (x == size - 1)
            result += 2;
        if (y == size - 1)
            result += 2;
        return result;
    }
}