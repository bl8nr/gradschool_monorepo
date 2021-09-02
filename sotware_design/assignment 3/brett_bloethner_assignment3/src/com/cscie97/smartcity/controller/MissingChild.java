package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.SmartCityModelService;

/**
 * MissingChild
 * If a child is reported missing, then announce the missing childs location and dispatch a robot to retrieve the child
 */
public class MissingChild extends Command {

    /**
     * Default constructor shape passing values through to the Command class
     */
    public MissingChild(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, String subjectId) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, subjectId);
    }

    /**
     * On the original sensing device, announce a message with the child's location, then dispatch the robot nearest to
     * the original sensing device to go and retrieve the child
     * @throws CommandException if the device speaker output or robot dispatch fail
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing MissingChild command...");

        this.respondFromSensingDeviceSpeaker(String.format("person %s is at lat %s long %s, a robot is retrieving now, stay where you are.", this.subject.getPersonId(), this.subject.getLocation().getLatitude(), this.subject.getLocation().getLongitude()));
        this.mobilizeNearestAvailableRobot(String.format("retrieve person %s and bring to lat %s long %s of microphone", this.subject.getPersonId(), this.device.getLocation().getLatitude(), this.device.getLocation().getLongitude()));
    }
}
