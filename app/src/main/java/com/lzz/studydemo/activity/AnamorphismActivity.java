package com.lzz.studydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.lzz.studtdemo.Logger;
import com.lzz.studydemo.ActionBarClickListener;
import com.lzz.studydemo.R;
import com.lzz.studydemo.present.ActionBarTrans;
import com.lzz.studydemo.view.TranslucentActionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * 渐变状态栏目
 */
public class AnamorphismActivity extends AppCompatActivity {


    private TranslucentActionBar actionBar;
    private NestedScrollView swipeTarget;
    private ImageView ivActCover;
    private SmartRefreshLayout refreshLayout;

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
        refreshLayout = findViewById(R.id.refreshLayout);
        initActionBar();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Logger.e("刷新数据");
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Logger.e("加载更多数据");
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

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
