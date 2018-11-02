package com.Intelligent.FamilyU.model.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MessageBean implements Serializable {


    @SerializedName("comment")
    private String comment;
    @SerializedName("content")
    private String content;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("gatewaySn")
    private String gatewaySn;
    @SerializedName("id")
    private String id;
    @SerializedName("isRead")
    private int isRead;//是否已读，1：未读，2：已读 ,
    @SerializedName("status ")
    private int status;//状态：1正常，2已删除 ,
    @SerializedName("type ")
    private int type;//消息类型：1：设备消息，2：系统消息

    public String getComment() {
        return comment;
    }

    public MessageBean setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MessageBean setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public MessageBean setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getGatewaySn() {
        return gatewaySn;
    }

    public MessageBean setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
        return this;
    }

    public String getId() {
        return id;
    }

    public MessageBean setId(String id) {
        this.id = id;
        return this;
    }

    public int getIsRead() {
        return isRead;
    }

    public MessageBean setIsRead(int isRead) {
        this.isRead = isRead;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public MessageBean setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getType() {
        return type;
    }

    public MessageBean setType(int type) {
        this.type = type;
        return this;
    }
}
