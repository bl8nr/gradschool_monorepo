package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.LedgerException;
import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 * Command
 * The Command class is and abstract class that implements behavior used by all the concrete commands that inherit from
 * it
 */
public abstract class Command {
    Random rand = new Random();
    SmartCityModelService smartCityModelService;
    SmartCityLedgerService smartCityLedgerService;
    City city = null;
    Device device = null;
    Person subject = null;
    ArrayList<String> occupiedRobotsIds = new ArrayList<String>();

    /**
     * Construct a Command with the bare necessities needed to execute the command. The constructor might load the
     * necessary city as well as the necessary device and subject from the passed in model and ledger services.
     * @param smartCityModelService an instance of the smartCityModelService singleton
     * @param smartCityLedgerService an instance of the smartCityLedgerService singleton
     * @param cityId the ID of a valid city in the Smart City system
     * @param deviceId the ID of a valid device in the fetched City
     * @param subjectId the ID of a valid subject/person in the Smarty City System
     * @throws CommandException if getByCityId fails and no city can be got
     * @throws CommandException if shoPerson fails and no subject/person can be got
     */
    public Command(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, String cityId, String deviceId, String subjectId) throws CommandException {
        this.smartCityModelService = smartCityModelService;
        this.smartCityLedgerService = smartCityLedgerService;

        // if the cityId is provided then get the city, otherwise leave it null
        if (cityId != null) {
            try {
                this.city = smartCityModelService.getCityById(cityId);
            } catch (SmartCityModelServiceException e) {
                throw new CommandException("Failed to getCityById when cityId was provided");
            }
        }

        // if the city is available and a deviceId is provided then get the device, otherwise leave null
        if ((city != null) && (deviceId != null)) {
                this.device = this.city.getDeviceById(deviceId);
        }

        // if the subjectId is provided then get the subject/person, otherwise leave null
        if (subjectId != null) {
            try {
                this.subject = this.smartCityModelService.showPerson(subjectId);
            } catch (SmartCityModelServiceException e) {
                throw new CommandException("Failed to showPerson when subjectId was provided");
            }
        }
    }

    /**
     * Respond via speaker sensor output on the device that sensed the original event
     * @param message String, the message to be output via speaker
     * @throws CommandException if the sensor output fails to execute
     */
    public void respondFromSensingDeviceSpeaker(String message) throws CommandException {

        // city and device must exist to target sensor to output
        assert city != null;
        assert device != null;

        // create sensor output and execute it on the original sensing device
        try {
            System.out.println(String.format("Executing speaker output from origin sensing device with dialog: %s", message));
            SensorOutput sensorOutput = new SensorOutput(SensorOutputTypeEnum.SPEAKER, message);
            this.smartCityModelService.createSensorOutput(this.city.getCityId(), this.device.getDeviceId(), sensorOutput);
        } catch (SmartCityModelServiceException e) {
            throw new CommandException("Failed to output message from speaker of sensing device.");
        }
    }

    /**
     * Find the robot closes to where the event was sensed and dispatch it to a specific activity
     * @param activity String, the activity the Robot is directed to do
     * @throws CommandException if the Robot is failed to be dispatched to the event location
     */
    public void mobilizeNearestAvailableRobot(String activity) throws CommandException {

        // city and device must exist to find event location
        assert city != null;
        assert device != null;

        // get all of the robots in the city and set the nearest robot as the responder as long as the robot it not occupied
        Robot responder = null;
        for (Map.Entry<String, Device> entry : this.smartCityModelService.getDevicesInLocation(this.city.getLocation()).entrySet()) {
            Device v = entry.getValue();
            if ((v instanceof Robot) && (!this.occupiedRobotsIds.contains(v.getDeviceId()))) {
                if (responder == null) {
                    responder = (Robot) v;
                } else if (v.getLocation().getDistaceFromEdge(this.device.getLocation()) < responder.getLocation().getDistaceFromEdge(this.device.getLocation())) {
                    responder = (Robot) v;
                }
            }
        }

        // a responding robot must be available to be dispatched
        assert responder != null;

        // mark the responding robot as occupied so they're not dispatched again for the same event
        this.occupiedRobotsIds.add(responder.getDeviceId());

        // update/dispatch the robot with the new activity
        try {
            responder.setActivity(activity);
            this.smartCityModelService.updateDevice(this.city.getCityId(), responder.getDeviceId(), responder);
            System.out.println(String.format("Dispatching Robot %s to event location (travel distance %s) with activity: %s", responder.getDeviceId(), responder.getLocation().getDistaceFromEdge(this.device.getLocation()), activity));
        } catch (SmartCityModelServiceException e) {
            throw new CommandException("Failed to dispatch Robot to the event location");
        }
    }

    /**
     * Process a transaction from a payerID to a receiverId for a specified amount
     * @param payerAddress String, a valid ID for an account in the ledger
     * @param receiverAddress String, a valid ID for an account in the ledger
     * @param amount Integer, the amount to be transferred
     * @throws CommandException if the transaction processing fails
     */
    public void processTransaction(String payerAddress, String receiverAddress, Integer amount) throws CommandException {

        try {
            // confirm the payer has enough funds to make the transaction
            Integer payerAccountBalance = this.smartCityLedgerService.getAccountBalance(payerAddress);
            if ((payerAccountBalance > (amount + 10))) {

                // assign a random id to the transaction and process the transaction with a note and fee of 10
                String uniqueNumber = Integer.toString(rand.nextInt(999999));
                String transactionId = this.smartCityLedgerService.createTransaction(uniqueNumber, amount, 10, "Automated transaction #"+uniqueNumber, payerAddress, receiverAddress);
                System.out.println(String.format("Transaction processed for %s (+10 fee) from %s to %s with transactionId: %s", amount, payerAddress, receiverAddress, transactionId));
            } else {
                throw new CommandException("Failed to process transaction ledger.");
            }
        } catch (LedgerException e) {
            throw new CommandException("Failed to process transaction ledger.");
        }
    }

    /**
     * Throw and error if the execute() method has not been overridden
     * @throws CommandException always
     */
    public void execute() throws CommandException {
        throw new CommandException("abstract command.execute() not implemented in concrete command");
    }
}
