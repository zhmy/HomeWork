package com.zmy.library;

import android.view.View;

public abstract class BdIListPage {

	private View mView = null;

	public abstract View createView();

	public final View getView() {
		if (mView == null) {
			mView = createView();
		}
		return mView;
	}

	public abstract void onClick();
}
