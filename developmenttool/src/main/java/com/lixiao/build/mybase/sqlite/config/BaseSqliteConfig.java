package com.lixiao.build.mybase.sqlite.config;

import com.lixiao.build.mybase.appliction.BaseApplication;
import com.lixiao.build.util.myapp.MyAppUtils;

/**
 * @author lixiaogege!
 * @description: one day day ,no zuo no die !
 * @date :2020/9/21 0021 10:51
 */
public class BaseSqliteConfig {


    public static final  String def_id_str="id";

    public static String getDef_sql_name() {
        String def_sql_name= MyAppUtils.getLastPackgeName();
        return def_sql_name;
    }


}
