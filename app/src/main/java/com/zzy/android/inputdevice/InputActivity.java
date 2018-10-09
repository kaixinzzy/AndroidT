package com.zzy.android.inputdevice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.zzy.event.ac.R;

/**
 * 输入设备 inputManager 区分大小写字母
 * 参考：
 *      https://www.jb51.net/article/128348.htm
 */
public class InputActivity extends AppCompatActivity {
    private static final String TAG = "InputActivity";
    private boolean mCaps = false;// 是否开启大小写

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        String deviceName = event.getDevice().getName();
        // 设备描述符匹配
        if (deviceName.contains("Logitech USB Keyboard")) {
            // 字母大小写判断
            if (checkLetterStatus(event)) {
                // 跳出，shift是功能键，不是真正我们需要解析的内容.
                return true;
            }
        }

        switch (event.getAction()) {
            case KeyEvent.ACTION_UP:
                // 设备描述符匹配
                if (deviceName.contains("Logitech USB Keyboard")) {
                    Log.d(TAG, getInputString(event));
                }
                break;
        }

        return super.dispatchKeyEvent(event);
    }

    // 检查Shift键
    private boolean checkLetterStatus(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT || keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                // 按下Shift键，表示大写
                mCaps = true;
            } else {
                // 松开Shift键,表示小写
                mCaps = false;
            }
            return true;
        }
        return false;
    }

    private String getInputString(KeyEvent event) {
        String result = "";
        int keyCode = event.getKeyCode();
        if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            // 字母
            if (mCaps) {
                result = String.valueOf(event.getDisplayLabel());
            } else {
                result = String.valueOf(event.getDisplayLabel()).toLowerCase();
            }
        } else if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            // 数字
            result = String.valueOf(event.getDisplayLabel());
        } else if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT
                || keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT
                || keyCode == KeyEvent.KEYCODE_CTRL_LEFT
                || keyCode == KeyEvent.KEYCODE_CTRL_RIGHT
                || keyCode == KeyEvent.KEYCODE_ALT_LEFT
                || keyCode == KeyEvent.KEYCODE_ALT_RIGHT) {

        } else {
            // 其他符号
            result = String.valueOf(event.getDisplayLabel());
        }

        return result;
    }

}
