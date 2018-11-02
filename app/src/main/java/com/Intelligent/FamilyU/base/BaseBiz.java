package com.Intelligent.FamilyU.base;

import com.Intelligent.FamilyU.http.retrofit.HttpRequest;

/**
 * 基础业务类
 *
 */
public class BaseBiz {


    protected HttpRequest mHttpRequest;

    public BaseBiz() {
        mHttpRequest = new HttpRequest();
    }

    protected HttpRequest getRequest() {
        if (mHttpRequest == null) {
            mHttpRequest = new HttpRequest();
        }
        return mHttpRequest;
    }


}
