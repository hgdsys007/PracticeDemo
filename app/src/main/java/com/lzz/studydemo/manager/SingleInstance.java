package com.lzz.studydemo.manager;

import com.lzz.studydemo.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通过enum枚举实现全局单例
 */
public enum SingleInstance {

    INSTANCE();

    private ExecutorService singleThreadExecutor;

    SingleInstance() {
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        Logger.e("新建线程");

    }

    /**
     * @param runnable 任务
     */
    public void execute(Runnable runnable) {
        singleThreadExecutor.execute(runnable);

    }

}
