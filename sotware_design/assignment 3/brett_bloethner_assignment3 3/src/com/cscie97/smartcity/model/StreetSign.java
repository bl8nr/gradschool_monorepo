package com.cscie97.smartcity.model;

/**
 * Street Sign
 * A digital street sign is an IoT device and is a child of the Device abstract class
 */
public class StreetSign extends Device {
    private String text;

    /**
     * StreetSign Constructor
     * @param deviceId String: An ID unique against all global devices
     * @param blockchainAddress String: Blockchain address use by the street sign for business
     * @param location Location: Location of the street sign
     * @param enabled Boolean: Indicator for whether or not the device is enabled
     * @param text String: Text currently displayed on the street sign
     */
    public StreetSign(String deviceId, String blockchainAddress, Location location, boolean enabled, String text) {
        super(deviceId, blockchainAddress, enabled, location);
        this.text = text;
    }

    /**
     * StreetSign constructor overload. Construct a StreetSign from data in another street sign (ie make a copy)
     * @param streetSign StreetSign: A valid street sign object
     */
    public StreetSign(StreetSign streetSign) {
        super(streetSign.getDeviceId(), streetSign.getBlockchainAddress(), streetSign.getEnabled(), streetSign.getLocation());
        this.text = streetSign.getText();
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "\nStreetSign{" +
                "text='" + text + '\'' +
                "}\n" + super.toString();
    }
}
