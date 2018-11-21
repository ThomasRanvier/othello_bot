/**
 * This class contains the main function.
 */
public class Othello {
    /**
     * This is the main function, it creates the position, the evaluator, the alpha beta algorithm and print the
     * determined best move to do.
     * @param args args[0] is the string position of the game, args[1] is the time limit.
     */
    public static void main(String[] args) {
        String pos_str = args[0];
        double time_limit = Double.parseDouble(args[1]);
        Position position = new Position(pos_str);
        Evaluator evaluator = new Evaluator(position.getPlayer());
        AlphaBeta algorithm = new AlphaBeta(evaluator, time_limit);
        Move move = algorithm.bestMove(position);
        System.out.println(move);
    }
}