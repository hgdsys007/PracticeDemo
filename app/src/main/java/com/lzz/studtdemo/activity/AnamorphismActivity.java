package com.lzz.studtdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.lzz.studtdemo.ActionBarClickListener;
import com.lzz.studtdemo.R;
import com.lzz.studtdemo.present.ActionBarTrans;
import com.lzz.studtdemo.view.TranslucentActionBar;

/**
 * 渐变状态栏目
 */
public class AnamorphismActivity extends AppCompatActivity {


    private TranslucentActionBar actionBar;
    private NestedScrollView swipeTarget;
    private ImageView ivActCover;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AnamorphismActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anamorphis);
        actionBar = findViewById(R.id.actionbar);
        swipeTarget = findViewById(R.id.swipe_target);
        ivActCover = findViewById(R.id.iv_act_cover);
        initActionBar();
    }

    private void initActionBar() {
        //初始actionBar
        actionBar.setData("渐变", R.mipmap.jianli_icon_fanhuibaise, null, 0, null, new ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });

        ActionBarTrans actionBarTrans = new ActionBarTrans(this, swipeTarget, actionBar, ivActCover);
        actionBarTrans.initBg();
    }
}
