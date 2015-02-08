package com.example.lxiao.aahelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by lxiao on 2015/2/8.
 */
public class SQLiteHelper extends SQLiteOpenHelper{
    private static SQLiteDataBaseConfig dataBaseConfig;
    private static SQLiteHelper instance;
    private Context mcontext;
    private SQLiteHelper(Context context)
    {
       super(context,dataBaseConfig.getdatabasename(),null,dataBaseConfig.getversion());
        mcontext = context;
    }
    public SQLiteHelper getInstance(Context pcontext)
    {
        if(instance == null)
        {
            instance = new SQLiteHelper(pcontext);
        }
        else;
        return instance;
    }
    public ArrayList  createtable()
    {
        return dataBaseConfig.gettable();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public interface  sqlitedatatable{
        public void oncreate(SQLiteDatabase qdatabase);
        public void upgrate(SQLiteDatabase qdatabase);
    }
}
