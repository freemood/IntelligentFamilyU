package com.Intelligent.FamilyU.model.network.bean;

import java.io.Serializable;
import java.util.List;

public class NetWorkListBean implements Serializable {
    private List<DeviceEntity> list;



    public List<DeviceEntity> getList() {
        return list;
    }

    public NetWorkListBean setList(List<DeviceEntity> list) {
        this.list = list;
        return this;
    }
}
