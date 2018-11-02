package com.Intelligent.FamilyU.model.network.bean;

public class NetWorkEventBus {
    private String name;
    private String basename;
    private String basedevicename;
    private String basemac;
    private String basesn;
    private String status;
    private String broadbandAccount;
    private String serialNo;
    private String wifiname;
    private String wifipassword;
    private String networkingSn;

    public String getBasename() {
        return basename;
    }

    public NetWorkEventBus setBasename(String basename) {
        this.basename = basename;
        return this;
    }

    public String getBasedevicename() {
        return basedevicename;
    }

    public NetWorkEventBus setBasedevicename(String basedevicename) {
        this.basedevicename = basedevicename;
        return this;
    }

    public String getBasemac() {
        return basemac;
    }

    public NetWorkEventBus setBasemac(String basemac) {
        this.basemac = basemac;
        return this;
    }

    public String getBasesn() {
        return basesn;
    }

    public NetWorkEventBus setBasesn(String basesn) {
        this.basesn = basesn;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public NetWorkEventBus setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getBroadbandAccount() {
        return broadbandAccount;
    }

    public NetWorkEventBus setBroadbandAccount(String broadbandAccount) {
        this.broadbandAccount = broadbandAccount;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public NetWorkEventBus setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getName() {
        return name;
    }

    public NetWorkEventBus setName(String name) {
        this.name = name;
        return this;
    }

    public String getWifiname() {
        return wifiname;
    }

    public NetWorkEventBus setWifiname(String wifiname) {
        this.wifiname = wifiname;
        return this;
    }

    public String getWifipassword() {
        return wifipassword;
    }

    public NetWorkEventBus setWifipassword(String wifipassword) {
        this.wifipassword = wifipassword;
        return this;
    }

    public String getNetworkingSn() {
        return networkingSn;
    }

    public NetWorkEventBus setNetworkingSn(String networkingSn) {
        this.networkingSn = networkingSn;
        return this;
    }
}
