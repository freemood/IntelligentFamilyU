package com.Intelligent.FamilyU.model.main.event;

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public MessageEvent setMessage(String message) {
        this.message = message;
        return this;
    }
}
