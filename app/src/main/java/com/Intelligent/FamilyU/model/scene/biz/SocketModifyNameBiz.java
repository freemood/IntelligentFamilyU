package com.Intelligent.FamilyU.model.scene.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayModifyNameBean;
import com.Intelligent.FamilyU.model.scene.entity.SocketModifyNameBean;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.TreeMap;

/**
 */
public class SocketModifyNameBiz extends BaseBiz {

    /**
     */
    private final String BASE_URL = "/api/home-server/app-home/device/update-name/";

    public void modifyNameSocket(String deviceId, String name, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("deviceId", deviceId);
        request.put("newName", name);
        request.put(HttpRequest.API_URL, BASE_URL);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                SocketModifyNameBean bean = new SocketModifyNameBean();
                bean.setResult(jsonElement.getAsBoolean());
                Object[] obj = new Object[1];
                obj[0] = bean;
                return obj;
            }
        });

        /**
         * 发送请求
         */
        getRequest().request(HttpRequest.Method.PATCH, request, lifecycle, callback);

    }

}
