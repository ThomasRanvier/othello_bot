public class Othello {
    public static void main(String[] args) {
        String pos_str = args[0];
        float time_limit = Float.parseFloat(args[1]);
        Position position = new Position(pos_str);
        CountingEvaluator evaluator = new CountingEvaluator(position.getPlayer());
        AlphaBeta algorithm = new AlphaBeta(evaluator, time_limit);
        Move move = algorithm.bestMove(position);
        System.out.println(move);

        //OK: getValidMoves, makeMove, checkPosition
        //Check makeMove && getValidMoves
        /*
        System.err.println(position.getValidMoves());
        position.makeMove(new Move(2, 4));
        System.err.println(position);
        System.err.println(position.getValidMoves());
        position.makeMove(new Move(2, 5));
        System.err.println(position);
        System.err.println(position.getValidMoves());
        */
        //Check checkPosition
        /*
        System.err.println(position);
        for (int dir_x = -1; dir_x <= 1; dir_x++) {
            for (int dir_y = -1; dir_y <= 1; dir_y++) {
                if (dir_x != 0 || dir_y != 0) {
                    System.err.println("x: 3, y: 5, dir_x: " + dir_x + ", dir_y: " + dir_y + " " + position.checkDirection(2, 4, dir_x, dir_y));
                    System.err.println("x: 4, y: 6, dir_x: " + dir_x + ", dir_y: " + dir_y + " " + position.checkDirection(3, 5, dir_x, dir_y));
                }
            }
        }
        */
    }
}