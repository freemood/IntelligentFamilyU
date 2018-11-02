package com.Intelligent.FamilyU.model.my.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateAppBean implements Serializable {

    @SerializedName("comment")
    private String comment;//更新说明 ,
    @SerializedName("createTime")
    private String createTime;//创建时间 ,
    @SerializedName("createUser")
    private String createUser;//创建人 ,
    @SerializedName("id")
    private String id;//主键 ,
    @SerializedName("isRelease")
    private int isRelease;//是否最新(1:是) ,
    @SerializedName("name")
    private String name;//名称 ,
    @SerializedName("status")
    private String status;//状态 0：有效，1：无效 ,
    @SerializedName("type")
    private Type type;//类型 = ['ALPHA', 'BETA', 'RC', 'RELEASE'],
    @SerializedName("updateTime")
    private String updateTime;//更新时间 ,
    @SerializedName("updateUser")
    private String updateUser;//更新人 ,
    @SerializedName("url")
    private String url;//APP 下载地址 ,
    @SerializedName("versionNo")
    private String versionNo;//版本号

    public class Type{
        private int index;
        private String text;
        private String value;
        private int mask;

        public int getIndex() {
            return index;
        }

        public Type setIndex(int index) {
            this.index = index;
            return this;
        }

        public String getText() {
            return text;
        }

        public Type setText(String text) {
            this.text = text;
            return this;
        }

        public String getValue() {
            return value;
        }

        public Type setValue(String value) {
            this.value = value;
            return this;
        }

        public int getMask() {
            return mask;
        }

        public Type setMask(int mask) {
            this.mask = mask;
            return this;
        }
    }

    public String getComment() {
        return comment;
    }

    public UpdateAppBean setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public UpdateAppBean setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreateUser() {
        return createUser;
    }

    public UpdateAppBean setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public String getId() {
        return id;
    }

    public UpdateAppBean setId(String id) {
        this.id = id;
        return this;
    }

    public int getIsRelease() {
        return isRelease;
    }

    public UpdateAppBean setIsRelease(int isRelease) {
        this.isRelease = isRelease;
        return this;
    }

    public String getName() {
        return name;
    }

    public UpdateAppBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UpdateAppBean setStatus(String status) {
        this.status = status;
        return this;
    }

    public Type getType() {
        return type;
    }

    public UpdateAppBean setType(Type type) {
        this.type = type;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public UpdateAppBean setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public UpdateAppBean setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public UpdateAppBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public UpdateAppBean setVersionNo(String versionNo) {
        this.versionNo = versionNo;
        return this;
    }
}