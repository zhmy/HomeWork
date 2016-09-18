package com.zmy.gradledemo.ala;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmy.gradledemo.R;

/**
 * Created by zmy on 16/9/14.
 */
public class PersonInfoActivity extends FragmentActivity implements View.OnClickListener {
    private Button attentionBtn, hasAttentionBtn;
    private LinearLayout attentionBottomLayout;
    private View closeIv, closeDesc;

    int attentionWidth, hasAttentionWidth;
    boolean isShowClose;

    private TextView fansNum, historyNum, attentionNum;

    private FrameLayout fragmengContainer;
    private View report;

    boolean isShowFragment;

    private View close;
    private View iconLayout, idLayout, operationLayout;
    private View nameLayout, numLayout;
    private TextView userName, aMoney, userGrade;
    private ImageView userSex;

    private View rootView;
    private RelativeLayout person_card_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.alpha_in, 0);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_person_info, null);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setContentView(rootView);
        attentionBtn = (Button) findViewById(R.id.attention_btn);
        hasAttentionBtn = (Button) findViewById(R.id.has_attention_btn);
        attentionBottomLayout = (LinearLayout) findViewById(R.id.attention_bottom_layout);
        closeIv = findViewById(R.id.close_iv);
        closeDesc = findViewById(R.id.close_desc);

        attentionBtn.setOnClickListener(this);
        hasAttentionBtn.setOnClickListener(this);
        closeIv.setOnClickListener(this);
        person_card_layout = (RelativeLayout) findViewById(R.id.person_card_layout);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_up_in_overshoot);
        person_card_layout.setAnimation(animation);
        animation.setDuration(300);
        animation.start();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                person_card_layout.setAnimation(animation);
//            }
//        }, 100);

//        person_card_layout.setVisibility(View.VISIBLE);

        LayoutTransition transition = new LayoutTransition();
