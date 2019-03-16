package com.lzz.studydemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lzz.studydemo.Logger;
import com.lzz.studydemo.R;
import com.lzz.studydemo.manager.GlideManager;
import com.lzz.studydemo.manager.SingleInstance;
import com.lzz.studydemo.ui.base.BaseActivity;
import com.lzz.studydemo.utils.DialogHelper;

/**
 * 单例例子
 */
public class SingleInstanceActivity extends BaseActivity {

    private ImageView ivImg;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SingleInstanceActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_instance;
    }

    @Override
    protected void initView() {

        ivImg = findViewById(R.id.iv_img);

        setViewOnClickListener(R.id.btn_loadImage);
        setViewOnClickListener(R.id.btn_thread);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_loadImage:
                GlideManager.getInstance().load(activity, null, null);
                break;
            case R.id.btn_thread:
                SingleInstance.INSTANCE.execute(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Logger.e("线程任务");
                                DialogHelper.getConfirmDialog(activity, "我是标题", "正在加载").show();

                            }
                        });
                    }
                });
                break;
        }
    }
}
