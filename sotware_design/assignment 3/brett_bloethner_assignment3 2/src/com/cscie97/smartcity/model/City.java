package com.cscie97.smartcity.model;

import java.util.HashMap;

/**
 * City
 * The city is the main model of the Smart City application. It contains a hashmap of devices its responsible for
 */
public class City {
    private String cityId;
    private String name;
    private Location location;
    private String blockchainAddress;
    private HashMap<String, Device> devices;

    /**
     * City Constructor
     * @param name String: Name of the city
     * @param cityId String: ID if the city that is globally unique against all other cities
     * @param blockchainAddress String: Blockchain address used by the city for business
     * @param location Location: location of the city with a radius
     */
    public City(String name, String cityId, String blockchainAddress, Location location) {
        this.name = name;
        this.cityId = cityId;
        this.location = location;
        this.blockchainAddress = blockchainAddress;
        this.devices = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public String getCityId() {
        return this.cityId;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setBlockchainAddress(String blockchainAddress) {
        this.blockchainAddress = blockchainAddress;
    }

    public String getBlockchainAddress() {
        return this.blockchainAddress;
    }

    /**
     * Add one device to the device HashMap (ie register the device with the city)
     * @param device Device: A valid device object such as StreetLight, Kiosk, etc...
     * @return String: The deviceId of the successfully added device
     */
    public String addDevice(Device device) {
        this.devices.put(device.getDeviceId(), device);
        return device.getDeviceId();
    }

    /**
     * Update (by replacement) a device that's registered in this city (update the device in the hashmap)
     * @param deviceId String: A valid deviceId for a device in this citys device hashmap
     * @param updatedDevice Device: A valid device object that will replace the old device in the hashmap
     * @return Device: The newly saved device
     */
    public Device updateDevice(String deviceId, Device updatedDevice) {
        this.devices.replace(deviceId, updatedDevice);
        return this.devices.get(deviceId);
    }

    /**
     * Get one device registered with this city, by id
     * @param id String: The deviceId of a device registered with this city
     * @return Device: Result of the query for the device, or null
     */
    public Device getDeviceById(String id) {
        return this.devices.get(id);
    }

    /**
     * Get all of the devices that this City is responsible for
     * @return HashMap of all the devices registered with this city
     */
    public HashMap<String, Device> getAllDevices() {
        return this.devices;
    }

    @Override
    public String toString() {
        return "\nCity{" +
                ", cityId='" + cityId + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                "}\n";
    }
}
