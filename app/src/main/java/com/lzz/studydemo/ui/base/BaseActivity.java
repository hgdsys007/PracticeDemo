package com.lzz.studydemo.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Activity activity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());

        }
        activity=this;
        init(savedInstanceState);
        initView();
        initData();
    }

    protected void initData() {

    }

    protected void initView() {


    }

    protected void init(Bundle savedInstanceState) {

    }

    protected abstract int getLayoutId();

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    /**
     * 设置点击监听
     * @param viewId
     */
    protected void setViewOnClickListener(int viewId){
        findViewById(viewId).setOnClickListener(this);
    }
}
