package com.cscie97.smartcity.model;

/**
 * Vehicle
 * A vehicle is an IoT device and is a child of the Device abstract class
 * A vehicle can be either a car or a bus
 */
public class Vehicle extends Device {
    private VehicleTypeEnum type;
    private String activity;
    private int capacity;
    private int fee;

    /**
     * Vehicle Constructor
     * @param deviceId String: An ID unique against all global devices
     * @param blockchainAddress String: Blockchain address use by the parking space for business
     * @param location Location: Location of the vehicle
     * @param enabled Boolean: Indicator for whether or not the device is enabled
     * @param type VehicleTypeEnum: Type of vehicle (either CAR or BUS)
     * @param activity String: Description of the activity currently being executed by the vehicle
     * @param capacity Integer: Maximum human capacity of the vehicle
     * @param fee Integer: Cost for use of the vehicles services
     */
    public Vehicle(String deviceId, String blockchainAddress, Location location, boolean enabled, VehicleTypeEnum type, String activity, int capacity, int fee) {
        super(deviceId, blockchainAddress, enabled, location);
        this.type = type;
        this.activity = activity;
        this.capacity = capacity;
        this.fee = fee;
    }

    /**
     * Vehicle constructor overload. Construct a Vehicle from data in another vehicle (ie make a copy)
     * @param vehicle Vehicle: A valid vehicle object
     */
    public Vehicle(Vehicle vehicle) {
        super(vehicle.getDeviceId(), vehicle.getBlockchainAddress(), vehicle.getEnabled(), vehicle.getLocation());

        this.type = vehicle.getType();
        this.activity = vehicle.getActivity();
        this.capacity = vehicle.getCapacity();
        this.fee = vehicle.getFee();
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getFee() {
        return this.fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public VehicleTypeEnum getType() {
        return  this.type;
    }

    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public String toString() {
        return "\nVehicle{" +
                "type=" + type +
                ", activity='" + activity + '\'' +
                ", capacity=" + capacity +
                ", fee=" + fee +
                "}\n" + super.toString();
    }
}
