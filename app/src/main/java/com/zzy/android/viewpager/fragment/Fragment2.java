package com.zzy.android.viewpager.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzy.android.viewpager.model.Loading;
import com.zzy.event.ac.R;
import com.zzy.event.ac.databinding.FragmentFragment2Binding;

public class Fragment2 extends LazyLoadFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Loading loading = new Loading(true);

    public Fragment2() {}

    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFragment2Binding binding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_fragment2, container, false);
        View view = binding.getRoot();
        binding.setLoad(loading);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override // 懒加载网络数据，并刷新UI
    public void fetchData() {
        try {
            Thread.sleep((long) 300f);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loading.setLoading(false);
    }
}
