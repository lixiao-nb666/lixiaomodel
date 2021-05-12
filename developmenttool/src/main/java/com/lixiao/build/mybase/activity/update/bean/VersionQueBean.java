package com.lixiao.build.mybase.activity.update.bean;

import java.io.Serializable;

public class VersionQueBean implements Serializable{
	private String appName;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return "VersionQueBean [appName=" + appName + "]";
	}
	
	

}
