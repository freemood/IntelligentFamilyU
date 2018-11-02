package com.Intelligent.FamilyU.model.network.bean;

import java.io.Serializable;

public class NetWorkBindBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public NetWorkBindBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
