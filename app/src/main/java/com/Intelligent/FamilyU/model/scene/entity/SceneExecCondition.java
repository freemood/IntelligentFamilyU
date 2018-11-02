package com.Intelligent.FamilyU.model.scene.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SceneExecCondition implements Serializable {
    @SerializedName("childDeviceMac")
    private String  childDeviceMac;//子设备mac ,
    @SerializedName("conditionType")
    private String conditionType;//条件类型 ,
    @SerializedName("criterion")
    private String criterion;//条件值比对标准 ,
    @SerializedName("dataType")
    private String  dataType;//产品id ,
    @SerializedName("deviceMac")
    private String deviceMac;//设备mac ,
    @SerializedName("deviceType")
    private String deviceType;//产品id ,
    @SerializedName("paramCode")
    private String paramCode;//条件参数代码 ,
    @SerializedName("paramIndex")
    private String  paramIndex;
    @SerializedName("paramValue")
    private String paramValue;//条件参数值 ,
    @SerializedName("resource")
    private String resource;//条件值来源

    public String getChildDeviceMac() {
        return childDeviceMac;
    }

    public SceneExecCondition setChildDeviceMac(String childDeviceMac) {
        this.childDeviceMac = childDeviceMac;
        return this;
    }

    public String getConditionType() {
        return conditionType;
    }

    public SceneExecCondition setConditionType(String conditionType) {
        this.conditionType = conditionType;
        return this;
    }

    public String getCriterion() {
        return criterion;
    }

    public SceneExecCondition setCriterion(String criterion) {
        this.criterion = criterion;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public SceneExecCondition setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public SceneExecCondition setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
        return this;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public SceneExecCondition setDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public String getParamCode() {
        return paramCode;
    }

    public SceneExecCondition setParamCode(String paramCode) {
        this.paramCode = paramCode;
        return this;
    }

    public String getParamIndex() {
        return paramIndex;
    }

    public SceneExecCondition setParamIndex(String paramIndex) {
        this.paramIndex = paramIndex;
        return this;
    }

    public String getParamValue() {
        return paramValue;
    }

    public SceneExecCondition setParamValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    public String getResource() {
        return resource;
    }

    public SceneExecCondition setResource(String resource) {
        this.resource = resource;
        return this;
    }
}
