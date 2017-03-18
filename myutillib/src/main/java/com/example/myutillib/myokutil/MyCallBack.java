package com.example.myutillib.myokutil;

import okhttp3.Response;

/**
 * Created by 区枫华 on 2017/3/18.
 */

public interface MyCallBack {

    void onSccess(Response response);
    void onFail();

}
