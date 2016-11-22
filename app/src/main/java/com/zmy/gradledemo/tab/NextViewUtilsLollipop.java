package com.zmy.gradledemo.tab;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * Created by zmy on 2016/11/11.
 */

public class NextViewUtilsLollipop {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    static void setBoundsViewOutlineProvider(View view) {
        view.setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }

}
