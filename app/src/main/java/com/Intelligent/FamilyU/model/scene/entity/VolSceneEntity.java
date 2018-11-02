package com.Intelligent.FamilyU.model.scene.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 */

public class VolSceneEntity implements Serializable {
    @SerializedName("code")
    private String code;//操作代码
    @SerializedName("areaCode")
    private String areaCode;//地区代码 ,
    @SerializedName("conditions")
    private List<SceneExecCondition> conditions;//场景触发条件集合 ,
    @SerializedName("createTime")
    private String createTime;//创建时间 ,
    @SerializedName("execType")
    private String execType;//自动化场景执行类型(多条件必填) ,
    @SerializedName("operations")
    private List<SceneOperation> operations;
    @SerializedName("gatewaySn")
    private String gatewaySn;//网关SN ,
    @SerializedName("id")
    private String id;//主键 ,
    @SerializedName("sceneName")
    private String sceneName;//场景名称 ,
    @SerializedName("sceneType")
    private String sceneType;//场景类型 = ['DEFAULT', 'CUSTOM'],
    @SerializedName("status")
    private String status;//状态 ,
    @SerializedName("type")
    private String type;// ['SAVEORUPDATE', 'SAVELIST', 'MANUALEXECUTION', 'DELETE'],
    @SerializedName("updateTime")
    private String updateTime;//更新时间 ,
    @SerializedName("userId")
    private String userId;//用户id
    private String title;

    public String getCode() {
        return code;
    }

    public VolSceneEntity setCode(String code) {
        this.code = code;
        return this;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public VolSceneEntity setAreaCode(String areaCode) {
        this.areaCode = areaCode;
        return this;
    }

    public List<SceneExecCondition> getConditions() {
        return conditions;
    }

    public VolSceneEntity setConditions(ArrayList<SceneExecCondition> conditions) {
        this.conditions = conditions;
        return this;
    }


    public String getCreateTime() {
        return createTime;
    }

    public VolSceneEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }



    public String getGatewaySn() {
        return gatewaySn;
    }

    public VolSceneEntity setGatewaySn(String gatewaySn) {
        this.gatewaySn = gatewaySn;
        return this;
    }

    public String getId() {
        return id;
    }

    public VolSceneEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getSceneName() {
        return sceneName;
    }

    public VolSceneEntity setSceneName(String sceneName) {
        this.sceneName = sceneName;
        return this;
    }

    public String getSceneType() {
        return sceneType;
    }

    public VolSceneEntity setSceneType(String sceneType) {
        this.sceneType = sceneType;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public VolSceneEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getType() {
        return type;
    }

    public VolSceneEntity setType(String type) {
        this.type = type;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public VolSceneEntity setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public VolSceneEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public VolSceneEntity setConditions(List<SceneExecCondition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public String getExecType() {
        return execType;
    }

    public VolSceneEntity setExecType(String execType) {
        this.execType = execType;
        return this;
    }

    public List<SceneOperation> getOperations() {
        return operations;
    }

    public VolSceneEntity setOperations(List<SceneOperation> operations) {
        this.operations = operations;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public VolSceneEntity setTitle(String title) {
        this.title = title;
        return this;
    }
}
