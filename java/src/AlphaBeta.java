import java.util.*;

public class AlphaBeta {
    private float time_limit;
    private CountingEvaluator evaluator;

    public AlphaBeta(CountingEvaluator evaluator, float time_limit) {
        this.time_limit = time_limit;
        this.evaluator = evaluator;
    }

    public Move bestMove(Position initial_position) {
        if (initial_position.getValidMoves().size() == 0)
            return new Move(true);
        return this.alphaBeta(initial_position, 8, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    }

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