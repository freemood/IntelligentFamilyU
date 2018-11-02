package com.Intelligent.FamilyU.model.network.bean;

import java.io.Serializable;

public class NetWorkUnbindBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public NetWorkUnbindBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
