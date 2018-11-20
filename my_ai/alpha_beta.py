import time
import sys
from copy import deepcopy
from move import Move
from position import Position

class AlphaBeta():
    def __init__(self, evaluator, time):
        self.__time_limit = time - 0.015
        self.__evaluator = evaluator
        self.__get_valid_moves = 0
        self.__deepcopies = 0
        self.__make_moves = 0
        self.__recursions = 0

    def __alpha_beta(self, initial_position, depth, alpha, beta, maximizing):
        self.__recursions += 1
        if depth == 0:
            evaluation_move = Move(value=self.__evaluator.evaluate(initial_position))
            #print(initial_position, file=sys.stderr)
            #print('evaluation move: ' + str(evaluation_move) + ', value: ' + str(evaluation_move.value), file=sys.stderr)
            return evaluation_move
        start = time.process_time()
        valid_moves = initial_position.get_valid_moves()
        self.__get_valid_moves += time.process_time() - start
        if len(valid_moves) == 0:
            evaluation_move = Move(value=self.__evaluator.evaluate(initial_position))
            #print(initial_position, file=sys.stderr)
            #print('evaluation move: ' + str(evaluation_move) + ', value: ' + str(evaluation_move.value), file=sys.stderr)
            return evaluation_move
        if maximizing:
            max_value = -float('inf')
            best_move = Move()
            for move in valid_moves:
                start = time.process_time()
                new_position = deepcopy(initial_position)
                #new_position = Position()
                #new_position.set_grid(initial_position.get_grid())
                #new_position.set_player(initial_position.get_player())
                self.__deepcopies += time.process_time() - start
                start = time.process_time()
                new_position.make_move(move)
                self.__make_moves += time.process_time() - start
                result_move = self.__alpha_beta(new_position, depth - 1, alpha, beta, False)
                #if depth == 3:
                #    print('Max, depth: ' + str(depth) + ', move: ' + str(move) + ' ' + str(result_move.value), file=sys.stderr)
                if result_move.value > max_value:
                    max_value = result_move.value
                    move.value = max_value
                    best_move = move
                if max_value >= beta:
                    move.value = max_value
                    return move
                alpha = max(alpha, max_value)
                #print(alpha, file=sys.stderr)
                #if alpha >= beta:
                    #print('break max', file=sys.stderr)
                #    break
            return best_move
        else:
            min_value = float('inf')
            best_move = Move()
            for move in valid_moves:
                start = time.process_time()
                new_position = deepcopy(initial_position)
                #new_position = Position()
                #new_position.set_grid(initial_position.get_grid())
                #new_position.set_player(initial_position.get_player())
                self.__deepcopies += time.process_time() - start
                start = time.process_time()
                new_position.make_move(move)
                self.__make_moves += time.process_time() - start
                result_move = self.__alpha_beta(new_position, depth - 1, alpha, beta, True)
                #print('Min, depth: ' + str(depth) + ', move: ' + str(move) + ' ' + str(result_move.value), file=sys.stderr)
                if result_move.value < min_value:
                    min_value = result_move.value
                    move.value = min_value
                    best_move = move
                if min_value <= alpha:
                    move.value = min_value
                    return move
                beta = min(beta, min_value)
                #print(beta, file=sys.stderr)
                #if alpha >= beta:
                    #print('break min', file=sys.stderr)
                #    break
            return best_move

    def best_move(self, initial_position):
        if len(initial_position.get_valid_moves()) == 0:
            return Move(is_pass_move=True)#'pass'
        start = time.process_time()
        result = self.__alpha_beta(initial_position, 7, -float('inf'), float('inf'), True)
        print('Total (s): ' + str(time.process_time() - start), file=sys.stderr)
        print('Deepcopies (s): ' + str(self.__deepcopies), file=sys.stderr)
        print('Make moves (s): ' + str(self.__make_moves), file=sys.stderr)
        print('Get valid moves (s): ' + str(self.__get_valid_moves), file=sys.stderr)
        print('Recursions: ' + str(self.__recursions), file=sys.stderr)
        return result
