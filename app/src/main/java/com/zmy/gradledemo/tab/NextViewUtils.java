package com.zmy.gradledemo.tab;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * Created by zmy on 2016/11/11.
 */

public class NextViewUtils {

    static final NextValueAnimatorCompat.Creator DEFAULT_ANIMATOR_CREATOR
            = new NextValueAnimatorCompat.Creator() {
        @Override
        public NextValueAnimatorCompat createAnimator() {
            return new NextValueAnimatorCompat(Build.VERSION.SDK_INT >= 12
                    ? new NextValueAnimatorCOmpatImplHoneycombMr1()
                    : new NextValueAnimatorCompatImplEclairMr1());
        }
    };

    private interface ViewUtilsImpl {
        void setBoundsViewOutlineProvider(View view);
    }

    private static class ViewUtilsImplBase implements NextViewUtils.ViewUtilsImpl {
        @Override
        public void setBoundsViewOutlineProvider(View view) {
            // no-op
        }
    }

    private static class ViewUtilsImplLollipop implements NextViewUtils.ViewUtilsImpl {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void setBoundsViewOutlineProvider(View view) {
            NextViewUtilsLollipop.setBoundsViewOutlineProvider(view);
        }
    }

    private static final NextViewUtils.ViewUtilsImpl IMPL;

    static {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            IMPL = new NextViewUtils.ViewUtilsImplLollipop();
        } else {
            IMPL = new NextViewUtils.ViewUtilsImplBase();
        }
    }

    static void setBoundsViewOutlineProvider(View view) {
        IMPL.setBoundsViewOutlineProvider(view);
    }

    static NextValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }
}
