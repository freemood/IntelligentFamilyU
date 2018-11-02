package com.Intelligent.FamilyU.model.cloud.entity;

public class CloudEvent {
    private String message;

    public CloudEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public CloudEvent setMessage(String message) {
        this.message = message;
        return this;
    }
}
