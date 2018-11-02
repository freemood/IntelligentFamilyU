package com.Intelligent.FamilyU.model.login.bean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginVerificationCodeBean implements Serializable {

    @SerializedName("result")
    private String result;
    @SerializedName("status")
    private int status = 0;
    @SerializedName("timestamp")
    private String timestamp;

    public String getResult() {
        return result;
    }

    public LoginVerificationCodeBean setResult(String result) {
        this.result = result;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public LoginVerificationCodeBean setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public LoginVerificationCodeBean setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
