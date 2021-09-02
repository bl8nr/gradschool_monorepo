package com.cscie97.smartcity.model;

import com.cscie97.smartcity.authentication.AuthServiceException;
import com.cscie97.smartcity.authentication.CredentialType;
import com.cscie97.smartcity.authentication.InvalidAccessTokenException;
import com.cscie97.smartcity.controller.SmartCityControllerServiceException;
import com.cscie97.smartcity.authentication.SmartCityAuthenticationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

/**
 * Smart City Model Service Class
 * The Smart City Model Service is the API for managing models in the smart city. It also holds global hash maps for
 * all the citys, devices and person in the system. All manipulation of citys device and persons can be done using this
 * class as the API.
 */
public class SmartCityModelService implements iSubject {
    private static  SmartCityModelService uniqueInstance = new SmartCityModelService();
    private SmartCityAuthenticationService smartCityAuthenticationService = SmartCityAuthenticationService.getInstance();

    private HashMap<String, City> cities;
    private HashMap<String, Person> persons;
    private HashMap<String, Device> devices;
    private List<iObserver> observerList = new ArrayList<iObserver>();

    private SmartCityModelService() {
        this.cities = new HashMap<>();
        this.persons = new HashMap<>();
        this.devices = new HashMap<>();
        System.out.println("SmartCityModelService Initialized");
    }

    public static SmartCityModelService getInstance() {
        return uniqueInstance;
    }

    /**
     * Create a new city and store it in the cities hash map
     * @param cityId String: a globally unique cityId
     * @param name String: a friendly name for the city
     * @param blockchainAddress String: blockchain address of the account belonging to the city
     * @param location Location: the location of the city, including a radius
     * @return String: the cityId of the newly created city
     * @throws SmartCityModelServiceException if a city with the provided cityId already exists
     * @throws SmartCityModelServiceException if a city already exists within the radius of the provided location
     */
    public String createCity(String cityId, String name, String blockchainAddress, Location location, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_define_city");

        // iterate through all the cities already under management
        Iterator cityIterator = cities.entrySet().iterator();
        while (cityIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) cityIterator.next();
            City cityEntry = (City) entry.getValue();

            // if a city with the same cityId already exists then return an error
            if (cityEntry.getCityId().equals(cityId)) {
                throw new SmartCityModelServiceException("defineCity()", "new city id conflicts with another city id");
            }

            // if the provided location is within the radius of an already existing city then throw an error
            Float distanceFromCityEdge = location.getDistaceFromEdge(cityEntry.getLocation());
            if (distanceFromCityEdge <= 0) {
                throw new SmartCityModelServiceException("defineCity()", "new city location conflicts with another city location");
            }
        }

        // create the city and store it in the citys hash map
        City city = new City(name, cityId, blockchainAddress, location);
        this.cities.put(city.getCityId(), city);
        System.out.printf("\nCity %s created. ID: %s%n", name, cityId);

        try {
            this.smartCityAuthenticationService.createResource(city.getCityId(), String.format("%S_%S", city.getName(), city.getCityId()), authToken);
            this.smartCityAuthenticationService.createResourceRole(String.format("%s_public_administrator", city.getCityId()), "public_administrator", city.getCityId(), authToken);
        } catch (AuthServiceException e) {
            throw new SmartCityModelServiceException("defineCity()", "Failed to create Auth Resource for City");
        }

