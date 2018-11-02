package com.Intelligent.FamilyU.http.function;

import com.google.gson.Gson;
import com.Intelligent.FamilyU.http.exception.ServerException;
import com.Intelligent.FamilyU.http.retrofit.HttpResponse;
import com.Intelligent.FamilyU.utils.LogUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 服务器结果处理函数
 *
 */
public class ServerResultFunction implements Function<HttpResponse, Object> {
    @Override
    public Object apply(@NonNull HttpResponse response) throws Exception {
        //打印服务器回传结果
        LogUtils.e("HttpResponse:" + response.toString());
        if (!response.isSuccess()) {
            int code = response.getCode();
            String data = response.getMsg();
            if (code == 1001) { //系统公告（示例）
                data = new Gson().toJson(response.getResult());
            } else if (code == 1002) {//token失效（示例）
                data = new Gson().toJson(response.getResult());
            }
            throw new ServerException(response.getCode(), data);//抛出服务器错误
        }
        return new Gson().toJson(response.getResult());
    }
}