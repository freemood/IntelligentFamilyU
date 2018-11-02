package com.Intelligent.FamilyU.model.message.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
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
public class MessageListBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/message-server/app-message/push-log/";

    public void messageListQuery(String gatewaySn, LifecycleProvider lifecycle, HttpRxCallback callback) {
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
                MessageListBean bean = new MessageListBean();
                JsonArray arrayJson = jsonElement.getAsJsonArray();
                List<MessageBean> list = new ArrayList<>();
                Iterator it = arrayJson.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    MessageBean pe = new Gson().fromJson(e, MessageBean.class);
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
        getRequest().request(HttpRequest.Method.GET, request, lifecycle, callback);

    }

}
