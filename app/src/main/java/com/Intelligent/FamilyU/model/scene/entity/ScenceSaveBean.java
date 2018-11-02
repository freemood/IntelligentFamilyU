package com.Intelligent.FamilyU.model.scene.entity;

import java.io.Serializable;

public class ScenceSaveBean implements Serializable {

    private String result;

    public String getResult() {
        return result;
    }

    public ScenceSaveBean setResult(String result) {
        this.result = result;
        return this;
    }
}
