package com.cscie97.smartcity.test;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.CommandProcessor;
import com.cscie97.smartcity.model.CommandProcessorException;
import com.cscie97.smartcity.controller.SmartCityControllerService;
import com.cscie97.smartcity.model.SmartCityModelService;


public class TestDriver {
    public static void main(String[] args) {

        CommandProcessor commandProcessor;
        SmartCityControllerService smartCityControllerService = SmartCityControllerService.getInstance();
        SmartCityLedgerService smartCityLedgerService = SmartCityLedgerService.getInstance();
        SmartCityModelService smartCityModelService = SmartCityModelService.getInstance();

        commandProcessor = new CommandProcessor();

        try {
            commandProcessor.processCommandFile(args[0]);
        } catch (CommandProcessorException e) {
            System.out.println(String.format("ERROR (%s): %s %s\n", "file read error", e.getCommand().toUpperCase(), e.getReason().toUpperCase()));
        }

    }
}
