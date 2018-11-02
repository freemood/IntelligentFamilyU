package com.Intelligent.FamilyU.model.download.biz;

import android.text.TextUtils;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.download.entity.RemoteBuildTaskBean;
import com.Intelligent.FamilyU.model.download.entity.RemoteStartTaskBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 *
 */
public class RemoteStartTaskBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/remote-download-server/remote-download/start-task/";

    public void remoteStartTaskQuery(String mSerialNo, final String taskId, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(API_PHONE_QUERY).append(mSerialNo);
        request.put("taskId", taskId);
        request.put("type", "start");
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                RemoteStartTaskBean bean = new Gson().fromJson(jsonElement, RemoteStartTaskBean.class);
                if(TextUtils.isEmpty(bean.getTaskId())){
                    bean.setTaskId(taskId);
                }
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
