package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.*;

import java.util.Map;

/**
 * CO2Event
 * Get all of the cars in a city and disable them or enabled them based on the isCarsEnabled boolean
 */
public class CO2Event extends Command {
    Boolean isCarsEnabled;

    /**
     * Default constructor shape passing values through to the Command class
     * @param isCarsEnabled Boolean, dictates whether City cars will be enabled or disabled
     */
    public CO2Event(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, Boolean isCarsEnabled, String authToken) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, null, authToken);
        this.isCarsEnabled = isCarsEnabled;
    }

    /**
     * Get all of the cars in the events city, set the enabled boolean according to the isCarsEnabled boolean provided
     * @throws CommandException if any Cars fail in having their enabled status updated
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing CO2Event command...");

        for (Map.Entry<String, Device> entry : this.smartCityModelService.getDevicesInLocation(this.city.getLocation(), this.authToken).entrySet()) {
            Device v = entry.getValue();
            if (v instanceof Vehicle && (((Vehicle) v).getType() == VehicleTypeEnum.CAR)) {
                v.setEnabled(isCarsEnabled);
                System.out.printf("Setting enabled value on car with id %s to: %s \n", v.getDeviceId(), isCarsEnabled);
                try {
                    this.smartCityModelService.updateDevice(city.getCityId(), v.getDeviceId(), v, this.authToken);
                } catch (SmartCityModelServiceException e) {
                    throw new CommandException("Failed to update Vehicles in City limits.");
                }
            }
        }
    }
}
