package com.Intelligent.FamilyU.model.scene.entity;

public class ScenceScoketEventBus {
    private String serialNo;
    private String name;
    private String status;
    private String deviceId;
    private String childDeviceId;
    private String parentId;
    private String modelId;
    private String basename;
    private String basedevicename;
    private String basemac;
    private String basesn;
    private String paramCode;
    private String paramValue;

    public String getBasename() {
        return basename;
    }

    public ScenceScoketEventBus setBasename(String basename) {
        this.basename = basename;
        return this;
    }

    public String getBasedevicename() {
        return basedevicename;
    }

    public ScenceScoketEventBus setBasedevicename(String basedevicename) {
        this.basedevicename = basedevicename;
        return this;
    }

    public String getBasemac() {
        return basemac;
    }

    public ScenceScoketEventBus setBasemac(String basemac) {
        this.basemac = basemac;
        return this;
    }

    public String getBasesn() {
        return basesn;
    }

    public ScenceScoketEventBus setBasesn(String basesn) {
        this.basesn = basesn;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ScenceScoketEventBus setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public ScenceScoketEventBus setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getName() {
        return name;
    }

    public ScenceScoketEventBus setName(String name) {
        this.name = name;
        return this;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public ScenceScoketEventBus setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getChildDeviceId() {
        return childDeviceId;
    }

    public ScenceScoketEventBus setChildDeviceId(String childDeviceId) {
        this.childDeviceId = childDeviceId;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public ScenceScoketEventBus setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getModelId() {
        return modelId;
    }

    public ScenceScoketEventBus setModelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    public String getParamCode() {
        return paramCode;
    }

    public ScenceScoketEventBus setParamCode(String paramCode) {
        this.paramCode = paramCode;
        return this;
    }

    public String getParamValue() {
        return paramValue;
    }

    public ScenceScoketEventBus setParamValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }
}
