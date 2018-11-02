package com.Intelligent.FamilyU.model.scene.entity;

import java.io.Serializable;

public class ScenceCustomDeleteBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public ScenceCustomDeleteBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
