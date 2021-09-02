package com.cscie97.smartcity.model;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command Processor
 * The command processor class takes in single command or a file path and process all the included commands. It also
 * prints out the responses and will report errors into the console.
 */
public class CommandProcessor {
    private SmartCityModelService smartCityModelService;

    /**
     * Process a file of commands sequentially, ignoring comments and blank lines and keeping track of the line numbers
     *
     * @param fileName String The path of the file containing the commands to be run
     * @throws CommandProcessorException if no file can be found at that path
     */
    public void processCommandFile(String fileName) throws CommandProcessorException {
        smartCityModelService = new SmartCityModelService();
        File file = new File(fileName);
        Scanner script;
        int lineNumber = 0;

        try {
            script = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new CommandProcessorException("FileNotFoundException", "File could not be Found", null);
        }


        // while there is a next line/command in the file, increment line counter and get and process next line/command
        while (script.hasNextLine()) {
            String line = script.nextLine();
            ++lineNumber;

            // ignore the line if its a comment or empty, catch errors and print them (dont stop execution)
            if ((!(line.startsWith("#"))) && (line.length() != 0)) {
                try {
                    processCommand(line);
                } catch (CommandProcessorException e) {
                    System.out.printf("ERROR (%s): %s FAILED, %s%n", lineNumber, e.getCommand().toUpperCase(), e.getReason().toUpperCase());
                }
            }
        }

        script.close();
    }

