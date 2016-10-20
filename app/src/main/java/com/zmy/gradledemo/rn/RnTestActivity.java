package com.zmy.gradledemo.rn;

import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.zmy.gradledemo.BuildConfig;

public class RnTestActivity extends ReactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //发送事件

//        WritableMap params = Arguments.createMap();
//        params.putString("name", "Jack");
//        getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
//                .emit("zmy", params);
    }



    @Override
    protected String getMainComponentName() {
        return "ZmyNative";
    }

    @Override
    protected boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }




}
