package com.Intelligent.FamilyU.model.login.bean;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LoginBean implements Serializable {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public LoginBean setToken(String token) {
        this.token = token;
        return this;
    }
}
