package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.SmartCityModelService;

/**
 * EmergencyTwo
 * Once an emergency of traffic accident occurs then announce a local notice to stay calm, then dispatch the two nearest
 * robots to help assist with the emergency
 */
public class EmergencyTwo extends Command {
    private EmergencyTwoTypeEnum emergencyType;

    /**
     * Default constructor shape passing values through to the Command class
     * @param emergencyType EmergencyTwoTypeNum, the type of emergency occurring in the event
     */
    public EmergencyTwo(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, EmergencyTwoTypeEnum emergencyType, String cityId, String deviceId) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, null);
        this.emergencyType = emergencyType;
    }

    /**
     * On the original sensing device, output a speaker announcement ot stay calm, then dispatch the two nearest robots
     * to respond to the emergency
     * @throws CommandException if the speaker output or either robot dispatch fails
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing EmergencyTwo command...");

        this.respondFromSensingDeviceSpeaker("Stay calm, help is on its way.");
        this.mobilizeNearestAvailableRobot(String.format("address %s at lat %s long %s", this.emergencyType, this.device.getLocation().getLatitude(), this.device.getLocation().getLongitude()));
        this.mobilizeNearestAvailableRobot(String.format("address %s at lat %s long %s", this.emergencyType, this.device.getLocation().getLatitude(), this.device.getLocation().getLongitude()));
    }
}
