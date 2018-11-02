package com.Intelligent.FamilyU.model.scene.entity;

public class DeviceScenceItem {
    private String childDeviceMac;
    private String deviceMac;
    private String deviceType;
    private String paramOpenCode;
    private String paramIndex;
    private String paramOpenValue;
    private String paramOpenName;
    private String paramCloseValue;
    private String paramCloseName;
    private String paramCloseCode;
    private String code;
    private String name;

    public String getChildDeviceMac() {
        return childDeviceMac;
    }

    public DeviceScenceItem setChildDeviceMac(String childDeviceMac) {
        this.childDeviceMac = childDeviceMac;
        return this;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public DeviceScenceItem setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
        return this;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public DeviceScenceItem setDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }


    public String getParamIndex() {
        return paramIndex;
    }

    public DeviceScenceItem setParamIndex(String paramIndex) {
        this.paramIndex = paramIndex;
        return this;
    }

    public String getCode() {
        return code;
    }

    public DeviceScenceItem setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public DeviceScenceItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getParamOpenCode() {
        return paramOpenCode;
    }

    public DeviceScenceItem setParamOpenCode(String paramOpenCode) {
        this.paramOpenCode = paramOpenCode;
        return this;
    }

    public String getParamOpenValue() {
        return paramOpenValue;
    }

    public DeviceScenceItem setParamOpenValue(String paramOpenValue) {
        this.paramOpenValue = paramOpenValue;
        return this;
    }

    public String getParamOpenName() {
        return paramOpenName;
    }

    public DeviceScenceItem setParamOpenName(String paramOpenName) {
        this.paramOpenName = paramOpenName;
        return this;
    }

    public String getParamCloseValue() {
        return paramCloseValue;
    }

    public DeviceScenceItem setParamCloseValue(String paramCloseValue) {
        this.paramCloseValue = paramCloseValue;
        return this;
    }

    public String getParamCloseName() {
        return paramCloseName;
    }

    public DeviceScenceItem setParamCloseName(String paramCloseName) {
        this.paramCloseName = paramCloseName;
        return this;
    }

    public String getParamCloseCode() {
        return paramCloseCode;
    }

    public DeviceScenceItem setParamCloseCode(String paramCloseCode) {
        this.paramCloseCode = paramCloseCode;
        return this;
    }
}
