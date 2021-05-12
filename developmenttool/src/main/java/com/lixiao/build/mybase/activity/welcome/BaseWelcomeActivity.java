package com.lixiao.build.mybase.activity.welcome;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.build.mybase.activity.welcome.bean.WelcomeInfoBean;
import com.lixiao.build.mybase.appliction.MyApplicationFile;
import com.lixiao.developmenttool.R;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseWelcomeActivity extends BaseCompatActivity {

    public abstract WelcomeInfoBean getWelcomeInfoBean();
    public abstract void showOverNeedDo();
    public abstract void toUserPrivateAgreemeetActivity();

    private WelcomeInfoBean welcomeInfoBean;
    private RelativeLayout bgRL;
    private ImageView welcomeIV;
    private TextView userAgress;
    @Override
    public int getViewLayoutRsId() {
        return R.layout.base_activity_welcome;
    }

    @Override
    public void initView() {
        bgRL=findViewById(R.id.rl_bg);
        welcomeIV=findViewById(R.id.iv_welcome);
        userAgress=findViewById(R.id.tv_user_apreements);
    }

    @Override
    public void initData() {
        welcomeInfoBean=getWelcomeInfoBean();
        if(null==welcomeInfoBean){
            welcomeInfoBean=new WelcomeInfoBean();
        }
        bgRL.setBackgroundResource(welcomeInfoBean.getBackGroundRsId());
        if(0!=welcomeInfoBean.getIconRsId()){
            welcomeIV.setImageResource(welcomeInfoBean.getIconRsId());
        }
        userAgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUserPrivateAgreemeetActivity();
            }
        });


    }

    @Override
    public void initControl() {
        initPermissions();
    }




        @Override
    public void closeActivity() {
    }

    @Override
    public void viewIsShow() {
        if(nowSendPermissionOkIsStart){
            sendPermissionIsOkMessage();
        }
    }

    @Override
    public void viewIsPause() {
    }

    @Override
    public void changeConfig() {
    }


    //权限处理
    // 要申请的权限
    private List<String> permissions;
    private AlertDialog dialog;
    private int getPermissionsActivityType = 52013;
    @TargetApi(Build.VERSION_CODES.M)
    private void initPermissions() {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initNeedPermissions();
            if (checkPermissions()) {
              showToast("获取全部权限成功");
                sendPermissionIsOkMessage();
            } else {
                showToast("获取全部权限失败");
                showDialogTipUserRequestPermission();
            }
        } else {
            sendPermissionIsOkMessage();
        }
    }

    private void initNeedPermissions() {
        permissions = welcomeInfoBean.getPermissionList();
        if(null==permissions){
            permissions=new ArrayList<>();
        }


    }

    //检测是否全部授权
    private boolean checkPermissions() {
        for (int i = 0; i < permissions.size(); i++) {
            if (!checkPermission(permissions.get(i))) {
                return false;
            }
        }
        return true;
    }

    //检测单个权限是否授权
    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermission(String permission) {
        // 检查该权限是否已经获取
        int a = this.checkSelfPermission(permission);
        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
        if (a == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }






    private  boolean nowSendPermissionOkIsStart=false;

    //发送权限OK的消息
    private void sendPermissionIsOkMessage() {
        nowSendPermissionOkIsStart=true;
        MyApplicationFile.getInstance().mkFile();
        showOverNeedDo();
        finish();

    }

    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                .setTitle("有必须权限不可用")
                .setMessage("由于APP需要获取对应权限才能操作；\n否则，您将无法正常使用")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        String[] strs = new String[permissions.size()];
        strs = permissions.toArray(strs);
        ActivityCompat.requestPermissions(this, strs, getPermissionsActivityType);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == getPermissionsActivityType) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean canStar = true;
                for (int i = 0; i < grantResults.length; i++) {
                    canStar = (grantResults[i] == PackageManager.PERMISSION_GRANTED);
                }
                if (canStar) {
                    sendPermissionIsOkMessage();
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                } else {
                    showDialogTipUserGoToAppSettting();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限
    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("权限不可用")
                .setMessage("请在-应用设置-权限-中，允许使用权限来操作APP")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                if (checkPermissions()) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    sendPermissionIsOkMessage();
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
