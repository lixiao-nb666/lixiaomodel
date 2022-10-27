package com.nrmyw.launcher.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lixiao.build.util.systemapp.PackageManagerUtil;
import com.lixiao.build.util.systemapp.bean.SystemAppInfoBean;
import com.lixiao.view.reflected.ReflectedUtil;
import com.nrmyw.launcher.R;
import com.nrmyw.launcher.glide.MyGlide;
import com.nrmyw.launcher.view.AdapterMeasureHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainLauncherIconAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<SystemAppInfoBean> apps;
    private Context context;
    private LayoutInflater layoutInflater;
    private ItemClick itemClick;
    private AdapterMeasureHelper mCardAdapterHelper = new AdapterMeasureHelper();

    public MainLauncherIconAdapter(Context context, ItemClick itemClick) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.itemClick = itemClick;
        this.apps = new ArrayList<>();
    }

    public void setData(List<SystemAppInfoBean> apps) {
        if (apps == null) {
            this.apps = new ArrayList<>();
            return;
        }

        this.apps = apps;
        this.notifyDataSetChanged();
        getItemData(0);
    }

    public void  getItemData(int index){
        if(null==apps||apps.size()==0){
            return;
        }
        SystemAppInfoBean systemAppInfoBean=apps.get(index);
        itemClick.nowSelect(systemAppInfoBean);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.adapter_lv_apps_layout, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        Log.i(tag, "on bing view " + position);
        final ViewHodler viewHodler = (ViewHodler) holder;
        final SystemAppInfoBean app = apps.get(position);
        Drawable initBm=PackageManagerUtil.getInstance().getIcon(app.getPakeageName());
        viewHodler.appIconIV.setImageDrawable(initBm);
//        try {
//
//            BitmapDrawable bd= (BitmapDrawable) initBm;
//            Bitmap bm = ReflectedUtil.createReflectedImages( bd.getBitmap());
//
//            ////////////
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            byte[] bytes=baos.toByteArray();
//
//
//
//            Glide.with(context).load(bytes).into(viewHodler.appIconIV);
////            viewHodler.appIconIV.setImageBitmap(bm);
//        }catch (Exception e){
//            viewHodler.appIconIV.setImageDrawable(initBm);
//        }

//        viewHodler.appIconIV.post(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        });

//        MyGlide.getInstance().setBitMapUsRefleted(context,viewHodler.appIconIV,app.getIconRs());
//        viewHodler.appIconIV.setBackground(PackageManagerUtil.getInstance().getIcon(app.getPakeageName()));
        viewHodler.appIconIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.nowSelect(app);
            }
        });
        viewHodler.appIconIV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
        viewHodler.appNameTV.setText(app.getName());
    }

    @Override
    public int getItemCount() {
        if(null==apps){
            return 0;
        }
        return apps.size();
    }


    class ViewHodler extends RecyclerView.ViewHolder {
        private ImageView appIconIV;
        private TextView appNameTV;

        public ViewHodler(View itemView) {
            super(itemView);
            appIconIV = itemView.findViewById(R.id.iv_app_icon);
            appNameTV = itemView.findViewById(R.id.tv_app_name);
        }
    }

    public interface ItemClick {

            void nowSelect(SystemAppInfoBean systemAppInfoBean);

    }
}