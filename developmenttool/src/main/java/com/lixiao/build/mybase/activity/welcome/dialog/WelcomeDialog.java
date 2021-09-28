package com.lixiao.build.mybase.activity.welcome.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.lixiao.build.mybase.dialog.BaseDialog;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/9/28 0028 15:36
 */
public class WelcomeDialog  {
    private AlertDialog dialog;
    private Click click;
    public WelcomeDialog(Click click){
        this.click=click;
    }


    public void show(Context context){
        if(null==dialog){
            dialog = new AlertDialog.Builder(context)
                    .setTitle("权限不可用")
                    .setMessage("由于APP需要获取对应权限才能操作；\n" +
                            "否则，您将无法正常使用\n" +
                            "请点击立即开始\n" +
                            "或点击去设置页面（应用设置-权限-中，允许使用权限来操作APP）")
                    .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳转到应用设置界面
                            click.userCanToCheck();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            click.userCancel();

                        }
                    })
                    .setNeutralButton("去设置页面", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            click.userToSet();
                        }
                    })
                    .setCancelable(false).show();
        }else {
            dialog.show();
        }

    }

    public void hide(){
        if(null!=dialog&&dialog.isShowing()){
            dialog.hide();
        }

    }

    public void close(){
        if(null!=dialog){
            dialog.cancel();
        }

    }

    public interface Click{

        public void userCanToCheck();

        public void userToSet();

        public void userCancel();

    }
}
