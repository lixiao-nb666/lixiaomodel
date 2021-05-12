package com.lixiao.http.okhttp.bean;

import java.io.Serializable;

public class Send implements Serializable{
	private String ss;//sendStr
	private String gs;//gsonStr
	
	
	public Send() {
		super();
	}


	public Send(String ss, String gs) {
		super();
		this.ss = ss;
		this.gs = gs;
	}


	public String getSs() {
		return ss;
	}


	public void setSs(String ss) {
		this.ss = ss;
	}


	public String getGs() {
		return gs;
	}


	public void setGs(String gs) {
		this.gs = gs;
	}


	@Override
	public String toString() {
		return "Send [ss=" + ss + ", gs=" + gs + "]";
	}
	
	
	
}
