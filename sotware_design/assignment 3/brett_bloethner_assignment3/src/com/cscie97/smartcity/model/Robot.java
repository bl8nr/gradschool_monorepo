package com.cscie97.smartcity.model;

/**
 * Robot
 * A Robot is an IoT device and is the child of the Device abstract class.
 */
public class Robot extends Device {
    private String activity;

    /**
     * Robot Constructor
     * @param deviceId String: An ID unique against all global devices
     * @param blockchainAddress String: Blockchain address use by the Robot for business
     * @param location Location: Location of the parking space
     * @param enabled Boolean: Indicator for whether or not the device is enabled
     * @param activity String: Description of the current activity being conducted by the robot
     */
    public Robot(String deviceId, String blockchainAddress, Location location, boolean enabled, String activity) {
        super(deviceId, blockchainAddress, enabled, location);
        this.activity = activity;
    }

    /**
     * Robot constructor overload. Construct a Robot from data in another Robot (ie make a copy)
     * @param robot Robot: A valid robot object
     */
    public Robot(Robot robot) {
        super(robot.getDeviceId(), robot.getBlockchainAddress(), robot.getEnabled(), robot.getLocation());
        this.activity = robot.getActivity();
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "\nRobot{" +
                "activity='" + activity + '\'' +
                "}\n" + super.toString();
    }
}
