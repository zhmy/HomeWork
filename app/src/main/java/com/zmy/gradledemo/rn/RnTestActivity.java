package com.zmy.gradledemo.rn;

import com.facebook.react.ReactActivity;
import com.zmy.gradledemo.BuildConfig;

public class RnTestActivity extends ReactActivity {

    @Override
    protected String getMainComponentName() {
        return "ZmyNative";
    }

    @Override
    protected boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }



}
