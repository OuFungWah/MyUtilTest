package com.example.myutiltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myutillib.myokutil.MyCallBack;
import com.example.myutillib.myokutil.NetWorkMethod;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=(TextView)findViewById(R.id.tv1);

        NetWorkMethod netWorkMethod = new NetWorkMethod(60,MainActivity.this);

        netWorkMethod.asynGet("http://blog.csdn.net/withiter/article/details/19908679", new MyCallBack() {
            @Override
            public void onSccess(final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText(response.toString());
                    }
                });
            }

            @Override
            public void onFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("出事啦");
                    }
                });
            }
        });
    }
}
