package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.SmartCityModelService;

/**
 * BrokenGlass
 * dispatch a Robot to clean up broken glass at the event location
 */
public class BrokenGlass extends Command {

    /**
     * Default constructor shape passing values through to the Command class. SubjectId is not used in this event so its
     * omitted.
     */
    public BrokenGlass(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, null);
    }

    /**
     * Dispatch the robot nearest to the sensing device location to clean up the broken glass
     * @throws CommandException if there is a failure in finding and dispatching a robot to the target location
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing BrokenGlass command...");

        this.mobilizeNearestAvailableRobot(String.format("clean up broken glass at lat %s long %s", device.getLocation().getLatitude(), device.getLocation().getLongitude()));
    }
}
