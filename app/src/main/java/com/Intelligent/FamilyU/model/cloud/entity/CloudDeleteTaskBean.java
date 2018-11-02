package com.Intelligent.FamilyU.model.cloud.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CloudDeleteTaskBean implements Serializable {

    @SerializedName("success")
    private boolean success;// 任务是否成功 ,
    @SerializedName("message ")
    private String message ;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
