package com.cscie97.smartcity.model;

/**
 * Street Light
 * A Street Light is an IoT device and is a child of the Device abstract class
 */
public class StreetLight extends Device {
    private int brightness;

    /**
     * Street Light Constructor
     * @param deviceId String: An ID unique against all global devices
     * @param blockchainAddress String: Blockchain address use by the Street Light for business
     * @param location Location: Location of the street light
     * @param enabled Boolean: Indicator for whether or not the device is enabled
     * @param brightness Int: THe current brightness of the street light
     */
    public StreetLight(String deviceId, String blockchainAddress, Location location, boolean enabled, int brightness) {
        super(deviceId, blockchainAddress, enabled, location);
        this.brightness = brightness;
    }

    /**
     * StreetLight constructor overload. Construct a StreetLight from data in another street light (ie make a copy)
     * @param streetLight StreetLight: A valid street light object
     */
    public StreetLight(StreetLight streetLight) {
        super(streetLight.getDeviceId(), streetLight.getBlockchainAddress(), streetLight.getEnabled(), streetLight.getLocation());
        this.brightness = streetLight.getBrightness();
    }

    public int getBrightness() {
        return this.brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return "\nStreetLight{" +
                "brightness=" + brightness +
                "}\n" + super.toString();
    }
}
