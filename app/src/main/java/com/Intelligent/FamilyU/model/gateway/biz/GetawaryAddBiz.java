package com.Intelligent.FamilyU.model.gateway.biz;

import android.text.TextUtils;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.gateway.entity.GetawaryAddBean;
import com.Intelligent.FamilyU.model.login.bean.LoginModifyBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 * 綁定网关
 */
public class GetawaryAddBiz extends BaseBiz {


    private final String API_PHONE_QUERY = "/api/device-server/app-device/bind-gateway";

    public void bindGateway(String serialNo, String userId, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("serialNo", serialNo);
        if (!TextUtils.isEmpty(userId)) {
            request.put("userId", userId);
        }
        request.put(HttpRequest.API_URL, API_PHONE_QUERY);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                GetawaryAddBean bean = new GetawaryAddBean();
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
