public class Move {
    public int x, y, value;
    public boolean is_pass_move;

    public Move(boolean is_pass_move) {
        this.is_pass_move = is_pass_move;
    }

    public Move(int value) {
        this.value = value;
    }

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
        this.is_pass_move = false;
        this.value = 0;
    }

    @java.lang.Override
    public java.lang.String toString() {
        if (this.is_pass_move)
            return "pass";
        return "(" + (this.y + 1) + "," + (this.x + 1) + ")";
    }
}