package com.example.myutillib.myokutil;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装了OkHttp联网的方法
 *先new一个NetWorkMethod对象，然后再调用相应的方法进行网络操作:
 *
 * NetWorkMethod netWorkMethod= new NetWorkMethod(60,MainActivity.this);
 *
 * Created by 区枫华 on 2017/3/18.
 */

public class NetWorkMethod {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charser=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType MEDIA_TYPE_XML = MediaType.parse("application/xml; charset=utf-8");
    public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
    public static final MediaType MEDIA_TYPE_GIF = MediaType.parse("image/gif");
    public static final MediaType MEDIA_TYPE_MP4 = MediaType.parse("video/mp4");

    private static String TAG = "NetWorkMethod";

    private Context context;
    private static NetWorkContext netWorkContext;
    private static OkHttpClient client;

    /**
     *默认超时时间为30
     * @param context 当前调用时上下文资源对象
     */
    public NetWorkMethod(Context context) {
        this.context = context;
        netWorkContext = NetWorkContext.build(context);
        this.client = netWorkContext.getOkHttpClient();
    }

    /**
     *
     * @param time 选择超时的时限
     * @param context 当前调用时上下文资源对象
     */
    public NetWorkMethod(int time,Context context) {
        this.context = context;
        netWorkContext = NetWorkContext.build(context,time);
        this.client = netWorkContext.getOkHttpClient();
    }

    /**
     * 同步Get请求
     * 需要注意的是，4.0以后不允许在主线程中进行网络请求，
     * 以免网络请求造成主线程假死，所以需要在另外自己新设
     * 的子线程中进行
     * @param url
     * @param callBack
     */
    public void synGet(String url, MyCallBack callBack) {
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                callBack.onSccess(response);
            } else {
                callBack.onFail();
            }
        } catch (IOException e) {
            Log.d(TAG, "异常:" + e.toString());
        }
    }

    /**
     * 异步Get请求
     *
     * @param url
     * @param callBack
     */
    public void asynGet(String url, final MyCallBack callBack) {
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "异常:" + e.toString());
                callBack.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onSccess(response);
            }
        });
    }

    /**
     * 自定义请求的同步请求
     * 新建Request例如:Request request = new Request.Builder()
     * .url("https://api.github.com/repos/square/okhttp/issues")
     * .header("User-Agent", "OkHttp Headers.java")
     * .addHeader("Accept", "application/json; q=0.5")
     * .addHeader("Accept", "application/vnd.github.v3+json")
     * .build();
     ** 需要注意的是，4.0以后不允许在主线程中进行网络请求，
     * 以免网络请求造成主线程假死，所以需要在另外自己新设
     * 的子线程中进行
     * @param request  自定义请求
     * @param callBack
     */
    public void synRequest(Request request, MyCallBack callBack) {

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                callBack.onSccess(response);
            } else {
                callBack.onFail();
            }
        } catch (IOException e) {
            Log.d(TAG, "异常:" + e.toString());
        }
    }

    /**
     * 自定义请求的异步请求
     * 新建Request例如:Request request = new Request.Builder()
     * .url("https://api.github.com/repos/square/okhttp/issues")
     * .header("User-Agent", "OkHttp Headers.java")
     * .addHeader("Accept", "application/json; q=0.5")
     * .addHeader("Accept", "application/vnd.github.v3+json")
     * .build();
     *
     * @param request  自定义的请求
     * @param callBack
     */
    public void asynRequest(Request request, final MyCallBack callBack) {

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onSccess(response);
            }
        });
    }

    /**
     * 同步Post請求
     * * 需要注意的是，4.0以后不允许在主线程中进行网络请求，
     * 以免网络请求造成主线程假死，所以需要在另外自己新设
     * 的子线程中进行
     *
     * @param requestBody 请求体
     * @param url 链接
     * @param callBack 回调接口
     */
    public void synPost(RequestBody requestBody, String url, MyCallBack callBack) {
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()){
                callBack.onSccess(response);
            }
            else{
                callBack.onFail();
            }
        } catch (IOException e) {
            Log.d(TAG, "同步POST异常:" + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 异步POST请求
     * @param requestBody 请求体
     * @param url 链接
     * @param callBack 回调接口
     */
    public void asynPost(RequestBody requestBody, String url, final MyCallBack callBack){
        Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFail();
                Log.d(TAG, "异步POST异常:" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onSccess(response);
            }
        });
    }

}
