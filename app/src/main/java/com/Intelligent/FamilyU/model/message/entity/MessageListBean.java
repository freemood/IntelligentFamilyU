package com.Intelligent.FamilyU.model.message.entity;

import java.io.Serializable;
import java.util.List;

public class MessageListBean implements Serializable {

    private List<MessageBean> list;

    public List<MessageBean> getList() {
        return list;
    }

    public MessageListBean setList(List<MessageBean> list) {
        this.list = list;
        return this;
    }
}
