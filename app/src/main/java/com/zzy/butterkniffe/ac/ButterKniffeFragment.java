package com.zzy.butterkniffe.ac;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzy.event.ac.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BCLZzy on 2018/6/13.
 */

public class ButterKniffeFragment extends Fragment {

    private Unbinder unbinder;//绑定View时，记得随View的生命周期注销
    @BindView(R.id.bktv1) TextView bktv1;
    @BindView(R.id.bktv2) TextView bktv2;
    @BindView(R.id.bktv3) TextView bktv3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_butter_kniffe, container, false);
        unbinder = ButterKnife.bind(this, view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
