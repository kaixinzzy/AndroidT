package com.zzy.librarycommon.utils;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * activity堆栈式管理
 * 初始化：在所有Activity初始化的时候调用：MyActivityManager.getInstance().addActivity(this);
 */
public class ActivityManagerT {

    private static Stack<Activity> activityStack;
    private static ActivityManagerT instance;

    private ActivityManagerT() {}

    /**
     * 单一实例
     */
    public static synchronized ActivityManagerT getInstance() {
        if (instance == null) {
            instance = new ActivityManagerT();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                break;
            }
        }
        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    public static Stack<Activity> getActivitys() {
        return activityStack;
    }

    /**
     * 退出应用程序
     */
    public void Exit(Context context) {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception ignored) {
        }
    }

    /**
     * 返回当前Activity栈中Activity的数量
     *
     * @return count值
     */
    public int getActivityCount() {
        return activityStack.size();
    }

    /**
     * 堆栈中移除Activity
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            return;
        } else if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }

        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }


}
