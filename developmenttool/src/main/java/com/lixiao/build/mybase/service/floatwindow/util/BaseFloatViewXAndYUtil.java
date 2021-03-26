package com.lixiao.build.mybase.service.floatwindow.util;

import android.text.TextUtils;

import com.lixiao.build.mybase.share.MyShare;



/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/1/20 0020 14:04
 */
public class BaseFloatViewXAndYUtil {
    private final String tag=getClass().getSimpleName()+">>>>";
    private static BaseFloatViewXAndYUtil util;

    private BaseFloatViewXAndYUtil(){
    }

    public static BaseFloatViewXAndYUtil getInstance(){
        if(null==util){
            synchronized (BaseFloatViewXAndYUtil.class){
                if(null==util){
                    util=new BaseFloatViewXAndYUtil();
                }
            }
        }
        return util;
    }

    public void putShare(String shareKey,int needShareX,int needShareY){
        MyShare.getInstance().putString(shareKey+shareX,needShareX+"");
        MyShare.getInstance().putString(shareKey+shareY,needShareY+"");
    }

    private final String shareX=tag+"_x";
    private final String shareY=tag+"_y";
    public int getShareX(String shareKey){
        String xStr= MyShare.getInstance().getString(shareKey+shareX);
        int startX=0;
        if(!TextUtils.isEmpty(xStr)){
            try {
                startX=Integer.valueOf(xStr);
            }catch (Exception e){}
        }
        return startX;
    }

    public int  getShareY(String shareKey){
        String yStr=MyShare.getInstance().getString(shareKey+shareY);
        int startY=0;
        if(!TextUtils.isEmpty(yStr)){
            try {
                startY=Integer.valueOf(yStr);
            }catch (Exception e){}
        }
        return startY;
    }
}
