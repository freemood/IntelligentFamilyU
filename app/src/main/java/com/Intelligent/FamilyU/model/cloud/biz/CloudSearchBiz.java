package com.Intelligent.FamilyU.model.cloud.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.cloud.entity.CloudSearchFileListBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 *
 */
public class CloudSearchBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/cloud-storage-server/cloud-storage/file-list/";

    public void cloudDownSearch(String mSerialNo, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("source", "/");
        request.put("type", "list");
        request.put("listType", 3);
        StringBuffer sb = new StringBuffer();
        sb.append(API_PHONE_QUERY).append(mSerialNo);
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                CloudSearchFileListBean bean = new Gson().fromJson(jsonElement, CloudSearchFileListBean.class);
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
