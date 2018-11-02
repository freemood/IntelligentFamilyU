package com.Intelligent.FamilyU.model.my.entity;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ProblemReportBean implements Serializable {

    @SerializedName("result")
    private String result;// 网关设备序列号 ,

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
