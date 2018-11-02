package com.Intelligent.FamilyU.model.message.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.message.entity.MessageAllDeleteBean;
import com.Intelligent.FamilyU.model.message.entity.MessageAllReadBean;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.TreeMap;

/**
 *
 */
public class MessageAllDeleteBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/message-server/app-message/clear-message/";

    public void allDeleteMessageList(String gatewaySn, LifecycleProvider lifecycle, HttpRxCallback callback) {
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
                MessageAllDeleteBean bean = new MessageAllDeleteBean();
                bean.setResult(jsonElement.getAsInt());
                Object[] obj = new Object[1];
                obj[0] = bean;
                return obj;
            }
        });

        /**
         * 发送请求
         */
        getRequest().request(HttpRequest.Method.DELETE, request, lifecycle, callback);

    }

}
