package com.bosong.demolibrary;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by boson on 2017/3/1.
 */

public class DeviceUtils {

    private DeviceUtils(){}
    /**
     * 获取屏幕原始尺寸高度，包括虚拟功能键高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        int height = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            height=displayMetrics.heightPixels;
        }catch(Exception e){
            height = getWindowHeight(context);
        }
        return height;
    }

    /**
     * 获得屏幕高度 不包括虚拟导航键
     *
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context)
    {
        int height = 0;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            height=displayMetrics.heightPixels;
        }catch(Exception e){
            height = 0;
        }
        return height;
    }

    /**
     * 获得屏幕宽度 不包括虚拟导航键
     *
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context)
    {
        int width = 0;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            width=displayMetrics.widthPixels;
        }catch(Exception e){
            width = 0;
        }
        return width;
    }

    /**
     * 是否有虚拟导航键，不论显示与否
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }

        return hasNavigationBar;
    }

    /**
     * 虚拟导航键的配置高度，不论显示与否
     * @param context
     * @return
     */
    public static int getNavigationBarConfigHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    /**
     * 获取实际虚拟导航栏的高度，没有虚拟导航栏或隐藏掉时为0
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context){
        return getScreenHeight(context) - getWindowHeight(context);
    }
}
