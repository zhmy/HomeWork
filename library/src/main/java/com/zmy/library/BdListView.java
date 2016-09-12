package com.zmy.library;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.security.InvalidParameterException;

/**
 * <h1>BdListView</h1> <h3>说明</h3>
 * <p>
 * 同{@link BdListAdpter}来配合,外部无需关心HeaderView和FooterView的计算等逻辑. 可直接调用
 * {@link #addHeaderView(View)} {@link #addFooterView(View)}等接口直接设置.
 * </p>
 * <p>
 * 封装了预加载和滑动加载等操作,并且封装了下拉刷新等.
 * <p>
 * <h3>使用</h3>
 * <p>
 * 使用的接口和普通的Listview保持一致,不过设置headerView和FooterView的操作可以放在设置Adapter之后.
 * </p>
 * <p>
 * 包含了设置下拉刷新的接口 {@link #setPullRefresh(BdIListPullView)}
 * </p>
 * 
 */
public class BdListView extends ListView {

	public static final int OVER_SCROLL_NEVER = 2;

	private static final int REFRESH_DELAY = 100;

	private BdListAdpter mBdListAdpter = null;
	private OnItemClickListener mOnItemClickListener = null;
	private OnItemLongClickListener mOnItemLongClickListener = null;
	private OnItemSelectedListener mOnItemSelectedListener = null;
	private OnHeaderClickListener mOnHeaderClickListener = null;
	private OnFooterClickListener mOnFooterClickListener = null;
	private OnScrollListener mOnScrollListener = null;
	private OnScrollStopDelayedListener mOnScrollStopDelayedListener = null;
	private OnScrollToPullListener mOnScrollToPullListener = null;
	private long mOnScrollStopDelayedMillis = 100;
	private OnScrollToTopListener mOnScrollToTopListener = null;
	private int mScrollToTopNum = 0;
	private OnScrollToBottomListener mOnScrollToBottomListener = null;
	private OnScrollToBottomListenerEx mExScrollToBottomListener = null;
	private BdIListPage mPrePage = null;
	private BdIListPage mNextPage = null;
	private View mNoDataView = null;
	private int mFirstVisibleItemIndex = 0;

	private Runnable mDelayedRunnable = new Runnable() {

		@Override
		public void run() {

			if (mOnScrollStopDelayedListener != null) {
				int firstVisiblePos = getFirstVisiblePosition();
				int lastVisiblePos = getLastVisiblePosition();
				if (mBdListAdpter != null
						&& mBdListAdpter.getWrappedAdapter() != null
						&& mBdListAdpter.getWrappedCount() > 0) {
					firstVisiblePos -= mBdListAdpter.getHeadersCount();
					if (firstVisiblePos < 0) {
						firstVisiblePos = 0;
					}
					lastVisiblePos -= mBdListAdpter.getHeadersCount();
					if (lastVisiblePos >= mBdListAdpter.getWrappedCount()) {
						lastVisiblePos = mBdListAdpter.getWrappedCount() - 1;
					}
					if (lastVisiblePos < 0) {
						lastVisiblePos = 0;
					}
				} else {
					firstVisiblePos = -1;
					lastVisiblePos = -1;
				}
				mOnScrollStopDelayedListener.onScrollStop(firstVisiblePos,
						lastVisiblePos);
			}

		}
	};

	private Runnable refreshRunnable = new Runnable() {

		@Override
		public void run() {
			int count = getChildCount();
			for (int i = 0; i < count; i++) {
				refreshImage(getChildAt(i));
			}

			IScrollable scrollable = IScrollableHelper.getIScrollable(getContext());
			if (scrollable != null) {
				scrollable.onPreLoad(BdListView.this);
			}
		}

		private void refreshImage(View view) {
			if (view == null) {
				return;
			}
			if (view instanceof IRefresh) {
				IRefresh r = (IRefresh) view;
				r.refresh();
			}
			if (view instanceof ViewGroup) {
				ViewGroup vg = (ViewGroup) view;
				int count = vg.getChildCount();
				for (int i = 0; i < count; i++) {
					refreshImage(vg.getChildAt(i));
				}
			}
		}
	};

	private Runnable preLoadRunnable = new Runnable() {

		@Override
		public void run() {
			IScrollable scrollable = IScrollableHelper.getIScrollable(getContext());
			if (scrollable != null) {
				scrollable.onPreLoad(BdListView.this);
			}
		}
	};

