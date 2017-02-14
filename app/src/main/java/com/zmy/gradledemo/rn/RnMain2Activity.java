package com.zmy.gradledemo.rn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.zmy.gradledemo.MainApplication;
import com.zmy.gradledemo.R;

public class RnMain2Activity extends AppCompatActivity implements DefaultHardwareBackBtnHandler{

    private ReactInstanceManager mReactInstanceManager;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MainApplication.getInstance().getReactNativeHost().getReactInstanceManager().createReactContextInBackground();

        mReactInstanceManager = MainApplication.getInstance().getReactNativeHost().getReactInstanceManager();
        ReactRootView reactRootView = new ReactRootView(this);
        reactRootView.startReactApplication(MainApplication.getInstance().getReactNativeHost().getReactInstanceManager(), "ZmyNative");

        setContentView(R.layout.activity_rn_main2);

        container = (FrameLayout) findViewById(R.id.container);
        container.addView(reactRootView);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(this);
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
}
