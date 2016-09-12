package com.zmy.library;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Scroller;

/**
 * 改变listview padding的动画
 * 
 * @author dengjie
 * 
 */

public class BdPaddingAnimation4ListView {
	private int mStep = 1;
	private int mToPadding;
	private BdOnAnimationOverListener mOverListener;
	private boolean isReadyToStart = true;

	FlingRunnable mFlinger;
	int mDuration;

	public BdPaddingAnimation4ListView(Context context, int startPadding,
									   int toPadding, int duration) {
		int abs = Math.abs(startPadding - toPadding);
		mToPadding = toPadding;
		if (abs < mStep) {
			isReadyToStart = false;
		}
		mFlinger = new FlingRunnable(context);
		mDuration = duration;
	}

	public void setOnAnimationOverListener(BdOnAnimationOverListener l) {
		mOverListener = l;
	}

	View mView;
	Handler mHandler = new Handler();
	Runnable endAnimationRun = new Runnable() {
		@Override
		public void run() {
			if (mFlinger != null) {
				mFlinger.endAnimation();
			}
		}

	};

	class FlingRunnable implements Runnable {
		private int mLastFlingY;
		private Scroller mScroller;

		FlingRunnable(Context context) {
			mScroller = new Scroller(context);
		}

		private void startCommon() {
			if (mHandler != null) {
				mHandler.removeCallbacks(endAnimationRun);
			}
			if (mView != null)
				mView.removeCallbacks(this);
		}

		public void run() {
			if (mView == null || mScroller == null) {
				return;
			}
			boolean bOver = false;
			boolean noFinish = mScroller.computeScrollOffset();
			int elapsed = mScroller.timePassed();
			if (elapsed >= mDuration) {
				noFinish = false;
			}
			// Log.d("test_anim", "elapsed=" + elapsed + ";mDuration=" +
			// mDuration);

			int curY = mScroller.getCurrY();
			int deltaY = curY - mLastFlingY;
			if (noFinish) {
				if (deltaY != 0) {
					if (!move(deltaY)) {
						bOver = true;
					}
					mLastFlingY = curY;
				}
				if (!bOver)
					mView.post(this);
			} else {
				bOver = true;
			}
			if (bOver) {
				mHandler.removeCallbacks(endAnimationRun);
				mHandler.post(endAnimationRun);
			}
		}

		public void startUsingDistance(int distance, int duration) {
			if (mView == null || mScroller == null) {
				return;
			}
			if (distance == 0)
				distance--;
			startCommon();
			mLastFlingY = 0;

			mScroller.startScroll(0, 0, 0, distance, duration);
			mView.post(this);
		}

		private void endAnimation() {
			mHandler.removeCallbacks(endAnimationRun);
			if (mScroller != null) {
				mScroller.abortAnimation();
				mScroller.forceFinished(true);
			}
			if (mView != null) {
				mView.removeCallbacks(this);
			}
			if (null != mOverListener) {
				mOverListener.onOver();
			}
			// mScroller = null;
			// v = null;
		}
	}

	public void startAnimation(final View view) {
		if (!isReadyToStart || mFlinger == null)
			return;
		mView = view;
		mFlinger.startUsingDistance(Math.abs(mToPadding), mDuration);
		mHandler.postDelayed(endAnimationRun, mDuration);
	}

	private boolean move(int deltaY) {
		boolean b = true;
		int paddingTop = mView.getPaddingTop();
		paddingTop -= Math.abs(deltaY);
		// Log.d("test_anim", "deltaY=" + deltaY + ";mToPadding=" + mToPadding);
		if (paddingTop <= mToPadding) {
			paddingTop = mToPadding;
			b = false;
		}
		mView.setPadding(mView.getPaddingLeft(), paddingTop,
				mView.getPaddingRight(), mView.getPaddingBottom());
		return b;
	}

}