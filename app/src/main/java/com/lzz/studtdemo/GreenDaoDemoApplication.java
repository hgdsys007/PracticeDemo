package com.lzz.studtdemo;

import android.app.Application;

import com.lzz.studtdemo.greendao.DaoMaster;
import com.lzz.studtdemo.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;


public class GreenDaoDemoApplication extends Application {
    private static final String DATA_BASE_NAME = "USER";
    private static GreenDaoDemoApplication context;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        createDataBase();
    }

    private void createDataBase() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, DATA_BASE_NAME);
        Database db = devOpenHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static GreenDaoDemoApplication getGreenDaoDemoApplication() {
        return context;
    }
}
