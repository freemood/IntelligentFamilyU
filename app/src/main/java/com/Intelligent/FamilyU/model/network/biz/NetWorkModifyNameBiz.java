package com.Intelligent.FamilyU.model.network.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayModifyNameBean;
import com.Intelligent.FamilyU.model.network.bean.NetWorkModifyNameBean;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 */
public class NetWorkModifyNameBiz extends BaseBiz {

    /**
     */
    private final String BASE_URL = "/api/networking-server/app-networking/update-info";

    public void modifyNameNetWork(String networkingSn,String name, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("networkingSn", networkingSn);
        request.put("newGatewayName", name);
        request.put(HttpRequest.API_URL, BASE_URL);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                NetWorkModifyNameBean bean = new NetWorkModifyNameBean();
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
