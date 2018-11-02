package com.Intelligent.FamilyU.model.scene.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 */

public class DeviceEntity implements Serializable {
    @SerializedName("deviceType")
    private String deviceType;//备注 ,
    @SerializedName("createTime")
    private String createTime;//创建时间 ,
    @SerializedName("firmwareVersion")
    private String firmwareVersion;//固件版本 ,
    @SerializedName("gatewaySn")
    private String gatewaySn;//绑定网关sn ,
    @SerializedName("hardwareVersion")
    private String hardwareVersion;//软件版本-->改为硬件版本 ,
    @SerializedName("id")
    private String id;//主键 ,
    @SerializedName("mac")
    private String mac;//MAC
    @SerializedName("modelId")
    private String modelId;//型号ID ,
    @SerializedName("name")
    private String name;//家居名称 ,
    @SerializedName("parentId")
    private String parentId;//家居名称 父ID ,
    @SerializedName("params")
    private String params;//设备类型（用于区分家居和ap） ,
    @SerializedName("register")
    private String register;//是否认证。。0:未认证，1：认证。 ,
    @SerializedName("registerTime")
    private String registerTime;//注册时间 ,
    @SerializedName("sn")
    private String sn;//家居SN ,
    @SerializedName("status")
    private String status;//家居状态；1、在线。0、离线 ,
    @SerializedName("updateTime")
    private String updateTime;// 更新时间
    @SerializedName("supplierId")
    private String supplierId;

    public String getDeviceType() {
        return deviceType;
    }

    public DeviceEntity setDeviceType(String deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public DeviceEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public DeviceEntity setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
        return this;
    }

    public String getGatewaySn() {
        return gatewaySn;
    }

    public DeviceEntity setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
        return this;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public DeviceEntity setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
        return this;
    }

    public String getId() {
        return id;
    }

    public DeviceEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getMac() {
        return mac;
    }

    public DeviceEntity setMac(String mac) {
        this.mac = mac;
        return this;
    }

    public String getModelId() {
        return modelId;
    }

    public DeviceEntity setModelId(String modelId) {
        this.modelId = modelId;
        return this;
    }

    public String getName() {
        return name;
    }

    public DeviceEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public DeviceEntity setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getParams() {
        return params;
    }

    public DeviceEntity setParams(String params) {
        this.params = params;
        return this;
    }

    public String getRegister() {
        return register;
    }

    public DeviceEntity setRegister(String register) {
        this.register = register;
        return this;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public DeviceEntity setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
        return this;
    }

    public String getSn() {
        return sn;
    }

    public DeviceEntity setSn(String sn) {
        this.sn = sn;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DeviceEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public DeviceEntity setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public DeviceEntity setSupplierId(String supplierId) {
        this.supplierId = supplierId;
        return this;
    }
}
