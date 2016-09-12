package com.zmy.library;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * <h1>BdListAdpter</h1> <h3>说明</h3>
 * <p>
 * 同{@link BdListView}来配合使用，贴吧中通用的BdListview使用到的数据加载器
 * </p>
 * <p>
 * 内部通过组合的方式来包装了{@link ListAdapter}的功能,这个被包装的{@link ListAdapter}是业务中实现的
 * {@link ListAdapter}。
 * <p>
 * 省去外部计算headerView和FooterView的逻辑。
 * <p>
 * 包含预加载的接口:{@link BdListPreLoad}
 * 
 * <h3>使用</h3>
 * <p>
 * 外部不需要直接使用，{@link BdListView}内部会使用业务中的Adapter构造一个BdListAdpter实例，
 * {@link BdListView}使用该实例实现数据和UI的绑定
 * </p>
 * <p>
 * 外部只需要构造出自己的adapter，并使用{@link BdListView#setAdapter(ListAdapter)}
 * 给BdListView设置Adapter即可。
 * </p>
 * 
 * <pre>
 * ....
 * mAdapter = new MemberPrivilegeAdapter(mActivity);//业务自己的adapter,只要实现Idapter接口即可
 * mListView.setAdapter(mAdapter);//将业务自己的adapter设置给BdListView实例即可
 * ....
 * </pre>
 * 
 * 
 */
public class BdListAdpter extends BaseAdapter {
	private Context mContext = null;
	private ListAdapter mAdapter = null;
	private ArrayList<FixedViewInfo> mHeaderViewInfos = null;
	private ArrayList<FixedViewInfo> mFooterViewInfos = null;
	private boolean mAreAllFixedViewsSelectable = false;
	private boolean mIsFilterable = false;
	private DataSetObserver mDataSetObserver = null;
	private DataSetObserver mAdapterDataSetObserver = null;

	private BdListPreLoad mListPreLoad = null;

	/**
	 * 预加载的接口
	 * 
	 */
	public interface BdListPreLoad {

		/**
		 * 预加载
		 */
		public void onPreLoad();
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	public BdListAdpter(Context context) {
		mContext = context;
		mHeaderViewInfos = new ArrayList<FixedViewInfo>();
		mFooterViewInfos = new ArrayList<FixedViewInfo>();
		mAreAllFixedViewsSelectable =
				areAllListInfosSelectable(mHeaderViewInfos)
						&& areAllListInfosSelectable(mFooterViewInfos);
		mAdapterDataSetObserver = new DataSetObserver() {

			@Override
			public void onChanged() {
				super.onChanged();
				if (mDataSetObserver != null) {
					mDataSetObserver.onChanged();
				}
				if (mListPreLoad != null) {
					mListPreLoad.onPreLoad();
				}
			}

			@Override
			public void onInvalidated() {
				super.onInvalidated();
				if (mDataSetObserver != null) {
					mDataSetObserver.onInvalidated();
				}
			}

		};
	}

	/**
	 * 设置预加载的接口
	 * 
	 * @param preLoad
	 */
	public void setListPreLoad(BdListPreLoad preLoad) {
		mListPreLoad = preLoad;
	}

	/**
	 * 得到包装Adapter的getCount()值
	 * 
	 * @return
	 */
	public int getWrappedCount() {
		if (mAdapter != null) {
			return mAdapter.getCount();
		} else {
			return 0;
		}
	}

	/**
	 * 得到包装的Adapter
	 * 
	 * @return
	 */
	public ListAdapter getWrappedAdapter() {
		return mAdapter;
	}

	/**
	 * 设置adapter
	 * 
	 * @param adapter
	 */
	public void setAdaper(ListAdapter adapter) {
		if (mAdapter != null) {
			mIsFilterable = false;
		}
		mAdapter = adapter;
		if (mAdapter != null) {
			mIsFilterable = mAdapter instanceof Filterable;
		}
		notifyDataSetChanged();
	}

	/**
	 * 注册一个数据有变化时的观察者
	 */
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		super.registerDataSetObserver(observer);
		mDataSetObserver = observer;
		if (mAdapter != null) {
			mAdapter.registerDataSetObserver(mAdapterDataSetObserver);
		}
	}

	/**
	 * 解注册一个数据有变化时的观察者
	 */
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		super.unregisterDataSetObserver(observer);
		mDataSetObserver = observer;
		if (mAdapter != null) {
			mAdapter.unregisterDataSetObserver(mAdapterDataSetObserver);
		}
	}

	/**
	 * 得到HeaderView的数目
	 * 
	 * @return
	 */
	public int getHeadersCount() {
		return mHeaderViewInfos.size();
	}

	/**
	 * 得到FooterView的数目
	 * 
	 * @return
	 */
	public int getFootersCount() {
		return mFooterViewInfos.size();
	}

	private boolean areAllListInfosSelectable(ArrayList<FixedViewInfo> infos) {
		if (infos != null) {
			for (FixedViewInfo info : infos) {
				if (!info.isSelectable) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 移除某个HeaderView
	 * 
	 * @param v
	 * @return 移除成功后会通知刷新页面并返回true。
	 */
	public boolean removeHeader(View v) {
		if (v == null) {
			return false;
		}
		for (int i = 0; i < mHeaderViewInfos.size(); i++) {
			FixedViewInfo info = mHeaderViewInfos.get(i);
			if (info.view == v) {
				mHeaderViewInfos.remove(i);
				mAreAllFixedViewsSelectable =
						areAllListInfosSelectable(mHeaderViewInfos)
								&& areAllListInfosSelectable(mFooterViewInfos);
				notifyDataSetChanged();
				return true;
			}
		}
		return false;
	}

	/**
	 * 移除某个FooterView
	 * 
	 * @param v
	 * @return 移除成功后会通知刷新页面并返回true。
	 */
	public boolean removeFooter(View v) {
		if (v == null) {
			return false;
		}
		for (int i = 0; i < mFooterViewInfos.size(); i++) {
			FixedViewInfo info = mFooterViewInfos.get(i);
			if (info.view == v) {
				mFooterViewInfos.remove(i);
				mAreAllFixedViewsSelectable =
						areAllListInfosSelectable(mHeaderViewInfos)
								&& areAllListInfosSelectable(mFooterViewInfos);
				notifyDataSetChanged();
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到Adapter对应的过滤器
	 * 
	 * @return
	 */
	public Filter getFilter() {
		if (mIsFilterable && mAdapter != null) {
			return ((Filterable) mAdapter).getFilter();
		}
		return null;
	}

	/**
	 * 添加HeaderView，会添加到其他HeaderView的后面
	 * 
	 * @param v
	 */
	public void addHeaderView(View v) {
		addHeaderView(v, null, true, -1);
	}

	/**
	 * 添加一个HeadVeiw
	 * 
	 * @param v
	 * @param index
	 *            添加的次序
	 */
	public void addHeaderView(View v, int index) {
		addHeaderView(v, null, true, index);
	}

	/**
	 * 添加一个Headerview
	 * 
	 * @param v
	 *            HeaderView的引用
	 * @param data
	 *            HeadView对应的数据
	 * @param isSelectable
	 *            HeaderView是否可以选择
	 * @param index
	 *            添加的次序
	 */
	public void addHeaderView(View v, Object data, boolean isSelectable, int index) {
		if (v == null) {
			return;
		}
		FixedViewInfo info = new FixedViewInfo();
		info.view = v;
		info.data = data;
		info.isSelectable = isSelectable;
		if (index < 0 || index > mHeaderViewInfos.size()) {
			mHeaderViewInfos.add(info);
		} else {
			mHeaderViewInfos.add(index, info);
		}
		notifyDataSetChanged();
	}

	/**
	 * 得到HeadView的数目
	 * 
	 * @return
	 */
	public int getHeaderViewsCount() {
		return mHeaderViewInfos.size();
	}

	/**
	 * 得到FooterView的数目
	 * 
	 * @return
	 */
	public int getFooterViewsCount() {
		return mFooterViewInfos.size();
	}

	/**
	 * 添加一个FooterView
	 * 
	 * @param v
	 */
	public void addFooterView(View v) {
		addFooterView(v, null, true, -1);
	}

	/**
	 * 按照次序添加一个FooterView
	 * 
	 * @param v
	 * @param index
	 */
	public void addFooterView(View v, int index) {
		addFooterView(v, null, true, index);
	}

	/**
	 * 添加一个FooterView
	 * 
	 * @param v
	 *            FooterView的引用
	 * @param data
	 *            FooterView对应的数据
	 * @param isSelectable
	 *            FooterView是否可以选择
	 * @param index
	 *            添加的次序
	 * 
	 */
	public void addFooterView(View v, Object data, boolean isSelectable, int index) {
		if (v == null) {
			return;
		}
		FixedViewInfo info = new FixedViewInfo();
		info.view = v;
		info.data = data;
		info.isSelectable = isSelectable;
		if (index < 0 || index > mFooterViewInfos.size()) {
			mFooterViewInfos.add(info);
		} else {
			mFooterViewInfos.add(index, info);
		}
		notifyDataSetChanged();
	}

	/**
	 * 获取Adapter的数目,内部会添加尚FooterView和HeaderView的数目。
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (mAdapter != null) {
			return getFootersCount() + getHeadersCount() + mAdapter.getCount();
		} else {
			return getFootersCount() + getHeadersCount();
		}
	}

	/**
	 * 得到数据源中position位置对应的数据item
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// Header (negative positions will throw an
		// ArrayIndexOutOfBoundsException)
		int numHeaders = getHeadersCount();
		if (position < numHeaders) {
			return mHeaderViewInfos.get(position).data;
		}

		// Adapter
		final int adjPosition = position - numHeaders;
		int adapterCount = 0;
		if (mAdapter != null) {
			adapterCount = mAdapter.getCount();
			if (adjPosition < adapterCount) {
				return mAdapter.getItem(adjPosition);
			}
		}

		// Footer (off-limits positions will throw an
		// ArrayIndexOutOfBoundsException)
		int index = adjPosition - adapterCount;
		if (index >= 0 && index < mFooterViewInfos.size()) {
			return mFooterViewInfos.get(index).data;
		} else {
			return null;
		}
	}

	/**
	 * 得到指定position位置对应的ItemId
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		int numHeaders = getHeadersCount();
		if (mAdapter != null && position >= numHeaders) {
			int adjPosition = position - numHeaders;
			int adapterCount = mAdapter.getCount();
			if (adjPosition < adapterCount) {
				return mAdapter.getItemId(adjPosition);
			}
		}
		return Long.MIN_VALUE;
	}

	/**
	 * Itme的Id是否是稳定的
	 * 
	 * @see android.widget.BaseAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		if (mAdapter != null) {
			return mAdapter.hasStableIds();
		} else {
			return super.hasStableIds();
		}
	}

	/**
	 * 是否所有的Item是Enabled的
	 *
	 * @see android.widget.BaseAdapter#areAllItemsEnabled()
	 */
	@Override
	public boolean areAllItemsEnabled() {
		if (mAdapter != null) {
			return mAreAllFixedViewsSelectable && mAdapter.areAllItemsEnabled();
		} else {
			return super.areAllItemsEnabled();
		}
	}

	/**
	 * 是否position位置对应的Item是Enabled的
	 *
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 */
	@Override
	public boolean isEnabled(int position) {
		// Header (negative positions will throw an
		// ArrayIndexOutOfBoundsException)
		int numHeaders = getHeadersCount();
		if (position < numHeaders) {
			return mHeaderViewInfos.get(position).isSelectable;
		}

		// Adapter
		final int adjPosition = position - numHeaders;
		int adapterCount = 0;
		if (mAdapter != null) {
			adapterCount = mAdapter.getCount();
			if (adjPosition < adapterCount) {
				return mAdapter.isEnabled(adjPosition);
			}
		}

		// Footer (off-limits positions will throw an
		// ArrayIndexOutOfBoundsException)
		int index = adjPosition - adapterCount;
		if (index >= 0 && index < mFooterViewInfos.size()) {
			return mFooterViewInfos.get(index).isSelectable;
		} else {
			return false;
		}
	}

	/**
	 * 得到对应position位置的Item的ViewType.
	 *
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		int numHeaders = getHeadersCount();
		if (mAdapter != null && position >= numHeaders) {
			int adjPosition = position - numHeaders;
			int adapterCount = mAdapter.getCount();
			if (adjPosition < adapterCount) {
				return mAdapter.getItemViewType(adjPosition);
			}
		}

		return AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER;
	}

	/**
	 * 得到ViewType的数目
	 *
	 * @see android.widget.BaseAdapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount() {
		if (mAdapter != null) {
			return mAdapter.getViewTypeCount() + 1;
		}
		return 1;
	}

	/**
	 * 数据源是否为空
	 *
	 * @see android.widget.BaseAdapter#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return mAdapter == null || mAdapter.isEmpty();
	}

	/**
	 * 构造每个具体的Item的View
	 *
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		// Header (negative positions will throw an
		// ArrayIndexOutOfBoundsException)
		int numHeaders = getHeadersCount();
		if (position < numHeaders) {
			v = mHeaderViewInfos.get(position).view;
			if (v == null) {
				v = createErrorView();
			}
			return v;
		}

		// Adapter
		final int adjPosition = position - numHeaders;
		int adapterCount = 0;
		if (mAdapter != null) {
			adapterCount = mAdapter.getCount();
			if (adjPosition < adapterCount) {
				try {
					v = mAdapter.getView(adjPosition, convertView, parent);
				} catch (Exception e) {
//					BdLog.detailException(e);
					Log.e("BdListAdapter", e.getMessage());
				} catch (OutOfMemoryError t) {
//					BdBaseApplication.getInst().onAppMemoryLow();

					// retry again
					v = mAdapter.getView(adjPosition, convertView, parent);
				}

				if (v == null) {
					v = createErrorView();
				}
				return v;
			}
		}

		// Footer (off-limits positions will throw an
		// ArrayIndexOutOfBoundsException)
		try {
			v = mFooterViewInfos.get(adjPosition - adapterCount).view;
		} catch (Exception e) {
			e.printStackTrace();
//			BdLog.detailException(e);
		}
		if (v == null) {
			v = createErrorView();
		}
		return v;
	}

	private View createErrorView() {
		TextView textView = new TextView(mContext);
//		textView.setText(BdBaseApplication.getInst().getContext().getString(R.string.load_res_failed));
//		int padding = BdUtilHelper.dip2px(mContext, 15);
//		textView.setPadding(padding, padding, padding, padding);
		return textView;
	}

	/**
	 * headerview和footerview的封装类
	 * 
	 */
	public class FixedViewInfo {
		/**
		 * view的引用
		 */
		public View view;
		/**
		 * 对应的数据
		 */
		public Object data;
		/**
		 * 是否可选择
		 */
		public boolean isSelectable;
	}

}
