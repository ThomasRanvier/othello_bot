public class CountingEvaluator {
    private char player;

    public CountingEvaluator(char player) {
        System.err.println("Evaluator created, player: " + player);
        this.player = player;
    }

    public int evaluate(Position position) {
        char[][] grid = position.getGrid();
        int result = 0;
        for(char[] row: grid) {
            for(char cell: row) {
                result += cell == 'E' ? 0 : (cell == this.player ? 1 : -1);
            }
        }
        return result;
    }
}