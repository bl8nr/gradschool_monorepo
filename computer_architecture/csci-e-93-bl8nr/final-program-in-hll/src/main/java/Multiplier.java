/**
 * Brett Bloethner
 * CSCI E-93
 * Final-program-in-hll
 * The purpose of this program is to multiply 2 integers while not using any multiplication operators
 * and the program must be built to the specifications on the README file
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.lang.Runtime;

public class Multiplier {
    boolean isNegative = false;

    public static void main(String[] args) {

        // create a new multiplier class object
        Multiplier multiplier = new Multiplier();

        String[] resultStrings;
        int multiplicandInt = 0;
        int multiplierInt = 0 ;
        int resultInt = 0 ;

        // prompt user the first number, the multiplicand
        System.out.println("This program multiplies two signed integers. Please enter the first number:");
        multiplicandInt = multiplier.promptForInput();

        // prompt user for the second number, the multiplier
        System.out.println("Please enter the second number:");
        multiplierInt = multiplier.promptForInput();

        // multiply the multiplicand times the multiplier using shift add method, store working copy
        resultInt = multiplier.multiply(multiplicandInt, multiplierInt);

        // convert the result int to a string, store a working copy
        resultStrings = multiplier.intToString(resultInt);

        // print the result out to the console using resultStrings and the isNegative bool
        multiplier.putString(resultStrings, multiplier.isNegative);

        /*
        used to test shift multiplier functionality
        multiplier.test();
         */
    }

    /**
     * small test just to test the shift multiplier across a wider specturm of values than i could manually
     */
    /* public void test () {
        int i1 = 0000;
        int i2 = 9999;

        while (i1 < 5000) {
            i1 += 1;
            i2 += 1;
            int expect = i1 * i2;
            int got = multiply(i1, i2);
            System.out.println(String.format("Multiply %s * %s expecting %s got %s", i1, i2, expect, got));
        }

    }
*/
    /**
     * Prompts user for input of a number, then tries to convert the number to an integer
     * if the converts then the number is returned, if it doesnt then the fn recalls itself
     * to reprompt the user for correction
     * @return integer version of the number the user typed in
     */
    public int promptForInput() {
        int userInputAsInteger;

        // get array of string numbers from user/terminal
        String[] stringOfChars = getString(System.in);

        // attempt to convert the array of string numbers to one int
        try {
            userInputAsInteger = stringToInt(stringOfChars);
        } catch (RuntimeException e) {
            // on fail, prompt the user to retry and refire promptForInput
            System.out.println("That was an invalid number. Please enter a number between -32768 and 32767");
            return promptForInput();
        }

        // once a successful conversion is completed, return the value
        return userInputAsInteger;
    };

    /**
     * Get the string input from the console as a series of ASCII
     * coded strings, then convert them to string integer represntations
     * and return them via an array of strings
     * @param is - the input from the user and console
     * @return - string of integers representing the once ascii coded chars
     */
    public String[] getString(InputStream is) {
        int i;
        String[] output = {};

        try {
            // read the console stream input until return is pressed
            while ((i = is.read()) != '\n') {
                char c = (char)i;
                Character d = c;

                // create new array one entry longer for new char
                String[] temp = new String[ output.length + 1 ];

                // copy old array of chars over
                for (int ii=0; ii < output.length; ii++)
                {
                    temp[ii] = output[ii];
                }

                // add the new char to the end of the array after decoding the number from ASCII
                temp[output.length] = asciiNumberDecoder(d) ;

                // set output to the entire array of user input chars
                output = temp;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // return the user entered chars in an array of strings
        return output;
    }

    /**
     * Get the integer value in string form, of an ascii coded character/string
     * @param character - in string form though, so of length of 1
     * @return integer in string representation of the ASCII code passed in
     */
    public String asciiNumberDecoder(int asciiCode) {
        /*
        massive case statement mapping ascii codes to their respective integers
         */
        switch(asciiCode) {
            case 45:
                return "-";
            case 48:
                return "0";
            case 49:
                return "1";
            case 50:
                return "2";
            case 51:
                return "3";
            case 52:
                return "4";
            case 53:
                return "5";
            case 54:
                return "6";
            case 55:
                return "7";
            case 56:
                return "8";
            case 57:
                return "9";
            default:
                return "F";
        }
    }

    /**
     * Convert a single char langthed string containg a number, to an integer
     * @param character - 1 base 10 number in the form of a string
     * @return 1 base 10 number in the form of an int
     */
    public int charToInt(String character) {
        /*
        massive case statement mapping string to int
         */
        switch(character) {
            case "0":
                return 0;
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            default:
                return 0;
        }
    }

    /**
     * Convert a single int to a string of that number
     * @param int - one integer
     * @return 1 base 10 number in the form of an string
     */
    public String intToChar(int integer) {
        /*
        massive case statement mapping int to string
         */
        switch(integer) {
            case 0:
                return "0";
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            default:
                return "0";
        }
    }

    /**
     * standard shift multiply alogrithm similiar to what's in patterson ~pg185
     * @param multiplicand
     * @param multiplier
     * @return
     */
    public int multiply(int multiplicand, int multiplier) {
        int result = 0;

        // repeat until 0 or for the number of bits invovled
        while (multiplier != 0) {

            // add multiplicand to result and store
            if ((multiplier & 1) != 0) {
                result = result + multiplicand;
            }

            // shift multiplicand left 1 bit
            multiplicand <<= 1;

            // shift multiplier right 1 bit
            multiplier >>= 1;

            // repeat until multiplier is 0
        }

        return result;
    }

    /**
     * return a integer value for the series of string number chars passed in
     * @param value - string consisting of numbers and dash
     * @return integer representation of that string of numbers
     */
    public int stringToInt(String[] values) {
        // working copy of the integer to be returned
        int sum = 0;

        /*
        validate the string to make sure it only includes digits
        throw error if it fails validation
         */
        if (validateString(values) == false) {
            throw new RuntimeException("invalid string");
        }

        /*
        reverse the array so that each decimal number matches up its position index
        with its 10s count so it can be easily iterated over and multipled by 10.
        values are swapped on opposite sides, going toward the middle
         */
        for (int i=0; i < values.length / 2; i++){
            String temp = values[i];
            values[i] = values[values.length -i -1];
            values[values.length -i -1] = temp;
        }

        /*
        iterate through each value and multiply it by 10 by the number the index its in
        i.e. if its in the hundreds place then its x * 10 * 10. if the string char is "-"
        then toggle is negative so that the ending value is negative.
         */
        for (int i=0; i < values.length; i++) {

            // test for negative
            if (values[i] == "-") {
                isNegative = !isNegative;
                break;
            }

            // convert char/string to int
            int value = charToInt(values[i]);

            // multiply it times its 10s place position x 10
            for (int z = 0; z < i; z++) {
                value = multiply(value, 10);
            };

            // add to the total sum
            sum = sum + value;
        }

        // return total sum
        return sum;
    }

    /**
     * take in integer after multiply operation and convert it to a series of strings number in an array
     * @param value - integer from after mulitplication
     * @return an array of strings whose value concatonated represent the multiplication product
     */
    public String[] intToString(int value) {
        // {billions, hundreds millions, tens millions, millions, hundredsthousand, tenthousands, thousands, hundreds, tens, ones}
        int[] valueInts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        String[] valueStrings = {"", "", "", "", "", "", "", "", "", ""};
        int[] positions = {1000000000, 100000000, 10000000 ,1000000 ,100000, 10000, 1000, 100, 10, 1};

        // go through each 10s place number (1b 1m ...) seeing how many times the number can be subtracted from value input
        // add that number to the array of strings. This is the same alogrithm the professor described in piazza and its
        // used to separate the integers in to single decimal places
        for (int i = 0; i < positions.length; i++) {
            while (positions[i] <= value) {
                value = value - positions[i];
                valueInts[i] += 1;
            }
        }

        // iterate through the array of ints and convert them to array of strings
        for (int i = 0; i < valueInts.length; i++) {
            valueStrings[i] = intToChar(valueInts[i]);
        }

        // return the array of string representing the integer after multiplication
        return valueStrings;
    }

    /**
     * Print the string of number to the console one char at a time
     * @param value is array of string number, isNegative denotes whether the result is a negative number
     */
    public void putString(String[] value, boolean isNegative) {
        boolean leadingZeros = true;

        // print negative sign first if the result is negative
        if(isNegative) {
            System.out.print("-");
        }

        // output each char while removing leading zeros
        for (int i = 0; i < value.length; i++) {
            if (value[i] != "0" || leadingZeros != true) {
                System.out.print(value[i]);
                leadingZeros = false;
            }
        }

        // line break for clarity
        System.out.println();
    }

    /**
     * Validate the array of strings passed in
     * 1st item can be 0-9 and dash
     * all others must only be 0-9
     * @param integerString
     * @return boolean true if valid boolean false if invalid
     */
    public boolean validateString(String[] integerStringArray) {
        boolean valid = true;

        // return invalid if any char isnt a 1 length 0-9 or dash too for first char in series
        for(int i=0; i < integerStringArray.length; i ++) {
            if (valid == true && i > 1) {
                valid = integerStringArray[i].matches("^[0-9]");
            } else if (valid == true) {
                valid = integerStringArray[i].matches("^(-?+)+[0-9]*$");
            }
        }

        return valid;
    }

}