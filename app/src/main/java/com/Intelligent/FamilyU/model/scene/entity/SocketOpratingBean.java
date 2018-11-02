package com.Intelligent.FamilyU.model.scene.entity;

import java.io.Serializable;

public class SocketOpratingBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public SocketOpratingBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
