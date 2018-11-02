package com.Intelligent.FamilyU.model.login.bean;

import java.io.Serializable;


public class UserRegisterInfo implements Serializable {
    //验证码
    private String code;
    //(string, optional): 姓名 ,
    private String name;
    //(string, optional): 密码 ,
    private String password;
    // (string, optional): 手机号码
    private String phone;
    //(string, optional): 令牌 ,
    private String token;
    //(string, optional): 帐号
    private String username;

    public String getCode() {
        return code;
    }

    public UserRegisterInfo setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserRegisterInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserRegisterInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserRegisterInfo setToken(String token) {
        this.token = token;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterInfo setUsername(String username) {
        this.username = username;
        return this;
    }
}
