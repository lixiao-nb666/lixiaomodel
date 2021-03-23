package com.lixiao.build.util.root;

import com.lixiao.build.mybase.LG;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 用root权限执行Linux下的Shell指令
 * 
 * @author lixiao
 * @since 2014-09-09
 */
public class RootShellCmd {
	private final String tag=getClass().getSimpleName()+">>>>";
 	private static RootShellCmd shellCmd;
	private OutputStream os;
	private RootShellCmd(){}

	public static RootShellCmd getInstance(){
		if(null==shellCmd){
			synchronized (RootShellCmd.class){
				if(null==shellCmd){
					shellCmd=new RootShellCmd();
				}
			}
		}
		return shellCmd;
	}
 
	/**
	 * 执行shell指令
	 * 
	 * @param cmd
	 *            指令
	 */
	public final void exec(String cmd) {
		try {
			if (os == null) {
				os = Runtime.getRuntime().exec("su").getOutputStream();
			}
			os.write(cmd.getBytes());
			os.flush();
		} catch (Exception e) {
			LG.i(tag,"---------exec:"+e.toString());
		}
	}
 
	/**
	 * 后台模拟全局按键
	 * 
	 * @param keyCode
	 *            键值
	 */
	public final void simulateKey(int keyCode) {
		execByRuntime("input keyevent " + keyCode + "\n");
	}



	/**
	 * 执行shell 命令， 命令中不必再带 adb shell
	 *
	 * @param cmd
	 * @return Sting  命令执行在控制台输出的结果
	 */
	public static String execByRuntime(String cmd) {
		Process process = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
			inputStreamReader = new InputStreamReader(process.getInputStream());
			bufferedReader = new BufferedReader(inputStreamReader);

			int read;
			char[] buffer = new char[4096];
			StringBuilder output = new StringBuilder();
			while ((read = bufferedReader.read(buffer)) > 0) {
				output.append(buffer, 0, read);
			}
			return output.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != inputStreamReader) {
				try {
					inputStreamReader.close();
				} catch (Throwable t) {
					//
				}
			}
			if (null != bufferedReader) {
				try {
					bufferedReader.close();
				} catch (Throwable t) {
					//
				}
			}
			if (null != process) {
				try {
					process.destroy();
				} catch (Throwable t) {
					//
				}
			}
		}
	}
}