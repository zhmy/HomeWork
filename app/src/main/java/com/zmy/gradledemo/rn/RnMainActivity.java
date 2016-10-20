package com.zmy.gradledemo.rn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wefan.tooltip.ToolTipPopWindow;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.zmy.gradledemo.BuildConfig;
import com.zmy.gradledemo.MainApplication;
import com.zmy.gradledemo.R;
import com.zmy.gradledemo.nativeview.ReactTestView;

/**
 * Created by zmy on 2016/10/11.
 */

public class RnMainActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler, View.OnClickListener {
    private TextView api_url;

    private ReactInstanceManager mReactInstanceManager;

    private ReactTestView rn_test1, rn_test2, rn_test3;

    private ToolTipPopWindow popWindowView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rn_main);
        api_url = (TextView) findViewById(R.id.api_url);
        api_url.setText(BuildConfig.API_URL);
        api_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RnMainActivity.this, RnTestActivity.class));
            }
        });

        mReactInstanceManager =
                ((MainApplication) getApplication()).getReactNativeHost().getReactInstanceManager();

        Fragment viewFragment = new HelloFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.rn_frame_layout, viewFragment).commit();

        rn_test1 = (ReactTestView) findViewById(R.id.rn_test1);
        rn_test2 = (ReactTestView) findViewById(R.id.rn_test2);
        rn_test3 = (ReactTestView) findViewById(R.id.rn_test3);
        rn_test1.setOnClickListener(this);
        rn_test2.setOnClickListener(this);
        rn_test3.setOnClickListener(this);

        popWindowView = new ToolTipPopWindow(this);
        popWindowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowView.dismiss();

                Toast.makeText(RnMainActivity.this, (String)v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        popWindowView.setData(new String[]{"删除", "分享"});
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause();
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
            mReactInstanceManager.onHostDestroy();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        popWindowView.show(v);
    }
}
