package com.Intelligent.FamilyU.model.gateway.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayRestartBean;
import com.Intelligent.FamilyU.model.gateway.entity.GatewayUntieBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**解绑
 */
public class GatewayUntieBiz extends BaseBiz {

    /**解绑
     */
    private final String BASE_URL = "/api/device-server/app-device/un-bind-gateway";

    public void untieGateway(String serialNo, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("serialNo", serialNo);
        request.put(HttpRequest.API_URL, BASE_URL);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                GatewayUntieBean bean = new Gson().fromJson(jsonElement,GatewayUntieBean.class);
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
