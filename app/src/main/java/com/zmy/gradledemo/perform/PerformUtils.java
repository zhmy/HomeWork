package com.zmy.gradledemo.perform;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Looper;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zmy on 2016/11/28.
 */

public class PerformUtils {
    /**
     * 获取Android Native App的缓存大小、数据大小、应用程序大小
     *
     * @param context
     *            Context对象
     * @param pkgName
     *            需要检测的Native App包名
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void getPkgSize(final Context context, String pkgName) throws NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {
        // getPackageSizeInfo是PackageManager中的一个private方法，所以需要通过反射的机制来调用
//        Method method = PackageManager.class.getMethod("getPackageSizeInfo",
//                new Class[] { String.class, IPackageStatsObserver.class });
//        // 调用 getPackageSizeInfo 方法，需要两个参数：1、需要检测的应用包名；2、回调
//        method.invoke(context.getPackageManager(), new Object[] {
//                pkgName,
//                new IPackageStatsObserver.Stub() {
//                    @Override
//                    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
//                        // 子线程中默认无法处理消息循环，自然也就不能显示Toast，所以需要手动Looper一下
//                        Looper.prepare();
//                        // 从pStats中提取各个所需数据
//                        Toast.makeText(context,
//                                "缓存大小=" + Formatter.formatFileSize(context, pStats.cacheSize) +
//                                        "\n数据大小=" + Formatter.formatFileSize(context, pStats.dataSize) +
//                                        "\n程序大小=" + Formatter.formatFileSize(context, pStats.codeSize),
//                                Toast.LENGTH_LONG).show();
//                        // 遍历一次消息队列，弹出Toast
//                        Looper.loop();
//                    }
//                }
//        });
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
