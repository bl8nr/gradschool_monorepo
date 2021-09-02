package com.cscie97.ledger;

public class LedgerException extends Throwable {
    private String action;
    private String reason;

    /**
     * Create a new ledger exception using the params in the args provided
     * @param action String The action being performed when the exception occurred
     * @param reason String The reason the action failed
     */
    public LedgerException(String action, String reason) {
        this.action = action;
        this.reason = reason;
    }

    /**
     * @return String The action being performed when the exception occurred
     */
    public String getAction() {
        return this.action;
    }

    /**
     * @return String The reason the action failed
     */
    public String getReason() {
        return this.reason;
    }
}
