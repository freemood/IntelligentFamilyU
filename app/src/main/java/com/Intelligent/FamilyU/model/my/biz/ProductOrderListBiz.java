package com.Intelligent.FamilyU.model.my.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.my.entity.ProblemReportBean;
import com.Intelligent.FamilyU.model.my.entity.ProductOrderEntity;
import com.Intelligent.FamilyU.model.my.entity.ProductOrderListBean;
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
public class ProductOrderListBiz extends BaseBiz {

    /**
     */
    private final String API_PHONE_QUERY = "/api/plugins-server/app-plugin-product/order-list/";

    public void productOrderList(String serialNo, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(API_PHONE_QUERY).append(serialNo);
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                ProductOrderListBean bean = new ProductOrderListBean();
                JsonArray arrayJson = jsonElement.getAsJsonArray();
                List<ProductOrderEntity> list = new ArrayList<>();
                Iterator it = arrayJson.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    ProductOrderEntity pe = new Gson().fromJson(e, ProductOrderEntity.class);
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
