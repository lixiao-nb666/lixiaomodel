package com.lixiao.oss.listen;


/**
 * Created by xiefuning on 2017/5/12.
 * about:
 */

public interface OssServiceDownListenObserver {
    /**
     *下载进度
     */
    public void progress(String fileName, String filePath, int progress, float downloadSpeed);
    /**
     * 状态文字说明
     */
    public void statuStr(String fileName, String filePath, String statuStr);

    /**
     * 出错了
     */
    public void err(String fileName, String filePath, String errStr);


    /**
     * 下载完成
     */
    public void downOver(String fileName, String filePath);


}
