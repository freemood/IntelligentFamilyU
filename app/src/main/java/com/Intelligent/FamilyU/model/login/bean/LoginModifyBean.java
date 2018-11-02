package com.Intelligent.FamilyU.model.login.bean;

import java.io.Serializable;

public class LoginModifyBean implements Serializable {

    private boolean result;

    public boolean isResult() {
        return result;
    }

    public LoginModifyBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
