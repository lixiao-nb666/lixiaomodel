package com.lixiao.oss.event;


/**
 * Created by xiefuning on 2017/5/12.
 * about:
 */

public interface OssServiceEventObserver {
    /**
     *  输入文件名字和本地路径就可以了
     * @param fileName
     * @param localFilePath
     */
    public void upload(String fileName, String localFilePath);

    /**
     * 取消上传
     * @param localFilePath
     */
    public void cacelUpload(String localFilePath);



    /**
     *  输入文件名字和本地路径就可以了
     * @param fileName
     * @param localFilePath
     */
    public void down(String ossDownUrl, String fileName, String localFilePath);

    /**
     * 取消上传
     */
    public void cacelDown(String downOssUrl);


}
