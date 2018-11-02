package com.Intelligent.FamilyU.model.my.upload;

import com.Intelligent.FamilyU.base.Constants;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 数据请求控制工具类
 */
public class ApiUtil {

    private static Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT = 10;//超时时长，单位：秒

    /**
     * 初始化 Retrofit
     */
    private static Retrofit getApiRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.addHeader("app-token", Constants.APP_TOKEN);
                    builder.addHeader("iot-user", Constants.APP_TOKEN);
                    return chain.proceed(builder.build());
                }
            });
            retrofit = new Retrofit.Builder()
                    .client(okHttpBuilder.build())
                    .baseUrl(Constants.BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 创建数据请求服务
     */
    private static ApiService getApiService() {
        return ApiUtil.getApiRetrofit().create(ApiService.class);
    }

    /**
     * 上传
     */
    public static Call<Result<String>> uploadMemberIcon(String path) {
//        Gson gson = new Gson();
//        JsonObject jObj = new JsonObject();
//
//        jObj.addProperty("id", String.valueOf(System.currentTimeMillis()));
//        jObj.addProperty("problemType", problemType);
//        jObj.addProperty("description", description);
        //构建要上传的文件
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        return ApiUtil.getApiService().uploadOneMemberIcon(body);

    }


}
