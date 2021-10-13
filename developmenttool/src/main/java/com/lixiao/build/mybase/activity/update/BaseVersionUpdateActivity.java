package com.lixiao.build.mybase.activity.update;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lixiao.build.gson.MyGson;
import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.build.mybase.activity.update.bean.VersionBean;
import com.lixiao.build.mybase.activity.update.bean.VersionQueBean;
import com.lixiao.developmenttool.R;
import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;
import com.lixiao.down.event.XiaoGeDownEvent;
import com.lixiao.down.event.XiaoGeDownEventType;
import com.lixiao.down.listen.XiaoGeDownListen;
import com.lixiao.down.listen.XiaoGeDownListenObserver;
import com.lixiao.http.okhttp.DataCallBack;
import com.lixiao.http.okhttp.bean.Msg;
import com.lixiao.http.okhttp.util.NetToGet;
import com.lixiao.http.okhttp.util.NetToSend;

import java.io.IOException;
import java.util.List;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/5/10 0010 10:23
 */
public abstract class BaseVersionUpdateActivity extends BaseCompatActivity {
    public abstract String getVersionShowName();
    public abstract VersionQueBean getVersionUpdateInfo();
    public abstract String getQueVersionUrl();
    public abstract String getCheckVersionStr();
    public abstract void netQueOk(VersionBean versionBean);
    public abstract void netQueErr(String errStr);
    public abstract void downOverNeedDo(String localPath);
    public abstract int getBGRsId();


    public DataCallBack dataCallBack=new DataCallBack() {
        @Override
        public void requestFailure(String request, IOException e, int netbs) {
            LG.i(tag,"===========kankan---1:"+request+":"+e.toString());
            canToGet=true;
            netQueErr(request+":"+e.toString());
        }

        @Override
        public void requestSuccess(String result, int netbs) throws Exception {
            canToGet=true;
            Msg msg=NetToGet.getInstance().getMessageBean(result);

            if(msg.getC()==0){
                LG.i(tag,"===========kankan---:"+msg.getO());
                List<VersionBean> versionBeanList=MyGson.getInstance().getObjectList(msg.getO(),VersionBean.class);
                if(null==versionBeanList||versionBeanList.size()==0){
                    netQueErr("code==0,but obj is null");
                }else {
                    versionBean=versionBeanList.get(0);
                    if(!TextUtils.isEmpty(getCheckVersionStr())
                            &&!TextUtils.isEmpty(versionBean.getVersionCode())
                    &&getCheckVersionStr().equals(versionBean.getVersionCode())){
                        netQueErr("is new version");
                    }else {
                        netQueOk(versionBean);
                    }


                }


            }else {
                netQueErr(msg.getM());
            }
        }
    };
    private VersionBean versionBean;
    protected TextView versionNameTV,updateVersionInfoTV;
    protected Button nowUpdateBT,regetBT;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.bt_update_now){
                if(null!=versionBean&& !TextUtils.isEmpty(versionBean.getDownUrl())){
                    basehandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean=new XiaoGeDownNeedInfoBean();
                            xiaoGeDownNeedInfoBean.setDownUrl(versionBean.getDownUrl());
                            XiaoGeDownEvent.getInstance().update(XiaoGeDownEventType.DOWN_FILE,xiaoGeDownNeedInfoBean);
                        }
                    },500);
                }
            }else if(v.getId()==R.id.bt_reget){

            }




        }
    };

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_version_update ;
    }

    @Override
    public void initView() {
        versionNameTV=findViewById(R.id.tv_update_version_name);
        updateVersionInfoTV=findViewById(R.id.tv_show_update_info);
        nowUpdateBT=findViewById(R.id.bt_update_now);
        regetBT=findViewById(R.id.bt_reget);
        downProgressTV=findViewById(R.id.tv_down_info);
        findViewById(R.id.ll_bg).setBackgroundResource(getBGRsId());
    }

    @Override
    public void initData() {
        versionNameTV.setText(getVersionShowName());
        nowUpdateBT.setOnClickListener(onClickListener);
        regetBT.setOnClickListener(onClickListener);
        getNetData();
    }

    private boolean canToGet=true;
    private void getNetData(){
        if(canToGet){
            canToGet=false;
            NetToSend.getInstance().setSendToRequest(getQueVersionUrl(),"vvq", MyGson.getInstance().toGsonStr(getVersionUpdateInfo()),dataCallBack,0);
        }

    }


    @Override
    public void initControl() {
        XiaoGeDownListen.getInstance().addObserver(xiaoGeDownListenObserver);
    }

    @Override
    public void closeActivity() {
        XiaoGeDownListen.getInstance().removeObserver(xiaoGeDownListenObserver);
    }

    @Override
    public void viewIsShow() {

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }

    //版本更新的代碼
    protected TextView downProgressTV;
    private XiaoGeDownListenObserver xiaoGeDownListenObserver=new XiaoGeDownListenObserver() {
        @Override
        public void ready(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setDownProgressTV("ready");
        }

        @Override
        public void start(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setDownProgressTV("start");
        }

        @Override
        public void downProgress(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, float progress, String downloadSpeed, long needTime) {
            int showP= (int) progress;
            setDownProgressTV("progress:"+showP+"%");
        }

        @Override
        public void downOver(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setDownProgressTV("downOver");
            downOverNeedDo(xiaoGeDownNeedInfoBean.getLocalAbsPath());
        }

        @Override
        public void onPause(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setDownProgressTV("onPause");
        }

        @Override
        public void onCancel(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setDownProgressTV("onCancel");
        }

        @Override
        public void statuStr(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String statuStr) {
            setDownProgressTV(statuStr);
        }

        @Override
        public void err(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String err) {
            setDownProgressTV(err);
        }
    };
    protected void setDownProgressTV(final String str){
        basehandler.post(new Runnable() {
            @Override
            public void run() {
                downProgressTV.setText(str);
            }
        });
    }


}
