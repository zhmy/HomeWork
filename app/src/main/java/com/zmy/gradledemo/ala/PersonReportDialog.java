package com.zmy.gradledemo.ala;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.zmy.gradledemo.R;

/**
 * Created by zmy on 16/9/14.
 */
public class PersonReportDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private View report, reportCancel;

    public PersonReportDialog(Context context) {
        super(context, R.style.Theme_Report_Dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_person_report);

        WindowManager m = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
        getWindow().setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(true);
        setCancelable(true);


        report = findViewById(R.id.report);
        reportCancel = findViewById(R.id.report_cancel);
        report.setOnClickListener(this);
        reportCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report:
                //请求接口
                break;
            case R.id.report_cancel:
                break;
            default:
                break;
        }
        dismiss();
    }
}