        return city.getCityId();
    }

    /**
     * Update a city
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param blockchainAddress String: blockchain address of the account belonging to the city
     * @return City: the updated city
     * @throws SmartCityModelServiceException if there is no city with that cityId
     */
    public City updateCity(String cityId, String blockchainAddress, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_manage_city");


        City city = this.getCityById(cityId, authToken);
        city.setBlockchainAddress(blockchainAddress);
        return city;
    }

    /**
     *  Get city by id
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @return City: the city result
     * @throws SmartCityModelServiceException if there is no city with that cityId
     */
    public City getCityById(String cityId, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_read_city");


        City result = this.cities.get(cityId);
        if (result == null) {
            throw new SmartCityModelServiceException("getCityById()", "city with that id doest not exist");
        }
        return result;
    }

    /**
     * Add a device to the global devices hash map the citys device hash map
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param device Device: a valid device with a globally unique deviceId
     * @return String: deviceId of the newly added device
     * @throws SmartCityModelServiceException if the device already exists in the global hash map
     */
    public String addDevice(String cityId, Device device, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_define_device");

        // if the device already exists globally then return an error
        if (devices.get(device.getDeviceId()) != null) {
            throw new SmartCityModelServiceException("addDevice()", "device id already exists");
        }

        // add the device to the global device hash map and to the city hash map
        City city = this.getCityById(cityId, authToken);
        this.devices.put(device.getDeviceId(), device);

        System.out.printf("\nDEVICE CREATED IN CITY %s, %s\n", cityId, device.toString());

        // create a Resource for this device in the Auth Service and add it to the city public administrator role
        try {
            this.smartCityAuthenticationService.createResource(device.getDeviceId(), String.format("%S_%S", device.getDeviceId(), cityId), authToken);
            this.smartCityAuthenticationService.createResourceRole(String.format("%s_public_administrator", device.getDeviceId()), "public_administrator", device.getDeviceId(), authToken);
        } catch (AuthServiceException e) {
            throw new SmartCityModelServiceException("addDevice()", "Failed to create Auth Resource for Device");
        }

        return city.addDevice(device);
    }

    /**
     * Get device by device id and city id
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param deviceId String: a valid device id that exists in the city whose id is provided
     * @return Device: the Device with corresponding deviceId
     * @throws SmartCityModelServiceException if the device does not exist
     */
    public Device getDevice(String cityId, String deviceId, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_manage_device");

        Device device = this.getCityById(cityId, authToken).getDeviceById(deviceId);
        if (device == null) {
            throw new SmartCityModelServiceException("getDevice()", "device with that city/device ID does not exist");
        }
        return  device;
    }

    /**
     * Get all devices in a city
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @return HashMap<String, Device>: all of the devices registered to a city
     * @throws SmartCityModelServiceException
     */
    public HashMap<String, Device> getDevice(String cityId, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_manage_device");

        City city = this.getCityById(cityId, authToken);
        return city.getAllDevices();
    }

    /**
     *  Update a device in a city by id
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param deviceId String: a valid device id that exists in the city whose id is provided
     * @param updatedDevice Device: the device in its updated state that will replace the out of date device
     * @return Device: the newly saved updated device
     * @throws SmartCityModelServiceException if the device being updated does not match the type of device provided
     */
    public Device updateDevice(String cityId, String deviceId, Device updatedDevice, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_manage_device");

        // TODO: verify the resourcerole access too

        City city = this.getCityById(cityId, authToken);
        Device currentDevice = this.getDevice(cityId, deviceId, authToken);


        // validate that the provided device is the same type of device as the one to be updated/replaced
        if (currentDevice.getClass() != updatedDevice.getClass()) {
            throw new SmartCityModelServiceException("updateDevice()", "device type mismatch");
        }

        // update/replace the device in the global hash map and in the city device hash map
        this.devices.replace(deviceId, updatedDevice);
        return city.updateDevice(deviceId, updatedDevice);
    }

    /**
     * Get all of the devices within a provided Location
     * @param location Location: a valid location with a radius larger than 0
     * @return HashMap<String, Device>: all of the devices located inside the provided location
     */
    public HashMap<String, Device> getDevicesInLocation(Location location, String authToken) {

        try {
            this.doesAuthTokenExist(authToken);
            this.doesAuthTokenHaveAccess(authToken, "scms_manage_device");
        } catch (SmartCityModelServiceException e) {
            e.printStackTrace();
        }
        // TODO: Check Auth Access

        HashMap<String, Device> results = new HashMap<>();
        this.devices.forEach((k, v) -> {
            Float distaceFromEdgeOfLocation = v.getLocation().getDistaceFromEdge(location);
            if (distaceFromEdgeOfLocation < 0) {
                results.put(v.getDeviceId(), v);
            }
        });

        return results;
    }

    /**
     * Add a person to the global person hash map
     * @param person Person: a valid Resident or Visitor person with a globally unique personId
     * @return Person: the newly added Person
     * @throws SmartCityModelServiceException if a person with the personId already exists
     */
    public Person createPerson(Person person, String authToken) throws SmartCityModelServiceException{
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_define_person");

        if (this.persons.get(person.getPersonId()) != null) {
            throw new SmartCityModelServiceException("createPerson()", "person with that ID already exists");
        }
        this.persons.put(person.getPersonId(), person);

        final String[] cityIdOfResidence = {null};
        this.cities.forEach((k ,v) -> {
            HashMap<String, Person> persons = this.getPersonsInLocation(v.getLocation(), authToken);
            persons.forEach((kp, vp) -> {
                if (vp.getPersonId().equals(person.getPersonId())) {
                    cityIdOfResidence[0] = v.getCityId();
                }
            });
        });

        System.out.printf("\nNEW PERSON CREATED %s \n", person );
        if (person instanceof Resident) {
            try {
                this.smartCityAuthenticationService.createUser(person.getPersonId(), ((Resident) person).getName(), authToken);
                this.smartCityAuthenticationService.updateUserCredential(person.getPersonId(), CredentialType.BIOMETRIC, person.getBiometricId(), authToken);

                if (((Resident) person).getRole() == ResidentRoleEnum.PUBLIC_ADMINISTRATOR) {
                    this.smartCityAuthenticationService.addRoleToUser(person.getPersonId(), "public_administrator", authToken);
                    this.smartCityAuthenticationService.addRoleToUser(person.getPersonId(), (cityIdOfResidence + "_public_administrator"), authToken);
                }
            } catch (AuthServiceException e) {
                throw new SmartCityModelServiceException("createPerson()", "Filed to create User or associate Role in Auth Service");
            }
        }
        return person;
    }

    /**
     * Get a person from the global person hash map by id
     * @param personId String: the personId of a person stored in the global persons hashmap
     * @return Person: the person associated with that personId
     * @throws SmartCityModelServiceException if no person exists by that personId
     */
    public Person showPerson(String personId, String authToken) throws SmartCityModelServiceException{
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_read_person");

        Person person = this.persons.get(personId);
        if (person == null) {
            throw new SmartCityModelServiceException("showPerson()", "person with that ID does not exist");
        }
        return person;
    }

    /**
     * Update a person in the global persons hash map by id
     * @param personId String: the personId of a person stored in the global persons hashmap
     * @param person Person: the person in its updated state that will replace the out of date person
     * @return Person: the newly updated Person
     */
    public Person updatePerson(String personId, Person person, String authToken) throws SmartCityModelServiceException{
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_manage_person");

        // try to fetch person to be sure they exist
        showPerson(personId, authToken);

        // update/replace the person in the global person hash map
        this.persons.replace(personId, person);
        return this.persons.get(personId);
    }

    /**
     * Get all the persons within the radius of a Location
     * @param location Location: a valid location with a radius larger than 0
     * @return HashMap<String, Person>: all of the persons located inside the provided location
     */
    public HashMap<String, Person> getPersonsInLocation(Location location, String authToken) {

        try {
            this.doesAuthTokenExist(authToken);
            this.doesAuthTokenHaveAccess(authToken, "scms_manage_person");
        } catch (SmartCityModelServiceException e) {
            e.printStackTrace();
        }

        HashMap<String, Person> results = new HashMap<>();
        this.persons.forEach((k, v) -> {
            Float distaceFromEdgeOfLocation = v.getLocation().getDistaceFromEdge(location);
            if (distaceFromEdgeOfLocation < 0) {
                results.put(v.getPersonId(), v);
            }
        });

        return results;
    }

    /**
     * Send a sensor event to all devices in a city
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param sensorEvent SensorEvent: a valid SensorEvent object
     * @throws SmartCityModelServiceException if there is no city with that cityId
     */
    public void createSensorEvent(String cityId, SensorEvent sensorEvent, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_create_sensor_event");

        this.getDevice(cityId, authToken).forEach((k,v) -> {
            v.registerSensorEvent(sensorEvent);
        });
        this.notifyObservers(sensorEvent);
    }

    /**
     * Send a Sensor Event to a device by deviceId
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param deviceId String: a valid device id that exists in the city whose id is provided
     * @param sensorEvent SensorEvent: a valid SensorEvent object
     * @throws SmartCityModelServiceException if there is no city with that cityId
     * @throws SmartCityModelServiceException if there is no device with that deviceId in the provided cityId
     */
    public void createSensorEvent(String cityId, String deviceId, SensorEvent sensorEvent, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_create_sensor_event");

        this.getDevice(cityId, deviceId, authToken).registerSensorEvent(sensorEvent);
        this.notifyObservers(sensorEvent);
    }

    /**
     * Send a Sensor Output to every device in a city
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param sensorOutput SensorOutput: a valid SensorOutput object
     * @throws SmartCityModelServiceException if there is no city with that cityId
     */
    public void createSensorOutput(String cityId, SensorOutput sensorOutput, String authToken) throws SmartCityModelServiceException{
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_create_sensor_output");

        this.getDevice(cityId, authToken).forEach((k,v) -> {
            v.registerSensorOutput(sensorOutput);
        });
    }

    /**
     * Send a Sensor Output to a device by deviceId
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param deviceId String: a valid device id that exists in the city whose id is provided
     * @param sensorOutput SensorOutput: a valid SensorOutput object
     * @throws SmartCityModelServiceException if there is no city with that cityId
     * @throws SmartCityModelServiceException if there is no device with that deviceId in the provided cityId
     */
    public void createSensorOutput(String cityId, String deviceId, SensorOutput sensorOutput, String authToken) throws SmartCityModelServiceException {
        this.doesAuthTokenExist(authToken);
        this.doesAuthTokenHaveAccess(authToken, "scms_create_sensor_output");

        this.getDevice(cityId, deviceId, authToken).registerSensorOutput(sensorOutput);
    }

    /**
     * Check to make sure Auth Token exists
     * @param authToken String an existing possibly valid auth token
     * @throws SmartCityModelServiceException if the authToken String does not exist
     */
    private void doesAuthTokenExist(String authToken) throws SmartCityModelServiceException {
        if (authToken == null) {
            throw new SmartCityModelServiceException("doesAuthTokenExist()", "Auth token is missing or null");
        }
    }

    /**
     * Check to make sure the AuthToken has access to the provided permission
     * @param authToken String a valid Auth Token
     * @param permissionName String a valid permissionId
     * @throws SmartCityModelServiceException if the Auth Token does not have access to the provided permissionID
     */
    private void doesAuthTokenHaveAccess(String authToken, String permissionName) throws SmartCityModelServiceException {
        try {
            this.smartCityAuthenticationService.checkAccess(authToken, permissionName);
        } catch (InvalidAccessTokenException e) {
            throw new SmartCityModelServiceException("doesAuthTokenHaveAccess()", e.getReason());
        }
    }

    @Override
    public void registerObserver(iObserver observer) {
        this.observerList.add(observer);
        System.out.println(String.format("%s has been registered as an Observer of the SmartCityModelService", observer.toString()));
    }

    @Override
    public void removeObserver(iObserver observer) throws SmartCityModelServiceException {
        throw new SmartCityModelServiceException("exception message", "reason");
    }

    @Override
    public void notifyObservers(SensorEvent sensorEvent) throws SmartCityModelServiceException {
        for (iObserver observer : this.observerList) {
            try {
                observer.update(sensorEvent);
            } catch (SmartCityControllerServiceException e) {
                throw new SmartCityModelServiceException("notifyObservers()", String.format("%s %s", e.className(), e.getReason()));
            }
        }
    }
}
