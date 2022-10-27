package com.lixiao.view;

import java.util.List;
  
import android.content.Context;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ImageView;  
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lixiao.build.util.systemapp.PackageManagerUtil;
import com.lixiao.build.util.systemapp.bean.SystemAppInfoBean;
import com.lixiao.developmenttool.R;
import com.lixiao.view.viewpager.ReflectedImageView;

public class GalleryAdapter extends  
        RecyclerView.Adapter<GalleryAdapter.ViewHolder>
{  
  
    private LayoutInflater mInflater;
    private List<SystemAppInfoBean> apps;
  
    public GalleryAdapter(Context context,  List<SystemAppInfoBean> apps)
    {  
        mInflater = LayoutInflater.from(context);  
        this.apps=apps;
    }  
  
    public static class ViewHolder extends RecyclerView.ViewHolder  
    {  
        public ViewHolder(View arg0)  
        {  
            super(arg0);  
        }  
  
        ReflectedImageView appIconIV;
        TextView appNameTV;
    }  
  
    @Override  
    public int getItemCount()  
    {  
        return apps.size();
    }  
  
    /** 
     * 创建ViewHolder 
     */  
    @Override  
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)  
    {  
        View view = mInflater.inflate(R.layout.adapter_3d_apps_layout,
                viewGroup, false);  
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.appNameTV = view.findViewById(R.id.tv_app_name);
        viewHolder.appIconIV  =  view
                .findViewById(R.id.iv_app_icon);
        return viewHolder;  
    }  
  
    /** 
     * 设置值 
     */  
    @Override  
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)  
    {


        Log.i("lixiao3d","lixiao3d---------------------");

        final SystemAppInfoBean app = apps.get(i);
//        MyGlide.getInstance().setBitMap();

//        viewHolder.appIconIV.setImageDrawable(PackageManagerUtil.getInstance().getIcon(app.getPakeageName()));
        Drawable drawable= PackageManagerUtil.getInstance().getIcon(app.getPakeageName());
        BitmapDrawable bd = (BitmapDrawable) drawable;
        viewHolder.appIconIV.setShowBitMap(bd.getBitmap());
//        viewHodler.appIconIV.setBackground(PackageManagerUtil.getInstance().getIcon(app.getPakeageName()));
        viewHolder.appIconIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.appIconIV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
        viewHolder.appNameTV.setText(app.getName());

    }  
  
}  