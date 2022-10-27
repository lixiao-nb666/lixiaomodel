package com.lixiao.view.viewpager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lixiao.developmenttool.R;

public class MainActivity extends Activity {

    private TextView tvTitle;
    private GalleryFlow gallery;
    private ImageAdapter adapter;
    private ReflectedImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRes();
    }

    private void initRes() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        gallery = (GalleryFlow) findViewById(R.id.mygallery);
        adapter = new ImageAdapter(this);
        //创建倒影效果
        adapter.createReflectedImages();
        gallery.setAdapter(adapter);

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvTitle.setText(adapter.titles[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "img " + (position + 1) + " selected", Toast.LENGTH_SHORT).show();
            }
        });
        iv=findViewById(R.id.iv);
        Bitmap originalImage = BitmapFactory.decodeResource(getResources(), R.drawable.image002);
        iv.setShowBitMap(originalImage);


    }
}
