package com.baidu.wefan.tooltip;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by zmy on 2016/10/18.
 */

public class ReactToolTipView extends FrameLayout {

    public ReactToolTipView(Context context) {
        this(context, null);
    }

    public ReactToolTipView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ReactToolTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ToolTipPopWindow toolTip;

    private void init() {
        toolTip = new ToolTipPopWindow(getContext());
        toolTip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toolTip.dismiss();
                onReceiveNativeEvent((String)v.getTag());
            }
        });
    }

    public void setData(String[] data) {
        toolTip.setData(data);
    }

    public void show() {
        toolTip.show(this);
    }

    public void onReceiveNativeEvent(String text) {
        WritableMap event = Arguments.createMap();
        event.putString("text", text);
        ReactContext reactContext = (ReactContext)getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                "topChange",
                event);
    }
}
