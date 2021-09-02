print("CSCI E-118 - Homework 0: Brett Bloethner")
print("Problem 4")

# ############################### #
# PALINDROME IDENTIFICATION CLASS #
# ############################### #
class PalindromeFinder:

    # determine wether the sameple is a 2 dimensional palindrome
    # columns constitue a palindrome (1st column == last column)
    # rows constitute a palindrome (1st row == last row)
    def is2dArrayPalindrome(self, arraySample):
        isPalindrome = False

        # create list that contains all the rows and list for storing all columns
        yAxis = arraySample.splitlines()
        xAxis = []

        # for each position in a yAxis list entry...
        for i, char in enumerate(yAxis[0]):
            arrOfChars = []
            # ...go through each yAxis list and create a new array from those chars
            for chars in yAxis:
                arrOfChars.append(chars[i])

            xAxis.append("".join(arrOfChars))

        # if the columns are a palindrome, and the rows are a palindrome...
        if (self.isArrayPalindrome(xAxis) & self.isArrayPalindrome(yAxis)):
            # ...then set isPalindrome to True
            isPalindrome = True
        
        return isPalindrome


    # determine wether or not a simple string is a palindrome
    def isStringPalindrome(self, stringSample):

        # split string into a list, use isArrayPalindrome to determine if list is palindrome
        return self.isArrayPalindrome(list(stringSample))


    # determine wether or not an array is a palindrome
    def isArrayPalindrome(self, arraySample):
        isPalindrome = True

        # for each char (include index) in the sample
        for i in range(len(arraySample)):
            
            # if the test loop has reached half way through the string then break the loop
            if (i > (len(arraySample) / 2)):
                break

            # pull out the value at the looped position as well as its opposing positions value
            leftSideChar = arraySample[i]
            rightSideChar = arraySample[len(arraySample) - (i + 1)]

            # if the left right value are different then set isPalindrome false and break loop
            if (leftSideChar != rightSideChar):
                isPalindrome = False
                break
        
        return isPalindrome
# ################################### #
# END PALINDROME IDENTIFICATION CLASS #
# ################################### #


# ######################## #
# PROBLEM 4 TEST FUNCTIONS #
# ######################## #
palindromeFinder = PalindromeFinder()

# first palindrome test, testing with valid palindrome
print("TEST 1: Testing if 'tattarrattat' is a palindrome...")
assert(palindromeFinder.isStringPalindrome("tattarrattat") == True)
print("TEST PASSED")

# second palindrome test, testing with invalid palindrome
print("TEST 2: Testing if 'notapalindrome' is NOT a palindrome...")
assert(palindromeFinder.isStringPalindrome("notapalindrome") == False)
print("TEST PASSED")

# third palindrome test, testing with a valid 2d palindrome
print("TEST 3: Testing if the sample below is a 2d palindrome... \n aaaa\n abba\n abba\n aaaa")
assert(palindromeFinder.is2dArrayPalindrome("aaaa\nabba\nabba\naaaa\n") == True)
print("TEST PASSED")

# fourth palindrome test, testing with an invalid 2d palindrome
print("TEST 4: Testing if the sample below is NOT a 2d palindrome... \n baaa\n abba\n abba\n aaaa")
assert(palindromeFinder.is2dArrayPalindrome("baaa\nabba\nabba\naaaa\n") == False)
print("TEST PASSED")

# fifth palindrome test, testing with another valid 2d palindrome
print("TEST 5: Testing if the sample below is a 2d palindrome... \n baab\n abba\n abba\n baab")
assert(palindromeFinder.is2dArrayPalindrome("baab\nabba\nabba\nbaab\n") == True)
print("TEST PASSED")

print("ALL TESTS PASSED")

# ############################ #
# END PROBLEM 4 TEST FUNCTIONS #
# ############################ #