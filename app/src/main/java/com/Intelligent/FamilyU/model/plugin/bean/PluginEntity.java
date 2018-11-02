package com.Intelligent.FamilyU.model.plugin.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PluginEntity implements Serializable {
    //归属区域ID
    @SerializedName("area")
    private String area;
    @SerializedName("author")
    private String author;
    @SerializedName("authorEmail")
    private String authorEmail;
    @SerializedName("authorPhone")
    private String authorPhone;
    //插件种类
    @SerializedName("categoryId")
    private String categoryId;
    //备注
    @SerializedName("comment")
    private String comment;
    @SerializedName("createTime")
    private String createTime;
    //下载次数
    @SerializedName("downloadTimes")
    private String downloadTimes;
    @SerializedName("id")
    private String id;
    //安装次数
    @SerializedName("installTimes")
    private String installTimes;
    //最新版本号
    @SerializedName("lastVersionNo")
    private String lastVersionNo;
    //标识
    @SerializedName("mark")
    private String mark;
    //插件名称
    @SerializedName("name")
    private String name;
    //运维插件
    @SerializedName("ops")
    private String ops;
    //安装方式：1预置安装。2自主安装 ,
    @SerializedName("preset")
    private String preset;
    @SerializedName("updateTime")
    private String updateTime;
    @SerializedName("versionCount")
    private String versionCount;
//    //状态 = ['RELEASE', 'PRIVATE', 'DISCARD'],
//    @SerializedName("status")
//    private StatusInfo status;
//    //插件类型 = ['OSGI', 'ANDROID', 'OPENWRT', 'C'],
//    @SerializedName("type")
//    private TypeInfo type;

    class StatusInfo {
        private String index;
        private String text;
        private String value;
        private String mask;

        public String getIndex() {
            return index;
        }

        public StatusInfo setIndex(String index) {
            this.index = index;
            return this;
        }

        public String getText() {
            return text;
        }

        public StatusInfo setText(String text) {
            this.text = text;
            return this;
        }

        public String getValue() {
            return value;
        }

        public StatusInfo setValue(String value) {
            this.value = value;
            return this;
        }

        public String getMask() {
            return mask;
        }

        public StatusInfo setMask(String mask) {
            this.mask = mask;
            return this;
        }
    }

    class TypeInfo {
        private String index;
        private String text;
        private String value;
        private String mask;

        public String getIndex() {
            return index;
        }

        public TypeInfo setIndex(String index) {
            this.index = index;
            return this;
        }

        public String getText() {
            return text;
        }

        public TypeInfo setText(String text) {
            this.text = text;
            return this;
        }

        public String getValue() {
            return value;
        }

        public TypeInfo setValue(String value) {
            this.value = value;
            return this;
        }

        public String getMask() {
            return mask;
        }

        public TypeInfo setMask(String mask) {
            this.mask = mask;
            return this;
        }
    }

    public String getArea() {
        return area;
    }

    public PluginEntity setArea(String area) {
        this.area = area;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public PluginEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public PluginEntity setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public String getAuthorPhone() {
        return authorPhone;
    }

    public PluginEntity setAuthorPhone(String authorPhone) {
        this.authorPhone = authorPhone;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public PluginEntity setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public PluginEntity setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public PluginEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getDownloadTimes() {
        return downloadTimes;
    }

    public PluginEntity setDownloadTimes(String downloadTimes) {
        this.downloadTimes = downloadTimes;
        return this;
    }

    public String getId() {
        return id;
    }

    public PluginEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getInstallTimes() {
        return installTimes;
    }

    public PluginEntity setInstallTimes(String installTimes) {
        this.installTimes = installTimes;
        return this;
    }

    public String getLastVersionNo() {
        return lastVersionNo;
    }

    public PluginEntity setLastVersionNo(String lastVersionNo) {
        this.lastVersionNo = lastVersionNo;
        return this;
    }

    public String getMark() {
        return mark;
    }

    public PluginEntity setMark(String mark) {
        this.mark = mark;
        return this;
    }

    public String getName() {
        return name;
    }

    public PluginEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getOps() {
        return ops;
    }

    public PluginEntity setOps(String ops) {
        this.ops = ops;
        return this;
    }

    public String getPreset() {
        return preset;
    }

    public PluginEntity setPreset(String preset) {
        this.preset = preset;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public PluginEntity setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getVersionCount() {
        return versionCount;
    }

    public PluginEntity setVersionCount(String versionCount) {
        this.versionCount = versionCount;
        return this;
    }

//    public StatusInfo getStatus() {
//        return status;
//    }
//
//    public PluginEntity setStatus(StatusInfo status) {
//        this.status = status;
//        return this;
//    }
//
//    public TypeInfo getType() {
//        return type;
//    }
//
//    public PluginEntity setType(TypeInfo type) {
//        this.type = type;
//        return this;
//    }
}
