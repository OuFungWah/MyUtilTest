package com.example.myutillib.myokutil;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 获取单一的okhttp对象
 * Created by 区枫华 on 2017/3/18.
 */

public class NetWorkContext {

    private static NetWorkContext netWorkContext = null;
    private static OkHttpClient okHttpClient;
    private Context context;

    /**
     * 创建一个okhttp对象
     */
    private NetWorkContext(Context context) {
        okHttpClient = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).build();
        this.context=context;
    }

    /**
     * @param time
     */
    private NetWorkContext(Context context, int time) {
        okHttpClient = new OkHttpClient.Builder().readTimeout(time, TimeUnit.SECONDS).build();
        this.context=context;
    }

    /**
     * @param context
     * @return
     */
    static public NetWorkContext build(Context context) {
        if (netWorkContext == null) {
            netWorkContext = new NetWorkContext(context);
        }
        return netWorkContext;
    }

    /**
     * @param context 上下文文件
     * @param time    超时时限
     * @return
     */
    static public NetWorkContext build(Context context, int time) {
        if (netWorkContext == null) {
            netWorkContext = new NetWorkContext(context, time);
        }
        return netWorkContext;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient != null)
            return okHttpClient;
        else
            return null;
    }
}
