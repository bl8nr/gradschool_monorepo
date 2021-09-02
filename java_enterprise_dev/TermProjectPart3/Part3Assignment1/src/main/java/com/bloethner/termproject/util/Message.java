package com.bloethner.termproject.util;

/**
 * Create utility message class to quickly create new messages
 * for i18n and application messaging
 */
public class Message {

    /**
     * Store message type and the message body
     */
    private String type;
    private String message;

    /**
     * Set message type and body on construction
     */
    public Message(String type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * getters
     */
    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
