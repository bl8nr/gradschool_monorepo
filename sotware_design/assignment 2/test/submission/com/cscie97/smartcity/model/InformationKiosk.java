package com.cscie97.smartcity.model;

/**
 * Information Kiosk
 * A Information Kiosk is an IoT device and is a child of the Device abstract class
 */
public class InformationKiosk extends Device {
    private String imageUri;

    /**
     * Information Kiosk Constructor
     * @param deviceId String: An ID unique against all global devices
     * @param blockchainAddress String: Blockchain address use by the information kiosk for business
     * @param location Location: Location of the information kiosk
     * @param enabled Boolean: Indicator for whether or not the device is enabled
     * @param imageUri String: Image URI location for the image currently being displayed by the information kiosk
     */
    public InformationKiosk(String deviceId, String blockchainAddress, Location location, boolean enabled, String imageUri) {
        super(deviceId, blockchainAddress, enabled, location);
        this.imageUri = imageUri;
    }

    /**
     * InformationKiosk constructor overload. Construct a InformationKiosk from data in another information kiosk (ie make a copy)
     * @param informationKiosk InformationKiosk: A valid information kiosk object
     */
    public InformationKiosk(InformationKiosk informationKiosk) {
        super(informationKiosk.getDeviceId(), informationKiosk.getBlockchainAddress(), informationKiosk.getEnabled(), informationKiosk.getLocation());
        this.imageUri = informationKiosk.getImageUri();
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "\nInformationKiosk{" +
                "imageUri='" + imageUri + '\'' +
                "]\n" + super.toString();
    }
}
