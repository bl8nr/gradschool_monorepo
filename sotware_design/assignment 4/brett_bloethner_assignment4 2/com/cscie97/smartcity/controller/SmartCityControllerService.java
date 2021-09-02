package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.authentication.AuthServiceException;
import com.cscie97.smartcity.authentication.AuthToken;
import com.cscie97.smartcity.authentication.SmartCityAuthenticationService;
import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.SensorEvent;
import com.cscie97.smartcity.model.SmartCityModelService;
import com.cscie97.smartcity.model.iObserver;

import java.util.HashMap;

/**
 * Smart City Controller Service
 * the Smart City Controller Service is responsible for subscribing to events triggered by the Smart City Model Service.
 * Upon receiving an event the Controller Service is to create Commands using the command pattern and execute those
 * same commands immediately afterwards.
 */
public class SmartCityControllerService implements iObserver {
    private static SmartCityControllerService uniqueInstance;
    private SmartCityModelService smartCityModelService;
    private SmartCityLedgerService smartCityLedgerService;
    private SmartCityAuthenticationService smartCityAuthenticationService;
    private HashMap<String, Integer> co2HashTable = new HashMap<String, Integer>();
    private AuthToken controllerAuthToken;

    /**
     * SmartCityControllerService constructor
     * @param smartCityModelService a valid SmartCityModelService singleton
     * @param smartCityLedgerService a valid SmartCityLedgerService singleton
     */
    private SmartCityControllerService(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, SmartCityAuthenticationService smartCityAuthenticationService) {
        this.smartCityLedgerService = smartCityLedgerService;
        this.smartCityModelService = smartCityModelService;
        this.smartCityAuthenticationService = smartCityAuthenticationService;
        System.out.println("SmartCityControllerService Initialized");

        try {
            this.controllerAuthToken = this.smartCityAuthenticationService.login("smcs", "fldi954*#jd&36s");
        } catch (AuthServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establish a singleton pattern using the getInstance method and a unique instance static var
     * @return the SmartCityControllerService singleton
     */
    public static SmartCityControllerService getInstance() {

        // if a singleton instance doesnt already exist then create one and register it as an observer on the ModelService
        if (uniqueInstance == null) {
            uniqueInstance = new SmartCityControllerService(SmartCityModelService.getInstance(), SmartCityLedgerService.getInstance(), SmartCityAuthenticationService.getInstance());
            SmartCityModelService.getInstance().registerObserver(uniqueInstance);
        }

        // then return the singleton SmartCityControllerService
        return uniqueInstance;
    }

    /**
     * Create the command, this is basically a concrete command factory
     * @param sensorEvent a valid sensor event which may contain a cityId, deviceId, subjectId nad value
     * @throws SmartCityControllerServiceException if there is when parsing the value executing the concrete command
     * @throws CommandException if there is a failure when creating the concrete command
     */
    private void createCommand(SensorEvent sensorEvent) throws SmartCityControllerServiceException, CommandException {
        Command command = null;

        // switch based one the sensor event type
        switch (sensorEvent.getType()) {
            // if the sensor was a camera...
            case CAMERA:
                switch (sensorEvent.getValue().toLowerCase()) {
                    // and it sense a fire, then create an emergency one command
                    case "fire":
                        command = new EmergencyOne(smartCityModelService, smartCityLedgerService, EmergencyOneTypeEnum.FIRE, sensorEvent.getCityId(), sensorEvent.getDeviceId(), controllerAuthToken.getToken());
                        break;
                    // and it sensed a flood, then create an emergency one command
                    case "flood":
                        command = new EmergencyOne(smartCityModelService, smartCityLedgerService, EmergencyOneTypeEnum.FLOOD, sensorEvent.getCityId(), sensorEvent.getDeviceId(), controllerAuthToken.getToken());
                        break;
                    // and it sensed a earthquake, then create an emergency one command
                    case "earthquake":
                        command = new EmergencyOne(smartCityModelService, smartCityLedgerService, EmergencyOneTypeEnum.EARTHQUAKE, sensorEvent.getCityId(), sensorEvent.getDeviceId(), controllerAuthToken.getToken());
                        break;
                    // and it sensed a severe weather event, then create an emergency one command
                    case "severe weather":
                        command = new EmergencyOne(smartCityModelService, smartCityLedgerService, EmergencyOneTypeEnum.SEVERE_WEATHER, sensorEvent.getCityId(), sensorEvent.getDeviceId(), controllerAuthToken.getToken());
                        break;
                    // and it sensed a traffic accident event, then create an emergency one command
                    case "traffic_accident":
                        command = new EmergencyTwo(smartCityModelService, smartCityLedgerService, EmergencyTwoTypeEnum.TRAFFIC_ACCIDENT, sensorEvent.getCityId(), sensorEvent.getDeviceId(), controllerAuthToken.getToken());
                        break;
                    // and it sensed a littering event, then create a litter event command
                    case "littering":
                        command = new LitterEvent(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), sensorEvent.getSubject(), controllerAuthToken.getToken());
                        break;
                    // and it sensed a person seen event, then create a person seen command
                    case "person_seen":
                        command = new PersonSeen(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), sensorEvent.getSubject(), controllerAuthToken.getToken());
                        break;
                    // and it sensed a person boarding bus event, then create a board bus command
                    case "person boards bus":
                        command = new BoardBus(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), sensorEvent.getSubject(), controllerAuthToken.getToken());
                        break;
                    default:

                        // or if no event can be deduced from the event value, then see if its a parking event and if so create a parking event command, else throw error
                        if (sensorEvent.getValue().toLowerCase().contains("parked for")) {
                            String cityOfVehicle = sensorEvent.getValue().split(" ")[1].split(":")[0];
                            String idOfVehicle = sensorEvent.getValue().split(" ")[1].split(":")[1];
                            Integer duration = Integer.parseInt(sensorEvent.getValue().split(" ")[4]);
                            command = new ParkingEvent(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), cityOfVehicle, idOfVehicle, duration, controllerAuthToken.getToken());
                            break;
                        } else {
                            throw new SmartCityControllerServiceException(String.format("Failed to process event, Unprocessable event received: %s", sensorEvent.getValue()));
                        }
                }
            break;

