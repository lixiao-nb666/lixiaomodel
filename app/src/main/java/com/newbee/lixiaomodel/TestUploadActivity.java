package com.newbee.lixiaomodel;

import android.util.Log;
import android.widget.TextView;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.activity.BaseCompatActivity;
import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;
import com.lixiao.down.event.XiaoGeDownEvent;
import com.lixiao.down.event.XiaoGeDownEventType;
import com.lixiao.down.listen.XiaoGeDownListen;
import com.lixiao.down.listen.XiaoGeDownListenObserver;
import com.lixiao.http.okhttp.DataCallBack;
import com.lixiao.http.okhttp.OkHttpManager;
import com.lixiao.oss.event.OssServiceEventSubscriptionSubject;
import com.lixiao.oss.listen.OssServiceUploadListenObserver;
import com.lixiao.oss.listen.OssServiceUploadListenSubscriptionSubject;
import com.lixiao.oss.upload.OssUploadUtil;
import com.nrmyw.launcher.R;
import com.nrmyw.launcher.util.UrlToFilePathUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/9 0009 15:35
 */
public class TestUploadActivity extends BaseCompatActivity {
    private TextView downProgressTV;
    private OssServiceUploadListenObserver ossServiceUploadListenObserver=new OssServiceUploadListenObserver() {
        @Override
        public void uploadOk(String uploadFilePath, String fileName) {
            setTV("uploadOk:"+fileName);
        }

        @Override
        public void uploadProgress(String uploadFilePath, int progress) {
            setTV("uploadProgress:"+uploadFilePath+"------"+progress);
        }

        @Override
        public void uploadErr(String uploadFilePath, String errStr) {
            setTV("uploadErr:"+uploadFilePath+"----"+errStr);
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
                OssServiceEventSubscriptionSubject.getInstence().upload("lixiaoDemoTest3_9.mp4","/storage/emulated/0/lixiaodown/lixiaomodel/2021-01-15%2013475911.mov");
            }
        },5000);
        OssServiceUploadListenSubscriptionSubject.getInstence().attach(ossServiceUploadListenObserver);
    }

    @Override
    public void closeActivity() {
        OssServiceUploadListenSubscriptionSubject.getInstence().detach(ossServiceUploadListenObserver);
    }
    String filePath;
    private DataCallBack dataCallBack=new DataCallBack() {
        @Override
        public void requestFailure(String request, IOException e, int netbs) {
            Log.i(tag,"----kankanfilePath111:"+filePath);
        }

        @Override
        public void requestSuccess(String result, int netbs) throws Exception {
            Log.i(tag,"----kankanfilePath222:"+result);
        }
    };
    @Override
    public void viewIsShow() {
        filePath= UrlToFilePathUtil.uriToFilePath(getIntent(),context);
        Log.i(tag,"----kankanfilePath:"+filePath);
        String url="https://marking.picture.innocn.com/api/file/putPic";
//        ?language=zh
        List<String> pathList=new ArrayList<>();
        pathList.add(filePath);
        Map<String,String> map=new HashMap<>();
        map.put("language","zh");
        OkHttpManager.getInstance().uploadFiles(url, pathList, map, dataCallBack,1);


    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }
}
