package com.zmy.gradledemo.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zmy.gradledemo.R;

public class AnimActivity extends AppCompatActivity {

    private ImageView imageView, imageView2;
    private FrameLayout tb_layout;
    private boolean toImage = true;
    private Button btn;
    AnimatorSet setRightOut;
    AnimatorSet setLeftIn;
    AnimatorSet setLeftOut;
    AnimatorSet setRightIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        imageView = (ImageView) findViewById(R.id.image);
        imageView2 = (ImageView) findViewById(R.id.image2);
        tb_layout = (FrameLayout) findViewById(R.id.tb_layout);
        btn = (Button) findViewById(R.id.btn);

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                image2Anim();
            }
        });

        setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.rotate_right_out);

        setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.rotate_left_in);

        setLeftOut = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.rotate_left_out);

        setRightIn = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(),
                R.animator.rotate_right_in);

        tb_layout.setAlpha(0);
    }

    private int full_time = 500;
    private int half_time = full_time / 2;

    public void btnClick(View v) {
        toImage = !toImage;
//        useXmlSetting();
//        useCodeSetting();
        useViewAnimSetting();
    }

    private void useViewAnimSetting() {
        if (toImage) {
            //left in
//            imageView.animate().alphaBy(0).alpha(1).setStartDelay(half_time).setDuration(1).start();
            imageView.animate().rotationYBy(-180).rotationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(full_time).start();
            ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 0, 1);
            alpha.setStartDelay(half_time);
            alpha.setDuration(1);
            alpha.start();
            //right out
//            tb_layout.animate().alphaBy(1).alpha(0).setStartDelay(half_time).setDuration(1).start();
            tb_layout.animate().rotationYBy(0).rotationY(180).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(full_time).start();
            ObjectAnimator alpha2 = ObjectAnimator.ofFloat(tb_layout, "alpha", 1, 0);
            alpha2.setStartDelay(half_time);
            alpha2.setDuration(1);
            alpha2.start();
        } else {
            //left out
//            imageView.animate().alphaBy(1).alpha(0).setStartDelay(half_time).setDuration(1).start();
            imageView.animate().rotationYBy(0).rotationY(-180).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(full_time).start();
            ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1, 0);
            alpha.setStartDelay(half_time);
            alpha.setDuration(1);
            alpha.start();

            //right in
//            tb_layout.animate().alphaBy(0).alpha(1).setStartDelay(half_time).setDuration(1).start();
            tb_layout.animate().rotationYBy(180).rotationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(full_time).start();
            ObjectAnimator alpha2 = ObjectAnimator.ofFloat(tb_layout, "alpha", 0, 1);
            alpha2.setStartDelay(half_time);
            alpha2.setDuration(1);
            alpha2.start();
        }
    }

    private void useCodeSetting() {
        if (toImage) {
            //left in
            ObjectAnimator rotate = ObjectAnimator.ofFloat(imageView, "rotationY", -180, 0);
            rotate.setInterpolator(new AccelerateDecelerateInterpolator());
            rotate.setDuration(full_time);
            rotate.start();
//            imageView.animate().rotationYBy(-180).rotationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(full_time).start();
            imageView.animate().alphaBy(0).alpha(1).setStartDelay(half_time).setDuration(1).start();
            //right out
            ObjectAnimator rotate2 = ObjectAnimator.ofFloat(tb_layout, "rotationY", 0, 180);
            rotate2.setInterpolator(new AccelerateDecelerateInterpolator());
            rotate2.setDuration(full_time);
            rotate2.start();
//            tb_layout.animate().rotationYBy(0).rotationY(180).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(full_time).start();
            tb_layout.animate().alphaBy(1).alpha(0).setStartDelay(half_time).setDuration(1).start();
        } else {
            //left out
            ObjectAnimator rotate = ObjectAnimator.ofFloat(imageView, "rotationY", 0, -180);
            rotate.setInterpolator(new AccelerateDecelerateInterpolator());
            rotate.setDuration(full_time);
            rotate.start();
//            imageView.animate().rotationYBy(0).rotationY(-180).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(full_time).start();
            imageView.animate().alphaBy(1).alpha(0).setStartDelay(half_time).setDuration(1).start();

            //right in
            ObjectAnimator rotate2 = ObjectAnimator.ofFloat(tb_layout, "rotationY", 180, 0);
            rotate2.setInterpolator(new AccelerateDecelerateInterpolator());
            rotate2.setDuration(full_time);
            rotate2.start();
//            tb_layout.animate().rotationYBy(180).rotationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(full_time).start();
            tb_layout.animate().alphaBy(0).alpha(1).setStartDelay(half_time).setDuration(1).start();
        }
    }

    private void useXmlSetting() {
        if (toImage) {
            setLeftIn.setTarget(imageView);
            setRightOut.setTarget(tb_layout);

            setLeftIn.start();
            setRightOut.start();
        } else {
            setLeftOut.setTarget(imageView);
            setRightIn.setTarget(tb_layout);

            setLeftOut.start();
            setRightIn.start();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void image2Anim() {
        int cx = (imageView2.getLeft() + imageView2.getRight()) / 2;
        int cy = (imageView2.getTop() + imageView2.getBottom()) / 2;

        int finalRadius = Math.max(imageView2.getWidth(), imageView2.getHeight());
        int initialRadius = imageView2.getWidth();

        if (imageView2.getVisibility() == View.VISIBLE) {
            // create the animation (the final radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(imageView2, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    imageView2.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(imageView2, cx, cy, 0, finalRadius);
            // make the view visible and start the animation
            imageView2.setVisibility(View.VISIBLE);
            anim.start();
        }
    }
}
