package com.lzz.studtdemo.present;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.lzz.studtdemo.StudyDemoApplication;
import com.lzz.studtdemo.utils.StatusBarUtil;
import com.lzz.studtdemo.utils.UIUtils;
import com.lzz.studtdemo.view.TranslucentActionBar;
import com.lzz.studtdemo.R;

/**
 * 顶部透明渐变
 * Created by limou on 2018/3/7 0007/下午 4:33.
 */

public class ActionBarTrans {
    private final NestedScrollView scrollView;
    String TAG = "ActionBarTrans";
    private TranslucentActionBar actionBar;
    private ImageView ivActCover;
    private Context context;

    // 这个是高斯图背景的高度
    private int imageBgHeight;

    private int slidingDistance;
    private Window window;

    /**
     * @param context
     * @param scrollView
     * @param actionBar
     * @param ivActCover
     */
    public ActionBarTrans(Context context, NestedScrollView scrollView, TranslucentActionBar actionBar, ImageView ivActCover) {
        this.context = context;
        this.ivActCover = ivActCover;
        this.actionBar = actionBar;
        this.scrollView = scrollView;
        window = ((Activity) context).getWindow();
    }

    public void initBg() {
        setImgHeaderBg();
        // toolbar 的高
        int toolbarHeight = actionBar.rlBar.getLayoutParams().height;
        Log.i(TAG, "toolbar height:" + toolbarHeight);
        final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(context);
        Log.i(TAG, "headerBgHeight:" + headerBgHeight);

        // 使背景图向上移动到图片的最低端，保留（titlebar+statusbar）的高度
        ViewGroup.LayoutParams ivParams = actionBar.vStatusBar.getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) actionBar.vStatusBar.getLayoutParams();
        int marginTop = ivParams.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);

        actionBar.vStatusBar.setImageAlpha(0);

        StatusBarUtil.setTranslucentImageHeader((Activity) context, 0, actionBar.rlBar);

        // 上移主要背景图片，使空白状态栏消失(这样下方就空了状态栏的高度)
        if (ivActCover != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) ivActCover.getLayoutParams();
            layoutParams.setMargins(0, -StatusBarUtil.getStatusBarHeight(context), 0, 0);
        }

        ViewGroup.LayoutParams imgItemBgparams = ivActCover.getLayoutParams();
        // 获得高斯图背景的高度
        imageBgHeight = imgItemBgparams.height;


        // 变色
        initScrollViewListener();

        initNewSlidingParams();
    }

    private void setImgHeaderBg() {
        // 高斯模糊背景 原来 参数：12,5  23,4
//        Glide.with(context).load(R.drawable.whitebg)
//                .into(actionBar.vStatusBar);
    }

    @SuppressLint("NewApi")
    private void initScrollViewListener() {
        // 为了兼容23以下
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollChangeHeader(scrollY);
            }
        });
    }

    private void initNewSlidingParams() {
        int titleBarAndStatusHeight = (int) (StudyDemoApplication.getStudyDemoApplication().getResources().getDimensionPixelSize(R.dimen.nav_bar_height)
                + StatusBarUtil.getStatusBarHeight(context));

        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (StudyDemoApplication.getStudyDemoApplication().getResources().getDimensionPixelSize(R.dimen.nav_bar_height_more)
        );

    }

    /**
     * 根据页面滑动距离改变Header方法
     */
    private void scrollChangeHeader(int scrolledY) {

        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);

        Drawable drawable = actionBar.vStatusBar.getDrawable();
        if (scrolledY <= slidingDistance) {
            // title部分的渐变
            if (drawable != null)
                drawable.mutate().setAlpha((int) (alpha * 255));
            actionBar.vStatusBar.setImageDrawable(drawable);
            actionBar.iconLeft.setBackground(UIUtils.getDrawable(R.mipmap.jianli_icon_fanhuibaise));
            //            actionBar.iconRight.setBackground(UIUtils.getDrawable(R.drawable.gengduobaise));
            actionBar.iconRight.setImageDrawable(context.getDrawable(R.mipmap.gengduobaise));
            actionBar.tvTitle.setTextColor(UIUtils.getColor(R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            actionBar.tvRight.setTextColor(UIUtils.getColor(R.color.white));
            actionBar.setBackgroundColor(UIUtils.getColor(R.color.transpart));
        } else {
            if (drawable != null)
                drawable.mutate().setAlpha(255);
            actionBar.vStatusBar.setImageDrawable(drawable);
            actionBar.iconLeft.setBackground(UIUtils.getDrawable(R.mipmap.gerenzhongxin_icon_fanhui));
            actionBar.tvTitle.setTextColor(UIUtils.getColor(R.color.text_black));
            //            actionBar.iconRight.setBackground(UIUtils.getDrawable(R.mipmap.gengduo_black));
            actionBar.iconRight.setImageDrawable(context.getDrawable(R.mipmap.gengduo_black));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            actionBar.tvRight.setTextColor(UIUtils.getColor(R.color.text_black));
            actionBar.setBackgroundColor(UIUtils.getColor(R.color.colorPrimary));
        }
    }
}
