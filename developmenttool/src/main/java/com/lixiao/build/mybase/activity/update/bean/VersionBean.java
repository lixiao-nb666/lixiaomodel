package com.lixiao.build.mybase.activity.update.bean;

import java.io.Serializable;

public class VersionBean implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appName;
    private String versionCode;
    private String downUrl;//下载地址
    private String notes;//备注说明
    boolean isNeedUpdate;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public boolean isNeedUpdate() {
		return isNeedUpdate;
	}
	public void setNeedUpdate(boolean isNeedUpdate) {
		this.isNeedUpdate = isNeedUpdate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "VersionBean [appName=" + appName + ", versionCode="
				+ versionCode + ", downUrl=" + downUrl + ", notes=" + notes
				+ ", isNeedUpdate=" + isNeedUpdate + "]";
	}
    
    
	
}
