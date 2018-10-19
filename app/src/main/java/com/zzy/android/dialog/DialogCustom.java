package com.zzy.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zzy.event.ac.R;

/**
 * 自定义Dialog
 * 参考：
 *      https://blog.csdn.net/oQiHaoGongYuan/article/details/50958659
 */
public class DialogCustom extends Dialog implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private TextView mTitle;
    private TextView mMessage;
    private String mTitleStr;// 标题文字
    private String mMessageStr;// 提示文字
    private CancelListener mCancelListener;// 取消事件监听
    private ConfirmListener mConfirmListener;// 确定事件监听

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface CancelListener {
        void onClick(Dialog dialog);
    }

    public interface ConfirmListener {
        void onClick(Dialog dialog);
    }

    public DialogCustom(@NonNull Context context, @NonNull String titleStr, @NonNull String messageStr) {
        super(context, R.style.AutomatDialog);
        this.mContext = context.getApplicationContext();
        this.mTitleStr = titleStr;
        this.mMessageStr = messageStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 禁掉title栏

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_t, null);
        setContentView(view);// 应用此布局

        mTitle = view.findViewById(R.id.title);
        mMessage = view.findViewById(R.id.message);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        Button confirmButton = view.findViewById(R.id.confirmButton);

        mTitle.setText(mTitleStr);
        mMessage.setText(mMessageStr);
        cancelButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelButton:
                if (mCancelListener != null) {
                    mCancelListener.onClick(this);
                }
                break;
            case R.id.confirmButton:
                if (mConfirmListener != null) {
                    mConfirmListener.onClick(this);
                }
                break;
        }
    }

    @Override // 显示对话框
    public void show() {
        super.show();
    }

    @Override // 取消对话框，内部调用了dismiss方法
    public void cancel() {
        super.cancel();
    }

    @Override // 关闭对话框
    public void dismiss() {
        super.dismiss();
    }

    // 设置取消按键监听
    public void setCancelListener(CancelListener cancelListener) {
        mCancelListener = cancelListener;
    }

    // 设置确定按键监听
    public void setConfirmListener(ConfirmListener confirmListener) {
        mConfirmListener = confirmListener;
    }

    public String getTitleStr() {
        return mTitleStr;
    }

    public void setTitleStr(String titleStr) {
        mTitleStr = titleStr;
        if (mTitle != null) {
            mTitle.setText(mTitleStr);
        }
    }

    public String getMessageStr() {
        return mMessageStr;
    }

    public void setMessageStr(String messageStr) {
        mMessageStr = messageStr;
        if (mMessage != null) {
            mMessage.setText(mMessageStr);
        }
    }
}
