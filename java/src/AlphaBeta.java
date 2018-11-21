import java.util.*;

/**
 * This class is used to run the alpha beta algorithm.
 * @author Thomas Ranvier
 */
public class AlphaBeta {
    private float time_limit;
    private Evaluator evaluator;

    /**
     * Class constructor taking as argument the evaluator to use and the time limit.
     * @param evaluator Evaluator that will be used to evaluate each game state.
     * @param time_limit The time limit that the algorithm must respects.
     */
    public AlphaBeta(Evaluator evaluator, float time_limit) {
        this.time_limit = time_limit;
        this.evaluator = evaluator;
    }

    /**
     * Calls the alpha beta function to determine the best move to do.
     * @param initial_position The initial position of the game.
     * @return The best move determined.
     */
    public Move bestMove(Position initial_position) {
        if (initial_position.getValidMoves().size() == 0)
            return new Move(true);
        return this.alphaBeta(initial_position, 8, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    }

    /**
     * This function implements the alpha beta algorithm.
     * @param initial_position The game state on which the function is called.
     * @param depth The actual depth.
     * @param alpha The actual value of alpha.
     * @param beta The actual value of beta.
     * @param maximizing A boolean true if the function is called in a maximizing state, false otherwise.
     * @return The local best move depending on how deep the call is in the recursion tree.
     */
    private Move alphaBeta(Position initial_position, int depth, int alpha, int beta, boolean maximizing) {
        if (depth == 0)
            return new Move(this.evaluator.evaluate(initial_position));
        ArrayList<Move> valid_moves = initial_position.getValidMoves();
        if (valid_moves.size() == 0)
            return new Move(this.evaluator.evaluate(initial_position));
        int value = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Move best_move = new Move(0, 0);
        for (Move move: valid_moves) {
            Position new_position = initial_position.clone();
            new_position.makeMove(move);
            Move result_move = this.alphaBeta(new_position, depth - 1, alpha, beta, !maximizing);
            boolean better_value = maximizing ? result_move.value > value : result_move.value < value;
            if (better_value) {
                value = result_move.value;
                move.value = value;
                best_move = move;
            }
            boolean cut_search = maximizing ? value >= beta : value <= alpha;
            if (cut_search) {
                move.value = value;
                return move;
            }
            if (maximizing)
                alpha = alpha > value ? alpha : value;
            else
                beta = beta < value ? beta : value;
        }
        return best_move;
    }
}