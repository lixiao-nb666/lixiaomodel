package com.lixiao.build.util.systemapp.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixiao.build.util.systemapp.PackageManagerUtil;
import com.lixiao.build.util.systemapp.bean.SystemAppInfoBean;
import com.lixiao.developmenttool.R;
import com.lixiao.view.viewpager.ReflectedImageView;


import java.util.List;

public class SystemAppsListAdapter extends BaseAdapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<SystemAppInfoBean> apps;
    private Context context;
    private ItemChick itemChick;

    public SystemAppsListAdapter(Context context, List<SystemAppInfoBean> apps, ItemChick itemChick) {
        this.apps = apps;
        this.context = context;
        this.itemChick = itemChick;
    }


    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int i) {
        return apps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (i - apps.size() >= 0) {
            return view;
        }
        ViewHodler viewHodler = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_apps_layout, null);
            viewHodler = new ViewHodler();
            viewHodler.appLL = view.findViewById(R.id.ll_app);
            viewHodler.appIconIV = view.findViewById(R.id.iv_app_icon);
            viewHodler.appNameTV = view.findViewById(R.id.tv_app_name);
            view.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) view.getTag();
        }
        final SystemAppInfoBean app = apps.get(i);
//        MyGlide.getInstance().setBitMap();
        viewHodler.appIconIV.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        viewHodler.appIconIV.setImageDrawable(PackageManagerUtil.getInstance().getIcon(app.getPakeageName()));
//        Drawable drawable=PackageManagerUtil.getInstance().getIcon(app.getPakeageName());
//        BitmapDrawable bd = (BitmapDrawable) drawable;
//        viewHodler.appIconIV.setShowBitMap(bd.getBitmap());
        viewHodler.appIconIV.setImageDrawable(PackageManagerUtil.getInstance().getIcon(app.getPakeageName()));
        viewHodler.appIconIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemChick.nowClicked(i, view,app);
            }
        });
        viewHodler.appIconIV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemChick.nowLongClicker(i,v,app);
                return false;
            }
        });
        viewHodler.appNameTV.setText(app.getName());
        if (i == 0) {
            itemChick.firstViewOk(view);
        }
        return view;
    }


    public interface ItemChick {

        public void firstViewOk(View v);

        public void nowClicked(int index, View view, SystemAppInfoBean appInfoBean);


        public void nowLongClicker(int index, View view, SystemAppInfoBean appInfoBean);

    }

    public class ViewHodler {
        private LinearLayout appLL;
        private ImageView appIconIV;
        private TextView appNameTV;
    }

}
