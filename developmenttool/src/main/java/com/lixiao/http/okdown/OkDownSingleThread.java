package com.lixiao.http.okdown;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;
import com.lixiao.build.mybase.LG;
import com.lixiao.down.bean.XiaoGeDownNeedInfoBean;
import com.lixiao.down.config.XiaoGeDownLoaderConfig;
import com.lixiao.down.listen.XiaoGeDownListen;
import java.io.File;
import java.util.List;
import java.util.Map;

public class OkDownSingleThread extends Thread {
    private final String tag = getClass().getName() + ">>>>";

    private DownloadListener4WithSpeed downloadListener4WithSpeed = new DownloadListener4WithSpeed() {
        private long totalLength;
        private String readableTotalLength;

        @Override
        public void taskStart(@NonNull DownloadTask task) {
            XiaoGeDownListen.getInstance().start(xiaoGeDownNeedInfoBean);
        }

        @Override
        public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
            XiaoGeDownListen.getInstance().statuStr(xiaoGeDownNeedInfoBean, "connectStart:" + blockIndex);
        }

        @Override
        public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
            XiaoGeDownListen.getInstance().statuStr(xiaoGeDownNeedInfoBean, "connectEnd:" + blockIndex);
        }

        @Override
        public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
            totalLength = info.getTotalLength();
            readableTotalLength = Util.humanReadableBytes(totalLength, true);
            XiaoGeDownListen.getInstance().statuStr(xiaoGeDownNeedInfoBean, "infoReady--fileLength is:" + readableTotalLength);
        }

        @Override
        public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {
            Log.i(tag, "---------------------progressBlock:" + blockIndex + "------" + currentBlockOffset);
        }

        private long lastCountTime = 0;

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
            try {
                if (System.currentTimeMillis() - lastCountTime < 999) {
                    return;
                }
                lastCountTime = System.currentTimeMillis();
                //下载速度单位是秒
                long downLoadSpeed = taskSpeed.getBytesPerSecondAndFlush();
                //现在还需要下载的文件长度为
                long nowNeedDown = totalLength - currentOffset;
                //计算需要的时间
                long nowNeedTime = nowNeedDown / downLoadSpeed;
                final String readableOffset = Util.humanReadableBytes(currentOffset, true);
                final String progressStatus = readableOffset + "/" + readableTotalLength;
                final String speed = taskSpeed.speed();
                final String progressStatusWithSpeed = progressStatus + "(" + speed + ")";
                int progress = (int) (currentOffset * 100 / totalLength);
                XiaoGeDownListen.getInstance().downProgress(xiaoGeDownNeedInfoBean, progress, progressStatusWithSpeed, nowNeedTime);
            } catch (Exception e) {
            }
        }

        @Override
        public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {
            Log.i(tag, "---------------------blockEnd:" + blockIndex + "------" + info);


        }

        @Override
        public void taskEnd(@NonNull final DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
            try {
                if (null == task) {
                    return;
                }
                switch (cause) {
                    case COMPLETED:
                        //下载完成了,名字换回来
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    File cacheFile = task.getFile();
                                    if (cacheFile.exists()) {
                                        cacheFile.renameTo(new File(xiaoGeDownNeedInfoBean.getParentFilePath(), xiaoGeDownNeedInfoBean.getFileName()));
                                    }
                                    new Thread().sleep(222);
                                    XiaoGeDownListen.getInstance().downOver(xiaoGeDownNeedInfoBean);
                                    canRemove();
                                }catch (Exception e){}
                            }
                        }).start();

                        break;
                    case ERROR:
                    case PRE_ALLOCATE_FAILED:
                        //一个是错误了，一个是分配任务失败了
                        LG.i(tag,"============okdownerr:"+ realCause.toString());
                        XiaoGeDownListen.getInstance().err(xiaoGeDownNeedInfoBean, realCause.toString());
                        err();
                        break;
                    case CANCELED:
                        //任务被取消了
                        XiaoGeDownListen.getInstance().onCancel(xiaoGeDownNeedInfoBean);
                        canRemove();
                        break;
                    default:
                        canRemove();
                        break;
                }
            } catch (Exception e) {
                XiaoGeDownListen.getInstance().err(xiaoGeDownNeedInfoBean, cause + ":" + e.toString());
            }


        }
    };
    private DownloadTask task;
    private XiaoGeDownNeedInfoBean xiaoGeDownNeedInfoBean;
    private OkDownSingleThreadListen okDownSingleThreadListen;

    public OkDownSingleThread(XiaoGeDownNeedInfoBean setInfoBean, OkDownSingleThreadListen setListen) {
        xiaoGeDownNeedInfoBean = setInfoBean;
        okDownSingleThreadListen = setListen;
    }


    public void cancel() {
        try {
            if (null != task) {
                task.cancel();
                task = null;
            }
        } catch (Exception e) {
            task = null;
        }
    }

    @Override
    public void run() {
        super.run();

        try {
            if (XiaoGeDownLoaderConfig.downCheckFileExist && new File(xiaoGeDownNeedInfoBean.getParentFilePath(), xiaoGeDownNeedInfoBean.getFileName()).exists()) {
                XiaoGeDownListen.getInstance().downOver(xiaoGeDownNeedInfoBean);
                canRemove();
                return;
            }

            task = new DownloadTask.Builder(xiaoGeDownNeedInfoBean.getNeedDownUrl(), new File(xiaoGeDownNeedInfoBean.getParentFilePath()))
                    .setFilename(xiaoGeDownNeedInfoBean.getFileCacheName())
                    // the minimal interval millisecond for callback progress
                    .setMinIntervalMillisCallbackProcess(30)
                    // do re-download even if the task has already been completed in the past.
                    .setPassIfAlreadyCompleted(false)
                    .build();
//            if (XiaoGeDownLoaderConfig.can_synchronous_down) {
//                task.execute(downloadListener4WithSpeed);
//            } else {
//            }
            task.enqueue(downloadListener4WithSpeed);
        } catch (Exception e) {
            err();
        }

    }


    private void err(){
        try {
            okDownSingleThreadListen.downErr(xiaoGeDownNeedInfoBean);
        } catch (Exception e) {
        }
    }

    private void canRemove(){
        try {
            okDownSingleThreadListen.canRemove(xiaoGeDownNeedInfoBean);
        } catch (Exception e) {
        }
    }



}
