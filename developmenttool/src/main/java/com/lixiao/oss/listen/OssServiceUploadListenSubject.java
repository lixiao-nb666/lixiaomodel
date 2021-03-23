package com.lixiao.oss.listen;


public interface OssServiceUploadListenSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(OssServiceUploadListenObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(OssServiceUploadListenObserver observer);

    public void uploadOk(String uploadFilePath, String fileName);


    public void uploadProgress(String uploadFilePath, int progress);

    public void uploadErr(String uploadFilePath, String errStr);
}
