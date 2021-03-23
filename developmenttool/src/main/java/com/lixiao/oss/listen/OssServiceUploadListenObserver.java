package com.lixiao.oss.listen;


/**
 * Created by xiefuning on 2017/5/12.
 * about:
 */

public interface OssServiceUploadListenObserver {

    public void uploadOk(String uploadFilePath, String fileName);

    public void uploadProgress(String uploadFilePath, int progress);

    public void uploadErr(String uploadFilePath, String errStr);
}
