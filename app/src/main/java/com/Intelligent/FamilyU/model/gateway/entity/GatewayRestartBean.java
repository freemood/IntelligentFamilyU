package com.Intelligent.FamilyU.model.gateway.entity;

import java.io.Serializable;

public class GatewayRestartBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public GatewayRestartBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
