package com.cscie97.smartcity.ledger.test;

import com.cscie97.smartcity.ledger.CommandProcessor;
import com.cscie97.smartcity.ledger.CommandProcessorException;

public class TestDriver {

    public static void main(String[] args) {
        CommandProcessor commandProcessor = new CommandProcessor();

        try {
            commandProcessor.processCommandFile(args[0]);
        } catch (CommandProcessorException e) {
            System.out.println(String.format("ERROR (%s): %s %s\n", "file read error", e.getCommand().toUpperCase(), e.getReason().toUpperCase()));
        }
    }

}
