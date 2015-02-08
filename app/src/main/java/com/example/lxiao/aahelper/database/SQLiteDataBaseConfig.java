package com.example.lxiao.aahelper.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxiao on 2015/2/8.
 */
public class SQLiteDataBaseConfig {
    private static String mdatabasename;
    private static int version;
    private static SQLiteDataBaseConfig instance;
    public static SQLiteDataBaseConfig getinstance()
    {
        if(instance == null)
        {
            instance = new SQLiteDataBaseConfig();
        }
        else;

        return instance;
    }
    public String getdatabasename()
    {
        return mdatabasename;
    }
    public int getversion()
    {
        return version;
    }
    public ArrayList gettable()
    {
        ArrayList list = new ArrayList();
        return list;
    }
}
