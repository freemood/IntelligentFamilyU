package com.Intelligent.FamilyU.model.download.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.download.entity.RemoteListBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteOneTaskBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 *
 */
public class RemoteOneTaskBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/remote-download-server/remote-download/task/";

    public void remoteOneTaskQuery(String mSerialNo, String taskId, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        StringBuffer sb = new StringBuffer();
        request.put("type", "");
        request.put("taskId", taskId);
        sb.append(API_PHONE_QUERY).append(mSerialNo);
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                RemoteOneTaskBean bean = new Gson().fromJson(jsonElement, RemoteOneTaskBean.class);
                Object[] obj = new Object[1];
                obj[0] = bean;
                return obj;
            }
        });

        /**
         * 发送请求
         */
        getRequest().request(HttpRequest.Method.GET, request, lifecycle, callback);

    }

}
