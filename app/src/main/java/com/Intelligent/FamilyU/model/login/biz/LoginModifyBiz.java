package com.Intelligent.FamilyU.model.login.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.login.bean.LoginModifyBean;
import com.Intelligent.FamilyU.model.login.bean.UserResetPwdInfo;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

public class LoginModifyBiz extends BaseBiz {
    /**
     * 重置密码信息
     */
    private final String API_LOGIN = "/api/user-server/app/reset-password";

    public void startLoginModifyParm(UserResetPwdInfo userResetPwdInfo, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("code", userResetPwdInfo.getCode());
        request.put("phone", userResetPwdInfo.getPhone());
        request.put("secInputPassword", userResetPwdInfo.getSecInputPassword());
        request.put("token", userResetPwdInfo.getToken());
        request.put("username", userResetPwdInfo.getUsername());
        request.put("newPassword", userResetPwdInfo.getNewPassword());
        request.put(HttpRequest.API_URL, API_LOGIN);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                LoginModifyBean bean = new LoginModifyBean();
                bean.setResult(jsonElement.getAsBoolean());
                Object[] obj = new Object[1];
                obj[0] = bean;
                return obj;
            }
        });

        /**
         * 发送请求
         */
        getRequest().request(HttpRequest.Method.POST, request, lifecycle, callback);
    }
}
