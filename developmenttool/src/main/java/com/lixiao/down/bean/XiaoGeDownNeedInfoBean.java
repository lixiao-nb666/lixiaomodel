package com.lixiao.down.bean;

import android.text.TextUtils;

import com.lixiao.down.config.XiaoGeDownLoaderConfig;
import java.io.File;
import java.io.Serializable;
import java.net.URLEncoder;

public class XiaoGeDownNeedInfoBean implements Serializable {
    /**
     * 网络参数相关
     */
    private String urlHead;//链接的请求头
    private String urlBody;//链接在服务器里面的地址
    private String downUrl;//下載完整地址

    /**
     * 下载的方法配置
     */
    private XiaoGeDownType downType;//下載種類

    /**
     * 下载本地管理
     */
    private String fileName;//文件名字
    private String parentFilePath;//父文件夾的路徑
    private String localAbsPath;//保存的完整路徑
    private int downErrNumb;

    public int getDownErrNumb() {
        return downErrNumb;
    }

    public void setDownErrNumb(int downErrNumb) {
        this.downErrNumb = downErrNumb;
    }

    @Override
    public String toString() {
        return "XiaoGeDownNeedInfoBean{" +
                "urlHead='" + urlHead + '\'' +
                ", urlBody='" + urlBody + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", downType=" + downType +
                ", fileName='" + fileName + '\'' +
                ", parentFilePath='" + parentFilePath + '\'' +
                ", localAbsPath='" + localAbsPath + '\'' +
                '}';
    }

    public String getUrlHead() {
        return urlHead;
    }

    public String getUrlBody() {
        return urlBody;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public String getNeedDownUrl(){
        if(XiaoGeDownLoaderConfig.needDownEncoded){
            return getDownUrlIsEncoded();

        }else {
            return getDownUrl();
        }
    }

    public String getDownUrlIsEncoded() {
        try {
            if(!TextUtils.isEmpty(fileName)){
                String encodedStr=URLEncoder.encode(fileName,"utf-8");
                return downUrl.replace(fileName,encodedStr);
            }else {
                return downUrl;
            }
        }catch (Exception e){
        }
        return downUrl;
    }

    public XiaoGeDownType getDownType() {
        if (null == downType) {
            return XiaoGeDownType.OK_DOWN;
        }
        return downType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileCacheName(){
        return fileName+".lixiaoTemp";
    }

    public String getParentFilePath() {
        return parentFilePath;
    }


    public String getLocalAbsPath() {
        return localAbsPath;
    }

    /**
     * 设置数据的方法，都要修改
     */

    public void setUrlHead(String urlHead) {
        //字符串最后要添加/
        this.urlHead = strEndAddFileSeparstor(urlHead);
        urlHeadAndBodyCreateDownUrl();
    }

    public void setUrlBody(String urlBody) {
        //字符串开始要删除/
        this.urlBody = strStartRemoveFileSeparstor(urlBody);
        urlHeadAndBodyCreateDownUrl();
    }

    public void setDownUrl(String downUrl) {


        this.downUrl=downUrl;
        downUrlCreateFileNameAndFilePathAndParentFilePath();
    }

    public void setDownType(XiaoGeDownType downType) {
        this.downType = downType;
    }

    public void setFileName(String fileName) {
        //字符串开始要删除/
        this.fileName = strStartRemoveFileSeparstor(fileName);
        fileNameAndParentFilePathCreatrLocalPath();
    }

    public void setParentFilePath(String parentFilePath) {
        //字符串结束要删除/
        this.parentFilePath = strEndRemoveFileSeparstor(parentFilePath);
        fileNameAndParentFilePathCreatrLocalPath();
    }

    public void setLocalAbsPath(String localAbsPath) {
        this.localAbsPath = localAbsPath;
        fileLocalPathCreateFileNameAndParentFilePath();
    }

    private String strEndAddFileSeparstor(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                int l = str.length();
                String lastStr = str.substring(l - 1, l);
                if (!File.separator.equals(lastStr)) {
                    str += File.separator;
                }
            }
        } catch (Exception e) {

        }
        return str;
    }

    private String strEndRemoveFileSeparstor(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                int l = str.length();
                String lastStr = str.substring(l - 1, l);
                if (File.separator.equals(lastStr)) {
                    str = str.substring(0, l - 1);
                }
            }
        } catch (Exception e) {
        }
        return str;
    }

    private String strStartRemoveFileSeparstor(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                if (File.separator.equals(str.substring(0, 1))) {
                    str = str.substring(1);
                }
            }
        } catch (Exception e) {
        }
        return str;
    }


    /**
     * 自动生成数据的私有方法
     */
    private void urlHeadAndBodyCreateDownUrl() {
        if (TextUtils.isEmpty(downUrl) && !TextUtils.isEmpty(urlHead) && !TextUtils.isEmpty(urlBody)) {
            downUrl = urlHead + urlBody;
            downUrlCreateFileNameAndFilePathAndParentFilePath();
        }
    }

    private void downUrlCreateFileNameAndFilePathAndParentFilePath() {
        if (!TextUtils.isEmpty(downUrl)) {
            boolean nowFileNameIsNull = TextUtils.isEmpty(fileName);
            boolean nowParentFilePathIsNull = TextUtils.isEmpty(parentFilePath);
            if (nowFileNameIsNull) {
                int index = downUrl.lastIndexOf(File.separator);
                if (index != -1) {
                    fileName = downUrl.substring(index + 1);
                }

            }
            if (nowParentFilePathIsNull) {
                if (TextUtils.isEmpty(XiaoGeDownLoaderConfig.defParentFilePath)) {
                    parentFilePath = XiaoGeDownLoaderConfig.getFinalDefParentFilePath();
                } else {
                    parentFilePath = XiaoGeDownLoaderConfig.defParentFilePath;
                }
            }
            if (nowFileNameIsNull || nowParentFilePathIsNull) {
                fileNameAndParentFilePathCreatrLocalPath();
            }
        }
    }

    private void fileNameAndParentFilePathCreatrLocalPath() {
        if (!TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(parentFilePath)) {
            localAbsPath = parentFilePath + File.separator + fileName;
        }
    }

    private void fileLocalPathCreateFileNameAndParentFilePath() {
        if (!TextUtils.isEmpty(localAbsPath)) {
            int index = localAbsPath.lastIndexOf(File.separator);
            if (index != -1) {
                fileName = localAbsPath.substring(index + 1);
                parentFilePath = localAbsPath.substring(0, index);
            }
        }
    }

}
