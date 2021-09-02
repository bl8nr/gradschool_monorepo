package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.InformationKiosk;
import com.cscie97.smartcity.model.Resident;
import com.cscie97.smartcity.model.SmartCityModelService;

/**
 * Movie Reservation
 * if the subject asking for reservation is a resident and the device is a kiosk then reserve movie tickets for the
 * subject after charging them 10 units
 */
public class MovieReservation extends Command {

    /**
     * Default constructor shape passing values through to the Command class
     */
    public MovieReservation(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, String subjectId) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, subjectId);
    }

    /**
     * if the original sensing device is a kiosk and the subject is a resident then charge the resident for the tickets
     * and announce via speaker the residents reservation
     * @throws CommandException if the original sensing device is not a Kiosk
     * @throws CommandException if the transaction processing or the speaker output of the reservation fail
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing MovieReservation command...");

        // throw error if the original sensing device is not a kiosk
        if (!(device instanceof InformationKiosk)) {
            throw new CommandException("Failed to execute MovieReservation command. Sensing device is not an Information Kiosk");
        }

        // respond with failure message if the subject is not a resident and so doesnt have a ledger account
        if (!(this.subject instanceof Resident)) {
            this.respondFromSensingDeviceSpeaker("your seats are could not be reserved; Blockchain accounts dont exist for Visitors.");
            return;
        }

        // process transaction and announce reserveation
        this.processTransaction(((Resident) this.subject).getBlockchainAddress(), this.device.getBlockchainAddress(), 10);
        this.respondFromSensingDeviceSpeaker("your seats are reserved; please arrive a few minutes early.");
    }
}
