package com.zmy.library;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import java.security.InvalidParameterException;

public abstract class BdIListPullView {
	private Context mContext = null;
	private View mView = null;
	private boolean mEnable = true;
	private int mHeadContentHeight = 0;
	private int mHeadContentWidth = 0;

	public abstract View createView();

	public abstract void releaseToRefresh();

	public abstract void pullToRefresh(boolean isBack);

	public abstract void refreshing();

	public abstract void done(boolean success);

	public abstract void onRefresh(boolean auto);

	public BdIListPullView(Context context) {
		if (context == null) {
			throw new InvalidParameterException("BdIListPullView context is null");
		}
		mContext = context;
	}

	public Context getContext() {
		return mContext;
	}

	public final View getView() {
		if (mView == null) {
			mView = createView();
			if (mView == null) {
				throw new IllegalStateException("BdIListPullView getView is null");
			}
			measureView(mView);
			mHeadContentHeight = mView.getMeasuredHeight();
			mHeadContentWidth = mView.getMeasuredWidth();
		}
		return mView;
	}

	public void setPadding(int left, int top, int right, int bottom) {
		if (mView != null) {
			mView.setPadding(left, top, right, bottom);
		}
	}

	public boolean isEnable() {
		return mEnable;
	}

	public void setEnable(boolean enable) {
		this.mEnable = enable;
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public int getHeadContentHeight() {
		return mHeadContentHeight;
	}

	public int getHeadContentWidth() {
		return mHeadContentWidth;
	}
}

