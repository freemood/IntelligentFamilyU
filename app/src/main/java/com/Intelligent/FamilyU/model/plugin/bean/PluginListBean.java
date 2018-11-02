package com.Intelligent.FamilyU.model.plugin.bean;

import java.io.Serializable;
import java.util.List;

public class PluginListBean implements Serializable {
    private List<PluginEntity> list;



    public List<PluginEntity> getList() {
        return list;
    }

    public PluginListBean setList(List<PluginEntity> list) {
        this.list = list;
        return this;
    }
}
