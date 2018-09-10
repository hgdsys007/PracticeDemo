package com.lzz.studydemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lzz.studtdemo.Logger;
import com.lzz.studydemo.Bean.StickyBean;
import com.lzz.studydemo.R;
import com.lzz.studydemo.ui.adapter.StickyExampleAdapter;
import com.lzz.studydemo.ui.view.TranslucentActionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 悬浮吸顶，加刷新和加载
 */
public class StickyHeaderActivity extends AppCompatActivity {
    /**
     * 用于动态替换的吸顶View
     */
    private TextView tvStickyHeaderView;
    private RecyclerView recyclerView;
    private TranslucentActionBar actionBar;

    private ImageView ivActCover;
    private SmartRefreshLayout refreshLayout;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, StickyHeaderActivity.class);
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sticky_head);
        initView();
        initListener();
    }

    /**
     * 初始化View
     */
    private void initView() {


        refreshLayout = findViewById(R.id.refreshLayout);



        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        tvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StickyExampleAdapter(this, getData()));
    }
    /**
     * 初始化Listener
     */
    private void initListener() {

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



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View stickview = recyclerView.findChildViewUnder(0, 0);
                if (stickview != null && stickview.getContentDescription() != null) {
                    if (!TextUtils.equals(tvStickyHeaderView.getText(), stickview.getContentDescription())) {

                        tvStickyHeaderView.setText(stickview.getContentDescription());
                    }
                }
                //获取到下一个View
                View transInfoView = recyclerView.findChildViewUnder(
                        0, tvStickyHeaderView.getHeight() + 1);
//                Logger.e("顶部View高是"+tvStickyHeaderView.getHeight());
                if (transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int top = transInfoView.getTop();
                    if (transViewStatus == StickyExampleAdapter.HAS_STICKY_VIEW) {
                        Logger.e("下一个需要吸顶View的Top"+transInfoView.getTop());

                        if (top > 0) {
                            int dealtY = top - tvStickyHeaderView.getMeasuredHeight();
                            Logger.e("顶部tvStickyHeaderView向上平移的View"+dealtY);
                            tvStickyHeaderView.setTranslationY(dealtY);
                        } else {
                            tvStickyHeaderView.setTranslationY(0);
                        }
                    } else if (transViewStatus == StickyExampleAdapter.NONE_STICKY_VIEW) {
                        tvStickyHeaderView.setTranslationY(0);
                    }
                }
            }
        });
    }

    public List<StickyBean> getData() {
        List<StickyBean> stickyExampleModels = new ArrayList<>();

        for (int index = 0; index < 100; index++) {
            if (index < 15) {
                stickyExampleModels.add(new StickyBean(
                        "吸顶文本1", "name" + index, "gender" + index));
            } else if (index < 25) {
                stickyExampleModels.add(new StickyBean(
                        "吸顶文本2", "name" + index, "gender" + index));
            } else if (index < 35) {
                stickyExampleModels.add(new StickyBean(
                        "吸顶文本3", "name" + index, "gender" + index));
            } else {
                stickyExampleModels.add(new StickyBean(
                        "吸顶文本4", "name" + index, "gender" + index));
            }
        }
        return stickyExampleModels;
    }
}
