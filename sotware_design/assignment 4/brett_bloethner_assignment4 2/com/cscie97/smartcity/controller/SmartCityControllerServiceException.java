package com.cscie97.smartcity.controller;

public class SmartCityControllerServiceException extends Throwable {
    private String className = "SmartCityControllerService";
    private String reason;

    /**
     * Create a new SmartCityControllerService exception using the params in the args provided
     * @param reason String The reason the action failed
     */
    public SmartCityControllerServiceException(String reason) {
        this.className = className;
        this.reason = reason;
    }

    public String className() {
        return this.className;
    }

    public String getReason() {
        return this.reason;
    }
}
