from move import Move
from alpha_beta import AlphaBeta
from position import Position
from counting_evaluator import CountingEvaluator
import sys

if __name__ == '__main__':
    
    pos_str = sys.argv[1]
    time = float(sys.argv[2])

    position = Position(pos_str)
    evaluator = CountingEvaluator(position.get_player())
    algorithm = AlphaBeta(evaluator, time)
    move = algorithm.best_move(position)
    print(move)
    """
    pos_str = 'WEEEEEEEEEEEEEEEEEEEXOEEEEEEXOEEEEEEXOEEEEEEEEEEEEEEEEEEEEEEEEEEE'
    position = Position(pos_str)
    evaluator = CountingEvaluator(position.get_player())
    print(position)
    print(evaluator.evaluate(position))
    move = Move(x=2, y=2)
    position.make_move(move)
    print(position)
    print(evaluator.evaluate(position))
    """
    
