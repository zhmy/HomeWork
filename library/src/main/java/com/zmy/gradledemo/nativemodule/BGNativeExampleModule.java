package com.zmy.gradledemo.nativemodule;

import android.app.Activity;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;

/**
 * Created by zmy on 2016/10/12.
 */

public class BGNativeExampleModule extends ReactContextBaseJavaModule implements LifecycleEventListener{
    private static final  String TestEventName = "TestEventName";
    private Timer timer;
    public BGNativeExampleModule(final ReactApplicationContext reactContext) {
        super(reactContext);

        //开启定时器
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //发送事件
                WritableMap params = Arguments.createMap();
                params.putString("name", "Jack");
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(TestEventName, params);
            }
        };
        timer = new Timer();
        timer.schedule(task, 1000, 1000);

        //添加监听
        reactContext.addLifecycleEventListener(this);
    }

    @Override
    public String getName() {
        return "BGNativeExampleModule";
    }

    @ReactMethod
    public void testPrint(String name, ReadableMap info) {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            String data = activity.getIntent().getStringExtra("zmy");
        }


        Log.e("zmy", name);
        Log.e("zmy", info.toString());
    }

    @ReactMethod
    public void getDataFromIntent(Callback callback) {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            String data = activity.getIntent().getStringExtra("zmy");
            data = "zzmy";
            callback.invoke(data);
        }
    }

    @ReactMethod
    public void getNativeClass(Callback callback) {
        callback.invoke("BGNativeExampleModule");
    }

    @ReactMethod
    public void testPromises(Boolean isResolve, Promise promise) {
        if(isResolve) {
            promise.resolve(isResolve.toString());
        }
        else {
            promise.reject(isResolve.toString());
        }
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("BGModuleName", "BGNativeModuleExample");
        return constants;
    }

    @Override
    public void onHostResume() {
        Log.e("zmy", "onHostResume");
    }

    @Override
    public void onHostPause() {
        Log.e("zmy", "onHostPause");
        timer.cancel();
    }

    @Override
    public void onHostDestroy() {
        Log.e("zmy", "onHostDestroy");
        timer.cancel();
    }
}
