/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzz.studydemo.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/14
 * 描    述：对于网络请求是否需要弹出进度对话框
 * 修订历史：
 * ================================================
 */
public abstract class DialogCallback<T> extends BaseHttpCallback<T> {

    private ProgressDialog dialog;

    private void initDialog(Context context, String msg) {
        dialog = new ProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(msg);
    }

    private void initDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    public DialogCallback(Context context) {
        super(context);
        initDialog(context);
    }

    public DialogCallback(Context context, String str) {
        super(context);
        initDialog(context, str);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onSuccess(Response<T> response) {
        HttpResult httpResult = (HttpResult) response.body();
        if (httpResult.getRespCode() != HttpResult.SUCCESS) {
            if (httpResult.getRespCode() == -1) {//登录失效
                if (context != null) {
                    Toast.makeText(context, httpResult.getRespDesc(), Toast.LENGTH_SHORT).show();
                }
                //Itent就是我们要发送的内容
                //设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
                //发送单点登录广播
            } else {
                response.setException(new Throwable(httpResult.getRespDesc()));
                error(response);
            }

        } else {
            success(response.body());
        }
    }

    @Override
    public void onFinish() {
        dialog.dismiss();

        //        ColorFulApplication.getHandler().postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        //                //网络请求结束后关闭对话框,一秒后取消
        //                if (dialog != null && dialog.isShowing()) {
        //                }
        //            }
        //        }, 1000);

    }
}
