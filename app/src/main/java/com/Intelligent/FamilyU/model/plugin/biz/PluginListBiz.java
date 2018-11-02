package com.Intelligent.FamilyU.model.plugin.biz;

import com.Intelligent.FamilyU.model.plugin.bean.PluginEntity;
import com.Intelligent.FamilyU.model.plugin.bean.PluginListBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class PluginListBiz extends BaseBiz {
    /**
     * 查询所有插件版本信息
     */
    private final String API_LOGIN = "/api/plugins-server/plugin/list";

    public void pluginListQuery(LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
//        request.put("app-token", appToken);
        request.put(HttpRequest.API_URL, API_LOGIN);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                PluginListBean bean = new PluginListBean();
                JsonArray arrayJson = jsonElement.getAsJsonArray();
                List<PluginEntity> list = new ArrayList<>();
                Iterator it = arrayJson.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    PluginEntity pe = new Gson().fromJson(e, PluginEntity.class);
                    list.add(pe);
                }
                bean.setList(list);
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
