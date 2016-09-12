package com.zmy.library;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * @author lixiurui
 *
 * 实现滑动停止刷新当前页，预加载的帮助类
 * @see IScrollable
 */
public class IScrollableHelper {

	/**
	 * 从一个context实例中获得{@link IScrollable}实例
	 * @param context
	 * @return
	 */
	public static IScrollable getIScrollable(Context context) {
		if (context == null) {
			return null;
		}

		// 自己启动
		if (context instanceof IScrollable) {
			return (IScrollable) context;
		}

		// 框会做一层proxy，从proxy中读取。
//		Field field = BSBeanUtils.getDeclaredField(context.getClass(), IScrollable.class);
//        if (field == null) {
//            field = BSBeanUtils.getDeclaredField(context.getClass(), MAActivity.class);
//            if (field == null) {
//                field = BSBeanUtils.getDeclaredField(context.getClass(), MAFragmentActivity.class);
//            }
//        }
//
//		if (field != null) {
//			Object result = BSBeanUtils.forceGetProperty(context, field);
//
//			if (result != null && result instanceof IScrollable) {
//				return (IScrollable) result;
//			}
//		}

		return null;
	}
	
//	/**
//	 * 从一个Context实例中获得{@link BdPageContextSupport}实例
//	 * @param context
//	 * @return
//	 */
//	public static BdPageContextSupport<?> getBdPageContextSupport(Context context) {
//		if (context == null) {
//			return null;
//		}
//
//		if (context instanceof BdPageContextSupport) {
//			return (BdPageContextSupport<?>) context;
//		}
//
//		Field field = BSBeanUtils.getDeclaredField(context.getClass(), IScrollable.class);
//        if (field == null) {
//            field = BSBeanUtils.getDeclaredField(context.getClass(), MAActivity.class);
//            if (field == null) {
//                field = BSBeanUtils.getDeclaredField(context.getClass(), MAFragmentActivity.class);
//            }
//        }
//        
//		if (field != null) {
//			Object result = BSBeanUtils.forceGetProperty(context, field);
//
//			if (result != null && result instanceof IScrollable) {
//				if(result instanceof BdPageContextSupport) {
//					return (BdPageContextSupport<?>) result;
//				}
//			}
//		}
//		return null;
//	}
//	
//	/**
//	 * 从一个Context实例中获得一个{@link BdPageContext}实例
//	 * @param context
//	 * @return
//	 */
//	public static BdPageContext<?> getBbPageContext(Context context) {
//		if (context == null) {
//			return null;
//		}
//
//		if (context instanceof BdPageContext) {
//			return (BdPageContext<?>) context;
//		}
//
//		if (context instanceof BdPageContextSupport) {
//			return ((BdPageContextSupport<?>) context).getPageContext();
//		}
//
//		Field field = BSBeanUtils.getDeclaredField(context.getClass(), IScrollable.class);
//        if (field == null) {
//            field = BSBeanUtils.getDeclaredField(context.getClass(), MAActivity.class);
//            if (field == null) {
//                field = BSBeanUtils.getDeclaredField(context.getClass(), MAFragmentActivity.class);
//            }
//        }
//        
//		if (field != null) {
//			Object result = BSBeanUtils.forceGetProperty(context, field);
//
//			if (result != null && result instanceof IScrollable) {
//				if(result instanceof BdPageContextSupport) {
//					return ((BdPageContextSupport<?>) result).getPageContext();
//				}
//			}
//		}
//		return null;
//	}
}
