package com.Intelligent.FamilyU.model.home.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductBannerEntity implements Serializable {
    @SerializedName("comment")
    private String comment;//备注说明
    @SerializedName("createTime")
    private String createTime;//创建时间
    @SerializedName("createUser")
    private String createUser;//创建人
    @SerializedName("productId")
    private String id;//主键
    @SerializedName("id")
    private String productId;//产品ID
    @SerializedName("status")
    private String status;//
    @SerializedName("updateTime")
    private String updateTime;//更新时间
    @SerializedName("updateUser")
    private String updateUser;//更新人
    @SerializedName("url")
    private String url;//图片连接
    @SerializedName("resources")
    private String  resources;// 资源地址 ,
    @SerializedName("type")
    private String  type;// banner类型，1：HTML，2：产品ID,如果类型是2则resources为空

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResources() {
        return resources;
    }

    public ProductBannerEntity setResources(String resources) {
        this.resources = resources;
        return this;
    }

    public String getType() {
        return type;
    }

    public ProductBannerEntity setType(String type) {
        this.type = type;
        return this;
    }
}
