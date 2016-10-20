package com.zmy.library;

import android.app.Application;
import android.content.Context;

import com.baidu.wefan.tooltip.ReactToolTipPackage;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.zmy.gradledemo.nativemodule.BGNativeExamplePackage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zmy on 16/9/6.
 */
public class BaseApplication extends Application implements ReactApplication {

    public static ArrayBlockingQueue<String> myBlockingQueue = new ArrayBlockingQueue<String>(10);

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


    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        protected boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new ReactToolTipPackage(),
                    new BGNativeExamplePackage()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
}
