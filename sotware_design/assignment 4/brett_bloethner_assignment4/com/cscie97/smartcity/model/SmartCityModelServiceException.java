package com.cscie97.smartcity.model;

public class SmartCityModelServiceException extends Throwable {
    private String action;
    private String reason;

    /**
     * Create a new ledger exception using the params in the args provided
     * @param action String The action being performed when the exception occurred
     * @param reason String The reason the action failed
     */
    public SmartCityModelServiceException(String action, String reason) {
        this.action = action;
        this.reason = reason;
    }

    public String getAction() {
        return this.action;
    }

    public String getReason() {
        return this.reason;
    }
}
