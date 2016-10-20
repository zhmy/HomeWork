package com.baidu.wefan.tooltip;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by zmy on 2016/10/18.
 */

public class ToolTipPopWindow extends PopupWindow{

    private int mWidth, mHeight;
    private Context context;
    private View layoutView;
    private LinearLayout btn_layout;
    private View.OnClickListener onClickListener;

    public ToolTipPopWindow(Context context) {
        super(context);
        this.context = context;
        layoutView = LayoutInflater.from(context).inflate(
                R.layout.tool_tip_pop_layout, null);
        setContentView(layoutView);
        btn_layout = (LinearLayout) layoutView.findViewById(R.id.tool_layout);

        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void setData(String[] data) {
        if (data == null || data.length == 0) {
            return;
        }
        btn_layout.removeAllViews();
        for (int i = 0; i < data.length; i++) {
            String text = data[i];
            TextView textView = new TextView(context);
            textView.setTextColor(context.getResources().getColor(android.R.color.white));
            textView.setText(text);
            textView.setTag(text);
            if (onClickListener != null) {
                textView.setOnClickListener(onClickListener);
            }

            btn_layout.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            if (i != data.length - 1) {
                View view = new View(context);
                view.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
                params.leftMargin = 20;
                params.rightMargin = 20;
                btn_layout.addView(view, params);
            }
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void show(View anchor) {
        if (btn_layout.getChildCount() == 0) {
            return;
        }
        int vWidth = anchor.getMeasuredWidth();
        int vHeight = anchor.getMeasuredHeight();
        if (vWidth == 0 || vHeight == 0) {
            anchor.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        }
        vWidth = anchor.getMeasuredWidth();
        vHeight = anchor.getMeasuredHeight();

        mWidth = layoutView.getMeasuredWidth();
        mHeight = layoutView.getMeasuredHeight();
        if (mWidth == 0 || mHeight == 0) {
            layoutView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        }
        mWidth = layoutView.getMeasuredWidth();
        mHeight = layoutView.getMeasuredHeight();

        showAsDropDown(anchor, (vWidth - mWidth) / 2, -vHeight - mHeight);
    }
}
