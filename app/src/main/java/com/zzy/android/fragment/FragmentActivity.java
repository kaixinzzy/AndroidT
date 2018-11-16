package com.zzy.android.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zzy.event.ac.R;

/**
 *  兼容v4包-1.6最低兼容到android 1.6版本
 *      1、自己的Activity需要继承v4包的FragmentActivity
 *      2、FragmentManager fm = getSupportFragmentManager();
 *      3、支持fragment嵌套fragment
 *  android3.0版本开始自带Fragment
 *      1、自己的Activity需要Activity即可
 *      2、FragmentManager fm = getFragmentManager();
 *      3、fragment嵌套fragment android4.2才支持
 *  传参 -- setArguments
 *      为何不通过fragment的构造函数传参，而要通过setArguments传参。
 *      横竖屏切换时，fragment会重构，默认调用无参构造函数，这样会导致数据丢失，setArguments传参则不会丢失数据。
 */
public class FragmentActivity extends android.support.v4.app.FragmentActivity implements LifeCycleFragment.OnFragmentInteractionListener {
    private static final String TAG = "FragmentActivity";
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        LifeCycleFragment fragment = LifeCycleFragment.newInstance("a", "b");
        ft.add(R.id.fragment, fragment);
//        ft.add(R.id.fragment, fragment, fragment.getClass().getName());// 带TAG
        ft.addToBackStack(fragment.getClass().getName());// 添加到回退栈
        ft.commit();

        ft.replace(R.id.fragment, fragment);// 替换
        ft.replace(R.id.fragment, fragment, fragment.getClass().getName());// 替换，带TAG
        ft.remove(fragment);// 移除

        //fragment = (LifeCycleFragment) fm.findFragmentByTag(fragment.getClass().getName());// 通过TAG，获取fragment
    }

    public void back() {
        if (null != fm && fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();//回退栈，会退到上一个fragment
        }
    }

    /**
     * 参考：https://blog.csdn.net/chen19960724/article/details/52517780
     * replace替换，销毁当前显示的Fragment，添加需要显示的Fragment，
     * add()+remove() = replace()
     * 优点：
     *      节省内存。
     * 缺点：
     *      对于需要切换频繁的时，性能消耗大。
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        if (ft != null) {
            ft.replace(R.id.fragment, fragment).commit();
        }
    }

    /**
     * hide()+show() 将Fragment视图隐藏之后在显示。
     * 优点：
     *      不用重新创建Fragment，性能消耗小
     * 缺点：
     *      占用内存
     *
     */
    private void changeFragment(Fragment from, Fragment to) {
        if (to.isAdded()) {
            ft.hide(from)
                    .show(to)
                    .commitAllowingStateLoss();
        } else {
            ft.hide(from)
                    .add(R.id.fragment, to)
                    .commitAllowingStateLoss();
        }
    }

    @Override // 回调方法，fragment通过此方法，控制activity
    public void onFragmentInteraction(Uri uri) {

    }
}
