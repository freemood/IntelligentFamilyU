package com.Intelligent.FamilyU.model.message.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.message.entity.MessageAllReadBean;
import com.Intelligent.FamilyU.model.message.entity.MessageBean;
import com.Intelligent.FamilyU.model.message.entity.MessageListBean;
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
public class MessageAllReadBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/message-server/app-message/read-all-message/";

    public void allReadMessageList(String gatewaySn, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(API_PHONE_QUERY).append(gatewaySn);
        request.put(HttpRequest.API_URL, stringBuffer.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                MessageAllReadBean bean = new MessageAllReadBean();
                bean.setResult(jsonElement.getAsInt());
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
