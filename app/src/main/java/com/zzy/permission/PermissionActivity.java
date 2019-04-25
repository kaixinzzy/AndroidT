package com.zzy.permission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.zzy.event.ac.R;
import com.zzy.librarycommon.base.BaseActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * ================================================
 * 作    者： zzy
 * 版    本：
 * 创建日期： 2019/3/14
 * 描    述: 运行时权限申请
 * 参    考：
 *          http://www.androidchina.net/8383.html
 *          对比分析了PermissionsDispatcher、easypermissions、RxPermissions说那个库
 * ================================================
 */
// 申请权限类标识注解
@RuntimePermissions
public class PermissionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
    }

    /** 申请权限成功时 **/
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void ApplySuccess() {
        Toast.makeText(this, "ACCESS_COARSE_LOCATION 权限申请成功，开始执行定位逻辑", Toast.LENGTH_LONG).show();
    }

    /** 申请权限前需要执行的任务，eg：告诉用户原因 **/
    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    void showRationaleForMap(PermissionRequest request) {
        showRationaleDialog("此功能需要打开定位的权限，", request);
    }

    /** 申请权限被拒绝时 **/
    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    void onMapDenied() {
        Toast.makeText(this,"你拒绝了权限，该功能不可用",Toast.LENGTH_LONG).show();
    }

    /** 申请权限被拒绝并勾选不再提醒时 **/
    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    void onMapNeverAskAgain() {
        AskForPermission();
    }

    /**
     * 告知用户具体需要权限的原因
     * @param messageResId
     * @param request
     */
    private void showRationaleDialog(String messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();//请求权限
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    /**
     * 被拒绝并且不再提醒,提示用户去设置界面重新打开权限
     */
    private void AskForPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("当前应用缺少定位权限,请去设置界面打开\n打开之后按两次返回键可回到该应用哦");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName())); // 根据包名打开对应的设置界面
                startActivity(intent);
            }
        });
        builder.create().show();
    }

}
