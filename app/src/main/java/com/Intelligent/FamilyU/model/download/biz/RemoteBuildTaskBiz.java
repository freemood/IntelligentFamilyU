package com.Intelligent.FamilyU.model.download.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.download.entity.RemoteBuildTaskBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 *
 */
public class RemoteBuildTaskBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/remote-download-server/remote-download/build-task/";

    public void remoteBuildTaskQuery(String mSerialNo, String fileName, String store, String url, String delayTime, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(API_PHONE_QUERY).append(mSerialNo);
        request.put("fileName", fileName);
        request.put("store", store);
        request.put("type", "build");
        request.put("url", url);
        request.put("delay ", delayTime);
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                RemoteBuildTaskBean bean = new Gson().fromJson(jsonElement, RemoteBuildTaskBean.class);
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
