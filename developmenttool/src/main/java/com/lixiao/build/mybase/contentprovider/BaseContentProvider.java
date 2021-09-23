package com.lixiao.build.mybase.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lixiao.build.mybase.LG;
import com.lixiao.build.mybase.sqlite.BaseSqlServer;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2021/6/4 0004 11:40
 */
public abstract class BaseContentProvider extends ContentProvider {
      private final int TABLE_ALl = 0; //访问表1的所有数据
      private final int TABLE_ONE = 1; //访问表1的单条数据
    private  UriMatcher uriMatcher;

    private final String tag=getClass().getSimpleName()+">>>>";


    public abstract BaseSqlServer getBaseSqlServer();
    public abstract String getAuthority();


    private String tablename;

    @Override
    public boolean onCreate() {
        LG.i(tag, "----第一次该提供者会调用的");

        tablename=getBaseSqlServer().getTablename();
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(getAuthority(),tablename,TABLE_ALl);
        uriMatcher.addURI(getAuthority(),tablename+"/#",TABLE_ONE);


        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //这里一定要记得 new MySQLieOpenHeler(this.getContext()); 否则就是爆空指针异常
        try {
            LG.i(tag,"query:111"+(getBaseSqlServer()==null));
            SQLiteDatabase  db= getBaseSqlServer().getDB();
            LG.i(tag,"query:11111"+(db==null));
            return db.query(tablename, projection, selection, selectionArgs, null, null, sortOrder, null);
        }catch (Exception e){
            LG.i(tag,"query:222"+e.toString());
            return null;
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        try {
            SQLiteDatabase  db= getBaseSqlServer().getDB();
            db.insert(tablename, null, contentValues);
        }catch (Exception e){
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        try {
            SQLiteDatabase  db= getBaseSqlServer().getDB();
            return db.delete(tablename, s, strings);
        }catch (Exception e){
            return -1;
        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        try {
            SQLiteDatabase  db= getBaseSqlServer().getDB();
            return db.update(tablename, contentValues, s, strings);
        }catch (Exception e){
            return -1;
        }

    }
}
