package com.Intelligent.FamilyU.model.home.entity;

import java.io.Serializable;
import java.util.List;

public class BannerListBean implements Serializable {
    private List<ProductBannerEntity> list;


    public List<ProductBannerEntity> getList() {
        return list;
    }

    public BannerListBean setList(List<ProductBannerEntity> list) {
        this.list = list;
        return this;
    }
}
