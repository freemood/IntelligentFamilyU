package com.Intelligent.FamilyU.model.cloud.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.cloud.entity.CloudAddTokenBean;
import com.Intelligent.FamilyU.model.cloud.entity.CloudStorageResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 *
 */
public class CloudAddTokenBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/cloud-storage-server/cloud-storage/add-token/";

    public void addToken(String mSerialNo, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
//        JsonArray ja = new JsonArray();
//        ja.add(mSerialNo);
        request.put("serialNos", mSerialNo);
        request.put("token", Constants.APP_TOKEN);
        request.put("type", "AddToken");
        request.put("timeout", System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        request.put(HttpRequest.API_URL, API_PHONE_QUERY);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                CloudAddTokenBean bean = new CloudAddTokenBean();
                JsonArray arrayJson = jsonElement.getAsJsonArray();
                List<CloudStorageResponse> list = new ArrayList<>();
                Iterator it = arrayJson.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    CloudStorageResponse pe = new Gson().fromJson(e, CloudStorageResponse.class);
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
        getRequest().request(HttpRequest.Method.POST, request, lifecycle, callback);
    }

}
