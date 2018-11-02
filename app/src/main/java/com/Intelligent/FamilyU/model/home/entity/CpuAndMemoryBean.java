package com.Intelligent.FamilyU.model.home.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CpuAndMemoryBean implements Serializable {
    //cup占用情况
    @SerializedName("cpuUsage")
    private String cpuUsage;
    //内存占用情况
    @SerializedName("memoryUsage")
    private String memoryUsage;
    //序列号
    @SerializedName("serialNo")
    private String serialNo;

    public String getCpuUsage() {
        return cpuUsage;
    }

    public CpuAndMemoryBean setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
        return this;
    }

    public String getMemoryUsage() {
        return memoryUsage;
    }

    public CpuAndMemoryBean setMemoryUsage(String memoryUsage) {
        this.memoryUsage = memoryUsage;
        return this;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public CpuAndMemoryBean setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }
}
