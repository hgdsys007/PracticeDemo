package com.lzz.studydemo;

import android.app.Application;

import com.lzz.studydemo.greendao.DaoMaster;
import com.lzz.studydemo.greendao.DaoSession;
import com.lzz.studydemo.greendao.MySQLiteOpenHelper;
import com.lzz.studydemo.http.J;

import org.greenrobot.greendao.database.Database;


public class StudyDemoApplication extends Application {
    private static final String DATA_BASE_NAME = "USER";
    private static StudyDemoApplication context;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        J.initHttp(this, null, null);
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
