package com.Intelligent.FamilyU.model.download.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.download.entity.RemoteBuildTaskBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteDeleteTaskBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 *
 */
public class RemoteDeleteTaskBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/remote-download-server/remote-download/delete-task/";

    public void remoteDeleteTaskQuery(String mSerialNo, String taskId, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(API_PHONE_QUERY).append(mSerialNo);
        request.put("deleteFile", false);
        request.put("taskId", taskId);
        request.put("type", "deleteTask");
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                RemoteDeleteTaskBean bean = new Gson().fromJson(jsonElement, RemoteDeleteTaskBean.class);
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
