import java.util.*;

/**
 * Class that is used to evaluate a state of the game.
 */
public class Evaluator {
    private char player;
    private int[][] WEIGHTS = {{20, -3, 11, 8, 8, 11, -3, 20},
                                {-3, -7, -4, -1, -1, -4, -7, -3},
                                {11, -4, 4, 2, 2, 4, -1, 11},
                                {8, -1, 2, 1, 1, 2, -1, 8},
                                {8, -1, 2, 1, 1, 2, -1, 8},
                                {11, -4, 4, 2, 2, 4, -4, 11},
                                {-3, -7, -4, -1, -1, -4, -7, -3},
                                {20, -3, 11, 8, 8, 11, -3, 20}};
    /*
    private int[][] WEIGHTS = {{6, -3, 2, 2, 2, 2, -3, 6},
                                {-3, -4, -1, -1, -1, -1, -4, -3},
                                {2, -1, 1, 0, 0, 1, -1, 2},
                                {2, -1, 0, 1, 1, 0, -1, 2},
                                {2, -1, 0, 1, 1, 0, -1, 2},
                                {2, -1, 1, 0, 0, 1, -1, 2},
                                {-3, -4, -1, -1, -1, -1, -4, -3},
                                {6, -3, 2, 2, 2, 2, -3, 6}}; */ /**
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
        return componentWiseHeuristic(position);
        //return staticWeightsHeuristic(position);
    }
    
    /**
     * That function uses constant weights as value for every cell of the 
     * grid to compute the heuristic of the game state.
     * @param position The actual position of the game.
     * @return A value higher if the player is in a good position, lower otherwise.
     */
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
        int coins = staticWeightsHeuristic(position);
        int moves = actualMobilityHeuristic(position);
        int stability = 0;//stabilityHeuristic(position);
        return coins + (int)Math.round(0.7 * moves) + stability;
    }

    private int actualMobilityHeuristic(Position position) {
        ArrayList<Move> own_valid_moves = position.getValidMoves();
        position.changePlayer();
        ArrayList<Move> enemy_valid_moves = position.getValidMoves();
        position.changePlayer();
        return own_valid_moves.size() - enemy_valid_moves.size();
    }

    private int stabilityHeuristic(Position position) {
        char[][] grid = position.getGrid();
        int own_stables = 0, enemy_stables = 0, size = position.SIZE;
        if (grid[0][0] != 'E' || grid[size - 1][0] != 'E' || grid[0][size - 1] != 'E' || grid[size - 1][size - 1] != 'E') {
            own_stables = totalStability(grid, size, position.getPlayer());
            enemy_stables = totalStability(grid, size, position.getPlayer() == 'X' ? 'O' : 'X');
        }
        return own_stables - enemy_stables;
    }

    private int totalStability(char[][] grid, int size, char player) {
        Set<ArrayList<Integer>> stable_coins = new HashSet<ArrayList<Integer>>();
        stabilityFromCorner(stable_coins, grid, size, player, 0, 0, 1, 1);
        stabilityFromCorner(stable_coins, grid, size, player, size - 1, 0, -1, 1);
        stabilityFromCorner(stable_coins, grid, size, player, 0, size - 1, 1, -1);
        stabilityFromCorner(stable_coins, grid, size, player, size - 1, size - 1, -1, -1);
        return stable_coins.size();
    }

    private void stabilityFromCorner(Set<ArrayList<Integer>> stable_coins, char[][] grid, int size, char player, int x, int y, int dir_x, int dir_y) {
        int mem_y = y;
        for (int i = 0; i < size; i++) {
            y = mem_y;
            for (int j = 0; j < size; j++) {
                if (grid[x][y] == player) {
                    ArrayList<Integer> pair = new ArrayList<>();
                    pair.add(x);
                    pair.add(y);
                    stable_coins.add(pair);
                } else {
                    break;
                }
                y += dir_y;
            }
            x += dir_x;
        }
    }
}
