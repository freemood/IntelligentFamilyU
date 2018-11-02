package com.Intelligent.FamilyU.model.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageAllDeleteBean implements Serializable {
    @SerializedName("result")
    private int result;

    public int getResult() {
        return result;
    }

    public MessageAllDeleteBean setResult(int result) {
        this.result = result;
        return this;
    }
}
