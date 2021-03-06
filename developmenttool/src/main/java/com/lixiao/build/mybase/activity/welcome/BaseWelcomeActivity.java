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


    //????????????
    // ??????????????????
    private List<String> permissions;
    private AlertDialog dialog;
    private int getPermissionsActivityType = 52013;
    @TargetApi(Build.VERSION_CODES.M)
    private void initPermissions() {
        // ???????????????????????????????????? 23 ?????????????????????????????????????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initNeedPermissions();
            if (checkPermissions()) {
              showToast("????????????????????????");
                sendPermissionIsOkMessage();
            } else {
                showToast("????????????????????????");
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

    //????????????????????????
    private boolean checkPermissions() {
        for (int i = 0; i < permissions.size(); i++) {
            if (!checkPermission(permissions.get(i))) {
                return false;
            }
        }
        return true;
    }

    //??????????????????????????????
    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermission(String permission) {
        // ?????????????????????????????????
        int a = this.checkSelfPermission(permission);
        // ?????????????????? ?????? GRANTED---??????  DINIED---??????
        if (a == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }






    private  boolean nowSendPermissionOkIsStart=false;

    //????????????OK?????????
    private void sendPermissionIsOkMessage() {
        nowSendPermissionOkIsStart=true;
        MyApplicationFile.getInstance().mkFile();
        showOverNeedDo();
        finish();

    }

    // ???????????????????????????????????????
    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                .setTitle("????????????????????????")
                .setMessage("??????APP???????????????????????????????????????\n?????????????????????????????????")
                .setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // ????????????????????????
    private void startRequestPermission() {
        String[] strs = new String[permissions.size()];
        strs = permissions.toArray(strs);
        ActivityCompat.requestPermissions(this, strs, getPermissionsActivityType);
    }

    // ???????????? ?????? ???????????????
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
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    showDialogTipUserGoToAppSettting();
                }
            }
        }
    }

    // ???????????????????????????????????????????????????
    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle("???????????????")
                .setMessage("??????-????????????-??????-?????????????????????????????????APP")
                .setPositiveButton("????????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ???????????????????????????
                        goToAppSetting();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // ????????????????????????????????????
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
                // ?????????????????????????????????
                if (checkPermissions()) {
                    // ?????????????????????????????????????????????????????????
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    sendPermissionIsOkMessage();
                    Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
