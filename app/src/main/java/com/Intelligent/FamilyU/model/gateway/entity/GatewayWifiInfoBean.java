package com.Intelligent.FamilyU.model.gateway.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GatewayWifiInfoBean implements Serializable {
    @SerializedName("authType")
    private String authType;
    @SerializedName("channel")
    private String channel;
    @SerializedName("password")
    private String password;
    @SerializedName("ssid")
    private String ssid;

    public String getAuthType() {
        return authType;
    }

    public GatewayWifiInfoBean setAuthType(String authType) {
        this.authType = authType;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public GatewayWifiInfoBean setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public GatewayWifiInfoBean setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSsid() {
        return ssid;
    }

    public GatewayWifiInfoBean setSsid(String ssid) {
        this.ssid = ssid;
        return this;
    }
}
