package com.baidu.wefan.tooltip;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

import java.util.Map;

/**
 * Created by zmy on 2016/10/17.
 */

public class ReactToolTipManager extends ViewGroupManager<ReactToolTipView> {
    public static final String REACT_CLASS = "RCTToolTipView";
    public static final int COMMAND_SHOW = 1;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "show",
                COMMAND_SHOW
        );
    }

    @Override
    protected ReactToolTipView createViewInstance(ThemedReactContext reactContext) {
        return new ReactToolTipView(reactContext);
    }

    @Override
    public void receiveCommand(ReactToolTipView root, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case COMMAND_SHOW:
                if (args != null && args.size() > 0) {
                    String[] data = new String[args.size()];
                    for (int i = 0; i < args.size(); i++) {
                        data[i] = args.getString(i);
                    }
                    root.setData(data);
                }
                root.show();
                break;
        }
    }
}
