package com.zmy.gradledemo.rn;

import android.os.Bundle;
import android.util.Log;

import com.facebook.react.ReactActivity;
import com.zmy.gradledemo.BuildConfig;

public class RnTestActivity extends ReactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //    getReactInstanceManager().createReactContextInBackground();

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
