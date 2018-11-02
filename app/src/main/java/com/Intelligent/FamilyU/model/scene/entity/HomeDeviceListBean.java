package com.Intelligent.FamilyU.model.scene.entity;

import java.io.Serializable;
import java.util.List;

public class HomeDeviceListBean implements Serializable {
    private List<DeviceEntity> list;



    public List<DeviceEntity> getList() {
        return list;
    }

    public HomeDeviceListBean setList(List<DeviceEntity> list) {
        this.list = list;
        return this;
    }
}
