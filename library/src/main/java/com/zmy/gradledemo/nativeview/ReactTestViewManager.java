package com.zmy.gradledemo.nativeview;

import android.support.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by zmy on 2016/10/17.
 */

public class ReactTestViewManager extends ViewGroupManager<ReactTestView> {
    public static final String REACT_CLASS = "RCTReactTestView";
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ReactTestView createViewInstance(ThemedReactContext reactContext) {
        return new ReactTestView(reactContext);
    }

    @ReactProp(name = "text")
    public void setText(ReactTestView view, @Nullable String text) {
        view.setText(text);
    }



}
