package com.Intelligent.FamilyU.model.cloud.upload;

import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 数据请求服务
 */
public interface ApiService {

    @POST
    @Multipart
    Call<Result<String>> upLoadCloud(@Url String fileUrl, @Part MultipartBody.Part file,@PartMap Map<String, RequestBody> data);

    @GET
    Call<ResponseBody> downLoadCloud(@Url String fileUrl, @QueryMap TreeMap<String, Object> request);
}
