package com.Intelligent.FamilyU.model.scene.entity;

import java.io.Serializable;
import java.util.List;

public class DeviceOperationListBean implements Serializable {
    private List<DeviceOperation> list;


    public List<DeviceOperation> getList() {
        return list;
    }

    public DeviceOperationListBean setList(List<DeviceOperation> list) {
        this.list = list;
        return this;
    }
}
