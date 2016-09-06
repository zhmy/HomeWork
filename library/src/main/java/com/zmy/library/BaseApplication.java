package com.zmy.library;

import android.app.Application;
import android.content.Context;

/**
 * Created by zmy on 16/9/6.
 */
public class BaseApplication extends Application {
    public static final String BROADCAST_CHANGE_SHARED_PREF = "com.baidu.tieba.broadcast.changeSharedPref";
    private static BaseApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application= this;
    }

    public static BaseApplication getInstance() {
        return application;
    }

    public Context getApp() {
        return getApplicationContext();
    }
}
