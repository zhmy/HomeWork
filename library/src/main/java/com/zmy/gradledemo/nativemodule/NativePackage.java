package com.zmy.gradledemo.nativemodule;

import android.widget.BaseAdapter;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.zmy.gradledemo.nativeview.ReactTestViewManager;
import com.zmy.library.BaseApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by zmy on 2016/10/12.
 */

public class NativePackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        BaseApplication.getInstance().setReactContext(reactContext);
        return Arrays.asList(new NativeModule[]{
                new ZmyNativeModule(reactContext),
        });
    }


    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new ReactTestViewManager()
        );
    }
}
