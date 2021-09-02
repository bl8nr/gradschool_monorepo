package com.cscie97.smartcity.controller;

public class CommandException extends Throwable {
    private String className = "Command";
    private String reason;

    /**
     * Create a new Command exception using the params in the args provided
     * @param reason String The reason the action failed
     */
    public CommandException(String reason) {
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
