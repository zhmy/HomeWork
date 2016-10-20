package com.zmy.gradledemo.nativeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.zmy.library.R;

/**
 * Created by zmy on 2016/10/17.
 */

public class ReactTestView extends FrameLayout {

    public ReactTestView(Context context) {
        this(context, null);
    }

    public ReactTestView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ReactTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private TextView textView;

    private void init() {
        setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        textView = new TextView(getContext());
        textView.setText("你好js");
        textView.setPadding(50, 10, 50, 10);
        addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setText(String text) {
        textView.setText(text);
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
}
