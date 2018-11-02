package com.Intelligent.FamilyU.model.scene.entity;

import java.io.Serializable;

public class ScenceUnbindBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public ScenceUnbindBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
