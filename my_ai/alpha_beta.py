import time
import sys
from copy import deepcopy
from move import Move

class AlphaBeta():
    def __init__(self, evaluator, time):
        self.__time_limit = time - 0.015
        self.__evaluator = evaluator

    def __alpha_beta(self, initial_position, depth, alpha, beta, maximizing):
        if depth == 0 or len(initial_position.get_valid_moves()) == 0:
            evaluation_move = Move(value=self.__evaluator.evaluate(initial_position))
            #print(initial_position, file=sys.stderr)
            #print('evaluation move: ' + str(evaluation_move) + ', value: ' + str(evaluation_move.value), file=sys.stderr)
            return evaluation_move
        valid_moves = initial_position.get_valid_moves()
        if maximizing:
            max_value = -float('inf')
            best_move = Move()
            for move in valid_moves:
                new_position = deepcopy(initial_position)
                new_position.make_move(move)
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
                if max_value > alpha:
                    alpha = max_value
                #alpha = max(alpha, max_value)
                #if alpha >= beta:
                    #print('break max', file=sys.stderr)
                #    break
            return best_move
        else:
            min_value = float('inf')
            best_move = Move()
            for move in valid_moves:
                new_position = deepcopy(initial_position)
                new_position.make_move(move)
                result_move = self.__alpha_beta(new_position, depth - 1, alpha, beta, True)
                #print('Min, depth: ' + str(depth) + ', move: ' + str(move) + ' ' + str(result_move.value), file=sys.stderr)
                if result_move.value < min_value:
                    min_value = result_move.value
                    move.value = min_value
                    best_move = move
                if min_value <= alpha:
                    move.value = min_value
                    return move
                if min_value < beta:
                    beta = min_value
                #beta = min(beta, min_value)
                #if alpha >= beta:
                    #print('break min', file=sys.stderr)
                #    break
            return best_move

    def best_move(self, initial_position):
        if len(initial_position.get_valid_moves()) == 0:
            return Move(is_pass_move=True)#'pass'
        return self.__alpha_beta(initial_position, 2, -float('inf'), float('inf'), True)

    def __find_best_move(self, positions):
        return True
