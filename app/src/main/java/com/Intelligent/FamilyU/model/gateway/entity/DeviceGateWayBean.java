package com.Intelligent.FamilyU.model.gateway.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceGateWayBean implements Serializable {
    private List<DeviceGateWayDTO> list;

    public List<DeviceGateWayDTO> getList() {
        return list;
    }

    public DeviceGateWayBean setList(List<DeviceGateWayDTO> list) {
        this.list = list;
        return this;
    }
}