//        transition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
//        transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
//        person_card_layout.setLayoutTransition(transition);


        fansNum = (TextView) findViewById(R.id.fans_num);
        historyNum = (TextView) findViewById(R.id.history_num);
        attentionNum = (TextView) findViewById(R.id.attention_num);
        fansNum.setOnClickListener(this);
        historyNum.setOnClickListener(this);
        attentionNum.setOnClickListener(this);

        fragmengContainer = (FrameLayout) findViewById(R.id.fragment_container);

        report = findViewById(R.id.report);
        report.setOnClickListener(this);

        close = findViewById(R.id.close);
        close.setOnClickListener(this);

        iconLayout = findViewById(R.id.icon_layout);
        idLayout = findViewById(R.id.id_layout);
        nameLayout = findViewById(R.id.name_layout);
        numLayout = findViewById(R.id.num_layout);
        userName = (TextView) findViewById(R.id.user_name);
        aMoney = (TextView) findViewById(R.id.a_money);
        userGrade = (TextView) findViewById(R.id.user_grade);
        userSex = (ImageView) findViewById(R.id.user_sex);
        operationLayout = findViewById(R.id.operation_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Animation animation = AnimationUtils.loadAnimation(PersonInfoActivity.this, R.anim.push_up_in_overshoot);
                person_card_layout.setAnimation(animation);
                person_card_layout.setVisibility(View.VISIBLE);
                animation.setDuration(300);
                animation.start();
            }
        }, 50);

    }

    private long lastFinishTime;

    @Override
    public void finish() {
        if (System.currentTimeMillis() - lastFinishTime < 300) {
//            person_card_layout.clearAnimation();
//            Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_up_out);
//            animation.setDuration(0);
//            person_card_layout.setAnimation(animation);
//            person_card_layout.setVisibility(View.GONE);
//            super.finish();
//            overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            return;
        }
        lastFinishTime = System.currentTimeMillis();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_up_out);
        person_card_layout.setAnimation(animation);
        person_card_layout.setVisibility(View.GONE);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                PersonInfoActivity.super.finish();
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attention_btn:
                //请求接口
                startAttentionAnim(true);
                break;
            case R.id.has_attention_btn:
                //请求接口
                startAttentionAnim(false);
                break;
            case R.id.close_iv:
                startCloseAnim(!isShowClose);
                break;
            case R.id.fans_num:
            case R.id.history_num:
            case R.id.attention_num:
                isShowFragment = !isShowFragment;
                if (isShowClose) {
                    startCloseAnim(!isShowClose);
                }
                startFragmentAnim(isShowFragment);
                break;
            case R.id.report:
                if (isShowClose) {
                    startCloseAnim(!isShowClose);
                }
                showReport();
                break;
            case R.id.close:
                finish();
                break;
        }
    }

    private void initAttentionWidth() {
        if (attentionWidth == 0 || hasAttentionWidth == 0) {
            attentionWidth = attentionBtn.getMeasuredWidth();
            hasAttentionWidth = hasAttentionBtn.getMeasuredWidth();
        }
    }

    private void showReport() {
        PersonReportDialog dialog = new PersonReportDialog(PersonInfoActivity.this);
        dialog.show();
    }

    PersonWrapperFragment personFragment;

    int iconHeight;

    private void startFragmentAnim(final boolean isShow) {
        if (iconHeight == 0) {
            iconHeight = iconLayout.getMeasuredHeight();
        }
        final int startValue = isShow ? 0 : 1200;
        final int endValue = isShow ? 1200 : 0;


        if (isShow) {
//            report.setVisibility(View.GONE);
//            operationLayout.setVisibility(View.GONE);
//            idLayout.setVisibility(View.GONE);
//            aMoney.setVisibility(View.GONE);
//            userSex.setVisibility(View.GONE);
//            userGrade.setVisibility(View.GONE);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.icon_scale_out);
            iconLayout.setAnimation(animation);
            animation.setDuration(300);
            animation.setFillAfter(true);
            iconLayout.setVisibility(View.GONE);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            ObjectAnimator animator = ObjectAnimator.ofFloat(iconLayout, "scaleX", )

//            iconLayout.animate().scaleX(0).scaleY(0).setDuration(300).setListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
////                    iconLayout.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//
//                }
//            }).start();
            nameLayout.animate().translationY(-20).setDuration(300).start();
            numLayout.animate().translationY(-20).setDuration(300).start();

//            fragmengContainer.setVisibility(View.VISIBLE);
        } else {
//            report.setVisibility(View.VISIBLE);
//            operationLayout.setVisibility(View.VISIBLE);
//            idLayout.setVisibility(View.VISIBLE);
//            aMoney.setVisibility(View.VISIBLE);
//            userSex.setVisibility(View.VISIBLE);
//            userGrade.setVisibility(View.VISIBLE);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.icon_scale_in);
            iconLayout.setAnimation(animation);
            animation.setDuration(300);
            animation.setFillAfter(true);
            iconLayout.setVisibility(View.VISIBLE);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            iconLayout.animate().scaleX(1).scaleY(1).setDuration(300).setListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
////                    iconLayout.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//
//                }
//            }).start();
//            iconLayout.animate().scaleX(1f).scaleY(1f).setDuration(300).start();
            nameLayout.animate().translationY(0).setDuration(0).start();
            numLayout.animate().translationY(0).setDuration(0).start();

//            fragmengContainer.setVisibility(View.GONE);
        }

        if (isShow && personFragment == null) {
            personFragment = new PersonWrapperFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, personFragment).commit();
        }
        ValueAnimator ballBouncer = ValueAnimator.ofInt(startValue, endValue);
        ballBouncer
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator ballBouncer) {
                        int animatedValue = (Integer) ballBouncer
                                .getAnimatedValue();
                        Log.e("zmy", "anvalue   = "+animatedValue + "    isshow = "+isShow  + "   startValue = "+startValue);
                        if (!isShow && animatedValue <= startValue / 4 && report.getVisibility() == View.GONE) {
//                            Log.e("zmy", "in = "+animatedValue);
                            report.setVisibility(View.VISIBLE);
                            operationLayout.setVisibility(View.VISIBLE);
                            idLayout.setVisibility(View.VISIBLE);
                            aMoney.setVisibility(View.VISIBLE);
                            userSex.setVisibility(View.VISIBLE);
                            userGrade.setVisibility(View.VISIBLE);
                        }
                        if (!isShow && animatedValue == startValue) {
//                            operationLayout.setVisibility(View.VISIBLE);
                        }

                        if (isShow && animatedValue >= endValue / 8 && report.getVisibility() == View.VISIBLE) {
                            report.setVisibility(View.GONE);
                            operationLayout.setVisibility(View.GONE);
                            idLayout.setVisibility(View.GONE);
                            aMoney.setVisibility(View.GONE);
                            userSex.setVisibility(View.GONE);
                            userGrade.setVisibility(View.GONE);
                        }

                        if (!isShow && animatedValue == endValue) {
//                            operationLayout.setVisibility(View.GONE);
                        }


                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) fragmengContainer.getLayoutParams();
                        lp.height = animatedValue;
                        fragmengContainer.setLayoutParams(lp);
                        fragmengContainer.invalidate();
                    }
                });
        ballBouncer.setDuration(300);
        ballBouncer.start();
    }

    private void startCloseAnim(final boolean isShow) {
        isShowClose = isShow;
//        if (isShowClose) {
//            closeDesc.setVisibility(View.VISIBLE);
//        } else {
//            closeDesc.setVisibility(View.GONE);
//        }

        final int startValue = isShow ? 0 : 200;
        final int endValue = isShow ? 200 : 0;

        ValueAnimator ballBouncer = ValueAnimator.ofInt(startValue, endValue);
        ballBouncer.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator ballBouncer) {
                final int animatedValue = (Integer) ballBouncer
                        .getAnimatedValue();
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) closeDesc.getLayoutParams();
                lp.height = animatedValue;
                closeDesc.setLayoutParams(lp);
                closeDesc.invalidate();
            }
        });

        ballBouncer.setDuration(300);
        ballBouncer.start();
    }

    private void startAttentionAnim(final boolean showBottomView) {
        initAttentionWidth();
        final int startValue = showBottomView ? attentionWidth : hasAttentionWidth;
        final int endValue = showBottomView ? hasAttentionWidth : attentionWidth;
        if (showBottomView) {
            attentionBottomLayout.setVisibility(View.VISIBLE);
        } else {
            attentionBottomLayout.setVisibility(View.GONE);
        }

        ValueAnimator ballBouncer = ValueAnimator.ofInt(startValue, endValue);
        ballBouncer.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator ballBouncer) {
                int animatedValue = (Integer) ballBouncer
                        .getAnimatedValue();
                if (showBottomView) {
                    if (animatedValue == endValue) {
                        attentionBtn.setVisibility(View.GONE);
                    }
                } else {
                    if (animatedValue == startValue) {
                        attentionBtn.setVisibility(View.VISIBLE);
                    }
                }
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) attentionBtn.getLayoutParams();
                lp.width = animatedValue;
                attentionBtn.setLayoutParams(lp);
                attentionBtn.invalidate();
            }
        });

        ballBouncer.setDuration(500);
        ballBouncer.start();
    }
}
