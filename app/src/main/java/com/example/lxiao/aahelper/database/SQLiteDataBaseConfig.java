package com.example.lxiao.aahelper.database;

import android.content.ContentResolver;
import android.content.Context;

import com.example.lxiao.aahelper.R;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxiao on 2015/2/8.
 * Data base config
 */
public class SQLiteDataBaseConfig {
    private static String DATABASE_NAME = "AAhelper";
    private static int version;
    private Context mcontext;
    private static SQLiteDataBaseConfig instance;
    private SQLiteDataBaseConfig(Context pcontext)
    {
        mcontext = pcontext;
    }
    public static SQLiteDataBaseConfig getinstance(Context pcontext)
    {
        if(instance == null)
        {
            instance = new SQLiteDataBaseConfig(pcontext);
        }
        else;

        return instance;
    }
    public String getdatabasename()//get database name
    {
        return DATABASE_NAME;
    }
    public int getversion()//get database version
    {
        return version;
    }
    public ArrayList gettable()//unknow means
    {
        ArrayList list = new ArrayList();
        String[] mstring = mcontext.getResources().getStringArray(R.array._sdatabasename);
        String path = mcontext.getPackageName()+".databaseDAL.SQLiteDALbase.";
        for(int i = 0; i < mstring.length; i++)
        {
            list.add(path+mstring[i]);
        }
        return list;
    }
}
