package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.ParkingSpace;
import com.cscie97.smartcity.model.SmartCityModelService;
import com.cscie97.smartcity.model.SmartCityModelServiceException;
import com.cscie97.smartcity.model.Vehicle;

/**
 * Parking Event
 * get the patron vehicle and charge that vehicles ledger account according to the parking space fee and how long they
 * were parked
 */
public class ParkingEvent extends Command {
    String cityOfVehicle;
    String idOfVehicle;
    Integer duration;

    /**
     * Default constructor shape passing values through to the Command class, subjectId is not used in this event so its
     * omitted
     * @param cityOfVehicle String, the cityId of the City where the Vehicle is registered
     * @param idOfVehicle String, the deviceId of the Vehicle that is parked
     * @param duration Integer, number of hours the Vehicle was parked in the parking space
     */
    public ParkingEvent(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, String cityOfVehicle, String idOfVehicle, Integer duration, String authToken) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, null, authToken);

        this.cityOfVehicle = cityOfVehicle;
        this.idOfVehicle = idOfVehicle;
        this.duration = duration;
    }

    /**
     * Get the vehicle object and charge to the vehicle account the fee on the device (parking space) times the duration
     * @throws CommandException if the Vehicle search results in technical failure
     * @throws NullPointerException if the Vehicle cannot be found in the city records
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing ParkingEvent command...");
        Vehicle patronVehicle = null;

        // find vehicle in the city where the vehicle is registered
        try {
            patronVehicle = (Vehicle) this.smartCityModelService.getCityById(this.cityOfVehicle, this.authToken).getDeviceById(this.idOfVehicle);
        } catch (SmartCityModelServiceException e) {
            throw new CommandException("Failed to execute ParkingEvent command. Could not retrieve Vehicle object.");
        }

        // if the vehicle record is found, then charge them accordingly
        assert patronVehicle != null;
        this.processTransaction(patronVehicle.getBlockchainAddress(), this.device.getBlockchainAddress(), (((ParkingSpace) this.device).getRate() * duration));
    }
}
