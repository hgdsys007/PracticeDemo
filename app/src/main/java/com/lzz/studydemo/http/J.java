package com.lzz.studydemo.http;

import android.app.Application;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

/**
 * 网络请求在Application初始化
 */
public class J {

    public static void initHttp(Application application, HttpHeaders headers, HttpParams params) {
        HttpManager.init(application);
        if (null != headers) {
            HttpManager.getInstance().addCommonHeaders(headers);
        }
        if (null != params) {
            HttpManager.getInstance().addCommonParams(params);
        }
    }

    public static HttpManager http() {
        return HttpManager.getInstance();
    }
}
