package com.Intelligent.FamilyU.model.gateway.entity;


import java.io.Serializable;

/**
 * 綁定网关实体类
 *
 */

public class GetawaryAddBean implements Serializable {


    private boolean result;

    public boolean isResult() {
        return result;
    }

    public GetawaryAddBean setResult(boolean result) {
        this.result = result;
        return this;
    }
}
