package com.Intelligent.FamilyU.model.gateway.entity;

import java.io.Serializable;

public class GatewayModifyNameBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public GatewayModifyNameBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
