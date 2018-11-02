package com.Intelligent.FamilyU.model.download.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RemoteBuildTaskBean implements Serializable {
    @SerializedName("acceptRanges")
    private boolean acceptRanges;//是否支持断点续传 ,
    @SerializedName("serialNo")
    private String serialNo;// 网关设备序列号 ,
    @SerializedName("success")
    private boolean success;// 任务是否成功 ,
    @SerializedName("taskId")
    private String taskId;//任务id

    public boolean isAcceptRanges() {
        return acceptRanges;
    }

    public void setAcceptRanges(boolean acceptRanges) {
        this.acceptRanges = acceptRanges;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
