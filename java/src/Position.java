import java.util.*;

public class Position {
    private static int SIZE = 8;
    private char[][] grid;
    private char player;

    public Position() {
        this.grid = new char[this.SIZE][this.SIZE];
    }

    public Position(String pos_str) {
        this.grid = new char[this.SIZE][this.SIZE];
        this.player = pos_str.charAt(0) == 'W' ? 'O' : 'X';
        this.initializeGrid(pos_str);
    }

    private void initializeGrid(String pos_str) {
        for (int x = 0; x < this.SIZE; x++) {
            for (int y = 0; y < this.SIZE; y++)
                this.grid[x][y] = pos_str.charAt(1 + x + (y * this.SIZE));
        }
    }

    public void makeMove(Move move) {
        int x = move.x;
        int y = move.y;
        if (x >= 0 && x < this.SIZE && y >= 0 && y < this.SIZE && !move.is_pass_move) {
            this.grid[x][y] = this.player;
            for (int dir_x = -1; dir_x <= 1; dir_x++) {
                for (int dir_y = -1; dir_y <= 1; dir_y++) {
                    if ((dir_x != 0 || dir_y != 0) && this.checkDirection(x, y, dir_x, dir_y))
                        this.fillDirection(x, y, dir_x, dir_y);
                }
            }
            this.player = this.player == 'X' ? 'O' : 'X';
        }
    }

    private void fillDirection(int x, int y, int dir_x, int dir_y) {
        boolean itself_encountered = false;
        while (!itself_encountered) {
            x += dir_x;
            y += dir_y;
            if (this.grid[x][y] == this.player)
                itself_encountered = true;
            else
                this.grid[x][y] = this.player;
        }
    }

    public ArrayList<Move> getValidMoves() {
        ArrayList<Move> valid_moves = new ArrayList<>();
        for (int x = 0; x < this.SIZE; x++) {
            for (int y = 0; y < this.SIZE; y++) {
                if (this.isCandidate(x, y) && this.isValid(x, y))
                    valid_moves.add(new Move(x, y));
            }
        }
        return valid_moves;
    }

    private boolean isValid(int x, int y) {
        for (int dir_x = -1; dir_x <= 1; dir_x++) {
            for (int dir_y = -1; dir_y <= 1; dir_y++) {
                if ((dir_x != 0 || dir_y != 0) && this.checkDirection(x, y, dir_x, dir_y))
                    return true;
            }
        }
        return false;
    }

    private boolean checkDirection(int x, int y, int dir_x, int dir_y) {
        boolean out_of_bounds = false;
        boolean enemy_between = false;
        while (!out_of_bounds) {
            x += dir_x;
            y += dir_y;
            if (x < 0 || x >= this.SIZE || y < 0 || y >= this.SIZE) {
                out_of_bounds = true;
            } else {
                if (this.grid[x][y] == this.player)
                    return enemy_between;
                else if (this.grid[x][y] != 'E')
                    enemy_between = true;
                else
                    return false;
            }
        }
        return false;
    }

    private boolean isCandidate(int x, int y) {
        if (this.grid[x][y] != 'E')
            return false;
        return this.hasEnemyNeighbour(x, y);
    }

    private boolean hasEnemyNeighbour(int x, int y) {
        boolean result = false;
        char enemy = this.player == 'X' ? 'O' : 'X';
        if (x > 0 && y > 0 && this.grid[x - 1][y - 1] == enemy)
            result = true;
        if (x > 0 && this.grid[x - 1][y] == enemy)
            result = true;
        if (x > 0 && y < this.SIZE - 1 && this.grid[x - 1][y + 1] == enemy)
            result = true;
        if (y > 0 && this.grid[x][y - 1] == enemy)
            result = true;
        if (y < this.SIZE - 1 && this.grid[x][y + 1] == enemy)
            result = true;
        if (x < this.SIZE - 1 && y > 0 && this.grid[x + 1][y - 1] == enemy)
            result = true;
        if (x < this.SIZE - 1 && this.grid[x + 1][y] == enemy)
            result = true;
        if (x < this.SIZE - 1 && y < this.SIZE - 1 && this.grid[x + 1][y + 1] == enemy)
            result = true;
        return result;
    }

    public Position clone() {
        Position new_position = new Position();
        new_position.player = this.player;
        for (int x = 0; x < this.SIZE; x++)
            for (int y = 0; y < this.SIZE; y++)
                new_position.grid[x][y] = this.grid[x][y];
        return new_position;
    }

    public char getPlayer() {
        return this.player;
    }

    public char[][] getGrid() {
        char[][] new_grid = new char[this.SIZE][this.SIZE];
        for (int x = 0; x < this.SIZE; x++) {
            for (int y = 0; y < this.SIZE; y++)
                new_grid[x][y] = this.grid[x][y];
        }
        return new_grid;
    }

    @java.lang.Override
    public java.lang.String toString() {
        String grid_str = " ";
        for (int i = 0; i < this.SIZE - 1; i++)
            grid_str += "--";
        grid_str += "- \n";
        for (int y = 0; y < this.SIZE; y++) {
            grid_str += "|";
            for (int x = 0; x < this.SIZE; x++) {
                grid_str += this.grid[x][y];
                if (x < this.SIZE - 1) {
                    grid_str += " ";
                }
            }
            grid_str += "|\n";
        }
        grid_str += " ";
        for (int i = 0; i < this.SIZE - 1; i++)
            grid_str += "--";
        grid_str += "- ";
        return grid_str;
    }
}