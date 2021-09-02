package com.cscie97.smartcity.test;

import com.cscie97.smartcity.model.CommandProcessor;
import com.cscie97.smartcity.model.CommandProcessorException;

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
