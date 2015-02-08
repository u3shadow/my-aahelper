package com.example.lxiao.aahelper.database;

import android.content.ContentResolver;
import android.content.Context;

import com.example.lxiao.aahelper.R;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxiao on 2015/2/8.
 */
public class SQLiteDataBaseConfig {
    private static String mdatabasename;
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
        String[] mstring = mcontext.getResources().getStringArray(R.array._sdatabasename);
        String path = mcontext.getPackageCodePath();
        for(int i = 0; i < mstring.length; i++)
        {
            list.add(mstring[i]);
        }
        return list;
    }
}
