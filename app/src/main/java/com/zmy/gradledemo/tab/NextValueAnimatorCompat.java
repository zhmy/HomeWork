package com.zmy.gradledemo.tab;

import android.view.animation.Interpolator;

/**
 * Created by zmy on 2016/11/11.
 */

public class NextValueAnimatorCompat {

    interface AnimatorUpdateListener {
        /**
         * <p>Notifies the occurrence of another frame of the animation.</p>
         *
         * @param animator The animation which was repeated.
         */
        void onAnimationUpdate(NextValueAnimatorCompat animator);
    }

    /**
     * An animation listener receives notifications from an animation.
     * Notifications indicate animation related events, such as the end or the
     * repetition of the animation.
     */
    interface AnimatorListener {
        /**
         * <p>Notifies the start of the animation.</p>
         *
         * @param animator The started animation.
         */
        void onAnimationStart(NextValueAnimatorCompat animator);
        /**
         * <p>Notifies the end of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.</p>
         *
         * @param animator The animation which reached its end.
         */
        void onAnimationEnd(NextValueAnimatorCompat animator);
        /**
         * <p>Notifies the cancellation of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.</p>
         *
         * @param animator The animation which was canceled.
         */
        void onAnimationCancel(NextValueAnimatorCompat animator);
    }

    static class AnimatorListenerAdapter implements NextValueAnimatorCompat.AnimatorListener {
        @Override
        public void onAnimationStart(NextValueAnimatorCompat animator) {
        }

        @Override
        public void onAnimationEnd(NextValueAnimatorCompat animator) {
        }

        @Override
        public void onAnimationCancel(NextValueAnimatorCompat animator) {
        }
    }

    interface Creator {
        NextValueAnimatorCompat createAnimator();
    }

    static abstract class Impl {
        interface AnimatorUpdateListenerProxy {
            void onAnimationUpdate();
        }

        interface AnimatorListenerProxy {
            void onAnimationStart();
            void onAnimationEnd();
            void onAnimationCancel();
        }

        abstract void start();
        abstract boolean isRunning();
        abstract void setInterpolator(Interpolator interpolator);
        abstract void setListener(NextValueAnimatorCompat.Impl.AnimatorListenerProxy listener);
        abstract void setUpdateListener(NextValueAnimatorCompat.Impl.AnimatorUpdateListenerProxy updateListener);
        abstract void setIntValues(int from, int to);
        abstract int getAnimatedIntValue();
        abstract void setFloatValues(float from, float to);
        abstract float getAnimatedFloatValue();
        abstract void setDuration(int duration);
        abstract void cancel();
        abstract float getAnimatedFraction();
        abstract void end();
        abstract long getDuration();
    }

    private final NextValueAnimatorCompat.Impl mImpl;

    NextValueAnimatorCompat(NextValueAnimatorCompat.Impl impl) {
        mImpl = impl;
    }

    public void start() {
        mImpl.start();
    }

    public boolean isRunning() {
        return mImpl.isRunning();
    }

    public void setInterpolator(Interpolator interpolator) {
        mImpl.setInterpolator(interpolator);
    }

    public void setUpdateListener(final NextValueAnimatorCompat.AnimatorUpdateListener updateListener) {
        if (updateListener != null) {
            mImpl.setUpdateListener(new NextValueAnimatorCompat.Impl.AnimatorUpdateListenerProxy() {
                @Override
                public void onAnimationUpdate() {
                    updateListener.onAnimationUpdate(NextValueAnimatorCompat.this);
                }
            });
        } else {
            mImpl.setUpdateListener(null);
        }
    }

    public void setListener(final NextValueAnimatorCompat.AnimatorListener listener) {
        if (listener != null) {
            mImpl.setListener(new NextValueAnimatorCompat.Impl.AnimatorListenerProxy() {
                @Override
                public void onAnimationStart() {
                    listener.onAnimationStart(NextValueAnimatorCompat.this);
                }

                @Override
                public void onAnimationEnd() {
                    listener.onAnimationEnd(NextValueAnimatorCompat.this);
                }

                @Override
                public void onAnimationCancel() {
                    listener.onAnimationCancel(NextValueAnimatorCompat.this);
                }
            });
        } else {
            mImpl.setListener(null);
        }
    }

    public void setIntValues(int from, int to) {
        mImpl.setIntValues(from, to);
    }

    public int getAnimatedIntValue() {
        return mImpl.getAnimatedIntValue();
    }

    public void setFloatValues(float from, float to) {
        mImpl.setFloatValues(from, to);
    }

    public float getAnimatedFloatValue() {
        return mImpl.getAnimatedFloatValue();
    }

    public void setDuration(int duration) {
        mImpl.setDuration(duration);
    }

    public void cancel() {
        mImpl.cancel();
    }

    public float getAnimatedFraction() {
        return mImpl.getAnimatedFraction();
    }

    public void end() {
        mImpl.end();
    }

    public long getDuration() {
        return mImpl.getDuration();
    }
}
