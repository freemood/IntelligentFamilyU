package com.Intelligent.FamilyU.model.home.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.home.entity.CpuAndMemoryBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 * cpu内存
 */
public class CpuAndMemoryBiz extends BaseBiz {

    /**
     * cpu内存
     */
    private final String BASE_URL = "/api/device-server/app-device/really-status/";

    public void queryCpuAndMemory(String serialNo, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(BASE_URL).append(serialNo);
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                CpuAndMemoryBean bean = new Gson().fromJson(jsonElement, CpuAndMemoryBean.class);
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
