package com.Intelligent.FamilyU.model.scene.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SceneOperation implements Serializable {
    @SerializedName("childDeviceMac")
    private String childDeviceMac;//子设备mac ,
    @SerializedName("deviceMac")
    private String deviceMac;//设备mac ,
    @SerializedName("deviceType")
    private String deviceType;//产品id ,
    @SerializedName("paramCode")
    private String paramCode;//条件参数代码 ,
    @SerializedName("paramIndex")
    private String paramIndex;//
    @SerializedName("paramValue")
    private String paramValue;//条件参数值

    public String getChildDeviceMac() {
        return childDeviceMac;
    }

    public SceneOperation setChildDeviceMac(String childDeviceMac) {
        this.childDeviceMac = childDeviceMac;
        return this;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public SceneOperation setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
        return this;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public SceneOperation setDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public String getParamCode() {
        return paramCode;
    }

    public SceneOperation setParamCode(String paramCode) {
        this.paramCode = paramCode;
        return this;
    }

    public String getParamIndex() {
        return paramIndex;
    }

    public SceneOperation setParamIndex(String paramIndex) {
        this.paramIndex = paramIndex;
        return this;
    }

    public String getParamValue() {
        return paramValue;
    }

    public SceneOperation setParamValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }
}
