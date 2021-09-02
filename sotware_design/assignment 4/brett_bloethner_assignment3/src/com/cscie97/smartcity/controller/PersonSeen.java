package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.SmartCityModelService;
import com.cscie97.smartcity.model.SmartCityModelServiceException;

/**
 * Person Seen
 * if a person is seen by an IoT device, then update that persons location in the Smart City system
 */
public class PersonSeen extends Command {

    /**
     * Default constructor shape passing values through to the Command class
     */
    public PersonSeen(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, String subjectId) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, subjectId);
    }

    /**
     * Update the subject (Person) location to the location of the original sensing device
     * @throws CommandException if the person update fails
     */
    @Override
    public void execute() throws CommandException {
        try {
            this.subject.setLocation(this.device.getLocation());
            this.smartCityModelService.updatePerson(this.subject.getPersonId(), subject);
            System.out.printf("Person successfully updated. %s: lat %s long %s \n", this.subject.getPersonId(), this.subject.getLocation().getLatitude(), this.subject.getLocation().getLongitude());
        } catch (SmartCityModelServiceException e) {
            throw new CommandException("Failed to update Person object");
        }
    }
}
