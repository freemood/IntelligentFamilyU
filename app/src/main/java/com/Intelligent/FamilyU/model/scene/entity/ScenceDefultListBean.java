package com.Intelligent.FamilyU.model.scene.entity;

import java.io.Serializable;
import java.util.List;

public class ScenceDefultListBean implements Serializable {
    private List<VolSceneEntity> list;



    public List<VolSceneEntity> getList() {
        return list;
    }

    public ScenceDefultListBean setList(List<VolSceneEntity> list) {
        this.list = list;
        return this;
    }
}
