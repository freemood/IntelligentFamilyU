package com.Intelligent.FamilyU.model.login.bean;

import java.io.Serializable;

public class UserResetPwdInfo implements Serializable {
    //验证码
    private String code;
    //(string, optional): 第二次输入密码 , ,
    private String secInputPassword;
    //(string, optional): 新密码 ,
    private String newPassword;
    // (string, optional): 手机号码
    private String phone;
    //(string, optional): 令牌 ,
    private String token;
    //(string, optional): 帐号
    private String username;

    public String getCode() {
        return code;
    }

    public UserResetPwdInfo setCode(String code) {
        this.code = code;
        return this;
    }

    public String getSecInputPassword() {
        return secInputPassword;
    }

    public UserResetPwdInfo setSecInputPassword(String secInputPassword) {
        this.secInputPassword = secInputPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserResetPwdInfo setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserResetPwdInfo setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserResetPwdInfo setToken(String token) {
        this.token = token;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserResetPwdInfo setUsername(String username) {
        this.username = username;
        return this;
    }
}
