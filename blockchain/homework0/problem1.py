print("CSCI E-118 - Homework 0: Brett Bloethner")
print("Problem 1")
import time

# ########################### #
# FIBONACCI MEMOIZATION CLASS #
############################# #
class FibonacciMemo:
    
    def __init__(self):
        # preset the values for 0 and 1 in the dict
        # set first and second positions in sequence to 1
        self.memo_dict = {0:1, 1:1}

    def fibonacci(self, index):

        # if the fibonacci sequence position (index) is in memory...
        if index in self.memo_dict:

            # ...return the value from memory
            return self.memo_dict[index]

        # ...else find the sum of the last two fibonacci numbers in sequence, store it in memory and return it
        else:
            self.memo_dict[index] = self.fibonacci(index - 1) + self.fibonacci(index - 2)
            return self.memo_dict[index]
# ############################### #
# END FIBONACCI MEMOIZATION CLASS #
# ############################### #




# ######################## #
# PROBLEM 1 TEST FUNCTIONS #
# ######################## #

# create a correct fibonacci sequence list to test against
fibonacci_sequence = [1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987]

# the test should assert that...
def testFibonacciFunction(sequence, fibonacciMemo):
    print("Testing fibonacci_memo.fibonacci(index) against fibnoacci_sequence list values...")

    # ...for each value in the fibnoacci_sequence list...
    for index, expectedValue in enumerate(fibonacci_sequence):
        
        #...the fibonacci function, using that values index, returns the correct fibonacci value
        print("*** " + str(fibonacci_sequence[index]) + " should equal " + str(fibonacciMemo.fibonacci(index)))
        assert(fibonacci_sequence[index] == fibonacciMemo.fibonacci(index))

    print("All test passed with no errors")



# the test should assert that...
def testFibonacciFunctionTiming(sequence, fibonacciMemo):
    print("Testing fibonacci_memo memoization timing asserting the second run is faster than first...")
    startingTime = time.time()
    print("*** Original fibonacciMemo.memo_dict length: " + str(len(fibonacciMemo.memo_dict)))
    assert(fibonacci_sequence[14] == fibonacciMemo.fibonacci(14))
    runtimeDuration1 = time.time() - startingTime
    print("*** Second run fibonacciMemo.memo_dict length: " + str(len(fibonacciMemo.memo_dict)))
    assert(fibonacci_sequence[14] == fibonacciMemo.fibonacci(14))
    runtimeDuration2 = ((time.time() - startingTime) - runtimeDuration1)
    assert(runtimeDuration1 > runtimeDuration2)

    print("Successfully completed fibonacci sequence quicker 2nd time than first time (completed in " + str(round((runtimeDuration2 / runtimeDuration1) * 100 )) + "% of original runtime duration)")

# ############################ #
# END PROBLEM 1 TEST FUNCTIONS #
# ############################ #

# run the fibonacci sequence value test
testFibonacciFunction(fibonacci_sequence, FibonacciMemo())

#run the fibonacci memoization test
testFibonacciFunctionTiming(fibonacci_sequence, FibonacciMemo())