package com.lzz.studydemo.http;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * ---网络请求成功,不读取缓存 onBefore -> convertSuccess -> onSuccess -> onAfter<br>
 * ---网络请求失败,读取缓存成功 onBefore -> parseError -> onError -> onCacheSuccess -> onAfter<br>
 * ---网络请求失败,读取缓存失败 onBefore -> parseError -> onError -> onCacheError -> onAfter<br>
 */
public abstract class BaseHttpCallback<T> extends AbsCallback<T> {

    protected Context context;


    public BaseHttpCallback(Context context) {
        this.context = context;
    }

    public abstract void onSuccess(Response<T> response);

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        error(response);
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        String b = response.body().string();
        response.close();
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        T t = new Gson().fromJson(b, type);
        return t;
    }

    public abstract void success(T res);

    public void error(Response<T> response) {
        if (context != null) {
            Toast.makeText(context, response.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
        onFinish();
    }


}

