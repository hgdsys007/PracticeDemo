package com.lzz.studydemo.manager;

import android.content.Context;
import android.widget.ImageView;

import com.example.lzz.studtdemo.Logger;

public class GlideManager {

    private static GlideManager INSTANCE;

    private GlideManager() {

    }

    // 静态内部类实现单例
    private static class SingleInstance {

        private static GlideManager INSTANCE = new GlideManager();

    }

    public static GlideManager getInstance() {

//        return   SingleInstance.INSTANCE;

        if (INSTANCE == null) {
            synchronized (GlideManager.class) {
                if (INSTANCE == null) {

                    INSTANCE = new GlideManager();
                }
            }
        }

        return INSTANCE;

    }

    public void load(Context context, Object load, ImageView view) {
        try {
            Logger.e("显示图片");
//            Glide.with(fragment).load(load)
//                    .apply(requestOptions)
//                    .into(iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
