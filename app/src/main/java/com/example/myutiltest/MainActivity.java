package com.example.myutiltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myutillib.myokuti.MyCallBack;
import com.example.myutillib.myokuti.NetWorkMethod;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG="MainActivity";

    private TextView tv1;
    private Button refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.tv1);
        refreshBtn=(Button)findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(this);

        final NetWorkMethod netWorkMethod = new NetWorkMethod(60, MainActivity.this);

        netWorkMethod.asynGet("http://508cst.gcu.edu.cn:8080/tentcooTools/api/v1/auth/check_network", new MyCallBack() {
            @Override
            public void onSccess(final Response response) {

                try {
                    runOnUiThread(new Runnable() {
                        String tempBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(tempBody);
                        @Override
                        public void run() {
                            try {
                                tv1.setText(jsonObject.get("status").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG,"成功");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG,"IO流出错误");
                }
            }

            @Override
            public void onFail() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv1.setText("出事啦");
                        Log.d(TAG,"失败");
                    }
                });

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refresh_btn:
                startActivity(new Intent(MainActivity.this,ExpandableListViewActivity.class));
                break;
        }
    }
}
