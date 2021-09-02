package com.cscie97.smartcity.authentication;

public class InvalidAccessTokenException extends Throwable {
    private String action;
    private String reason;

    /**
     * Create a new InvalidAccessTokenException using the params in the args provided
     * @param action String The action being performed when the exception occurred
     * @param reason String The reason the action failed
     */
    public InvalidAccessTokenException(String action, String reason) {
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
