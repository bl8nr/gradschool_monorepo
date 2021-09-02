package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.SmartCityModelService;
import com.cscie97.smartcity.model.SmartCityModelServiceException;
import com.cscie97.smartcity.model.InformationKiosk;

/**
 * MovieInfo
 * if an information kiosk is asked for movie times, then respond with the current movie times and update the kiosk
 * display
 */
public class MovieInfo extends Command {

    /**
     * Default constructor shape passing values through to the Command class
     */
    public MovieInfo(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, String subjectId) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, subjectId);
    }

    /**
     * if the original sensing device is a kiosk, then announce that Casablanca is showing and display the Casablanca
     * cover image
     * @throws CommandException if the original sensing device is not a kiosk
     * @throws CommandException if the kiosk fails to output the schedule via speaker or update its display
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing MovieInfo command...");

        // throw exception if the original sensing device is not a kiosk
        if (!(device instanceof InformationKiosk)) {
            throw new CommandException("Failed to execute MovieInfo command. Sensing device is not an Information Kiosk");
        }

        // respond with schedule and update the kiosk display accordingly
        try {
            this.respondFromSensingDeviceSpeaker(String.format("Casablanca is showing at 9 pm"));
            ((InformationKiosk) this.device).setImageUri("https://en.wikipedia.org/wiki/Casablanca_(film)#/media/File:CasablancaPoster-Gold.jpg");
            this.smartCityModelService.updateDevice(this.city.getCityId(), this.device.getDeviceId(), this.device);
        } catch (SmartCityModelServiceException e) {
            throw new CommandException("Failed to execute MovieInfo command.");
        }
    }
}
