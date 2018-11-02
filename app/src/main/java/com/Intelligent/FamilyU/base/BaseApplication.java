package com.Intelligent.FamilyU.base;

import android.app.Application;
import android.content.Context;

import com.maywide.uap.api.PayApi;

import cn.jpush.android.api.JPushInterface;


public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        PayApi.initX5Environment(this);
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    // 调用
    public static Context getContext() {
        return context;
    }
}

