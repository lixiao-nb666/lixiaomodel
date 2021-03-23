package com.lixiao.oss.bean;

import java.io.Serializable;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/3/11 0011 15:43
 */
public class OssConfigBean implements Serializable {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret ;
    private String  bucketName;
    private String ossUploadPath;
    private String ossDownUrlHead;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getOssUploadPath() {
        return ossUploadPath;
    }

    public void setOssUploadPath(String ossUploadPath) {
        this.ossUploadPath = ossUploadPath;
    }

    public String getOssDownUrlHead() {
        return ossDownUrlHead;
    }

    public void setOssDownUrlHead(String ossDownUrlHead) {
        this.ossDownUrlHead = ossDownUrlHead;
    }

    @Override
    public String toString() {
        return "OssConfigBean{" +
                "endpoint='" + endpoint + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", ossUploadPath='" + ossUploadPath + '\'' +
                ", ossDownUrlHead='" + ossDownUrlHead + '\'' +
                '}';
    }
}
