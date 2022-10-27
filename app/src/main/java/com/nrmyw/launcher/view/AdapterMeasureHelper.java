package com.nrmyw.launcher.view;


import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @创建者 mingyan.su
 * @创建时间 2018/9/5 19:13
 * @类描述 ${TODO}adapter中调用onCreateViewHolder, onBindViewHolder，计算间距和宽度
 */
public class AdapterMeasureHelper {


    public void onCreateViewHolder(ViewGroup parent, View itemView) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        lp.width = parent.getWidth() - ScreenUtil.dip2px(itemView.getContext(), 2 * (SpeedConfig.mPagePadding + SpeedConfig.mShowLeftCardWidth));
        itemView.setLayoutParams(lp);
    }

    public void onBindViewHolder(View itemView, final int position, int itemCount) {
        int padding = ScreenUtil.dip2px(itemView.getContext(), SpeedConfig.mPagePadding);
        itemView.setPadding(padding, 0, padding, 0);
        int leftMarin = position == 0 ? padding + ScreenUtil.dip2px(itemView.getContext(), SpeedConfig.mShowLeftCardWidth) : 0;
        int rightMarin = position == itemCount - 1 ? padding + ScreenUtil.dip2px(itemView.getContext(), SpeedConfig.mShowLeftCardWidth) : 0;
        setViewMargin(itemView, leftMarin, 0, rightMarin, 0);
    }

    private void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom);
            view.setLayoutParams(lp);
        }
    }

}