    /**
     * Process a single command
     * @param command String: the entire command
     * @throws CommandProcessorException if the command is not recognized
     * @throws CommandProcessorException if the Smart City Model Service throws an error below it
     */
    public void processCommand(String command) throws CommandProcessorException {
        List<String> args = new ArrayList<>();

        // create the regex matcher to find substrings separated by spaces
        Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
        Matcher matcher = regex.matcher(command);

        // while there are remaining results in the matcher...
        while (matcher.find()) {
            // ...add the result the list of args, removing surrounding quotation marks
            args.add(matcher.group().replace("\"", ""));
        }

        // get the subject and the action of the command
        String action = args.get(0);
        String subject = args.get(1);
        String[] cityIdDeviceId;

        // create a location object
        Location location = new Location((float) 0.0, (float) (0.0), (float) 0.0);


        try {

            // switch based on the action provided in the command (define, update, show or create)
            switch (action) {
                case "define":

                    // create location since its required for each type of define
                    Float lat = Float.parseFloat(getRequiredArgumentByKey("lat", args));
                    Float lon = Float.parseFloat(getRequiredArgumentByKey("long", args));
                    Float radius = Float.parseFloat((getArgumentByKey("radius", args) != null) ? getArgumentByKey("radius", args) : "0.0");
                    location = new Location(lat, lon, radius);

                    // switch based on the subject provided in the command (city, street-sign, info-kiosk, street-light, parking-space, robot, vehicle, resident, visitor)
                    switch (subject) {
                        case "city":

                            // create new city and save new city
                            String cityUuid = this.smartCityModelService.createCity(
                                    args.get(2),
                                    getRequiredArgumentByKey("name", args),
                                    getRequiredArgumentByKey("account", args),
                                    location);
                            System.out.printf("City %s created. ID: %s%n", args.get(4), cityUuid);
                            break;

                        case "street-sign":

                            // create new street-sign and save new street sign
                            cityIdDeviceId = args.get(2).split(":");
                            StreetSign streetSign = new StreetSign(
                                    cityIdDeviceId[1],
                                    getRequiredArgumentByKey("account", args),
                                    location,
                                    Boolean.parseBoolean(getRequiredArgumentByKey("enabled", args)),
                                    getRequiredArgumentByKey("text", args)
                            );
                            smartCityModelService.addDevice(cityIdDeviceId[0], streetSign);
                            System.out.printf("\nSTREET SIGN CREATED IN CITY %s, %s\n%n", cityIdDeviceId[0], streetSign.toString());
                            break;

                        case "info-kiosk":

                            // create new information kiosk and save new information kiosk
                            cityIdDeviceId = args.get(2).split(":");
                            InformationKiosk informationKiosk = new InformationKiosk(
                                    cityIdDeviceId[1],
                                    getRequiredArgumentByKey("account", args),
                                    location,
                                    Boolean.parseBoolean(getRequiredArgumentByKey("enabled", args)),
                                    getRequiredArgumentByKey("image", args)
                            );
                            smartCityModelService.addDevice(cityIdDeviceId[0], informationKiosk);
                            System.out.printf("\nINFORMATION KIOSK CREATED IN CITY %s, %s\n%n", cityIdDeviceId[0], informationKiosk.toString());
                            break;

                        case "street-light":

                            // create new street light and save new street light
                            cityIdDeviceId = args.get(2).split(":");
                            StreetLight streetLight = new StreetLight(
                                    cityIdDeviceId[1],
                                    getRequiredArgumentByKey("account", args),
                                    location,
                                    Boolean.parseBoolean(getRequiredArgumentByKey("enabled", args)),
                                    Integer.parseInt(getRequiredArgumentByKey("brightness", args))
                            );
                            smartCityModelService.addDevice(cityIdDeviceId[0], streetLight);
                            System.out.printf("\nSTREET LIGHT CREATED IN CITY %s, %s\n%n", cityIdDeviceId[0], streetLight.toString());
                            break;

                        case "parking-space":

                            // create new parking space and save new parking space
                            cityIdDeviceId = args.get(2).split(":");
                            ParkingSpace parkingSpace = new ParkingSpace(
                                    cityIdDeviceId[1],
                                    getRequiredArgumentByKey("account", args),
                                    location,
                                    Boolean.parseBoolean(getRequiredArgumentByKey("enabled", args)),
                                    Integer.parseInt(getRequiredArgumentByKey("rate", args))
                            );
                            smartCityModelService.addDevice(cityIdDeviceId[0], parkingSpace);
                            System.out.printf("\nPARKING SPACE CREATED IN CITY %s, %s\n%n", cityIdDeviceId[0], parkingSpace.toString());
                            break;

                        case "robot":

                            // create new robot and save new robot
                            cityIdDeviceId = args.get(2).split(":");
                            Robot robot = new Robot(
                                    cityIdDeviceId[1],
                                    getRequiredArgumentByKey("account", args),
                                    location,
                                    Boolean.parseBoolean(getRequiredArgumentByKey("enabled", args)),
                                    getRequiredArgumentByKey("activity", args)
                            );
                            smartCityModelService.addDevice(cityIdDeviceId[0], robot);
                            System.out.printf("\nROBOT CREATED IN CITY %s, %s\n%n", cityIdDeviceId[0], robot.toString());
                            break;

                        case "vehicle":

                            // create new vehicle and save new vehicle
                            cityIdDeviceId = args.get(2).split(":");
                            Vehicle vehicle = new Vehicle(
                                    cityIdDeviceId[1],
                                    getRequiredArgumentByKey("account", args),
                                    location,
                                    Boolean.parseBoolean(getRequiredArgumentByKey("enabled", args)),
                                    VehicleTypeEnum.valueOf(getRequiredArgumentByKey("type", args).toUpperCase()),
                                    getRequiredArgumentByKey("activity", args),
                                    Integer.parseInt(getRequiredArgumentByKey("capacity", args)),
                                    Integer.parseInt(getRequiredArgumentByKey("fee", args))
                            );
                            smartCityModelService.addDevice(cityIdDeviceId[0], vehicle);
                            System.out.printf("\nVEHICLE CREATED IN CITY %s, %s\n%n", cityIdDeviceId[0], vehicle.toString());
                            break;

                        case "resident":

                            // create new resident and save new resident
                            Resident resident = new Resident(
                                    args.get(2),
                                    getRequiredArgumentByKey("name", args),
                                    getRequiredArgumentByKey("bio-metric", args),
                                    getRequiredArgumentByKey("phone", args),
                                    getRequiredArgumentByKey("account", args),
                                    ResidentRoleEnum.valueOf(getRequiredArgumentByKey("role", args).toUpperCase()),
                                    location
                            );
                            smartCityModelService.createPerson(resident);
                            System.out.printf("\nRESIDENT CREATED %s\n%n", resident.toString());
                            break;

                        case "visitor":

                            // create new visitor and save new visitor
                            Visitor visitor = new Visitor(
                                    args.get(2),
                                    getRequiredArgumentByKey("bio-metric", args),
                                    location
                            );
                            smartCityModelService.createPerson(visitor);
                            System.out.printf("\nVISITOR CREATED %s\n%n", visitor.toString());
                            break;

                    }
                    break;
                case "update":

                    // switch based on the subject provided in the command (city, street-sign, info-kiosk, street-light, parking-space, robot, vehicle, resident, visitor)
                    switch (subject) {
                        case "city":

                            // update city
                            City updatedCity = this.smartCityModelService.updateCity(args.get(2), this.getRequiredArgumentByKey("account", args));
                            System.out.printf("\nCITY UPDATED %s %s%n", args.get(2), updatedCity);
                            break;

                        case "street-sign":

                            // create new street-sign from the current street-sign
                            cityIdDeviceId = args.get(2).split(":");
                            StreetSign currentStreetSign = (StreetSign) this.smartCityModelService.getDevice(cityIdDeviceId[0], cityIdDeviceId[1]);
                            StreetSign updatedStreetSign = new StreetSign(currentStreetSign);

                            // update account in new street-sign if the param is provided in the command
                            if (this.getArgumentByKey("account", args) != null) {
                                updatedStreetSign.setBlockchainAddress(this.getArgumentByKey("account", args));
                            }

                            // update enabled in new street-sign if the param is provided in the command
                            if (this.getArgumentByKey("enabled", args) != null) {
                                updatedStreetSign.setEnabled(Boolean.parseBoolean(getArgumentByKey("enabled", args)));
                            }

                            // update text in new street-sign if the param is provided in the command
                            if (this.getArgumentByKey("text", args) != null) {
                                updatedStreetSign.setText(this.getArgumentByKey("text", args));
                            }

                            // replace the current street-sign with the newly updated street-sign
                            Device savedUpdatedDevice = smartCityModelService.updateDevice(cityIdDeviceId[0], cityIdDeviceId[1], updatedStreetSign);
                            System.out.printf("\nSTREET SIGN UPDATED IN %s %s%n", cityIdDeviceId[0], savedUpdatedDevice);
                            break;
                        case "info-kiosk":

                            // create new info-kiosk from the current info-kiosk
                            cityIdDeviceId = args.get(2).split(":");
                            InformationKiosk currentInformationKiosk = (InformationKiosk) this.smartCityModelService.getDevice(cityIdDeviceId[0], cityIdDeviceId[1]);
                            InformationKiosk updatedInformationKiosk = new InformationKiosk(currentInformationKiosk);

                            // update account in new info-kiosk if the param is provided in the command
                            if (this.getArgumentByKey("account", args) != null) {
                                updatedInformationKiosk.setBlockchainAddress(this.getArgumentByKey("account", args));
                            }

                            // update enabled in new info-kiosk if the param is provided in the command
                            if (this.getArgumentByKey("enabled", args) != null) {
                                updatedInformationKiosk.setEnabled(Boolean.parseBoolean(getArgumentByKey("enabled", args)));
                            }

                            // update image in new info-kiosk if the param is provided in the command
                            if (this.getArgumentByKey("image", args) != null) {
                                updatedInformationKiosk.setImageUri(this.getArgumentByKey("image", args));
                            }

                            // replace the current info-kiosk with the newly updated info-kiosk
                            Device savedUpdatedInformationKiosk = smartCityModelService.updateDevice(cityIdDeviceId[0], cityIdDeviceId[1], updatedInformationKiosk);
                            System.out.printf("\nINFORMATION KIOSK UPDATED IN %s %s%n", cityIdDeviceId[0], savedUpdatedInformationKiosk);
                            break;
                        case "street-light":

                            // create new street-light from the current street-light
                            cityIdDeviceId = args.get(2).split(":");
                            StreetLight currentStreetLight = (StreetLight) this.smartCityModelService.getDevice(cityIdDeviceId[0], cityIdDeviceId[1]);
                            StreetLight updatedStreetLight = new StreetLight(currentStreetLight);

                            // update account in new street-light if the param is provided in the command
                            if (this.getArgumentByKey("account", args) != null) {
                                updatedStreetLight.setBlockchainAddress(this.getArgumentByKey("account", args));
                            }

                            // update enabled in new street-light if the param is provided in the command
                            if (this.getArgumentByKey("enabled", args) != null) {
                                updatedStreetLight.setEnabled(Boolean.parseBoolean(getArgumentByKey("enabled", args)));
                            }

                            // update brightness in new street-light if the param is provided in the command
                            if (this.getArgumentByKey("brightness", args) != null) {
                                updatedStreetLight.setBrightness(Integer.parseInt(this.getArgumentByKey("brightness", args)));
                            }

                            // replace the current street-light with the newly updated street-light
                            Device savedUpdatedStreetLight = smartCityModelService.updateDevice(cityIdDeviceId[0], cityIdDeviceId[1], updatedStreetLight);
                            System.out.printf("\nSTREET LIGHT UPDATED IN %s %s%n", cityIdDeviceId[0], savedUpdatedStreetLight);
                            break;
                        case "parking-space":

                            // create new parking-space from the current parking-space
                            cityIdDeviceId = args.get(2).split(":");
                            ParkingSpace currentParkingSpace = (ParkingSpace) this.smartCityModelService.getDevice(cityIdDeviceId[0], cityIdDeviceId[1]);
                            ParkingSpace updatedParkingSpace = new ParkingSpace(currentParkingSpace);

                            // update account in new parking-space if the param is provided in the command
                            if (this.getArgumentByKey("account", args) != null) {
                                updatedParkingSpace.setBlockchainAddress(this.getArgumentByKey("account", args));
                            }

                            // update enabled in new parking-space if the param is provided in the command
                            if (this.getArgumentByKey("enabled", args) != null) {
                                updatedParkingSpace.setEnabled(Boolean.parseBoolean(getArgumentByKey("enabled", args)));
                            }

                            // update rate in new parking-space if the param is provided in the command
                            if (this.getArgumentByKey("rate", args) != null) {
                                updatedParkingSpace.setRate(Integer.parseInt(this.getArgumentByKey("rate", args)));
                            }

                            // replace the current parking-space with the newly updated parking-space
                            Device savedUpdatedParkingSpace = smartCityModelService.updateDevice(cityIdDeviceId[0], cityIdDeviceId[1], updatedParkingSpace);
                            System.out.printf("\nPARKING SPACE UPDATED IN %s %s%n", cityIdDeviceId[0], savedUpdatedParkingSpace);
                            break;
                        case "robot":

                            // create new robot from the current robot
                            cityIdDeviceId = args.get(2).split(":");
                            Robot currentRobot = (Robot) this.smartCityModelService.getDevice(cityIdDeviceId[0], cityIdDeviceId[1]);
                            Robot updatedRobot = new Robot(currentRobot);

                            // update account in new robot if the param is provided in the command
                            if (this.getArgumentByKey("account", args) != null) {
                                updatedRobot.setBlockchainAddress(this.getArgumentByKey("account", args));
                            }

                            // update enabled in new robot if the param is provided in the command
                            if (this.getArgumentByKey("enabled", args) != null) {
                                updatedRobot.setEnabled(Boolean.parseBoolean(getArgumentByKey("enabled", args)));
                            }

                            // update activity in new robot if the param is provided in the command
                            if (this.getArgumentByKey("activity", args) != null) {
                                updatedRobot.setActivity(this.getArgumentByKey("activity", args));
                            }

                            // update location in new robot if the param is provided in the command
                            if ((this.getArgumentByKey("lat", args) != null) & (this.getArgumentByKey("long", args) != null)) {
                                updatedRobot.setLocation(new Location(Float.parseFloat(this.getArgumentByKey("lat", args)), Float.parseFloat(this.getArgumentByKey("long", args)), Float.parseFloat("0.0")));
                            }

                            // replace the current robot with the newly updated robot
                            Device savedUpdatedRobot = smartCityModelService.updateDevice(cityIdDeviceId[0], cityIdDeviceId[1], updatedRobot);
                            System.out.printf("\nROBOT UPDATED IN %s %s%n", cityIdDeviceId[0], savedUpdatedRobot);
                            break;
                        case "vehicle":

                            // create new vehicle from the current vehicle
                            cityIdDeviceId = args.get(2).split(":");
                            Vehicle currentVehicle = (Vehicle) this.smartCityModelService.getDevice(cityIdDeviceId[0], cityIdDeviceId[1]);
                            Vehicle updatedVehicle = new Vehicle(currentVehicle);

                            // update account in new vehicle if the param is provided in the command
                            if (this.getArgumentByKey("account", args) != null) {
                                updatedVehicle.setBlockchainAddress(this.getArgumentByKey("account", args));
                            }

                            // update enabled in new vehicle if the param is provided in the command
                            if (this.getArgumentByKey("enabled", args) != null) {
                                updatedVehicle.setEnabled(Boolean.parseBoolean(getArgumentByKey("enabled", args)));
                            }

                            // update activity in new vehicle if the param is provided in the command
                            if (this.getArgumentByKey("activity", args) != null) {
                                updatedVehicle.setActivity(this.getArgumentByKey("activity", args));
                            }

                            // update fee in new vehicle if the param is provided in the command
                            if (this.getArgumentByKey("fee", args) != null) {
                                updatedVehicle.setFee(Integer.parseInt(this.getArgumentByKey("fee", args)));
                            }

                            // update location in new vehicle if the param is provided in the command
                            if ((this.getArgumentByKey("lat", args) != null) & (this.getArgumentByKey("long", args) != null)) {
                                updatedVehicle.setLocation(new Location(Float.parseFloat(this.getArgumentByKey("lat", args)), Float.parseFloat(this.getArgumentByKey("long", args)), Float.parseFloat("0.0")));
                            }

                            // replace the current vehicle with the newly updated vehicle
                            Device savedUpdatedVehicle = smartCityModelService.updateDevice(cityIdDeviceId[0], cityIdDeviceId[1], updatedVehicle);
                            System.out.printf("\nVEHICLE UPDATED IN %s %s%n", cityIdDeviceId[0], savedUpdatedVehicle);
                            break;
                        case "resident":

                            // create new resident from the current resident
                            Resident currentResident = (Resident) this.smartCityModelService.showPerson(args.get(2));
                            Resident updatedResident = new Resident(currentResident);

                            // update name in new resident if the param is provided in the command
                            if (this.getArgumentByKey("name", args) != null) {
                                updatedResident.setName(this.getArgumentByKey("name", args));
                            }

                            // update bio-metric in new resident if the param is provided in the command
                            if (this.getArgumentByKey("bio-metric", args) != null) {
                                updatedResident.setBiometricId(this.getArgumentByKey("bio-metric", args));
                            }

                            // update account in new resident if the param is provided in the command
                            if (this.getArgumentByKey("account", args) != null) {
                                updatedResident.setBlockchainAddress(this.getArgumentByKey("account", args));
                            }

                            // update phone in new resident if the param is provided in the command
                            if (this.getArgumentByKey("phone", args) != null) {
                                updatedResident.setPhoneNumber(this.getArgumentByKey("phone", args));
                            }

                            // update role in new resident if the param is provided in the command
                            if (this.getArgumentByKey("role", args) != null) {
                                updatedResident.setRole(ResidentRoleEnum.valueOf(this.getArgumentByKey("role", args)));
                            }

                            // update location in new resident if the param is provided in the command
                            if ((this.getArgumentByKey("lat", args) != null) & (this.getArgumentByKey("long", args) != null)) {
                                updatedResident.setLocation(new Location(Float.parseFloat(this.getArgumentByKey("lat", args)), Float.parseFloat(this.getArgumentByKey("long", args)), Float.parseFloat("0.0")));
                            }

                            // replace the current resident with the newly updated street-sign
                            Person savedUpdatedResident = smartCityModelService.updatePerson(args.get(2), updatedResident);
                            System.out.printf("\nRESIDENT UPDATED ID:%s %s%n", args.get(2), savedUpdatedResident);
                            break;
                        case "visitor":

                            // create new visitor from the current street-sign
                            Visitor currentVisitor = (Visitor) this.smartCityModelService.showPerson(args.get(2));
                            Visitor updatedVisitor = new Visitor(currentVisitor);

                            // update bio-metric in new visitor if the param is provided in the command
                            if (this.getArgumentByKey("bio-metric", args) != null) {
                                updatedVisitor.setBiometricId(this.getArgumentByKey("bio-metric", args));
                            }

                            // update location in new visitor if the param is provided in the command
                            if ((this.getArgumentByKey("lat", args) != null) & (this.getArgumentByKey("long", args) != null)) {
                                updatedVisitor.setLocation(new Location(Float.parseFloat(this.getArgumentByKey("lat", args)), Float.parseFloat(this.getArgumentByKey("long", args)), Float.parseFloat("0.0")));
                            }

                            // replace the current visitor with the newly updated visitor
                            Person savedUpdatedVisitor = smartCityModelService.updatePerson(args.get(2), updatedVisitor);
                            System.out.printf("\nVISITOR UPDATED ID:%s %s%n", args.get(2), savedUpdatedVisitor);
                            break;
                    }
                    break;

                case "show":

                    // switch based on the subject provided in the command (city, device, person)
                    switch (subject) {
                        case "city":

                            // get config of the city, hashmap of devices in city limits and hashmap of person in city limits
                            City city = this.smartCityModelService.getCityById(args.get(2));
                            HashMap<String, Device> devicesInCityLimits = this.smartCityModelService.getDevicesInLocation(city.getLocation());
                            HashMap<String, Person> personsInCityLimits = this.smartCityModelService.getPersonsInLocation(city.getLocation());

                            // print out a readable version of entire city info
                            System.out.printf("\n---------- %s ---------- \n%s\n", city.getName(), city.toString());

                            System.out.printf("---DEVICES IN %s", city.getName());
                            devicesInCityLimits.forEach((k,v ) -> {
                                System.out.println(v);
                            });
                            System.out.printf("\n---PERSONS IN %s", city.getName());
                            personsInCityLimits.forEach((k,v ) -> {
                                System.out.println(v);
                            });

                            System.out.printf("\n---------- END %s ---------- \n", city.getName());
                            break;
                        case "device":

                            // determine whether the command includes both a city and device id
                            cityIdDeviceId = args.get(2).split(":");
                            if (cityIdDeviceId.length == 2) {
                                // it it includes both a city and device id, get the single device and print its info
                                Device device = this.smartCityModelService.getDevice(cityIdDeviceId[0], cityIdDeviceId[1]);
                                System.out.printf("\nSHOWING DEVICE INFO FOR ID %s:%s %s\n%n", cityIdDeviceId[0], device.getDeviceId(), device.toString());
                            } else {
                                // if it only includes a city id, get all the devices registered to that city and print their info
                                HashMap<String, Device> devices = this.smartCityModelService.getDevice(cityIdDeviceId[0]);
                                Iterator deviceIterator = devices.entrySet().iterator();
                                devices.size();
                                int counter = 1;
                                while (deviceIterator.hasNext()) {
                                    Map.Entry entry = (Map.Entry) deviceIterator.next();
                                    Device deviceEntry = (Device) entry.getValue();
                                    System.out.printf("\nSHOWING DEVICE INFO FOR ID %s. Device %s of %s %s\n%n", cityIdDeviceId[0], counter, devices.size(), deviceEntry.toString());
                                    counter++;
                                }
                            }

                            break;
                        case "person":

                            // get person by the provided person id and print out their info
                            Person result = this.smartCityModelService.showPerson(args.get(2));
                            System.out.printf("\nSHOWING PERSON INFO FOR ID %s %s\n%n", args.get(2), result.toString());
                            break;

                    }
                    break;
                case "create":

                    // switch based on the subject provided in the command (sensor-event, sensor-output)
                    switch (subject) {
                        case "sensor-event":

                            // create a new sensor event
                            SensorEvent sensorEvent = new SensorEvent(
                                    SensorEventTypeEnum.valueOf(getRequiredArgumentByKey("type", args).toUpperCase()),
                                    getRequiredArgumentByKey("value", args),
                                    (getArgumentByKey("subject", args) != null) ? getArgumentByKey("subject", args) : null
                            );

                            // determine whether the command includes both a city and device id
                            if (args.get(2).contains(":")) {
                                // if it includes both a city and device id, then send the Sensor Event to the single device by id
                                cityIdDeviceId = args.get(2).split(":");
                                this.smartCityModelService.createSensorEvent(cityIdDeviceId[0], cityIdDeviceId[1], sensorEvent);
                                System.out.printf("\nSENSOR EVENT REGISTERED FOR DEVICE %s IN %s %s", cityIdDeviceId[1], cityIdDeviceId[0], sensorEvent);
                            } else {
                                // if it only includes a city id, then send the sensor event to every device registered in that city
                                this.smartCityModelService.createSensorEvent(args.get(2), sensorEvent);
                                System.out.printf("\nSENSOR EVENT REGISTERED FOR ALL DEVICES %s %s", args.get(2), sensorEvent);
                            }

                            break;
                        case "sensor-output":

                            // create a new sensor ouput
                            SensorOutput sensorOutput = new SensorOutput(
                                    SensorOutputTypeEnum.valueOf(getRequiredArgumentByKey("type", args).toUpperCase()),
                                    getRequiredArgumentByKey("value", args)
                            );

                            // determine whether the command includes both a city and device id
                            if (args.get(2).contains(":")) {
                                // if it includes both a city and device id, then send the Sensor output to the single device by id
                                cityIdDeviceId = args.get(2).split(":");
                                System.out.printf("\nSENSOR OUTPUT REGISTERED FOR DEVICE %s IN %s %s", cityIdDeviceId[1], cityIdDeviceId[0], sensorOutput);
                                this.smartCityModelService.createSensorOutput(cityIdDeviceId[0], cityIdDeviceId[1], sensorOutput);
                            } else {
                                // if it only includes a city id, then send the sensor output to every device registered in that city
                                System.out.printf("\nSENSOR EVENT REGISTERED FOR ALL DEVICES %s %s", args.get(2), sensorOutput);
                                this.smartCityModelService.createSensorOutput(args.get(2), sensorOutput);
                            }

                            break;
                    }
                    break;
                default:
                    throw new CommandProcessorException(action, "Command not recognized", null);
            }
        } catch (SmartCityModelServiceException e) {
            throw new CommandProcessorException(String.format("%s %s", action, subject), e.getReason(), null);
        }

    }

    /**
     * Get and argument from the list of args, by key or throw error
     * @param key String: the key that corresponds to the value sought
     * @param args List<String>: a list of all the words in a command
     * @return String: the value associated with the provided key
     * @throws CommandProcessorException if there is no arg with that key
     */
    private String getRequiredArgumentByKey(String key, List<String> args) throws CommandProcessorException {
        int indexOfKey = args.indexOf(key);

        if (indexOfKey == -1) {
            throw new CommandProcessorException("PARSE CMD", String.format("MISSING REQUIRED ARG %s", key), null);
        }

        return args.get(indexOfKey + 1);
    }

    /**
     * et and argument from the list of args, by key or return null String
     * @param key String: the key that corresponds to the value sought
     * @param args List<String>: a list of all the words in a command
     * @return String: the value associated with the provided key, or null
     */
    private String getArgumentByKey(String key, List<String> args) {
        int indexOfKey = args.indexOf(key);
        String value = args.get(indexOfKey + 1);
        return (indexOfKey != -1) ? value : null;
    }

}
