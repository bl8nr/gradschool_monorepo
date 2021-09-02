package com.cscie97.smartcity.model;

/**
 * Device
 * The device class is an abstract class that determines the minimum requirements a Devices like Vehicles and Parking
 * Spaces must all share in order to be valid and storeable
 */
public abstract class Device {
    private String deviceId;
    private String blockchainAddress;
    private CurrentStatusEnum status;
    private Boolean enabled;
    private SensorEvent lastDeviceEvent;
    private Location location;

    /**
     * Device Constructor
     * @param deviceId String: device id unique to all devices in the system
     * @param blockchainAddress String: Blockchain address used by the device for business
     * @param enabled Boolean: Identifies whether or not the device is enabled
     * @param location Location: The current location of the device
     */
    Device(String deviceId, String blockchainAddress, boolean enabled, Location location) {
        this.deviceId = deviceId;
        this.blockchainAddress = blockchainAddress;
        this.location = location;
        this.enabled = enabled;
        this.status = CurrentStatusEnum.OFFLINE;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getBlockchainAddress() {
        return this.blockchainAddress;
    }

    protected void setBlockchainAddress(String blockchainAddress) {
        this.blockchainAddress = blockchainAddress;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    protected void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Location getLocation() {
        return this.location;
    }

    protected void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Register a sensor event in order for the device to respond to it somehow
     * @param sensorEvent SensorEvent: A valid sensor event thats received by this device
     * @return SensorEvent: The sensor event passed in and successfully stored
     */
    public SensorEvent registerSensorEvent(SensorEvent sensorEvent) {
        this.lastDeviceEvent = sensorEvent;
        return this.lastDeviceEvent;
    }

    /**
     * Execute an output event (like playing audio on a speaker)
     * @param sensorOutput SensorOutput: A valid SensorOutput object that describes type and content of device output
     */
    public void registerSensorOutput(SensorOutput sensorOutput) {
        System.out.println(String.format("\n%s OUTPUT FROM DEVICE %s \n%s", sensorOutput.type, this.deviceId, sensorOutput.value));
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId='" + deviceId + '\'' +
                ", blockchainAddress='" + blockchainAddress + '\'' +
                ", status=" + status +
                ", enabled=" + enabled +
                ", lastDeviceEvent=" + lastDeviceEvent +
                '}';
    }
}

