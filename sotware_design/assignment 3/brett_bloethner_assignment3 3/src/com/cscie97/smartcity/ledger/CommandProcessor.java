package com.cscie97.smartcity.ledger;

import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class CommandProcessor {
    private SmartCityLedgerService ledger;

    /**
     * Process a file of commands sequentially, ignoring comments and blank lines and keeping track of the line numbers
     * @param fileName String The path of the file containing the commands to be run
     * @throws CommandProcessorException if no file can be found at that path
     */
    public void processCommandFile(String fileName) throws CommandProcessorException {
        File file = new File(fileName);
        Scanner script;
        int lineNumber = 0;

        try {
            script = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new CommandProcessorException("FileNotFoundException", "File could not be Found", null);
        }


        // while there is a next line/command in the file, increment line counter and get and process next line/command
        while (script.hasNextLine()) {
            String line = script.nextLine();
            ++lineNumber;

            // ignore the line if its a comment or empty, catch errors and print them (dont stop execution)
            if ((!(line.startsWith("#"))) && (line.length() != 0)) {
                    try {
                        processCommand(line);
                    } catch (CommandProcessorException e) {
                        System.out.println(String.format("ERROR (%s): %s %s\n", lineNumber, e.getCommand().toUpperCase(), e.getReason().toUpperCase()));
                    }
            }
        }

        script.close();
    }

    /**
     * Process a single command, match the first arg to a set of available commands and call the ledger service passing
     * in the appropriate arg positions for that command
     * CITATION: https://stackoverflow.com/questions/7804335/split-string-on-spaces-in-java-except-if-between-quotes-i-e-treat-hello-wor
     * @param command String A single command containing args separated by spaces
     * @throws CommandProcessorException if the command (arg[0]) is not recognized in the switch statement
     * @throws CommandProcessorException if a command files to be successfully executed by the ledger service
     */
    public void processCommand(String command) throws CommandProcessorException {
        List<String> args = new ArrayList<>();

        // create the regex matcher to find substrings separated by spaces
        Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
        Matcher matcher = regex.matcher(command);

        // while there are remaining results in the matcher...
        while (matcher.find()) {
            // ...add the result the list of args, removing surrounding quotation marks
            args.add(matcher.group().replace("\"", ""));
        }

        // use switch statement based on the first arg (command/action arg)
        // map the remaining args based on ledger method being called, print out result
        String action = args.get(0);
        try {
            switch (action) {
                case "create-ledger":
                    this.ledger = SmartCityLedgerService.getInstance();
                    System.out.println(String.format("NEW LEDGER CREATED"));
                    System.out.println(String.format("Name: %s", this.ledger.getName()));
                    System.out.println(String.format("Description: %s", this.ledger.getDescription()));
                    System.out.println(String.format("Seed: %s\n", this.ledger.getSeed()));
                    break;
                case "create-account":
                    String createAccountResponse = this.ledger.createAccount(args.get(1));
                    System.out.println(String.format("NEW ACCOUNT CREATED"));
                    System.out.println(String.format("AccountId: %s\n", createAccountResponse));
                    break;
                case "process-transaction":
                    String transactionIdentifier = this.ledger.createTransaction(args.get(1),
                            Integer.parseInt(args.get(3)),
                            Integer.parseInt(args.get(5)),
                            args.get(7),
                            args.get(9),
                            args.get(11));
                    System.out.println(String.format("TRANSACTION PROCESSED"));
                    System.out.println(String.format("TransactionId: %s\n", transactionIdentifier));
                    break;
                case "get-account-balance":
                    int accountBalance = ledger.getAccountBalance(args.get(1));
                    System.out.println(String.format("GET ACCOUNT BALANCE"));
                    System.out.println(String.format("%s: %s\n", args.get(1), accountBalance));
                    break;
                case "get-account-balances":
                    System.out.println("GET ACCOUNT BALANCES");
                    System.out.println(String.format("%s\n", ledger.getAccountBalances()));
                    break;
                case "get-block":
                    Block block = this.ledger.getBlock(Integer.parseInt(args.get(1)));
                    System.out.println(String.format("GET BLOCK"));
                    System.out.println(String.format("Block Number: %s", Integer.parseInt(args.get(1))));
                    System.out.println(String.format("Account Balance Map: %s", block.getAccountBalanceMap()));
                    System.out.println(String.format("Transactions: %s\n", block.getTransactionList()));
                    break;
                case "get-transaction":
                    String transactionIdForGetTransaction = args.get(1);
                    Transaction getTransactionResponse = this.ledger.getTransaction(transactionIdForGetTransaction);
                    System.out.println(String.format("GET TRANSACTION"));
                    System.out.println(String.format("%s\n", getTransactionResponse.toString()));
                    break;
                case "validate":
                    this.ledger.validate();
                    System.out.println("BLOCKCHAIN SUCCESSFULLY VALIDATED\n");
                    break;
                default:
                    throw new CommandProcessorException(action, "Command not recognized", null);
            }
        } catch (LedgerException e) {
            throw new CommandProcessorException(action, e.getReason(), null);
        }
    }
}
