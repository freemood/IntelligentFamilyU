package com.Intelligent.FamilyU.model.my.upload;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 数据请求服务
 */
public interface ApiService {
    @Multipart
    @POST("/api/message-server/app-problem-report/commit/")
    Call<Result<String>> uploadMemberIcon(@Part("json") RequestBody jsonBody, @Part List<MultipartBody.Part> partList);

    @Multipart
    @POST("/api/file-server/file/upload-static/")
    Call<Result<String>> uploadOneMemberIcon(@Part MultipartBody.Part file);


    @Multipart
    @POST("/api/file-server/file/upload-static/")
    Call<Result<String>> downLoadCloud(@Part("token") String token, @Part MultipartBody.Part file);
}
