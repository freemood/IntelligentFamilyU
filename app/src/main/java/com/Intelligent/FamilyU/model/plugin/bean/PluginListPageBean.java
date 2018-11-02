package com.Intelligent.FamilyU.model.plugin.bean;

import java.io.Serializable;
import java.util.List;

public class PluginListPageBean implements Serializable {
    private List<PluginPageEntity> list;



    public List<PluginPageEntity> getList() {
        return list;
    }

    public PluginListPageBean setList(List<PluginPageEntity> list) {
        this.list = list;
        return this;
    }
}
