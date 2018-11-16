class Test():
    def print(self, text):
        print(text)

def pass_object(test_object, text):
    be = test_object()
    be.print(text)

pass_object(Test, 'lkfsjqhflkjq')
