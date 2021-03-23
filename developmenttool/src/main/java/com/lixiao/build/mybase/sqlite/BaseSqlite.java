package com.lixiao.build.mybase.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lixiao.build.mybase.appliction.BaseApplication;
import com.lixiao.build.mybase.bean.MyClassInfoBean;
import com.lixiao.build.mybase.sqlite.config.BaseSqliteConfig;
import com.lixiao.build.mybase.sqlite.util.SqliteUtil;


/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/9/21 0021 10:42
 */
class BaseSqlite extends SQLiteOpenHelper {



    private int sqlVersion;
    private String tablename;
    private String  createtableStr;


    public BaseSqlite(String tablename,String  createtableStr,int sqlVersion){
        super(BaseApplication.getContext(), BaseSqliteConfig.getDef_sql_name(), null, sqlVersion);
        this.tablename=tablename;
        this.createtableStr=createtableStr;
        this.sqlVersion=sqlVersion;


    }








    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createtableStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String oldTableReName=tablename+"_old";
        //老的表重命名
        String reNameOldTable = "alter table "+tablename+" rename to "+oldTableReName;
        db.execSQL(reNameOldTable);
        //创建新的表，表名跟原来一样，并保留原来的字段和增加新的字段
        db.execSQL(createtableStr);
        //将重命名后的老表中的数据导入新的表中
        String INSERT_DATA = "insert into "+tablename+" select *,'' from "+oldTableReName;
        db.execSQL(INSERT_DATA);
        //删除老表
        String DROP_PPERSON  = "drop table "+oldTableReName;
        db.execSQL(DROP_PPERSON);
    }


}
