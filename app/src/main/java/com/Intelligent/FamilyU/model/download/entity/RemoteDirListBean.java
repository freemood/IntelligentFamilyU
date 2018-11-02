package com.Intelligent.FamilyU.model.download.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class RemoteDirListBean implements Serializable {

    @SerializedName("messageId")
    private int messageId;
    @SerializedName("serialNo")
    private String serialNo;//网关设备序列号 ,
    @SerializedName("success")
    private boolean success;//任务是否成功 ,
    @SerializedName("type")
    private String type;//任务,
    @SerializedName("stores")
    private List<String> stores;//获取路径

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getStores() {
        return stores;
    }

    public void setStores(List<String> stores) {
        this.stores = stores;
    }
}
