package com.Intelligent.FamilyU.model.my.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.my.entity.ProblemReportBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 */
public class ProblemReportBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/message-server/app-problem-report/commit/";

    public void upProblemReport(String problemType, String description, ArrayList<String> list, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("id", String.valueOf(System.currentTimeMillis()));
        request.put("problemType", problemType);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(list);
        request.put("fileList", jsonStr);
        request.put("description", description);
        request.put(HttpRequest.API_URL, API_PHONE_QUERY);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                ProblemReportBean bean = new ProblemReportBean();
                bean.setResult(jsonElement.getAsString());
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
