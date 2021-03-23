package com.lixiao.build.mybase;

import android.util.Log;

public class LG {

    /**
     * 输入tag标识出具体类的具体方法，直接打出对应的异常错误
     */
    public static void e(String tag, String eStr) {
        StringBuilder sb = new StringBuilder();
        sb.append("----lixiao 程序异常 >>>>>>位于");
        sb.append(tag);
        sb.append("的");
        sb.append(eStr);
        Log.e(tag, sb.toString());
    }

    /**
     * 输入tag标识出具体类的具体方法，直接打出对应的异常错误
     */
    public static void e(String tag, Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append("----lixiao 程序异常 >>>>>>位于");
        sb.append(tag);
        sb.append("的");
        sb.append(e.toString());
        Log.e(tag, sb.toString());
    }

    /**
     * 输入tag标识出具体类的具体方法，直接打出对应的日志
     */
    public static void i(String tag, Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append("----lixiao 数据查询 >>>>>>位于");
        sb.append(tag);
        sb.append("的");
        sb.append(object.toString());
        Log.i(tag, sb.toString());
    }

    /**
     * 专门为服务存在的查询数据自定义LOG
     */
    public static void w(String tag, Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append("----service 数据查询>>>>>>位于");
        sb.append(tag);
        sb.append("的");
        sb.append(object.toString());
        Log.w(tag,sb.toString());
    }

    /**
     * 专门为网络初始大数据存在的查询自定义LOG
     */
    public static void d(String tag, Object object) {
        StringBuilder sb = new StringBuilder();
        sb.append("----dashuju 数据查询 >>>>>>位于");
        sb.append(tag);
        sb.append("的");
        sb.append(object.toString());
        Log.d(tag, sb.toString());
    }
}