            // if the sensor was a microphone...
            case MICROPHONE:
                AuthToken authToken;

                // get the auth token of the subject of the microphone command and make command on behalf of the subject with the subjects auth token
                try {
                    authToken = this.smartCityAuthenticationService.login(sensorEvent.getCredential());
                } catch (AuthServiceException e) {
                    throw new SmartCityControllerServiceException("Failed to process event, Could not get token on behalf of user");
                }

                // and the value implies a broken glass event, then create a broken glass command
                if (sensorEvent.getValue().equals("broken_glass_sound")) {
                    command = new BrokenGlass(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), authToken.getToken());
                // else if the value implies a bus route inquiry event, then create a bus route command
                } else if (sensorEvent.getValue().toLowerCase().contains("does this bus go to")) {
                    command = new BusRoute(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), sensorEvent.getSubject(), sensorEvent.getValue().substring(20, (sensorEvent.getValue().length() - 1)), authToken.getToken());
                // else if the value implies a missing child event, then create a missing command
                } else if (sensorEvent.getValue().toLowerCase().contains("can you help me find my child")) {
                    command = new MissingChild(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), sensorEvent.getSubject(), authToken.getToken());
                // else if the value implies a movie info inquiry event, then create a movie info command
                } else if (sensorEvent.getValue().toLowerCase().contains("what movies are showing tonight")) {
                    command = new MovieInfo(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), sensorEvent.getSubject(), authToken.getToken());
                // else if the value implies a movie reservation event, then create a movie reservation command
                } else if (sensorEvent.getValue().toLowerCase().contains("reserve")) {
                    command = new MovieReservation(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), sensorEvent.getSubject(), authToken.getToken());
                // otherwise throw an error because the event is not processable
                } else {
                    throw new SmartCityControllerServiceException("Unprocessable event received");
                }
                break;

            // if the sensor was a thermometer...
            case THERMOMETER:
                // throw an exception because the Smart City Controller Service does not support this type of event yet
                throw new SmartCityControllerServiceException("Unprocessable thermometer event received");

            // if the sensor was a CO2 meter...
            case CO2METER:

                // parse the measurement to see if its above or below the recommended limit
                Boolean isSensedCo2Over1000 = Integer.parseInt(sensorEvent.getValue()) > 1000;
                Integer currentCo2Record = this.co2HashTable.get(sensorEvent.getCityId());
                System.out.println("Recording CO2 Meter Reading...");

                // if relevant city has no CO2 record yet, then create the record and set it to 0 and store it
                if (currentCo2Record == null) {
                    this.co2HashTable.put(sensorEvent.getCityId(), 0);
                    currentCo2Record = this.co2HashTable.get(sensorEvent.getCityId());
                }

                // if the reading is above 1000, move the current record to +1, or add 1 if its already > 0
                if (isSensedCo2Over1000) {
                    currentCo2Record = currentCo2Record <= 0 ? 0 : currentCo2Record;
                    this.co2HashTable.put(sensorEvent.getCityId(), currentCo2Record + 1);
                }

                // if the reading is below 1000, move the current record to -1, or subtract 1 if its already < 0
                if (!isSensedCo2Over1000) {
                    currentCo2Record = currentCo2Record >= 0 ? 0 : currentCo2Record;
                    this.co2HashTable.put(sensorEvent.getCityId(), currentCo2Record - 1);
                }

                // if the resulting city CO2 record is 3 or greater, then create CO2 command to disable all cars in city
                currentCo2Record = this.co2HashTable.get(sensorEvent.getCityId());
                if (currentCo2Record >= 3) {
                    command = new CO2Event(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(),false, controllerAuthToken.getToken());
                // else if the resulting city CO2 record is -3 or less, then create CO2 command to enable all cars in city
                } else if (currentCo2Record <= -3) {
                    command = new CO2Event(smartCityModelService, smartCityLedgerService, sensorEvent.getCityId(), sensorEvent.getDeviceId(), true, controllerAuthToken.getToken());
                }
                break;

            // otherwise the sensor type is unrecognized, so throw an exception
            default:
                throw new SmartCityControllerServiceException("Unprocessable event received, sensor type not recognized");

        }

        // if a command was created, then try to execute that command and throw an error if execution fails
        if (command != null) {
            try {
                command.execute();
            } catch (CommandException e) {
                throw new SmartCityControllerServiceException("Failed to execute execute method on concrete command");
            }
        }
    }

    /**
     * Observer Pattern Updater
     * @param sensorEvent SensorEvent, a valid sensor event reflecting the events details
     * @throws SmartCityControllerServiceException if there is a failure while trying to create and execute the resulting command
     */
    @Override
    public void update(SensorEvent sensorEvent) throws SmartCityControllerServiceException {
        try {
            this.createCommand(sensorEvent);
        } catch (CommandException e) {
             throw new SmartCityControllerServiceException(String.format("%s: %s",e.className(), e.getReason()));
        }
    }
}
