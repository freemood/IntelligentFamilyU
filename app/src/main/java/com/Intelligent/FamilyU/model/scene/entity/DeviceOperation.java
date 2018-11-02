package com.Intelligent.FamilyU.model.scene.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 */

public class DeviceOperation implements Serializable {
    @SerializedName("code")
    private String code;//操作代码
    @SerializedName("comment")
    private String comment;//操作描述 ,
    @SerializedName("defaultValue")
    private String defaultValue;//默认值 ,
    @SerializedName("id")
    private String id;//主键 ,
    @SerializedName("isPush")
    private String isPush;//是否需要推送 ,
    @SerializedName("name")
    private String name;//操作名称 ,
    private List<OperationValueList> operationValueList;//设备操作值 ,
   // @SerializedName("type")
    //private String type;//操作类型 = ['INPUT', 'OUTPUT', 'CONFIG']

    public class OperationValueList {
        private String code;
        private String name;
        private String value;

        public String getCode() {
            return code;
        }

        public OperationValueList setCode(String code) {
            this.code = code;
            return this;
        }

        public String getName() {
            return name;
        }

        public OperationValueList setName(String name) {
            this.name = name;
            return this;
        }

        public String getValue() {
            return value;
        }

        public OperationValueList setValue(String value) {
            this.value = value;
            return this;
        }
    }

    public String getCode() {
        return code;
    }

    public DeviceOperation setCode(String code) {
        this.code = code;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public DeviceOperation setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public DeviceOperation setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getId() {
        return id;
    }

    public DeviceOperation setId(String id) {
        this.id = id;
        return this;
    }

    public String getIsPush() {
        return isPush;
    }

    public DeviceOperation setIsPush(String isPush) {
        this.isPush = isPush;
        return this;
    }

    public String getName() {
        return name;
    }

    public DeviceOperation setName(String name) {
        this.name = name;
        return this;
    }


    public List<OperationValueList> getOperationValueList() {
        return operationValueList;
    }

    public DeviceOperation setOperationValueList(List<OperationValueList> operationValueList) {
        this.operationValueList = operationValueList;
        return this;
    }
}
