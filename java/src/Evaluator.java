/**
 * Class that is used to evaluate a state of the game.
 */
public class Evaluator {
    private char player;
    private int[][] weights

    /**
     * Class constructor that takes the played player as argument.
     * @param player The player that the bot plays.
     */
    public Evaluator(char player) {
        this.player = player;
        this.weights = {{4, -3, 2, 2, 2, 2, -3, 4},
                        {-3, -4, -1, -1, -1, -1, -4, -3},
                        {2, -1, 1, 0, 0, 1, -1, 2},
                        {2, -1, 0, 1, 1, 0, -1, 2},
                        {2, -1, 0, 1, 1, 0, -1, 2},
                        {2, -1, 1, 0, 0, 1, -1, 2},
                        {-3, -4, -1, -1, -1, -1, -4, -3},
                        {4, -3, 2, 2, 2, 2, -3, 4}};
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
                    //int value = computeValue(x, y, position.SIZE);
                    result += grid[x][y] == this.player ? this.weights[x][y] : -this.weights[x][y];
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
        int result = 1;
        return result;
    }
}