package com.cscie97.smartcity.controller;

import com.cscie97.smartcity.ledger.SmartCityLedgerService;
import com.cscie97.smartcity.model.*;

import java.util.Map;

/**
 * EmergencyOne
 * Once an emergency of fire, flood, earthquake or severe weather occur then announce a warning in the city of the
 * occurrence, dispatch half of the robots in the city to assist in the relief effort and dispatch the other half
 * to help city persons take shelter
 */
public class EmergencyOne extends Command {
    private EmergencyOneTypeEnum emergencyType;

    /**
     * Default constructor shape passing values through to the Command class, deviceId is not needed in this event so
     * its omitted
     * @param emergencyType EmergencyOneTypeNum, the type of emergency occurring in the event
     */
    public EmergencyOne(SmartCityModelService smartCityModelService, SmartCityLedgerService smartCityLedgerService, EmergencyOneTypeEnum emergencyType, String cityId, String deviceId) throws CommandException {
        super(smartCityModelService, smartCityLedgerService, cityId, deviceId, null);
        this.emergencyType = emergencyType;
    }

    /**
     * announce the emergency message in the city then dispatch half the robots to a relief activity and dispatch the
     * remaining robots to help people take shelter
     * @throws CommandException if the city fails to announce the warning across all devices in the city
     */
    @Override
    public void execute() throws CommandException {
        System.out.println("Executing EmergencyOne command...");

        // announce the message throughout the city
        try {
            this.smartCityModelService.createSensorOutput(this.city.getCityId(), new SensorOutput(SensorOutputTypeEnum.SPEAKER, String.format("There is a %s emergency in %s, please find shelter immediately", emergencyType, this.city.getName())));
        } catch (SmartCityModelServiceException e) {
            throw new CommandException("Failed to announce city wide emergency message");
        }

        // get a count of all the robots in the city
        Integer cityWideRobotCount = 0;
        for (Map.Entry<String, Device> entry : this.city.getAllDevices().entrySet()) {
            Device v = entry.getValue();
            if (v instanceof Robot) {
                cityWideRobotCount++;
            }
        }

        // dispatch half of those robots to address the emergency
        for(int i = 0; i < (cityWideRobotCount / 2); ++i) {
            this.mobilizeNearestAvailableRobot(String.format("address %s at lat %s long %s", emergencyType, this.device.getLocation().getLatitude(), this.device.getLocation().getLongitude()));
        }

        // dispatch the other half of those robots to help people find shelter
        for(int i = 0; i < (cityWideRobotCount / 2); ++i) {
            this.mobilizeNearestAvailableRobot("Help people find shelter");
        }
    }
}
