package com.Intelligent.FamilyU.model.gateway.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GatewayUntieBean implements Serializable {
    @SerializedName("comment")
    private String comment;//备注 ,
    @SerializedName("createTime")
    private String createTime;//创建时间 ,
    @SerializedName("createUser")
    private String createUser;//创建人ID ,
    @SerializedName("createUserName")
    private String createUserName;//创建人名称 ,
    @SerializedName("gatewayId")
    private String gatewayId;//网关id ,
    @SerializedName("gatewaySn")
    private String gatewaySn;//网关序列号 ,
    @SerializedName("id")
    private String id;//主键 ,
    @SerializedName("status")
    private int status;//状态 1有效。2无效 ,
    @SerializedName("updateTime")
    private String updateTime;//更新时间 ,
    @SerializedName("updateUser")
    private String updateUser;//更新人ID ,
    @SerializedName("updateUserName")
    private String updateUserName;//更新人名称 ,
    @SerializedName("userId")
    private String userId;//用户ID

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getGatewaySn() {
        return gatewaySn;
    }

    public void setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
