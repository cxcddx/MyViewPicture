package com.ygwl.lz.myviewpicture.application;

import android.app.Application;

import com.previewlibrary.ZoomMediaLoader;

/**
 * @author cx
 * @class describe
 * @time 2019/4/24 10:10
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZoomMediaLoader.getInstance().init(new MyImageLoader());
    }
}
