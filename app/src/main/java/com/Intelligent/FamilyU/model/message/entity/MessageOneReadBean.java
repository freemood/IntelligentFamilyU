package com.Intelligent.FamilyU.model.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageOneReadBean implements Serializable {
    @SerializedName("result")
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public MessageOneReadBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
