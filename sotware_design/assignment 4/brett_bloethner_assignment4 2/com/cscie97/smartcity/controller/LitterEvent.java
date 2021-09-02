package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.Resident;
import com.cscie97.smartcity.model.SmartCityModelService;

/**
 * Litter Event
 * Once someone is caught littering, have the device ask them via speaker to not litter, dispatch a robot to clean up
 * the trash and charge the offending person 50 units
 */
public class LitterEvent extends Command {

    /**
     * Default constructor shape passing values through to the Command class
     */
    public LitterEvent(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, String subjectId, String authToken) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, subjectId, authToken);
    }

    /**
     * One the original sensing device respond with a do not litter message, then dispatch a robot to clean up the mess,
     * then charge the offending Person a 50 unit fine
     * @throws CommandException if the device speaker output, robot dispatch or transaction processing fail
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing LitterEvent command...");

        this.respondFromSensingDeviceSpeaker("Please do not litter.");
        this.mobilizeNearestAvailableRobot(String.format("clean garbage at lat %s long %s", device.getLocation().getLatitude(), device.getLocation().getLongitude()));
        this.processTransaction(((Resident) this.subject).getBlockchainAddress(), this.city.getBlockchainAddress(), 50);
    }
}
