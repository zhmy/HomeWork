package com.zmy.gradledemo.rn;

import android.util.Log;

import com.facebook.react.ReactActivity;
import com.zmy.gradledemo.BuildConfig;

public class RnTestActivity extends ReactActivity {

    @Override
    protected String getMainComponentName() {
        return "ZmyNative";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zmy", "RnTestActivity onDestroy");
    }
}
