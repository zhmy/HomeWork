package com.zmy.library;

/**
 * 配合bdlistview使用，主要为了实现滑动停止刷新当前页，预加载的功能。
 * 
 * @author zhaoxuyang
 * 
 */
public interface IScrollable {

	/**
	 * 获得页面唯一id
	 * @return
	 */
	public BdUniqueId getUniqueId();

	/**
	 * 当前是否正在滑动
	 * @return
	 */
	public boolean isScroll();

	/**
	 * 设置当前是否正在滑动
	 * @param isScroll
	 */
	public void setIsScroll(boolean isScroll);

	/**
	 * 为BdListView进行预加载功能
	 * @param listView
	 */
	public void onPreLoad(BdListView listView);
}
