package com.cscie97.smartcity.ledger;

public class CommandProcessorException extends Throwable {
    private String command;
    private String reason;
    private String lineNumber;

    /**
     * Create a new Command Processor exception using the params in the args provided
     * @param command String The command issued that resulted in an exception
     * @param reason String The reason the command failed
     * @param lineNumber String The line number of the script where the command is that failed
     */
    public CommandProcessorException(String command, String reason, String lineNumber) {
        this.command = command;
        this.reason = reason;
        this.lineNumber = lineNumber;
    }

    /**
     * @return String The command issued that resulted in an exception
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * @return String The reason the command failed
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * @return String The line number of the script where the command is that failed
     */
    public String getLineNumber() {
        return this.lineNumber;
    }

}
