package com.Intelligent.FamilyU.model.gateway.entity;

public class  GatewayEventBus {
    private String name;
    private String basename;
    private String basedevicename;
    private String basemac;
    private String basesn;
    private String  status;
    private String broadbandAccount;
    private String  serialNo;
    private String  pluginId;
    public String getBasename() {
        return basename;
    }

    public GatewayEventBus setBasename(String basename) {
        this.basename = basename;
        return this;
    }

    public String getBasedevicename() {
        return basedevicename;
    }

    public GatewayEventBus setBasedevicename(String basedevicename) {
        this.basedevicename = basedevicename;
        return this;
    }

    public String getBasemac() {
        return basemac;
    }

    public GatewayEventBus setBasemac(String basemac) {
        this.basemac = basemac;
        return this;
    }

    public String getBasesn() {
        return basesn;
    }

    public GatewayEventBus setBasesn(String basesn) {
        this.basesn = basesn;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public GatewayEventBus setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getBroadbandAccount() {
        return broadbandAccount;
    }

    public GatewayEventBus setBroadbandAccount(String broadbandAccount) {
        this.broadbandAccount = broadbandAccount;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public GatewayEventBus setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public String getName() {
        return name;
    }

    public GatewayEventBus setName(String name) {
        this.name = name;
        return this;
    }

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }
}
