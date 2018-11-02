package com.Intelligent.FamilyU.model.network.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeviceEntity implements Serializable {
    //天线：1、内置；2、外置
    @SerializedName("antenna")
    private int antenna;
    @SerializedName("createTime")
    private String createTime;//创建时间
    @SerializedName("firmwareVersion")
    private String firmwareVersion; //固件版本
    @SerializedName("frequency")
    private int frequency; //频段:1、双频;2、单频2.4G;3、双频5G
    @SerializedName("gatewaySn")
    private String gatewaySn; //网关SN
    @SerializedName("hardwareVersion")
    private String hardwareVersion;//软件版本-->改为硬件版本
    @SerializedName("id")
    private String id;//主键
    @SerializedName("mac")
    private String mac; //MAC
    @SerializedName("name")
    private String name;//名称
    @SerializedName("password24")
    private String password24;//2.4GHz WIFI密码
    @SerializedName("password50")
    private String password50;//5.0GHz WIFI密码

    @SerializedName("register")
    private int register;// 是否认证2:未认证，1：已认证
    @SerializedName("registerTime")
    private String registerTime;//  注册时间
    @SerializedName("sn")
    private String sn;
    @SerializedName("status")
    private int status;//设备状态:1、在线，0、离线
    @SerializedName("supplierId")
    private String supplierId;//厂家ID
    @SerializedName("supplierName")
    private String supplierName;// 厂家名称

//    @SerializedName("typeCode")
    private TypeCode typeCode;// 组网类型code['CAT', 'NT', 'MOCA', 'OTHER', 'ROUTING']
    @SerializedName("typeId")
    private String typeId;//型号
    @SerializedName("typeName")
    private String typeName;//型号名称
    @SerializedName("updateTime")
    private String updateTime;//更新时间

    private String wifi24;// 2.4GHz WIFI账号

    private int  wifi24Status;// 2,4GHz 状态 2：关闭，1：开启

    private String wifi50;//5.0GHz WIFI账号

    private int wifi50Status;//5.0GHz 状态 2：关闭，1：开启
    public class TypeCode {
        private int index;
        private String text;
        private String value;
        private int mask;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getMask() {
            return mask;
        }

        public void setMask(int mask) {
            this.mask = mask;
        }
    }
class TypeEntity {
    private int index;
    private String text;
    private String value;
    private int mask;

    private int antenna;//天线：1、内置；2、外置

    private String createTime;// 创建时间

    private int frequency;// 频段:1、双频;2、单频2.4G;3、双频5G

    private String id;//主键

    private String model;//型号

    private String name;// 名称

    private String supplierId;// 厂家ID

    private String supplierName;// 厂家名称

    private String  type;//类型= ['CAT', 'NT', 'MOCA', 'OTHER', 'ROUTING']

    private String  updateTime;// 更新时间

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public int getAntenna() {
        return antenna;
    }

    public void setAntenna(int antenna) {
        this.antenna = antenna;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

    public int getAntenna() {
        return antenna;
    }

    public void setAntenna(int antenna) {
        this.antenna = antenna;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getGatewaySn() {
        return gatewaySn;
    }

    public void setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword24() {
        return password24;
    }

    public void setPassword24(String password24) {
        this.password24 = password24;
    }

    public String getPassword50() {
        return password50;
    }

    public void setPassword50(String password50) {
        this.password50 = password50;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public TypeCode getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(TypeCode typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWifi24() {
        return wifi24;
    }

    public void setWifi24(String wifi24) {
        this.wifi24 = wifi24;
    }

    public int getWifi24Status() {
        return wifi24Status;
    }

    public void setWifi24Status(int wifi24Status) {
        this.wifi24Status = wifi24Status;
    }

    public String getWifi50() {
        return wifi50;
    }

    public void setWifi50(String wifi50) {
        this.wifi50 = wifi50;
    }

    public int getWifi50Status() {
        return wifi50Status;
    }

    public void setWifi50Status(int wifi50Status) {
        this.wifi50Status = wifi50Status;
    }


}
