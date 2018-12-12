package com.lzz.studydemo;

import android.app.Application;

import com.lzz.studydemo.greendao.DaoMaster;
import com.lzz.studydemo.greendao.DaoSession;
import com.lzz.studydemo.greendao.MySQLiteOpenHelper;
import com.lzz.studydemo.http.J;

import org.greenrobot.greendao.database.Database;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


public class StudyDemoApplication extends Application {
    private static final String DATA_BASE_NAME = "USER";
    private static StudyDemoApplication context;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        /**
         * 网络请求初始化
         */
        J.initHttp(this, null, null);

        /**
         * Activity侧滑返回帮助类
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
        createDataBase();
    }

    private void createDataBase() {
        //第二部，替换OpenHelper，解决数据库升级，数据丢失问题。
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(context, DATA_BASE_NAME, null);
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, DATA_BASE_NAME);
        Database db = mySQLiteOpenHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static StudyDemoApplication getStudyDemoApplication() {
        return context;
    }
}
