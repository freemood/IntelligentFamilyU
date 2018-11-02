package com.Intelligent.FamilyU.model.cloud.entity;

import java.io.Serializable;
import java.util.List;

public class CloudAddTokenBean implements Serializable {

    private List<CloudStorageResponse> list;

    public List<CloudStorageResponse> getList() {
        return list;
    }

    public CloudAddTokenBean setList(List<CloudStorageResponse> list) {
        this.list = list;
        return this;
    }
}
