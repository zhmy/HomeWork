package com.zmy.library;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.baidu.wefan.tooltip.ReactToolTipPackage;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.shell.MainReactPackage;
import com.reactnativenavigation.NavigationApplication;
import com.zmy.gradledemo.nativemodule.NativePackage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by zmy on 16/9/6.
 */
public class BaseApplication extends NavigationApplication {


    public static ArrayBlockingQueue<String> myBlockingQueue = new ArrayBlockingQueue<String>(1);

    public static final String BROADCAST_CHANGE_SHARED_PREF = "com.baidu.tieba.broadcast.changeSharedPref";
    private static BaseApplication application;
    private ReactContext reactContext;

    @Override
    public void onCreate() {
        super.onCreate();
        application= this;
    }

    public void setReactContext(ReactContext reactContext) {
        this.reactContext = reactContext;
    }

    public ReactContext getReactContext() {
        return reactContext;
    }


    public static BaseApplication getInstance() {
        return application;
    }

    public Context getApp() {
        return getApplicationContext();
    }


//    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
//        @Override
//        protected boolean getUseDeveloperSupport() {
//            return BuildConfig.DEBUG;
//        }
//
//        @Override
//        protected List<ReactPackage> getPackages() {
//            return Arrays.<ReactPackage>asList(
//                    new MainReactPackage(),
//                    new ReactToolTipPackage(),
//                    new NativePackage()
//            );
//        }
//
//        @Override
//        protected ReactInstanceManager createReactInstanceManager() {
//            return super.createReactInstanceManager();
//        }
//    };
//
//    @Override
//    public ReactNativeHost getReactNativeHost() {
//        return mReactNativeHost;
//    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @Nullable
    @Override
    public List<ReactPackage> createAdditionalReactPackages() {
        return Arrays.<ReactPackage>asList(
                new ReactToolTipPackage(),
                new NativePackage()
        );
    }
}
