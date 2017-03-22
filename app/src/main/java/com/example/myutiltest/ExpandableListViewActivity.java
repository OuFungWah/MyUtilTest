package com.example.myutiltest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by 区枫华 on 2017/3/22.
 */

public class ExpandableListViewActivity extends Activity{

    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_listview_activity);

        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.image_network);

    }
}
