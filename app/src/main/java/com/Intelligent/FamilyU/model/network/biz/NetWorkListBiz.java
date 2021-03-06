package com.Intelligent.FamilyU.model.network.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.network.bean.DeviceEntity;
import com.Intelligent.FamilyU.model.network.bean.NetWorkListBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class NetWorkListBiz extends BaseBiz {
    /**
     * 查询组网
     */
    private final String API_LOGIN = "/api/networking-server/app-networking/gateway/";

    public void netWorkListQuery(String gatewaySn,LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
//        request.put("app-token", appToken);
        StringBuffer sb = new StringBuffer();
        sb.append(API_LOGIN).append(gatewaySn);
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                NetWorkListBean bean = new NetWorkListBean();
                JsonArray arrayJson = jsonElement.getAsJsonArray();
                List<DeviceEntity> list = new ArrayList<>();
                Iterator it = arrayJson.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    DeviceEntity pe = new Gson().fromJson(e, DeviceEntity.class);
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
        getRequest().
                request(HttpRequest.Method.GET, request, lifecycle, callback);

    }
}
