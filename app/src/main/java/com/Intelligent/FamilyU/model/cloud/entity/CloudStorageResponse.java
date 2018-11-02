package com.Intelligent.FamilyU.model.cloud.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CloudStorageResponse implements Serializable {
    @SerializedName("message")
    public String message;
    @SerializedName("success")
    public boolean success;//任务是否成功

    public String getMessage() {
        return message;
    }

    public CloudStorageResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public CloudStorageResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
