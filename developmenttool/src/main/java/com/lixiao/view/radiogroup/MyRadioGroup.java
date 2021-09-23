package com.lixiao.view.radiogroup;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.List;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/24 0024 11:42
 */
public class MyRadioGroup extends RadioGroup {
    public MyRadioGroup(Context context) {
        this(context,null);
    }

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            int i=0;
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    if(i==0){
                        i++;
                        return;
                    }
                    i++;
                    MyRadioDataInfoBean dataInfoBean=list.get(checkedId);
                    itemClick.nowSelect(dataInfoBean);
                }catch (Exception e){}
            }
        });
    }

    private int w=150;
    private int h=200;
    public void setSize(int w,int h){
        this.w=w;
        this.h=h;
    }

    private int l=11;
    private int t=0;
    private int r=11;
    private int b=0;

    public void setLTRB(int l,int t,int r,int b){
        this.l=l;
        this.t=t;
        this.r=r;
        this.b=b;
    }

    private ItemClick itemClick;
    public void setListen(ItemClick itemClick){
        this.itemClick=itemClick;
    }

    private List<MyRadioDataInfoBean> list;
    public void setList(final List<MyRadioDataInfoBean> list, final String defSelectType){
        this.list=list;
        post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    MyRadioDataInfoBean dataInfoBean=list.get(i);
                    RadioButton radioButton = new RadioButton(getContext());
                    RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(w,h);
                    radioButton.setLayoutParams(layoutParams);

                    layoutParams.setMargins(l, t, r, b);
                    if(dataInfoBean.getBgRs()!=0){
                        radioButton.setBackgroundResource(dataInfoBean.getBgRs());
                    }
                    if(!TextUtils.isEmpty(dataInfoBean.getShowText())){
                        radioButton.setText(dataInfoBean.getShowText());
                    }
                    if(TextUtils.isEmpty(defSelectType)){
                        if(i==0){
                            radioButton.setChecked(true);
                            itemClick.nowSelect(dataInfoBean);
                        }
                    }else{
                        if(!TextUtils.isEmpty(dataInfoBean.getType())&&defSelectType.equals(dataInfoBean.getType())){
                            radioButton.setChecked(true);
                            itemClick.initSelect(dataInfoBean);
                        }
                    }
                    radioButton.setId(i);
                    addView(radioButton);
                }
            }
        });

    }

    public interface ItemClick{

        public void initSelect(MyRadioDataInfoBean dataInfoBean);

        public void nowSelect(MyRadioDataInfoBean dataInfoBean);
    }
}
