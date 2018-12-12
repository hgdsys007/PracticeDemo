package com.lzz.studydemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lzz.studtdemo.Logger;
import com.lzz.studydemo.Bean.ClassListBean;
import com.lzz.studydemo.R;
import com.lzz.studydemo.http.HttpResult;
import com.lzz.studydemo.http.J;
import com.lzz.studydemo.http.callback.DialogCallback;
import com.lzz.studydemo.ui.base.BaseActivity;

/**
 * 网络请求Activity
 */
public class HttpActivity extends BaseActivity {

    private Button btnGet;
    private TextView tvContent;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HttpActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_http;
    }

    @Override
    protected void initView() {
        btnGet = findViewById(R.id.btn_get);
        tvContent = findViewById(R.id.tv_content);
        setViewOnClickListener(R.id.btn_get);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                httpGet();
                break;
        }
    }


    /**
     * get请求
     */
    private void httpGet() {
        J.http().get("http://sdrz-wap-api.gxk.yxlearning.com/train-api/class/query-class-list", this, null,
                new DialogCallback<HttpResult<ClassListBean>>(activity) {
                    @Override
                    public void success(HttpResult<ClassListBean> res) {
                        Logger.e(res.toString());
                        tvContent.setText(res.toString());
                    }

                });
    }

}
