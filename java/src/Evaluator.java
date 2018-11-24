import java.util.*;

/**
 * Class that is used to evaluate a state of the game.
 */
public class Evaluator {
    private char player;
    private int STABLE_INCREMENT = 8;
    private double MOBILITY_WEIGHT = 0.85;
    private int[][] WEIGHTS = {{20, -3, 11, 8, 8, 11, -3, 20},
                                {-3, -7, -4, -1, -1, -4, -7, -3},
                                {11, -4, 4, 2, 2, 4, -1, 11},
                                {8, -1, 2, 1, 1, 2, -1, 8},
                                {8, -1, 2, 1, 1, 2, -1, 8},
                                {11, -4, 4, 2, 2, 4, -4, 11},
                                {-3, -7, -4, -1, -1, -4, -7, -3},
                                {20, -3, 11, 8, 8, 11, -3, 20}};

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
        return componentWiseHeuristic(position);
    }
    
    /**
     * That function returns an evaluation value of the board which uses the static
     * weights and increases the value if the cell is stable.
     * @param position The actual position of the game.
     * @return A value higher if the player is in a good position, lower otherwise.
     */
    private int staticWeightsAndStabilityHeuristic(Position position) {
        char[][] grid = position.getGrid();
        int result = 0;
        for(int x = 0; x < position.SIZE; x++) {
            for(int y = 0; y < position.SIZE; y++) {
                if (grid[x][y] != 'E') {
                    int cell_value = this.cellEvaluation(grid, position.SIZE, x, y);
                    result += grid[x][y] == this.player ? cell_value : -cell_value;
                }
            }
        }
        return result;
    }

    /**
     * That function returns a computed value for the selected cell of the grid, that value
     * is determined from the static weights and the value is incremented by a bonus if the cell
     * is considered as stable.
     * @param grid The grid of the game at its actual state
     * @param size The size of the grid
     * @param x Coordinate x
     * @param x Coordinate y
     * @return The computed value of the cell
     */
    private int cellEvaluation(char[][] grid, int size, int x, int y) {
        int cell_value = this.WEIGHTS[x][y];
        if (isStableCell(grid, size, x, y))
            cell_value = cell_value < 0 ? this.STABLE_INCREMENT : cell_value + this.STABLE_INCREMENT;
        return cell_value;
    }

    /**
     * That function evaluates if the selected cell can be considered as stable for the next move or not,
     * that means that it returns false if the cell can potentially be captured the direct next move
     * and true otherwise.
     * @param grid The grid of the game at its actual state
     * @param size The size of the grid
     * @param x Coordinate x
     * @param x Coordinate y
     * @return A boolean true if the cell is stable, false otherwise
     */
    private boolean isStableCell(char[][] grid, int size, int x, int y) {
        for (int dir_x = -1; dir_x <= 1; dir_x++) {
            for (int dir_y = -1; dir_y <= 1; dir_y++) {
                int new_x = x + dir_x, new_y = y + dir_y;
                if (this.isInGrid(new_x, new_y, size)) {
                    if (grid[new_x][new_y] == 'E') {
                        int opp_dir_x = dir_x * -1, opp_dir_y = dir_y * -1;
                        new_x = x + opp_dir_x;
                        new_y = y + opp_dir_y;
                        while (isInGrid(new_x, new_y, size) && grid[new_x][new_y] == grid[x][y]) {
                            new_x += opp_dir_x;
                            new_y += opp_dir_y;
                        }
                        if (isInGrid(new_x, new_y, size))
                            return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * That function simply returns true if the selected cell is in the grid boundaries.
     * @param x Coordinate x
     * @param x Coordinate y
     * @param size The size of the grid
     * @return A boolean true if the cell is in the grid, false otherwise
     */
    private boolean isInGrid(int x, int y, int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    /**
     * That function gets the different heuristics values and weights them to return a balanced
     * result.
     * @param position The actual position of the game
     * @return The weighted heuristics value
     */
    private int componentWiseHeuristic(Position position) {
        int coins = staticWeightsAndStabilityHeuristic(position);
        int moves = actualMobilityHeuristic(position);
        return coins + (int)Math.round(this.MOBILITY_WEIGHT * moves);
    }

    /**
     * That function evaluates the possible mobility of the player in regard to the opponent
     * possible mobility.
     * @param position The actual position of the game
     * @return A value higher if the player is in a good position, lower otherwise
     */
    private int actualMobilityHeuristic(Position position) {
        ArrayList<Move> own_valid_moves = position.getValidMoves();
        position.changePlayer();
        ArrayList<Move> enemy_valid_moves = position.getValidMoves();
        position.changePlayer();
        return own_valid_moves.size() - enemy_valid_moves.size();
    }
}
