package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.*;

/**
 * Board Bus
 * Event triggered once a person boards a bus. Welcome the person onto the bus and charge them the fee if they're
 * a city resident
 */
public class BoardBus extends Command {

    /**
     * Default constructor shape passing values through to the Command class
     */
    public BoardBus(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, String subjectId, String authToken) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, subjectId, authToken);
    }

    /**
     * Welcome the subject onto the bus and charge them the bus fare
     * @throws CommandException if the bus fare transaction fails to go through
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing BoardBus command...");

        // if the event is being reported from a bus then welcome the person onto the bus via the sensing device (bus) speaker
        if ((device instanceof Vehicle) && (((Vehicle) device).getType() == VehicleTypeEnum.BUS)) {
            this.respondFromSensingDeviceSpeaker(String.format("hello, good to see you %s", this.subject.getPersonId()));

            // if the person is also a resident, then attempt to charge them the bus fare
            if (subject instanceof Resident) {
                this.processTransaction(((Resident) this.subject).getBlockchainAddress(), this.device.getBlockchainAddress(), ((Vehicle) this.device).getFee());
            } else {
                System.out.println("Bus rider is not a Resident. Bus fare has been waived.");
            }
        }
    }
}
