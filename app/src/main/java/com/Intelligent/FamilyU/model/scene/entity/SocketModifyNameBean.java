package com.Intelligent.FamilyU.model.scene.entity;

import java.io.Serializable;

public class SocketModifyNameBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public SocketModifyNameBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
