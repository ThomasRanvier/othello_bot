from copy import deepcopy
from move import Move

class Position():
    def __init__(self, pos_str='', grid_size=8):
        self.__size = grid_size
        self.__grid = [[None for _ in range(self.__size)] for _ in range(self.__size)]
        self.__player = ''
        if pos_str != '':
            self.__player = pos_str[0]        
            self.__initalize_grid(pos_str[1:])

    def __initalize_grid(self, pos_str):
        for y in range(self.__size):
            for x in range(self.__size):
                char = pos_str[x + (y * self.__size)]
                if char == 'O':
                    self.__grid[x][y] = 'W'
                elif char == 'X':
                    self.__grid[x][y] = 'B'

    def make_move(self, move):
        x = move.x
        y = move.y
        if x >= 0 and x < self.__size and y >= 0 and y < self.__size and not move.is_pass_move:
            self.__grid[x][y] = self.__player
            for dir_x in [-1, 0, 1]:
                for dir_y in [-1, 0, 1]:
                    if (dir_x != 0 or dir_y != 0) and self.__check_direction(x, y, dir_x, dir_y):
                        self.__fill_direction(x, y, dir_x, dir_y)
            self.__player = 'B' if self.__player == 'W' else 'W'

    def __fill_direction(self, x, y, dir_x, dir_y):
        itself_encountered = False
        while not itself_encountered:
            x += dir_x
            y += dir_y
            if self.__grid[x][y] == self.__player:
                itself_encountered = True
            else:
                self.__grid[x][y] = self.__player

    def get_valid_moves(self):
        valid_moves = []
        for x in range(self.__size):
            for y in range(self.__size):
                if self.__is_candidate(x, y) and self.__is_valid(x, y):
                    valid_moves.append(Move(x=x, y=y))
        return valid_moves

    def __is_valid(self, x, y):
        for dir_x in [-1, 0, 1]:
            for dir_y in [-1, 0, 1]:
                if (dir_x != 0 or dir_y != 0) and self.__check_direction(x, y, dir_x, dir_y):
                    return True
        return False

    def __check_direction(self, x, y, dir_x, dir_y):
        out_of_bounds = False
        enemy_between = False
        while not out_of_bounds:
            x += dir_x
            y += dir_y
            if x < 0 or x >= self.__size or y < 0 or y >= self.__size:
                out_of_bounds = True
            else:
                if self.__grid[x][y] == self.__player:
                    return enemy_between
                elif self.__grid[x][y] != None:
                    enemy_between = True
                else:
                    return False
        return False

    def __is_candidate(self, x, y):
        if self.__grid[x][y] != None:
            return False
        return self.__has_enemy_neighbour(x, y)

    def __has_enemy_neighbour(self, x, y):
        result = False
        enemy = ('B' if self.__player == 'W' else 'W')
        if x > 0 and y > 0 and self.__grid[x - 1][y - 1] == enemy:
            result = True
        if x > 0 and self.__grid[x - 1][y] == enemy:
            result = True
        if x > 0 and y < self.__size - 1 and self.__grid[x - 1][y + 1] == enemy:
            result = True
        if y > 0 and self.__grid[x][y - 1] == enemy:
            result = True
        if y < self.__size - 1 and self.__grid[x][y + 1] == enemy:
            result = True
        if x < self.__size - 1 and y > 0 and self.__grid[x + 1][y - 1] == enemy:
            result = True
        if x < self.__size - 1 and self.__grid[x + 1][y] == enemy:
            result = True
        if x < self.__size - 1 and y < self.__size - 1 and self.__grid[x + 1][y + 1] == enemy:
            result = True
        return result

    def __str__(self):
        grid_str = ' ' + '--' * (self.__size - 1) + '- \n'
        for y in range(self.__size):
            grid_str += '|'
            for x in range(self.__size):
                grid_str += (' ' if self.__grid[x][y] == None else self.__grid[x][y])
                if x < self.__size - 1:
                    grid_str += ' '
            grid_str += '|\n'
        grid_str += ' ' + '--' * (self.__size - 1) + '- '
        return grid_str
        
    def get_grid(self):
        return deepcopy(self.__grid)

    def get_player(self):
        return self.__player
        
    def set_grid(self, grid):
        self.__grid = grid

    def set_player(self, player):
        self.__player = player
