package com.cscie97.smartcity.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Smart City Model Service Class
 * The Smart City Model Service is the API for managing models in the smart city. It also holds global hash maps for
 * all the citys, devices and person in the system. All manipulation of citys device and persons can be done using this
 * class as the API.
 */
public class SmartCityModelService {
    private HashMap<String, City> cities;
    private HashMap<String, Person> persons;
    private HashMap<String, Device> devices;

    public SmartCityModelService() {
        this.cities = new HashMap<>();
        this.persons = new HashMap<>();
        this.devices = new HashMap<>();
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
    public String createCity(String cityId, String name, String blockchainAddress, Location location) throws SmartCityModelServiceException {

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
        return city.getCityId();
    }

    /**
     * Update a city
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param blockchainAddress String: blockchain address of the account belonging to the city
     * @return City: the updated city
     * @throws SmartCityModelServiceException if there is no city with that cityId
     */
    public City updateCity(String cityId, String blockchainAddress) throws SmartCityModelServiceException {
        City city = this.getCityById(cityId);
        city.setBlockchainAddress(blockchainAddress);
        return city;
    }

    /**
     *  Get city by id
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @return City: the city result
     * @throws SmartCityModelServiceException if there is no city with that cityId
     */
    public City getCityById(String cityId) throws SmartCityModelServiceException {
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
    public String addDevice(String cityId, Device device) throws SmartCityModelServiceException {

        // if the device already exists globally then return an error
        if (devices.get(device.getDeviceId()) != null) {
            throw new SmartCityModelServiceException("addDevice()", "device id already exists");
        }

        // add the device to the global device hash map and to the city hash map
        City city = this.getCityById(cityId);
        this.devices.put(device.getDeviceId(), device);
        return city.addDevice(device);
    }

    /**
     * Get device by device id and city id
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param deviceId String: a valid device id that exists in the city whose id is provided
     * @return Device: the Device with corresponding deviceId
     * @throws SmartCityModelServiceException if the device does not exist
     */
    public Device getDevice(String cityId, String deviceId) throws SmartCityModelServiceException {
        Device device = this.getCityById(cityId).getDeviceById(deviceId);
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
    public HashMap<String, Device> getDevice(String cityId) throws SmartCityModelServiceException {
        City city = this.getCityById(cityId);
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
    public Device updateDevice(String cityId, String deviceId, Device updatedDevice) throws  SmartCityModelServiceException {
        City city = this.getCityById(cityId);
        Device currentDevice = this.getDevice(cityId, deviceId);


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
    public HashMap<String, Device> getDevicesInLocation(Location location) {
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
    public Person createPerson(Person person) throws SmartCityModelServiceException{
        if (this.persons.get(person.getPersonId()) != null) {
            throw new SmartCityModelServiceException("createPerson()", "person with that ID already exists");
        }
        this.persons.put(person.getPersonId(), person);
        return person;
    }

    /**
     * Get a person from the global person hash map by id
     * @param personId String: the personId of a person stored in the global persons hashmap
     * @return Person: the person associated with that personId
     * @throws SmartCityModelServiceException if no person exists by that personId
     */
    public Person showPerson(String personId) throws SmartCityModelServiceException{
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
    public Person updatePerson(String personId, Person person) throws SmartCityModelServiceException{

        // try to fetch person to be sure they exist
        showPerson(personId);

        // update/replace the person in the global person hash map
        this.persons.replace(personId, person);
        return this.persons.get(personId);
    }

    /**
     * Get all the persons within the radius of a Location
     * @param location Location: a valid location with a radius larger than 0
     * @return HashMap<String, Person>: all of the persons located inside the provided location
     */
    public HashMap<String, Person> getPersonsInLocation(Location location) {
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
    public void createSensorEvent(String cityId, SensorEvent sensorEvent) throws SmartCityModelServiceException {
        this.getDevice(cityId).forEach((k,v) -> {
            v.registerSensorEvent(sensorEvent);
        });
    }

    /**
     * Send a Sensor Event to a device by deviceId
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param deviceId String: a valid device id that exists in the city whose id is provided
     * @param sensorEvent SensorEvent: a valid SensorEvent object
     * @throws SmartCityModelServiceException if there is no city with that cityId
     * @throws SmartCityModelServiceException if there is no device with that deviceId in the provided cityId
     */
    public void createSensorEvent(String cityId, String deviceId, SensorEvent sensorEvent) throws SmartCityModelServiceException {
        this.getDevice(cityId, deviceId).registerSensorEvent(sensorEvent);
    }

    /**
     * Send a Sensor Output to every device in a city
     * @param cityId String: a valid cityId matching a city stored in the cities hash map
     * @param sensorOutput SensorOutput: a valid SensorOutput object
     * @throws SmartCityModelServiceException if there is no city with that cityId
     */
    public void createSensorOutput(String cityId, SensorOutput sensorOutput) throws SmartCityModelServiceException{
        this.getDevice(cityId).forEach((k,v) -> {
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
    public void createSensorOutput(String cityId, String deviceId, SensorOutput sensorOutput) throws SmartCityModelServiceException {
        this.getDevice(cityId, deviceId).registerSensorOutput(sensorOutput);
    }

}