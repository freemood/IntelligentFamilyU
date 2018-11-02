package com.Intelligent.FamilyU.model.cloud.upload;

import android.util.Log;
import android.widget.TextView;

import com.Intelligent.FamilyU.base.Constants;
import com.Intelligent.FamilyU.model.cloud.entity.CloudFileBean;
import com.Intelligent.FamilyU.model.cloud.iface.IDownLoadCloud;
import com.Intelligent.FamilyU.model.cloud.iface.IUpLoadCloud;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * 数据请求控制工具类
 */
public class ApiUtil {

    private static Retrofit retrofit;
    private final static int DEFAULT_TIMEOUT = 10;//超时时长，单位：秒
    //192.168.1.202 云存储IP10.50.10.82"
    public final static String CLOUD_IP = "192.168.1.202";
    public final static String CLOUD_LOAD_IP = "http://" + CLOUD_IP + ":8080/";

    /**
     * 初始化 Retrofit
     */
    private static Retrofit getApiRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            okHttpBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.addHeader("token", Constants.APP_TOKEN);
                    return chain.proceed(builder.build());
                }
            });
            retrofit = new Retrofit.Builder()
                    .client(okHttpBuilder.build())
                    .baseUrl(CLOUD_LOAD_IP)
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
    public static Call<Result<String>> uploadCloud(TextView tv, CloudFileBean mCloudFileBean, IUpLoadCloud iUpLoadCloud) {
        StringBuffer targetSb = new StringBuffer();
        if (null != Constants.CLOUD_ALL_FILES_DIR && 0 != Constants.CLOUD_ALL_FILES_DIR.size()) {
            String path = Constants.CLOUD_ALL_FILES_DIR.get(0).getPath().replace("\\", "");
            targetSb.append(path);
        }
        targetSb.append("/").append(mCloudFileBean.getFileName());
        String target = targetSb.toString();
        String path = mCloudFileBean.getSdcardFilePath();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), target);
        Map<String, RequestBody> request = new HashMap<>();
        request.put("target", requestBody);

        //构建要上传的文件
        File file = new File(path.replaceAll("file:///", ""));
        UploadFileRequestBody requestFile = new UploadFileRequestBody(file, iUpLoadCloud, tv, mCloudFileBean);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return ApiUtil.getApiService().upLoadCloud("/store/upload/", body, request);
    }


    /**
     * 下载
     */
    public static void downLoadCloud(CloudFileBean mCloudFileBean, final int position, final TextView tv, final IDownLoadCloud iDownLoadCloud) {
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("source", mCloudFileBean.getFileDownPath().replace("//", "/"));
        Response<ResponseBody> resout;
        ApiUtil.getApiService().downLoadCloud("/store/download/", request).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    iDownLoadCloud.onSuccessful(response, position, tv);
                } else {
                    iDownLoadCloud.onDownLoadFailful();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                iDownLoadCloud.onFailure();
            }
        });
    }

}
