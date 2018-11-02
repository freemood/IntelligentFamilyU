package com.Intelligent.FamilyU.model.network.bean;

import java.io.Serializable;

public class NetWorkModifyNameBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public NetWorkModifyNameBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
