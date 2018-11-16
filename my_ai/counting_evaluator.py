
class CountingEvaluator():
    def __init__(self, player):
        self.__player = player
        
    def evaluate(self, position):
        grid = position.get_grid()
        #print(player)
        result = 0
        for row in grid:
            for cell in row:
                if cell != None:
                    result += 1 if cell == self.__player else -1
        #if len(position.get_valid_moves()) == 0:
        #    if result > 0:
        #        result = float('100')
        #    else:
        #        result = -float('100')
        return result
