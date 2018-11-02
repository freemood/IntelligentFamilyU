package com.Intelligent.FamilyU.model.gateway.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.gateway.entity.DeviceGateWayBean;
import com.Intelligent.FamilyU.model.gateway.entity.DeviceGateWayDTO;
import com.Intelligent.FamilyU.model.login.bean.LoginModifyBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * 查看网关详情
 */
public class GetawaryBaseDetailBiz extends BaseBiz {


    private final String BASE_URL = "/api/device-server/app-device/detail";

    public void queryGateway(LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();

        request.put(HttpRequest.API_URL, BASE_URL);

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                DeviceGateWayBean bean = new DeviceGateWayBean();
                JsonArray arrayJson = jsonElement.getAsJsonArray();
                List<DeviceGateWayDTO> dgwdList = new ArrayList<>();
                Iterator it = arrayJson.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    //JsonElement转换为JavaBean对象
                    DeviceGateWayDTO pe = new Gson().fromJson(e, DeviceGateWayDTO.class);
                    dgwdList.add(pe);
                }
                bean.setList(dgwdList);
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
