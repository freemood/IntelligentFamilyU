package com.Intelligent.FamilyU.model.scene.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.network.bean.NetWorkUnbindBean;
import com.Intelligent.FamilyU.model.scene.entity.ScenceUnbindBean;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 */
public class ScenceUnbineBiz extends BaseBiz {

    /**
     */
    private final String BASE_URL = "/api/home-server/app-home/save-blacklist/";

    public void unbindScence(String mac, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(BASE_URL).append(mac);
        request.put("mac", mac);
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                ScenceUnbindBean bean = new ScenceUnbindBean();
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
