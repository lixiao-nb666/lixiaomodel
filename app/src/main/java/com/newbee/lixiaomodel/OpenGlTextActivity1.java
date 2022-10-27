package com.newbee.lixiaomodel;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.ComponentActivity;

import com.nrmyw.launcher.R;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/4/16 0016 15:33
 */
public class OpenGlTextActivity1 extends ComponentActivity {
    private ImageView iv1,iv2,iv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_opengl1);
        initView();
//        MyGlide.GetBitmapImp getBitmapImp=new MyGlide.GetBitmapImp() {
//            @Override
//            public void getBitmap(Bitmap bitmap) {
//                int w=bitmap.getWidth();
//                int h=bitmap.getHeight();
//                int needW=w/10*4;
//                int addW=w/10*3;
//
//               Bitmap b1= Bitmap.createBitmap(bitmap, 0, 0, needW, h);
//                iv1.setImageBitmap(b1);
//                Bitmap b2= Bitmap.createBitmap(bitmap, addW, 0, needW, h);
//                iv2.setImageBitmap(b2);
//                Bitmap b3= Bitmap.createBitmap(bitmap, addW*2, 0, needW, h);
//                iv3.setImageBitmap(b3);
//            }
//        };
//        MyGlide.getInstance().getBitmap(this,R.drawable.main,getBitmapImp);

    }

    private void initView() {
        iv1=findViewById(R.id.iv1);
        iv2=findViewById(R.id.iv2);
        iv3=findViewById(R.id.iv3);

    }





}
