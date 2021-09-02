Checklist

- [x] create a makefile for the word count application
- [x] main file must be named WordCount.java
- [x] file name to be read must be specified on the command line
- [x] parse input file into a data structure to keep track of number of occurrences of words
- [x] words a delimited by white space which includes
    - [x] ' '
    - [x] '\t'
    - [x] '\n'
    - [x] '\r'
    - [x] as well as multiple occurrences of the above
- [x] no other chars are to be treated specially
- [x] do not convert the case of any of the letters
- [x] case and Case should be treated as different words
- [x] upon reaching the end of file the program should output
    - [x] the number of lines in the input file
    - [x] the total number of words in the input file
    - [x] list of each unique word in the input file along with the number of times it occurs

### Using this application

This word counter application includes a makefile. To make the application simply run "make" while in the "word-count" directory.

The application will then be made in the "bin" folder. The make file also includes commands to run the app.

To run the application without the make file command just target the java app in the /bin folder like below...

`java -cp bin WordCount text1.txt`

text1.txt can be replaced with any txt file you may place in the word-count folder
To use the makefile to run the app with the test1.txt file, run

`make run`

### Testing the application
The application was tested using manual testing and two text files.
To run the manual test cases, use the make commands.

---

This one will run test 1 with test1.txt file

`make test1`

Test one should show 8 lines with a word count of 23.

---

This one will run test 2 with test2.txt file

`make test2`

Test two should show 5 lines with a word count of 18.


