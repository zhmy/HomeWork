package com.zmy.gradledemo.tab;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zmy on 2016/11/11.
 */

public class NextTabItem extends View {

    final CharSequence mText;
    final Drawable mIcon;
    final int mCustomLayout;

    public NextTabItem(Context context) {
        this(context, null);
    }

    public NextTabItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs,
                android.support.design.R.styleable.TabItem);
        mText = a.getText(android.support.design.R.styleable.TabItem_android_text);
        mIcon = a.getDrawable(android.support.design.R.styleable.TabItem_android_icon);
        mCustomLayout = a.getResourceId(android.support.design.R.styleable.TabItem_android_layout, 0);
        a.recycle();
    }
}
