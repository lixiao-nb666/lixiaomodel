//package com.lixiao.view.viewpager;
//
//import android.content.Context;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.viewpager.widget.PagerAdapter;
//
//import com.lixiao.build.util.systemapp.PackageManagerUtil;
//import com.lixiao.build.util.systemapp.bean.SystemAppInfoBean;
//import com.lixiao.developmenttool.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GalleryAdapter extends PagerAdapter {
//
//    private List<SystemAppInfoBean> apps;
//    private Context context;
//
//    public GalleryAdapter(Context context,List<SystemAppInfoBean> apps) {
//        this.context=context;
//        this.apps=apps;
//    }
//
//    public void setData(List<SystemAppInfoBean> apps){
//        this.apps=apps;
//    }
//
//    @Override
//    public int getCount() {
//        if(null==apps||apps.size()==0){
//            return 0;
//        }
//
//        return apps.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        View view = LayoutInflater.from(context).inflate(R.layout.adapter_3d_apps_layout, null);
//
//         ReflectedImageView appIconIV = view.findViewById(R.id.iv_app_icon);
//         TextView appNameTV = view.findViewById(R.id.tv_app_name);
//        final SystemAppInfoBean app = apps.get(position);
////        MyGlide.getInstance().setBitMap();
//     appIconIV.setScaleType(ImageView.ScaleType.CENTER_CROP);
////        viewHodler.appIconIV.setImageDrawable(PackageManagerUtil.getInstance().getIcon(app.getPakeageName()));
//        Drawable drawable= PackageManagerUtil.getInstance().getIcon(app.getPakeageName());
//        BitmapDrawable bd = (BitmapDrawable) drawable;
//       appIconIV.setShowBitMap(bd.getBitmap());
////        viewHodler.appIconIV.setBackground(PackageManagerUtil.getInstance().getIcon(app.getPakeageName()));
//       appIconIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//       appIconIV.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                return false;
//            }
//        });
//        appNameTV.setText(app.getName());
//        container.addView(view);
//        return view;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//
//        container.removeView((View) object);
//    }
//}