package com.zmy.gradledemo.text;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Rasterizer;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RasterizerSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.zmy.gradledemo.R;

public class TextViewActivity extends AppCompatActivity {

    private boolean startState = false;
    private int startIndex, endIndex = 1;
    private String str = "看，这里的文字会动哦！";
    private int reDynamic = 0;
    private TextSwitcher switcher;
    private static final String[] TEXTS = {"相册", "拍照", "视频"};
    private int mTextsPosition = 0;
    private Handler handler = new Handler();
    TextView textView8;
    SpannableString spannableString = new SpannableString(str);
    RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(1.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview);
        TextView textView = (TextView) findViewById(R.id.text);
        TextView textView2 = (TextView) findViewById(R.id.text2);
        TextView textView3 = (TextView) findViewById(R.id.text3);
        TextView textView4 = (TextView) findViewById(R.id.text4);
        TextView textView5 = (TextView) findViewById(R.id.text5);
        TextView textView6 = (TextView) findViewById(R.id.text6);
        TextView textView7 = (TextView) findViewById(R.id.text7);
        textView8 = (TextView) findViewById(R.id.text8);
        switcher = (TextSwitcher) findViewById(R.id.text_switcher);

        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(TextViewActivity.this);
                t.setGravity(Gravity.CENTER);
                return t;
            }
        });
        // 设置切入动画效果,使用系统的谈入效果，也可以自定义
        switcher.setInAnimation(this, android.R.anim.fade_in);
        // 设置切出动画效果，使用系统的谈出效果，也可以自定义
        switcher.setOutAnimation(this, android.R.anim.fade_out);


        setForegroundColorSpan(textView);
        setRelativeSizeSpan(textView3);
        setImageSpan(textView4);
        setClickableSpan(textView5);

        setUrlSpan(textView6);

        setMaskFilterSpan(textView2);

        setRasterizerSpan(textView7);

        textView8.setTextColor(Color.parseColor("#0099EE"));
        textView8.setText(spannableString);
        setDynamic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (handler != null && hasPause) {
            hasPause = false;
            doDynamic();
        }
    }

    private boolean hasPause;

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            hasPause = true;
            handler.removeCallbacks(runnable);
        }
    }

    public void onSwitchText() {
        // 设置渐变切换文字
        switcher.setText(TEXTS[mTextsPosition]);
        //改变文字，待下一次显示
        setNextPosition();
    }

    private void setNextPosition() {
        mTextsPosition = (mTextsPosition + 1) % TEXTS.length;
    }

    private void setDynamic() {
        doDynamic();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            count++;
            if (startIndex == str.length() - 1 && reDynamic == 1) {
                //TODO 第二次到哦
//                    startIndex --;
//                    endIndex --;
//                    reDynamic = 0;
            }
            if (startIndex == str.length()) {
//                Log.e("zmy", "startIndex = " + startIndex);
                spannableString.removeSpan(sizeSpan01);
                textView8.setText(str);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                startIndex = 0;
                endIndex = 1;
                reDynamic = 1;
            }
            spannableString.setSpan(sizeSpan01, startIndex++, endIndex++, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView8.setText(spannableString);
//            if (count < 4) {
                doDynamic();
//            } else {
//                textView8.setText(str);
//            }

            onSwitchText();
        }
    };

    int count = 0;

    private void doDynamic() {
        handler.postDelayed(runnable, 500);
    }

    private void setRasterizerSpan(TextView textView) {
        SpannableString spannableString = new SpannableString("设置文字的光栅效果");
        RasterizerSpan rasterizerSpan = new RasterizerSpan(new Rasterizer());
        spannableString.setSpan(rasterizerSpan, 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void setUrlSpan(TextView textView) {
        SpannableString spannableString = new SpannableString("为文字设置超链接");
        URLSpan urlSpan = new URLSpan("http://www.jianshu.com/users/dbae9ac95c78");
        spannableString.setSpan(urlSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.parseColor("#36969696"));
        textView.setText(spannableString);
    }

    private void setClickableSpan(TextView textView) {
        SpannableString spannableString = new SpannableString("为文字设置点击事件");
        MyClickableSpan clickableSpan = new MyClickableSpan("http://www.jianshu.com/users/dbae9ac95c78");
        spannableString.setSpan(clickableSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.parseColor("#36969696"));
        textView.setText(spannableString);
    }

    private void setImageSpan(TextView textView) {
        SpannableString spannableString = new SpannableString("在文本中添加表情（表情）");
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, 42, 42);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString.setSpan(imageSpan, 6, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void setRelativeSizeSpan(TextView textView) {
        SpannableString spannableString = new SpannableString("万丈高楼平地起");
        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(1.2f);
        RelativeSizeSpan sizeSpan02 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpan03 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan04 = new RelativeSizeSpan(1.8f);
        RelativeSizeSpan sizeSpan05 = new RelativeSizeSpan(1.6f);
        RelativeSizeSpan sizeSpan06 = new RelativeSizeSpan(1.4f);
        RelativeSizeSpan sizeSpan07 = new RelativeSizeSpan(1.2f);

        spannableString.setSpan(sizeSpan01, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan02, 1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan03, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan04, 3, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan05, 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan06, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan07, 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    private void setForegroundColorSpan(TextView textView) {
        SpannableString spannableString = new SpannableString("设置文字的前景色为淡蓝色");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        spannableString.setSpan(colorSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        BackgroundColorSpan colorSpan2 = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
        spannableString.setSpan(colorSpan2, 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
    }

    public void setMaskFilterSpan(TextView textView) {
        SpannableString spannableString = new SpannableString("设置文字的模糊和浮雕效果");
        MaskFilterSpan maskSpan = new MaskFilterSpan(new MaskFilter());
        spannableString.setSpan(maskSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    class MyClickableSpan extends ClickableSpan {

        private String content;

        public MyClickableSpan(String content) {
            this.content = content;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(TextViewActivity.this, TextViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("content", content);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }
}
