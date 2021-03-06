/**
 * This class contains the main function.
 */
public class Othello {
    /**
     * This is the main function, it creates the position, the evaluator, the alpha beta algorithm and print the
     * determined best move to do.
     * @param args args[0] is the string position of the game, args[1] is the time limit.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new Exception("Too few/many arguments");
        } else if (args[0].length() != 65) {
            throw new Exception("Position too short/long");
        }
        String pos_str = args[0];
        double time_limit = Double.parseDouble(args[1]);
        Position position = new Position(pos_str);
        Evaluator evaluator = new Evaluator(position.getPlayer());
        AlphaBeta algorithm = new AlphaBeta(position.getPlayer(), evaluator, time_limit);
        Move move = algorithm.bestMove(position);
        System.out.println(move);
    }
}
