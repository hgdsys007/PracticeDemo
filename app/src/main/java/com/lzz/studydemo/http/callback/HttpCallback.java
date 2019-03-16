package com.lzz.studydemo.http.callback;

import android.content.Context;
import android.widget.Toast;

import com.lzz.studydemo.Logger;
import com.lzy.okgo.model.Response;
import com.lzz.studydemo.http.HttpResult;

/**
 * TODO:
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * on 2017/11/10 .
 */
public abstract class HttpCallback<T> extends BaseHttpCallback<T> {
    public HttpCallback(Context context) {
        super(context);
    }


    @Override
    public abstract void success(T res);

    @Override
    public void onSuccess(Response<T> response) {
        HttpResult httpResult = (HttpResult) response.body();
        if (httpResult != null) {
            if (httpResult.getRespCode() != HttpResult.SUCCESS) {
                if (httpResult.getRespCode() == -1) {//登录失效
                    if (context != null) {
                        Toast.makeText(context, httpResult.getRespDesc(), Toast.LENGTH_SHORT).show();
                    }
                    //Itent就是我们要发送的内容
                    // 设置你这个广播的action，只有和这个action一样的接受者才能接受者才能接收广播
                    //发送广播
                } else {
                    response.setException(new Throwable(httpResult.getRespDesc()));
                    error(response);
                }

            } else {
                success(response.body());
            }
        } else {
            error(response);
        }

    }

    @Override
    public void onCacheSuccess(Response<T> response) {
        Logger.e("缓存成功");
        onSuccess(response);
    }

    @Override
    public void error(Response<T> response) {
        if (context != null) {
            Toast.makeText(context, response.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
        onFinish();
    }
}
