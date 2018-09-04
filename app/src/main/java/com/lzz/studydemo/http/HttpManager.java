package com.lzz.studydemo.http;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;


public class HttpManager {

    private static HttpManager instance;

    private static Application context;

    public static void init(Application application) {
        context = application;
    }

    public static synchronized HttpManager getInstance() {
        if (null == instance) {
            instance = new HttpManager();
        }
        return instance;
    }

    private HttpManager() {
        try {
            OkGo.getInstance().init(context);
            initOkGo();
        } catch (Exception e) {
        }

    }

    /**
     * 初始化
     */
    public void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("HttpManager");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        //全局的读取超时时间,5秒
        builder.readTimeout(5, TimeUnit.SECONDS);
        //全局的写入超时时间
        builder.writeTimeout(5, TimeUnit.SECONDS);
        //全局的连接超时时间
        builder.connectTimeout(5, TimeUnit.SECONDS);

        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(context)));
        //使用数据库保持cookie，如果cookie不过期，则一直有效
        //        builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));
        //使用内存保持cookie，app退出后，cookie消失
        //        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));


        /*
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(application)));
        //使用数据库保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(application)));
        //使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        */


        /*
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
        builder.hostnameVerifier(new SafeHostnameVerifier());
        */

        OkGo.getInstance().init(context)                        //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(2)                            //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
        ;

    }

    /**
     * 全局公共头
     *
     * @param httpHeaders
     */
    public void addCommonHeaders(HttpHeaders httpHeaders) {
        OkGo.getInstance().addCommonHeaders(httpHeaders);
    }

    /**
     * 全局公共参数
     *
     * @param httpParams
     */
    public void addCommonParams(HttpParams httpParams) {
        OkGo.getInstance().addCommonParams(httpParams);
    }

    /**
     * post json 提交
     *
     * @param url
     * @param tag
     * @param params
     * @param callback
     * @param <T>
     */
    public <T> void postJson(String url, Object tag, Map<String, String> params, BaseHttpCallback<T> callback) {
        String json = "";
        if (null == params) {
            json = "{}";
        } else {
            json = new JSONObject(params).toString();
        }
        OkGo.<T>post(url).tag(tag).upJson(json).execute(callback);
    }

    /**
     * 普通 get 提交
     *
     * @param url
     * @param tag
     * @param map
     * @param callback
     * @param <T>
     */
    public <T> void get(String url, Object tag, Map<String, String> map, BaseHttpCallback<T> callback) {
        HttpParams params = new HttpParams();
        params.put(map);
        OkGo.<T>get(url).tag(tag).params(params).execute(callback);
    }


    /**
     * 普通 post 提交
     *
     * @param url
     * @param tag
     * @param map
     * @param callback
     * @param <T>
     */
    public <T> void post(String url, Object tag, Map<String, String> map, BaseHttpCallback<T> callback) {
        HttpParams params = new HttpParams();
        params.put(map);
        OkGo.<T>post(url).tag(tag).params(params).execute(callback);
    }

    /**
     * 普通 post 缓存提交
     *
     * @param url
     * @param tag
     * @param map
     * @param callback
     * @param <T>
     */
    public <T> void postCache(String url, Object tag, Map<String, String> map, BaseHttpCallback<T> callback) {
        HttpParams params = new HttpParams();
        params.put(map);
        OkGo.<T>post(url).tag(tag).params(params).cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST).execute(callback);
    }

    /**
     * 普通无缓存 post 提交
     *
     * @param url
     * @param tag
     * @param map
     * @param callback
     * @param <T>
     */
    public <T> void noCachepost(String url, Object tag, Map<String, String> map, BaseHttpCallback<T> callback) {
        HttpParams params = new HttpParams();
        params.put(map);
        OkGo.<T>post(url).tag(tag).params(params).cacheMode(CacheMode.NO_CACHE).execute(callback);
    }

    /**
     * 单文件上传
     *
     * @param url
     * @param tag
     * @param path
     * @param map
     * @param callback
     * @param <T>
     */
    public <T> void postFile(String url, Object tag, String path, Map<String, String> map, BaseHttpCallback<T> callback) {
        HttpParams params = new HttpParams();
        params.put(map);
        OkGo.<T>post(url).tag(tag).isMultipart(false).params("lg", new File(path)).params(params).execute(callback);
    }


}
