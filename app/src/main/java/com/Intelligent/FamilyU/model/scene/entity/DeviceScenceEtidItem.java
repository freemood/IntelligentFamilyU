package com.Intelligent.FamilyU.model.scene.entity;

import java.util.List;

public class DeviceScenceEtidItem {
    private List<SceneOperation> operations;
    private List<SceneExecCondition> sceneExecConditions;
    private String pk;
    public List<SceneOperation> getOperations() {
        return operations;
    }

    public DeviceScenceEtidItem setOperations(List<SceneOperation> operations) {
        this.operations = operations;
        return this;
    }

    public List<SceneExecCondition> getSceneExecConditions() {
        return sceneExecConditions;
    }

    public DeviceScenceEtidItem setSceneExecConditions(List<SceneExecCondition> sceneExecConditions) {
        this.sceneExecConditions = sceneExecConditions;
        return this;
    }

    public String getPk() {
        return pk;
    }

    public DeviceScenceEtidItem setPk(String pk) {
        this.pk = pk;
        return this;
    }
}
