package com.Intelligent.FamilyU.model.scene.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.scene.entity.ScoketRestartBean;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 * 解绑
 */
public class ScoketUntieBiz extends BaseBiz {

    /**
     * 解绑
     */
    private final String BASE_URL = "/api/home-server/app-home/device-unbind";

    public void untieScoket(String serialNo, String deviceMac, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("gatewaySn", serialNo);
        request.put("deviceMac", deviceMac);
        request.put(HttpRequest.API_URL, BASE_URL);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                ScoketRestartBean bean = new ScoketRestartBean();
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
