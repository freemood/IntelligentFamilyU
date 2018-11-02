package com.Intelligent.FamilyU.model.plugin.biz;

import com.Intelligent.FamilyU.model.plugin.bean.PluginPageEntity;
import com.Intelligent.FamilyU.model.plugin.bean.PluginListPageBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.google.gson.JsonObject;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class PluginListPageBiz extends BaseBiz {
    /**
     * 查询插件page信息
     */
    private final String BASE_URL = "/api/plugins-server/app-gateway-plugin/list";

    public void pluginListQuery(String gatewayId, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("gatewayId", gatewayId);
        request.put(HttpRequest.API_URL, BASE_URL);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray arrayJson = jsonObject.getAsJsonArray("data");
                List<PluginPageEntity.PluginGatewayInfo> bean = new ArrayList<>();
                Iterator it = arrayJson.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    PluginPageEntity.PluginGatewayInfo pe = new Gson().fromJson(e, PluginPageEntity.PluginGatewayInfo.class);
                    bean.add(pe);
                }
                Object[] obj = new Object[1];
                obj[0] = bean;
                return obj;
            }
        });

        /**
         * 发送请求
         */
        getRequest().
                request(HttpRequest.Method.GET, request, lifecycle, callback);

    }
}