	/**
	 * 构造函数
	 *
	 * @param context
	 */
	public BdListView(Context context) {
		super(context);
		initial();
	}

	/**
	 * 构造函数
	 *
	 * @param context
	 * @param attrs
	 */
	public BdListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initial();
	}

	/**
	 * 构造函数
	 *
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public BdListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initial();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		try {
			super.dispatchDraw(canvas);
		} catch (NullPointerException ex) {
//			BdLog.detailException(ex);
			if (getContext() instanceof Activity) {
				((Activity) getContext()).finish();
			}
		} catch (OutOfMemoryError t) {
//			BdBaseApplication.getInst().onAppMemoryLow();

			if (getContext() instanceof Activity) {
				((Activity) getContext()).finish();
			}
		}
	}

	@Override
	protected void layoutChildren() {
		try {
			super.layoutChildren();
		} catch (NullPointerException ex) {
//			BdLog.detailException(ex);
			if (getContext() instanceof Activity) {
				((Activity) getContext()).finish();
			}
		}
	}

	/**
	 * 刷新资源
	 *
	 * <p>
	 * 场景，当页面重新resume时，被取消的等待资源任务要重新发起请求
	 * </p>
	 */
	// public void refreshResource() {
	// removeCallbacks(refreshRunnable);
	// postDelayed(refreshRunnable, REFRESH_DELAY);
	// }

	/**
	 * 停止刷新资源
	 */
	public void cancelRefresh() {
		removeCallbacks(refreshRunnable);
	}

	private void initial() {
		setFadingEdgeLength(0);

		// 解决魅族手机下拉悬停
		// if (BdUtilHelper.isFlymeOsAbove35()) {
		// try {
		// Method method = AbsListView.class.getMethod("setOverScrollMode",
		// int.class);
		// if (method != null) {
		// method.invoke(this, OVER_SCROLL_NEVER);
		// }
		// } catch (Exception e) {
		// }
		// }

		mBdListAdpter = new BdListAdpter(getContext());
		mBdListAdpter.setListPreLoad(new BdListAdpter.BdListPreLoad() {

			@Override
			public void onPreLoad() {
				removeCallbacks(preLoadRunnable);
				postDelayed(preLoadRunnable, REFRESH_DELAY);
			}
		});
		super.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				int numHeaders = mBdListAdpter.getHeadersCount();
				if (position < numHeaders) {
					if (mPrePage != null && view == mPrePage.getView()) {
						mPrePage.onClick();
					} else if (mOnHeaderClickListener != null) {
						mOnHeaderClickListener.onClick(view);
					}
					return;
				}
				// Adapter
				final int adjPosition = position - numHeaders;
				int adapterCount = 0;
				ListAdapter adapter = mBdListAdpter.getWrappedAdapter();
				if (adapter != null) {
					adapterCount = adapter.getCount();
					if (adjPosition < adapterCount) {
						if (mOnItemClickListener != null) {
							mOnItemClickListener.onItemClick(parent, view,
									adjPosition, id);
						}
						return;
					}
				}

				// Footer (off-limits positions will throw an
				// ArrayIndexOutOfBoundsException)
				if (mNextPage != null && view == mNextPage.getView()) {
					mNextPage.onClick();
				} else if (mOnFooterClickListener != null) {
					mOnFooterClickListener.onClick(view);
				}
			}
		});
		super.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				IScrollable ba = IScrollableHelper.getIScrollable(getContext());

				if (ba != null) {
					if (scrollState == OnScrollListener.SCROLL_STATE_FLING) {
						removeCallbacks(refreshRunnable);
						ba.setIsScroll(true);
					} else {
						if (ba.isScroll()) {
							ba.setIsScroll(false);
							removeCallbacks(refreshRunnable);
							postDelayed(refreshRunnable, REFRESH_DELAY);
						} else if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
							removeCallbacks(preLoadRunnable);
							postDelayed(preLoadRunnable, REFRESH_DELAY);
						}
					}
				}

				if (mOnScrollListener != null) {
					mOnScrollListener.onScrollStateChanged(view, scrollState);
				}

				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {

					if (mOnScrollToBottomListener != null
							&& view.getLastVisiblePosition() == view.getCount() - 1
							&& (view.getCount() - getHeaderViewsCount() - getFooterViewsCount()) != 0) {
						mOnScrollToBottomListener.onScrollToBottom();
					}
					if (mExScrollToBottomListener != null
							&& view.getLastVisiblePosition() == view.getCount() - 1
							&& view.getFirstVisiblePosition() != 0) {
						mExScrollToBottomListener.onScrollToBottom(BdListView.this);
					}
					if (mOnScrollToTopListener != null
							&& view.getFirstVisiblePosition() <= mScrollToTopNum) {
						mOnScrollToTopListener.onScrollToTop();
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				mFirstVisibleItemIndex = firstVisibleItem;

				if (mOnScrollListener != null) {
					mOnScrollListener.onScroll(view, firstVisibleItem,
							visibleItemCount, totalItemCount);
				}
				if (mOnScrollStopDelayedListener != null
						&& mOnScrollStopDelayedMillis > 0) {
					getHandler().removeCallbacks(mDelayedRunnable);
					getHandler().postDelayed(mDelayedRunnable,
							mOnScrollStopDelayedMillis);
				}
			}
		});
	}

	/**
	 * 设置OnScrollToPullListener
	 *
	 * @param onScrollToPullListener
	 */
	public void setOnScrollToPullListener(
			OnScrollToPullListener onScrollToPullListener) {
		this.mOnScrollToPullListener = onScrollToPullListener;
	}

	/**
	 * 设置滑动到顶部的监听
	 *
	 * @param l
	 */
	public void setOnSrollToTopListener(OnScrollToTopListener l) {
		mOnScrollToTopListener = l;
	}

	/**
	 * 设置滑动到某一个顶部item的监听，并设指定该"顶部item"在listview中具体位置
	 *
	 * @param l
	 * @param num
	 *            被当做"顶部item"的具体序号
	 */
	public void setOnSrollToTopListener(OnScrollToTopListener l, int num) {
		mOnScrollToTopListener = l;
		num--;
		if (num < 0) {
			num = 0;
		}
		mScrollToTopNum = num;
	}

	/**
	 * 设置滑动到底部的监听
	 *
	 * @param l
	 */
	public void setOnSrollToBottomListener(OnScrollToBottomListener l) {
		mOnScrollToBottomListener = l;
	}

	/**
	 * 设置滑动到底部的监听 <br>
	 * 区别
	 *
	 * @param l
	 */
	public void setExOnSrollToBottomListener(OnScrollToBottomListenerEx l) {
		mExScrollToBottomListener = l;
	}

	/**
	 * 设置滑动停止后的listener，并设置该listener开始执行的延迟时间
	 *
	 * @param l
	 * @param delayMillis
	 */
	public void setOnScrollStopDelayedListener(OnScrollStopDelayedListener l,
											   long delayMillis) {
		mOnScrollStopDelayedListener = l;
		mOnScrollStopDelayedMillis = delayMillis;
	}

	/**
	 * 设置Item的长按点击事件监听
	 *
//	 * @see android.widget.AdapterView#setOnItemLongClickListener(android.widget.
	 *      AdapterView.OnItemLongClickListener)
	 */
	@Override
	public void setOnItemLongClickListener(OnItemLongClickListener listener) {
		if (listener == null) {
			super.setOnItemLongClickListener(null);
		} else {
			mOnItemLongClickListener = listener;
			super.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
											   View view, int position, long id) {
					int numHeaders = mBdListAdpter.getHeadersCount();
					if (position < numHeaders) {
						return true;
					}
					// Adapter
					final int adjPosition = position - numHeaders;
					int adapterCount = 0;
					ListAdapter adapter = mBdListAdpter.getWrappedAdapter();
					if (adapter != null) {
						adapterCount = adapter.getCount();
						if (adjPosition < adapterCount) {
							if (mOnItemLongClickListener != null) {
								return mOnItemLongClickListener
										.onItemLongClick(parent, view,
												adjPosition, id);
							} else {
								return false;
							}
						}
					}
					return true;
				}
			});
		}
	}

	/**
	 * 设置Item选择状态变化的监听
	 *
//	 * @see android.widget.AdapterView#setOnItemSelectedListener(android.widget.
	 *      AdapterView.OnItemSelectedListener)
	 */
	@Override
	public void setOnItemSelectedListener(OnItemSelectedListener listener) {
		if (listener == null) {
			super.setOnItemSelectedListener(null);
		} else {
			mOnItemSelectedListener = listener;
			super.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
										   int position, long id) {
					int numHeaders = mBdListAdpter.getHeadersCount();
					if (position < numHeaders) {
						return;
					}
					// Adapter
					final int adjPosition = position - numHeaders;
					int adapterCount = 0;
					ListAdapter adapter = mBdListAdpter.getWrappedAdapter();
					if (adapter != null) {
						adapterCount = adapter.getCount();
						if (adjPosition < adapterCount) {
							if (mOnItemSelectedListener != null) {
								mOnItemSelectedListener.onItemSelected(parent,
										view, adjPosition, id);
							}
						}
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					if (mOnItemSelectedListener != null) {
						mOnItemSelectedListener.onNothingSelected(parent);
					}
				}
			});
		}
	}

	/**
	 * 设置Listview滚动的监听
	 *
//	 * @see android.widget.AbsListView#setOnScrollListener(android.widget.AbsListView
	 *      .OnScrollListener)
	 */
	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mOnScrollListener = l;
	}

	/**
	 * 设置Item的点击事件监听
	 *
	 * @see android.widget.AdapterView#setOnItemClickListener(android.widget.AdapterView.OnItemClickListener)
	 */
	@Override
	public void setOnItemClickListener(OnItemClickListener listener) {
		mOnItemClickListener = listener;
	}

	/**
	 * 获取当前Listview使用的Adapter
	 *
	 * @see android.widget.ListView#getAdapter()
	 */
	@Override
	public ListAdapter getAdapter() {
		return mBdListAdpter;
	}

	/**
	 * 获取该Listview对应的被包装的Adapter
	 *
	 * @return
	 */
	public ListAdapter getWrappedAdapter() {
		if (mBdListAdpter instanceof BdListAdpter) {
			return ((BdListAdpter) mBdListAdpter).getWrappedAdapter();
		}

		return null;
	}

	/**
	 * 给该Listview设置Adapter
	 *
	 * @see android.widget.ListView#setAdapter(android.widget.ListAdapter)
	 */
	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(null);
		mBdListAdpter.setAdaper(adapter);
		super.setAdapter(mBdListAdpter);

		// BdLog.i("activity="+this.getContext().getClass().getName());
		// BdLog.i("adapter="+adapter.getClass().getName());
	}

	/**
	 * 设置headerview的点击监听
	 *
	 * @param listener
	 */
	public void setOnHeaderClickListener(OnHeaderClickListener listener) {
		mOnHeaderClickListener = listener;
	}

	/**
	 * 设置FooterView的点击事件监听
	 *
	 * @param listener
	 */
	public void setOnFooterClickListener(OnFooterClickListener listener) {
		mOnFooterClickListener = listener;
	}

	/**
	 * 添加一个HeadView
	 *
	 * @see android.widget.ListView#addHeaderView(android.view.View,
	 *      java.lang.Object, boolean)
	 */
	@Override
	public void addHeaderView(View v, Object data, boolean isSelectable) {
		mBdListAdpter.addHeaderView(v, data, isSelectable, getHeaderIndex());
	}

	/**
	 * 添加一个HeadView
	 *
	 * @see android.widget.ListView#addHeaderView(android.view.View)
	 */
	/**
	 * 添加一个HeadView
	 *
	 * @see android.widget.ListView#addHeaderView(android.view.View)
	 */
	@Override
	public void addHeaderView(View v) {
//		super.addHeaderView(v);
		mBdListAdpter.addHeaderView(v, getHeaderIndex());
	}

	/**
	 * 按照顺序添加一个HeaView
	 *
	 * @param v
	 * @param index
	 */
	public void addHeaderView(View v, int index) {
		mBdListAdpter.addHeaderView(v, index);
	}

	/**
	 * 得到该Listview对应的HeadView的数目
	 *
	 * @see android.widget.ListView#getHeaderViewsCount()
	 */
	@Override
	public int getHeaderViewsCount() {
		return mBdListAdpter.getHeaderViewsCount();
	}

	/**
	 * 得到该Listview对应的FooterView的数目
	 *
	 * @see android.widget.ListView#getFooterViewsCount()
	 */
	@Override
	public int getFooterViewsCount() {
		return mBdListAdpter.getFooterViewsCount();
	}

	/**
	 * 添加一个下拉刷新View
	 *
	 * @param v
	 */
	public void addPullRefreshView(View v) {
		mBdListAdpter.addHeaderView(v, null, false, 0);
	}

	/**
	 * 添加一个FooterView
	 *
	 * @see android.widget.ListView#addFooterView(android.view.View,
	 *      java.lang.Object, boolean)
	 */
	@Override
	public void addFooterView(View v, Object data, boolean isSelectable) {
		mBdListAdpter.addFooterView(v, data, isSelectable, -1);
	}

	/**
	 * 添加一个FooterView
	 *
	 * @see android.widget.ListView#addFooterView(android.view.View)
	 */
	@Override
	public void addFooterView(View v) {
		super.addFooterView(v);
//		mBdListAdpter.addFooterView(v);
	}

	/**
	 * 移除一个HeaderView
	 *
	 * @see android.widget.ListView#removeHeaderView(android.view.View)
	 */
	@Override
	public boolean removeHeaderView(View v) {
		return mBdListAdpter.removeHeader(v);
	}

	/**
	 * 移除一个FooterView
	 *
	 * @see android.widget.ListView#removeFooterView(android.view.View)
	 */
	@Override
	public boolean removeFooterView(View v) {
		return mBdListAdpter.removeFooter(v);
	}

	/**
	 * 给该Listview设置无数据TextView <br>
	 * 该TextView是作为HeaderView
	 *
	 * @param text
	 */
	public void setNoData(String text) {
		if (text != null) {
			TextView v = new TextView(getContext());
			v.setText(text);
			v.setTextSize(16);
			v.setGravity(Gravity.CENTER);
			setNoData(v);
		}
	}

	/**
	 * 给该Listview设置无数据的View
	 *
	 * @param v
	 */
	public void setNoData(View v) {
		if (mNoDataView != null) {
			removeHeaderView(mNoDataView);
			mNoDataView = null;
		}
		if (null != v) {
			addHeaderView(v, null, false);
			mNoDataView = v;
		}
	}

	/**
	 * 设置"上一页"
	 *
	 * @param page
	 */
	public void setPrePage(BdIListPage page) {
		if (mPrePage != null) {
			removeHeaderView(mPrePage.getView());
			mPrePage = null;
		}
		if (page != null) {
			addHeaderView(page.getView());
			mPrePage = page;
		}
	}

	@Override
	public void setSelectionFromTop(int position, int y) {
		super.setSelectionFromTop(position, y);
	}

	/**
	 * 设置"下一页"
	 *
	 * @param page
	 */
	public void setNextPage(BdIListPage page) {
		if (mNextPage != null) {
			removeFooterView(mNextPage.getView());
			mNextPage = null;
		}
		if (page != null) {
			mBdListAdpter.addFooterView(page.getView(), null, true, 0);
			mNextPage = page;
		}
	}

	private int getHeaderIndex() {
		if (mPrePage != null) {
			return mBdListAdpter.getHeadersCount() - 1;
		} else {
			return -1;
		}
	}

	/**
	 * HeaderView点击事件的监听接口
	 *
	 */
	public interface OnHeaderClickListener {
		public void onClick(View v);
	}

	/**
	 * FooterView点击事件的监听接口
	 *
	 */
	public interface OnFooterClickListener {
		/**
		 * 点击事件
		 *
		 * @param v
		 */
		public void onClick(View v);
	}

	@Override
	protected void onDetachedFromWindow() {
		try {
			super.onDetachedFromWindow();
			getHandler().removeCallbacks(mDelayedRunnable);
			getHandler().removeCallbacks(mSelectRunnable);
		} catch (Exception e) {
//			BdLog.e(e.getMessage());
		}
	}

	/**
	 * 滑动去刷新的监听器
	 *
	 * @author chenrensong
	 *
	 */
	public interface OnScrollToPullListener {
		public void onScrollToPull(boolean isPulling);
	}

	/**
	 * 滑动到顶部的监听
	 *
	 */
	public interface OnScrollToTopListener {
		public void onScrollToTop();
	}

	/**
	 * 滑动到底部的监听 <br>
	 * 注意同{@link OnScrollToBottomListenerEx}的不同:本接口的回调函数
	 * {@link #onScrollToBottom()}是无参数的. <br>
	 * 接口{@link OnScrollToBottomListenerEx}的回调函数中会将BdListView作为参数传递
	 *
	 */
	public interface OnScrollToBottomListener {
		/**
		 * 滑动到底部的回调函数
		 */
		public void onScrollToBottom();
	}

	/**
	 * 滑动到底部的监听 <br>
	 * 注意同{@link OnScrollToBottomListener}的不同:本接口的回调函数
	 * {@link #onScrollToBottom(BdListView listView)}
	 * 是有参数的,会将BdListView作为参数传递给回调函数.
	 *
	 */
	public interface OnScrollToBottomListenerEx {
		/**
		 * 滑动到底部的回调函数,会将BdListView的引用当做参数传递给该函数
		 *
		 * @param listView
		 */
		public void onScrollToBottom(BdListView listView);
	}

	/**
	 * Listview滚动停止后延迟加载的监听 <br>
	 *
	 */
	public interface OnScrollStopDelayedListener {
		public void onScrollStop(int firstVisiblePos, int lastVisiblePos);
	}

	// /////////////////////////////////////////////////////////////////////////
	// 键盘相关
	public static final byte KEYBOARD_STATE_SHOW = -3;
	public static final byte KEYBOARD_STATE_HIDE = -2;
	public static final byte KEYBOARD_STATE_INIT = -1;
	private boolean mLayoutHasInit = false;
	private boolean mHasKeybord = false;
	private boolean mKeybordScrollBottom = false;
	private int mMaxHeight = 0;
	private int mPreHeight = 0;
	private OnKybdsChangeListener mOnKybdsChangeListener = null;

	private Runnable mSelectRunnable = new Runnable() {

		@Override
		public void run() {
			setSelection(getCount() - 1);
		}
	};

	/**
	 * 设置键盘弹出后,键盘在Listview的下面而不是遮盖Listview
	 *
	 * @param scroll
	 */
	public void setKybdsScrollBottom(boolean scroll) {
		mKeybordScrollBottom = scroll;
	}

	/**
	 * 键盘改变时的监听接口
	 *
	 */
	public interface OnKybdsChangeListener {
		public void onKeyBoardStateChange(int state);
	}

	/**
	 * 设置键盘状态改变的监听
	 *
	 * @param listener
	 */
	public void setOnkbdStateListener(OnKybdsChangeListener listener) {
		mOnKybdsChangeListener = listener;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int last = getLastVisiblePosition();
		try {
			super.onLayout(changed, l, t, r, b);
		} catch (Throwable ex) {
//			BdLog.e(ex.getMessage());
			if (getContext() instanceof Activity) {
				((Activity) getContext()).finish();
			}
			return;
		}
		if (!mLayoutHasInit) {
			mLayoutHasInit = true;
			mMaxHeight = b;
			if (mOnKybdsChangeListener != null) {
				mOnKybdsChangeListener
						.onKeyBoardStateChange(KEYBOARD_STATE_INIT);
			}
		} else {
			mMaxHeight = mMaxHeight < b ? b : mMaxHeight;
		}
		if (mLayoutHasInit && mMaxHeight > b && b != mPreHeight) {
			mHasKeybord = true;
			if (mOnKybdsChangeListener != null) {
				mOnKybdsChangeListener
						.onKeyBoardStateChange(KEYBOARD_STATE_SHOW);
			}
			if (mKeybordScrollBottom == true && last >= getCount() - 1) {
				getHandler().postDelayed(mSelectRunnable, 1);
			}
		}
		if (mLayoutHasInit && mHasKeybord && mMaxHeight == b) {
			mHasKeybord = false;
			if (mOnKybdsChangeListener != null) {
				mOnKybdsChangeListener
						.onKeyBoardStateChange(KEYBOARD_STATE_HIDE);
			}
		}
		mPreHeight = b;
	}

	// //////////////////////////////////////////////////////////////////////////////
	// 下拉刷新相关
	private PullRefresh mPullRefresh = null;

	/**
	 * 设置下拉刷新的View
	 *
	 * @param view
	 */
	public void setPullRefresh(BdIListPullView view) {
		if (mPullRefresh != null) {
			removeHeaderView(mPullRefresh.getBdIListPullView().getView());
		}
		mPullRefresh = null;
		if (view != null) {
			mPullRefresh = new PullRefresh(this, view);
			mPullRefresh.setOnScrollToPullListener(mOnScrollToPullListener);
		}
	}

	/**
	 * 完成下拉刷新
	 */
	public void completePullRefresh() {
		// if (mPullRefresh != null) {
		// mPullRefresh.done();
		// }
		if (mPullRefresh != null) {
			mPullRefresh.animatePullView();
		}
	}

	/**
	 * 开始下拉刷新
	 */
	public void startPullRefresh() {
		if (mPullRefresh != null) {
			setSelection(0);
			mPullRefresh.startPullRefresh(true);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mPullRefresh != null) {
			mPullRefresh.onInterceptTouchEvent(ev, mFirstVisibleItemIndex);
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mPullRefresh != null) {
			mPullRefresh.onTouchEvent(event, mFirstVisibleItemIndex);
		}
		boolean ret = false;
		try {
			ret = super.onTouchEvent(event);
		} catch (Exception e) {
//			BdLog.e(e.getMessage());
		}
		return ret;
	}

	/**
	 * 设置下拉刷新的比率
	 *
	 * @param ratio
	 * @return
	 */
	public static boolean setPullRefreshRatio(float ratio) {
		return PullRefresh.setRatio(ratio);
	}

	/**
	 * 下拉刷新是否刷新结束。
	 */
	public boolean isRefreshDone() {
		if (mPullRefresh != null) {
			return mPullRefresh.mState == PullRefresh.DONE;
		}

		return true;
	}

	private static class PullRefresh {

		private final static int RELEASE_TO_REFRESH = 0;
		private final static int PULL_TO_REFRESH = 1;
		private final static int REFRESHING = 2;
		private final static int DONE = 3;

		// 实际的padding的距离与界面上偏移距离的比例
		private static float sRatio = 3;

		private BdIListPullView mBdIListPullView = null;
		private boolean mIsRecored = false;
		private int mStartY = 0;
		protected int mState = DONE;
		private BdListView mListView = null;
		private Boolean mIsBack = false;
		/**
		 * @author chenrensong 滑动去加载Listener
		 */
		private OnScrollToPullListener mOnScrollToPullListener = null;

		public static boolean setRatio(float ratio) {
			if (ratio > 0) {
				sRatio = ratio;
				return true;
			} else {
				return false;
			}
		}

		public PullRefresh(BdListView listView, BdIListPullView view) {
			if (view == null) {
				throw new InvalidParameterException("PullRefresh view is null");
			}
			if (listView == null) {
				throw new InvalidParameterException(
						"PullRefresh listView is null");
			}
			mBdIListPullView = view;
			mListView = listView;
			View headView = mBdIListPullView.getView();
			headView.setPadding(0, -mBdIListPullView.getHeadContentHeight(), 0,
					0);
			headView.invalidate();
			mListView.addPullRefreshView(headView);
		}

		public BdIListPullView getBdIListPullView() {
			return mBdIListPullView;
		}

		public void done() {
			mState = DONE;
			mBdIListPullView.setPadding(0,
					-mBdIListPullView.getHeadContentHeight(), 0, 0);
			mBdIListPullView.done(true);
			if (mOnScrollToPullListener != null) {
				mOnScrollToPullListener.onScrollToPull(false);
			}
		}

		public void startPullRefresh(boolean auto) {
			mState = REFRESHING;
			mBdIListPullView.setPadding(0, 0, 0, 0);
			mBdIListPullView.refreshing();
			mBdIListPullView.onRefresh(auto);
		}

		public void setOnScrollToPullListener(
				OnScrollToPullListener onScrollToPullListener) {
			this.mOnScrollToPullListener = onScrollToPullListener;
		}

		public void onInterceptTouchEvent(MotionEvent ev, int firstItemIndex) {
			if (ev.getAction() == MotionEvent.ACTION_DOWN
					&& getBdIListPullView().isEnable()) {
				mIsRecored = false;
				mIsBack = false;
				if (firstItemIndex == 0 && !mIsRecored) {
					mIsRecored = true;
					mStartY = (int) ev.getY();
				}
			}
			return;
		}

		public void onTouchEvent(MotionEvent event, int firstItemIndex) {
			if (getBdIListPullView().isEnable()) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL:
						if (mState != REFRESHING) {
							if (mState == PULL_TO_REFRESH) {
								mState = DONE;
								mBdIListPullView.setPadding(0,
										-mBdIListPullView.getHeadContentHeight(),
										0, 0);
								mBdIListPullView.done(false);
								if (mOnScrollToPullListener != null) {
									mOnScrollToPullListener.onScrollToPull(true);
								}
							} else if (mState == RELEASE_TO_REFRESH) {
								startPullRefresh(false);
								// animatePullView();
								if (mOnScrollToPullListener != null) {
									mOnScrollToPullListener.onScrollToPull(true);
								}
							} else if (mState == DONE) {
								if (mOnScrollToPullListener != null) {
									mOnScrollToPullListener.onScrollToPull(false);
								}
							}

						}

						break;

					case MotionEvent.ACTION_MOVE:
						int tempY = (int) event.getY();

						if (!mIsRecored && firstItemIndex == 0) {
							mIsRecored = true;
							mStartY = tempY;
						}

						if (mState != REFRESHING && mIsRecored) {

							// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

							// 可以松手去刷新了
							if (mState == RELEASE_TO_REFRESH) {
								mListView.setSelection(0);
								// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
								if (((int) ((tempY - mStartY) / sRatio) < mBdIListPullView
										.getHeadContentHeight())
										&& (tempY - mStartY) > 0) {
									mState = PULL_TO_REFRESH;
									mBdIListPullView.pullToRefresh(mIsBack);
									mIsBack = false;
									if (mOnScrollToPullListener != null) {
										mOnScrollToPullListener
												.onScrollToPull(true);
									}
								} else if (tempY - mStartY <= 0) { // 一下子推到顶了
									mState = DONE;
									mBdIListPullView.setPadding(0,
											-mBdIListPullView
													.getHeadContentHeight(), 0, 0);
									mBdIListPullView.done(false);
									if (mOnScrollToPullListener != null) {
										mOnScrollToPullListener
												.onScrollToPull(true);
									}
								}

							} else if (mState == PULL_TO_REFRESH) {
								mListView.setSelection(0);
								// 下拉到可以进入RELEASE_TO_REFRESH的状态
								if ((int) ((tempY - mStartY) / sRatio) >= mBdIListPullView
										.getHeadContentHeight()) {
									mState = RELEASE_TO_REFRESH;
									mIsBack = true;
									mBdIListPullView.releaseToRefresh();
									if (mOnScrollToPullListener != null) {
										mOnScrollToPullListener
												.onScrollToPull(true);
									}
								} else if (tempY - mStartY <= 0) { // 上推到顶了
									mState = DONE;
									mBdIListPullView.setPadding(0,
											-mBdIListPullView
													.getHeadContentHeight(), 0, 0);
									mBdIListPullView.done(false);
									if (mOnScrollToPullListener != null) {
										mOnScrollToPullListener
												.onScrollToPull(true);
									}
								}

							} else if (mState == DONE) {
								if (tempY - mStartY > 0) {
									mState = PULL_TO_REFRESH;
									mBdIListPullView.pullToRefresh(mIsBack);
									mIsBack = false;
									if (mOnScrollToPullListener != null) {
										mOnScrollToPullListener
												.onScrollToPull(true);
									}
								} else {
									if (mOnScrollToPullListener != null) {
										mOnScrollToPullListener
												.onScrollToPull(false);
									}
								}
							}

							// 更新headView的paddingTop
							if (mState == PULL_TO_REFRESH
									|| mState == RELEASE_TO_REFRESH) {
								mBdIListPullView.setPadding(
										0,
										(int) ((tempY - mStartY) / sRatio)
												- mBdIListPullView
												.getHeadContentHeight(), 0,
										0);

							}

						}

						break;
				}
			}
		}

		public static final int DEFAULT_REFRESH_DURATION_TIME = 800;
		private int mAnimDurationTime = DEFAULT_REFRESH_DURATION_TIME;

		private void animatePullView() {

			BdIListPullView mBdIListPullView = getBdIListPullView();
			if (mBdIListPullView == null)
				return;

			View mView = mBdIListPullView.getView();
			if (mView == null)
				return;
			int from = 0;
			int to = -mBdIListPullView.getHeadContentHeight();
			BdPaddingAnimation4ListView mBdPaddingAnimation = new BdPaddingAnimation4ListView(
					mView.getContext(), from, to, mAnimDurationTime);
			// BdPaddingAnimation mBdPaddingAnimation = new BdPaddingAnimation(
			// from, to, mAnimDurationTime);
			mBdPaddingAnimation
					.setOnAnimationOverListener(new BdOnAnimationOverListener() {
						@Override
						public void onOver() {
							done();
						}
					});
			mBdPaddingAnimation.startAnimation(mView);

		}
	}

}