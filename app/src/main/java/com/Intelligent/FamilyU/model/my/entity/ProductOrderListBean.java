package com.Intelligent.FamilyU.model.my.entity;


import java.io.Serializable;
import java.util.List;

public class ProductOrderListBean implements Serializable {
    private List<ProductOrderEntity> list;

    public List<ProductOrderEntity> getList() {
        return list;
    }

    public ProductOrderListBean setList(List<ProductOrderEntity> list) {
        this.list = list;
        return this;
    }
}
