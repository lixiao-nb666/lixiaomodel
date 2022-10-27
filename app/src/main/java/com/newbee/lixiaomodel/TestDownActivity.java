package com.newbee.lixiaomodel;

import android.widget.TextView;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;
import com.lixiao.down.event.XiaoGeDownEvent;
import com.lixiao.down.event.XiaoGeDownEventSubject;
import com.lixiao.down.event.XiaoGeDownEventType;
import com.lixiao.down.listen.XiaoGeDownListen;
import com.lixiao.down.listen.XiaoGeDownListenObserver;
import com.lixiao.down.listen.XiaoGeDownListenSubject;
import com.nrmyw.launcher.R;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/9 0009 15:35
 */
public class TestDownActivity extends BaseCompatActivity {
    private TextView downProgressTV;
    private XiaoGeDownListenObserver xiaoGeDownListenObserver=new XiaoGeDownListenObserver() {
        @Override
        public void ready(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setTV("ready");
        }

        @Override
        public void start(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setTV("start");
        }

        @Override
        public void downProgress(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, float progress, String downloadSpeed, long needTime) {
            setTV("progress:"+progress);
        }

        @Override
        public void downOver(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setTV("downOver");
        }

        @Override
        public void onPause(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setTV("onPause");
        }

        @Override
        public void onCancel(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean) {
            setTV("onCancel");
        }

        @Override
        public void statuStr(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String statuStr) {
            setTV(statuStr);
        }

        @Override
        public void err(XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean, String err) {
            setTV(err);
        }
    };

    private void setTV(final String str){
        basehandler.post(new Runnable() {
            @Override
            public void run() {
                downProgressTV.setText(str);
            }
        });

    }

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_test_down;
    }

    @Override
    public void initView() {
        downProgressTV=findViewById(R.id.tv_down_progress);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initControl() {
        basehandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean=new XiaoGeDownNeedInfoBean();
                xiaoGeDownNeedInfoBean.setDownUrl("http://innosmart2018.oss-cn-shenzhen.aliyuncs.com/private/u5ff804c3f6b82e42e39168fe/2021-01-15%20134759.mov");
                XiaoGeDownEvent.getInstance().update(XiaoGeDownEventType.DOWN_FILE,xiaoGeDownNeedInfoBean);
            }
        },5000);
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
}
