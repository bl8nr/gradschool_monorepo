/**
 * WordCount Application
 * The purpose of this application is to parse a text file whose location is given
 * via the command line. The application will then print the number of uniquie words
 * as well as how many times each word is used. Lastly, it will print the number of
 * words used in total as well as the total number of lines in the text file.
 * Manual testing direction are outline in the README.md file.
 */

import java.io.*;
import java.util.HashMap;


public class WordCount {
    private HashMap<String, Integer> words;
    public Integer lineCount;
    public Integer wordCount;

    /**
     * WordCount main appliication mathod
     * @param args which includes the text file file path
     * @return void
     */
    public static void main(String[] args) {
        // create a new word count class object
        WordCount wordCounter = new WordCount();

        // use file reader to text read the file into the application by line/string
        try(BufferedReader br = new BufferedReader(new FileReader(args[0]))) {

            // for each line...
            for(String line; (line = br.readLine()) != null; ) {

                // increment the line counter by one
                wordCounter.lineCount = wordCounter.lineCount + 1;

                // split the line/string into an array by each white space using split with a regex
                String[] utilArr = line.split("\\s+");

                // add each word in the array to the wordCounters data structure
                for (String word : utilArr) {
                    wordCounter.addWordToHashMap(word);
                }
            }

            // after all the lines have been processed, print out the results
            wordCounter.words.remove("");
            wordCounter.printResults();
        } catch (IOException ex) {
            System.out.println("An error has occured");
        }

    }

    /**
     * WordCount class constructor, create the HashMap data structure
     * for storing the unique words and their usage count. Init
     * vars for tracking line count as well as word count with a starting
     * value of zero.
     */
    public WordCount() {
        words = new HashMap<String, Integer>();
        lineCount = 0;
        wordCount = 0;
    }

    /**
     * Print the text file specs to system out. Print each word
     * while tracking the number of words. Lastly, print the number
     * of lines value and the number of words value
     * @param null
     * @return void
     */
    public void printResults() {
        System.out.println("Unique Word Counts \n");

        // remove the empty string hashmap entry that tracks new lines places after spaces
        if (words.get("") != null) {
            words.remove("");
        }

        // for each word in the data structure...
        words.forEach((word, count) -> {

            // increment the word count
            wordCount = wordCount + count;

            // print the word and its number of times used
            System.out.format("%s %d%n", word, count);
        });

        // print the total word count and total line count
        System.out.println("\nTotal Line Count: " + lineCount);
        System.out.println("Total Word Count: " + wordCount + "\n");
    }

    /**
     * Add a word to the application data structure where the text file
     * parsed specs are stored. If the word already exists then increment
     * the related keys value in the hashmap. If it doesnt already exist
     * then create a new key with a value of 1 for the new unique word
     * @param word string to be added to word storage data structure
     * @return void
     */
    public void addWordToHashMap(String word) {
        Integer wordInHashMap = words.get(word);

        // if the word already exists, increment the value in the hashmap,
        // otherwise create a new entry
        if (wordInHashMap != null) {
            words.put(word, words.get(word) + 1);
        } else {
            words.put(word, 1);
        }
    }
}