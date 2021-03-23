package com.lixiao.oss.event;


public interface OssServiceEventSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(OssServiceEventObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(OssServiceEventObserver observer);

    public void upload(String fileName, String localFilePath);

    public void cacelUpload(String localFilePath);

    /**
     *  输入文件名字和本地路径就可以了
     * @param fileName
     * @param localFilePath
     */
    public void down(String ossDownUrl, String fileName, String localFilePath);

    /**
     * 取消上传
     * @param downOssUrl
     */
    public void cacelDown(String downOssUrl);


}
