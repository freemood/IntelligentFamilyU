package com.Intelligent.FamilyU.model.scene.biz;

import com.Intelligent.FamilyU.base.BaseBiz;
import com.Intelligent.FamilyU.http.helper.ParseHelper;
import com.Intelligent.FamilyU.http.observer.HttpRxCallback;
import com.Intelligent.FamilyU.http.retrofit.HttpRequest;
import com.Intelligent.FamilyU.model.scene.entity.ScenceSaveBean;
import com.Intelligent.FamilyU.model.scene.entity.SceneExecCondition;
import com.Intelligent.FamilyU.model.scene.entity.SceneOperation;
import com.Intelligent.FamilyU.model.scene.entity.ScoketRestartBean;
import com.google.gson.JsonElement;
import com.trello.rxlifecycle2.LifecycleProvider;

import org.json.JSONArray;

import java.util.List;
import java.util.TreeMap;

import okhttp3.RequestBody;

/**
 * 解绑
 */
public class ScenceSaveBiz extends BaseBiz {

    /**
     * 解绑
     */
    private final String BASE_URL = "/api/home-server/app-home-scene/scene-save/";

    public void scenceSave(String serialNo, String id, String sceneName, List<SceneOperation> operations, List<SceneExecCondition> conditions, String sceneType, LifecycleProvider lifecycle, HttpRxCallback callback) {
        /**
         * 构建参数
         */
        TreeMap<String, Object> request = new TreeMap<>();
        StringBuffer sb = new StringBuffer();
        sb.append(BASE_URL).append(serialNo);
        request.put("execType", "默认手动");
        request.put("id", id);
        request.put("sceneName", sceneName);
        request.put("gatewaySn", serialNo);
        request.put("sceneType",sceneType);
        request.put("status", "1");
        request.put("operations", operations);
        request.put("conditions", conditions);
        request.put(HttpRequest.API_URL, sb.toString());

        /**
         * 解析数据
         */
        callback.setParseHelper(new ParseHelper() {
            @Override
            public Object[] parse(JsonElement jsonElement) {
                ScenceSaveBean bean = new ScenceSaveBean();
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
