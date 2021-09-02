package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.SmartCityModelService;
import com.cscie97.smartcity.model.Vehicle;
import com.cscie97.smartcity.model.VehicleTypeEnum;

/**
 * BusRoute
 * Answer whether or not a bus gos to a specific location
 */
public class BusRoute extends Command {
    String locationString;

    /**
     * Default constructor shape passing values through to the Command class
     * @param locationString String, the location the subject is asking if the bus goes to
     */
    public BusRoute(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, String subjectId, String locationString, String authToken) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, subjectId, authToken);
        this.locationString = locationString;
    }

    /**
     * if the sensing device is a bus, then respond with an answer that the bus goes to the location
     * @throws CommandException if the sensing device (bus) fails to output a message from its speaker
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing BusRoute command...");

        if ((device instanceof Vehicle) && (((Vehicle) device).getType() == VehicleTypeEnum.BUS)) {
            this.respondFromSensingDeviceSpeaker(String.format("Yes, this bus goes to %s", this.locationString));
        }
    }
}
