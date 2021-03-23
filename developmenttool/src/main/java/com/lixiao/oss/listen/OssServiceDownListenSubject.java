package com.lixiao.oss.listen;


public interface OssServiceDownListenSubject {
    /**
     * 增加订阅者
     *
     * @param observer
     */
    public void attach(OssServiceDownListenObserver observer);

    /**
     * 删除订阅者
     *
     * @param observer
     */
    public void detach(OssServiceDownListenObserver observer);

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

    public void progress(String fileName, String filePath, int progress, float downloadSpeed);
}
