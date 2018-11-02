package com.Intelligent.FamilyU.http.Api;

import com.Intelligent.FamilyU.http.retrofit.HttpResponse;

import java.util.TreeMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Api接口
 */
public interface Api {


    /**
     * GET请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @GET
    Observable<HttpResponse> get(@Url String url, @QueryMap TreeMap<String, Object> request);
    /**
     * put请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @PUT
    Observable<HttpResponse> put(@Url String url, @Body TreeMap<String, Object> request);

    /**
     * POST请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @POST
    Observable<HttpResponse> post(@Url String url, @Body TreeMap<String, Object> request);

    /**
     * patch请求
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @FormUrlEncoded
    @PATCH
    Observable<HttpResponse> patch(@Url String url, @FieldMap TreeMap<String, Object> request);

    /**
     * DELETE
     *
     * @param url     api接口url
     * @param request 请求参数map
     * @return
     */
    @HTTP(method = "DELETE",hasBody = true)
    Observable<HttpResponse> delete(@Url String url, @Body TreeMap<String, Object> request);
}
