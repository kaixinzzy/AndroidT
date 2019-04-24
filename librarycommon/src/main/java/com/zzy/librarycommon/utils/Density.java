package com.zzy.librarycommon.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * 通过修改系统参数来适配android设备
 * 注意：并不能解决大屏幕与小屏幕的适配。还是需要资源限定符来做区分。因为一般大屏幕显示的布局跟小屏幕是不一样的。
 */
public class Density {

    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;
    private static int barHeight;
    // 当前主流分辨率1920*1080 和 1280*720，他们的比值都是1.777
    // 参考：https://mta.qq.com/mta/data/device/resolution
    private static float width = 360f;
    private static float height = 640f;

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 初始化Application时，必须调用此方法。
     * @param application Application
     */
    public static void setDensity(@NonNull final Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        //获取状态栏高度
        barHeight = getStatusBarHeight(application);

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {
                }
            });
        }
    }

    /**
     * 重新计算density值，并将其赋值给Activity,（可以在BaseActivity中调用）
     * 需要在onCreate方法中的setContentView前面执行，才生效。
     * @param activity Activity
     * @param orientation 方向（true:width\false:height）
     */
    public static void setAppOrientation(@Nullable Activity activity, boolean orientation) {

        float targetDensity;
        if (orientation) {
            targetDensity = appDisplayMetrics.widthPixels / width;
        } else {
            targetDensity = (appDisplayMetrics.heightPixels - barHeight) / height;
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        // 将计算得到的值赋值给此Activity
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 重新计算density值，并将其赋值给整个应用。
     * 应用不调用外部UI，并且确定全部为竖屏or全部为横屏时，在Application中调用此方法，Activity中就不用调适配的方法了。
     * @param orientation 方向（true:width\false:height）
     */
    public static void setAppOrientation(boolean orientation){

        float targetDensity;
        if (orientation) {
            targetDensity = appDisplayMetrics.widthPixels / width;
        } else {
            targetDensity = (appDisplayMetrics.heightPixels - barHeight) / height;
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        // 将计算得到的值赋值给整个应用
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
    }

}
