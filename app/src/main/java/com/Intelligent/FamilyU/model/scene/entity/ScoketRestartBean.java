package com.Intelligent.FamilyU.model.scene.entity;

import java.io.Serializable;

public class ScoketRestartBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public ScoketRestartBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
