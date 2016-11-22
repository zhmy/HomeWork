package com.zmy.gradledemo.rn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.react.ReactRootView;
import com.zmy.gradledemo.MainApplication;

public class RnMain2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MainApplication.getInstance().getReactNativeHost().getReactInstanceManager().createReactContextInBackground();

        ReactRootView reactRootView = new ReactRootView(this);
        reactRootView.startReactApplication(MainApplication.getInstance().getReactNativeHost().getReactInstanceManager(), "ZmyNative");
        setContentView(reactRootView);

//        setContentView(R.layout.activity_rn_main2);
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

}
