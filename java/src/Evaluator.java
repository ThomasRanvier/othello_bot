import java.util.*;

/**
 * Class that is used to evaluate a state of the game.
 */
public class Evaluator {
    private char player;
    private int[][] WEIGHTS = {{4, -3, 2, 2, 2, 2, -3, 4},
                                {-3, -4, -1, -1, -1, -1, -4, -3},
                                {2, -1, 1, 0, 0, 1, -1, 2},
                                {2, -1, 0, 1, 1, 0, -1, 2},
                                {2, -1, 0, 1, 1, 0, -1, 2},
                                {2, -1, 1, 0, 0, 1, -1, 2},
                                {-3, -4, -1, -1, -1, -1, -4, -3},
                                {4, -3, 2, 2, 2, 2, -3, 4}};

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
        //return componentWiseHeuristic(position);
        return staticWeightsHeuristic(position);
    }

    private int staticWeightsHeuristic(Position position) {
        char[][] grid = position.getGrid();
        int result = 0;
        for(int x = 0; x < position.SIZE; x++) {
            for(int y = 0; y < position.SIZE; y++) {
                if (grid[x][y] != 'E') {
                    result += grid[x][y] == this.player ? this.WEIGHTS[x][y] : -this.WEIGHTS[x][y];
                }
            }
        }
        return result;
    }

    private int componentWiseHeuristic(Position position) {
        char[][] grid = position.getGrid();
        float c = coinParityHeuristic(grid, position.SIZE);
        float m = actualMobilityHeuristic(position);
        float wc = 1000, wm = 1000;
        int final_c = Math.round(wc * c);
        int final_m = Math.round(wm * m);
        return final_c + final_m;
    }

    private float coinParityHeuristic(char[][] grid, int size) {
        float own_coins = 0, enemy_coins = 0;
        for(int x = 0; x < size; x++) {
            for(int y = 0; y < size; y++) {
                if (grid[x][y] != 'E') {
                    if (grid[x][y] == this.player)
                        own_coins += this.WEIGHTS[x][y];
                    else
                        enemy_coins += this.WEIGHTS[x][y];
                }
            }
        }
        return 100 * (own_coins - enemy_coins) / (own_coins + enemy_coins);
    }

    private float actualMobilityHeuristic(Position position) {
        ArrayList<Move> own_valid_moves = position.getValidMoves();
        position.changePlayer();
        ArrayList<Move> enemy_valid_moves = position.getValidMoves();
        position.changePlayer();
        float own_size = own_valid_moves.size(), enemy_size = enemy_valid_moves.size();
        if (own_size == 0 && enemy_size == 0)
            return 0;
        else
            return 100 * (own_size - enemy_size) / (own_size + enemy_size);
    }
}
