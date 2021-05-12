package com.lixiao.http.okhttp.bean;

public class Msg {
	private String o;// object use gson.jar to gsonString
	private int c;// request back code,0 is false ,1 is true
	private String m;// request back message ,if is false ,message is exception

	public Msg() {
		super();
	}

	public Msg(String o, int c, String m) {
		super();
		this.o = o;
		this.c = c;
		this.m = m;
	}

	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	@Override
	public String toString() {
		return "Msg [o=" + o + ", c=" + c + ", m=" + m + "]";
	}




}
