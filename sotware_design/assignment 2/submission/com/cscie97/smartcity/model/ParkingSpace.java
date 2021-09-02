package com.cscie97.smartcity.model;

/**
 * Parking Space
 * A parking space is an IoT device and is a child of the Device abstract class
 */
public class ParkingSpace extends Device {
    private int rate;

    /**
     * ParkingSpace Constructor
     * @param deviceId String: An ID unique against all global devices
     * @param blockchainAddress String: Blockchain address use by the parking space for business
     * @param location Location: Location of the parking space
     * @param enabled Boolean: Indicator for whether or not the device is enabled
     * @param rate Int: The current going price for the parking space
     */
    public ParkingSpace(String deviceId, String blockchainAddress, Location location, boolean enabled, int rate) {
        super(deviceId, blockchainAddress, enabled, location);
        this.rate = rate;
    }

    /**
     * ParkingSpace constructor overload. Construct a ParkingSpace from data in another parking space (ie make a copy)
     * @param parkingSpace ParkingSpace: A valid parking space object
     */
    public ParkingSpace(ParkingSpace parkingSpace) {
        super(parkingSpace.getDeviceId(), parkingSpace.getBlockchainAddress(), parkingSpace.getEnabled(), parkingSpace.getLocation());
        this.rate = parkingSpace.getRate();
    }

    public int getRate() {
        return this.rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "\nParkingSpace{" +
                "rate=" + rate +
                "}\n" + super.toString();
    }
}
