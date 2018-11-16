class Move(object):
    """
      This class represents a move in a game.
      The move is simply represented by two integers: the x and the y where the player puts the marker and a
      boolean to mark if it is a pass move or not.
      In addition, the Move has a field where the estimated value of the move can be stored during
      computations.

      Author: Ola Ringdahl
      Modified by: Thomas Ranvier
    """

    def __init__(self, x=-1, y=-1, is_pass_move=False, value=0):
        """
        Creates a new Move for (y, x) with value 0.
        :param y: Row
        :param x: Column
        :param is_pass_move: True if it is a pass move
        """
        self.x = x
        self.y = y
        self.is_pass_move = is_pass_move
        self.value = value

    def __str__(self):
        """
        Overrides the __str__ method to print the move on the format (3,6) or pass when print(move) is called
        :return: The string to be printed
        """
        if self.is_pass_move:
            return 'pass'
        else:
            return '(' + str(self.y + 1) + ',' + str(self.x + 1) + ')'
