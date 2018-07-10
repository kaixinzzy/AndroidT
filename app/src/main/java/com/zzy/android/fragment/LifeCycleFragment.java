package com.zzy.android.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.event.ac.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LifeCycleFragment extends Fragment {
    private static final String TAG = "LifeCycleFragment";
    private OnFragmentInteractionListener callback;// 回调接口
    public static final String ARG_PARAM1 = "param1";// 参数1
    public static final String ARG_PARAM2 = "param2";// 参数2
    private String mParam1,mParam2;// 由Activity传递的值

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public LifeCycleFragment() {}

    // 创建fragment，并传参
    @SuppressWarnings("SameParameterValue")
    public static LifeCycleFragment newInstance(String param1, String param2) {
        LifeCycleFragment fragment = new LifeCycleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, param1);
        bundle.putString(ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override // Fragment与Activity建立关联
    public void onAttach(Context context) {
        super.onAttach(context);
        // 获取回调
        if (context instanceof  OnFragmentInteractionListener) {
            callback = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取传参
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override // 创建视图（加载布局）时调用-------------------------------------回退栈------------------
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_life_cycle, container, false);
    }

    @Override // 当Activity的onCreate方法执行完成后，调用
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {super.onStart();}

    @Override
    public void onResume() {super.onResume();}

    /*************************************** Fragment is active ************************************/

    @Override
    public void onPause() {super.onPause();}

    @Override
    public void onStop() {super.onStop();}

    @Override // Fragment布局被移除时调用【表示fragment销毁相关联的UI布局】--------回退栈----------------
    public void onDestroyView() {super.onDestroyView();}

    @Override
    public void onDestroy() {super.onDestroy();}

    @Override // Fragment与Activity解除关联
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override // 进入停止状态，可能会被内存回收，可用这个方法Bundle保存数据
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
