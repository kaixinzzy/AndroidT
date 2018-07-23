package com.zzy.android.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zzy.event.ac.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    private Context mContext;
    private List<RecyclerBean> mDatas;

    RecyclerAdapter(Context context, List<RecyclerBean> listS) {
        this.mContext = context;
        this.mDatas = listS;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new MyHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        RecyclerBean bean = mDatas.get(position);
        holder.bind(bean);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mAgeTextView;
        private RecyclerBean mBean;

        MyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.recycler_item, parent, false));

            mNameTextView = itemView.findViewById(R.id.name);
            mAgeTextView = itemView.findViewById(R.id.age);
            itemView.setOnClickListener(this);
        }

        void bind(RecyclerBean bean) {
            this.mBean = bean;
            mNameTextView.setText(mBean.getName());
            mAgeTextView.setText(mBean.getAge());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, mBean.getName() +"被点击", Toast.LENGTH_SHORT).show();
            notifyItemMoved(0, 5);// 将位置0移动到位置5
            notifyItemRemoved(1);// 移除位置1
        }
    }

}
