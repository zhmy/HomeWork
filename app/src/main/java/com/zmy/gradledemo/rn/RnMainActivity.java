package com.zmy.gradledemo.rn;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wefan.tooltip.ToolTipPopWindow;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.zmy.gradledemo.BuildConfig;
import com.zmy.gradledemo.MainApplication;
import com.zmy.gradledemo.R;
import com.zmy.gradledemo.nativeview.ReactTestView;
import com.zmy.library.BaseApplication;

import java.util.concurrent.ArrayBlockingQueue;

import static java.security.AccessController.getContext;

/**
 * Created by zmy on 2016/10/11.
 */

public class RnMainActivity extends FragmentActivity implements DefaultHardwareBackBtnHandler, View.OnClickListener {
    private TextView api_url;

    private ReactInstanceManager mReactInstanceManager;

    private ReactTestView rn_test1, rn_test2, rn_test3;

    private ToolTipPopWindow popWindowView;

    private ViewPager viewpager;
    Fragment viewFragment = new HelloFragment();
    Fragment nativeFragment = new NativeFragment();
    boolean isRN = true;
    private Fragment mContent;
    private Button btn1, btn2, btn3, btn4;

    public void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.rn_frame_layout, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_rn_main);
        api_url = (TextView) findViewById(R.id.api_url);
        api_url.setText(BuildConfig.API_URL);
        api_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(RnMainActivity.this, RnMain2Activity.class));
//                startActivity(new Intent(RnMainActivity.this, RnTestActivity.class));

                if (isRN) {
                    switchContent(viewFragment, nativeFragment);
                    isRN = false;
                } else {
                    switchContent(nativeFragment, viewFragment);
                    isRN = true;
                }
            }
        });

//        api_url.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
//            @Override
//            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
//                return null;
//            }
//        });
        mReactInstanceManager =
                ((MainApplication) getApplication()).getReactNativeHost().getReactInstanceManager();


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

                Toast.makeText(RnMainActivity.this, (String) v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        popWindowView.setData(new String[]{"删除", "分享"});


//        viewpager = (ViewPager) findViewById(R.id.viewpager);
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewpager.setAdapter(adapter);

        initBtn();
    }

    private void initBtn() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
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
            Log.e("zmy", " mReactInstanceManager != null main resumed");
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
        if (v == btn1) {
            startActivity(new Intent(RnMainActivity.this, RnTestActivity.class));
        } else if (v == btn2) {
            startActivity(new Intent(RnMainActivity.this, RnMain2Activity.class));
        } else if (v == btn3) {
            callJsToNextPage();
        } else if (v == btn4) {

        } else {
            popWindowView.show(v);
        }
    }

    private void callJsToNextPage() {
        WritableMap event = Arguments.createMap();
        event.putString("page", "secondPage");
        ReactContext reactContext = BaseApplication.getInstance().getReactContext();
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("key1", event);
    }

//    public void onReceiveNativeEvent() {
//        WritableMap event = Arguments.createMap();
//        event.putString("message", "MyMessage");
//        ReactContext reactContext = (ReactContext)getContext();
//        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
//                getId(),
//                "topChange",
//                event);
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            String result = data.getStringExtra("result");
            if (TextUtils.isEmpty(result)) {
                BaseApplication.myBlockingQueue.add(result);
            } else {
                BaseApplication.myBlockingQueue.add("无数据传回");
            }
        } else {
            BaseApplication.myBlockingQueue.add("没有");
        }
    }


}
