package com.example.lxiao.aahelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lxiao.aahelper.utility.Reflection;

import java.util.ArrayList;

/**
 * Created by lxiao on 2015/2/8.
 * help open database
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteDataBaseConfig dataBaseConfig;//user to get database name and version
    private static SQLiteHelper instance;
    private Context mcontext;
    private Reflection mreflection;

    private SQLiteHelper(Context context) {
        super(context, dataBaseConfig.getdatabasename(), null, dataBaseConfig.getversion());
        mcontext = context;
    }

    public static SQLiteHelper getInstance(Context pcontext)//get the instance of SQLhelper
    {

        if (instance == null) {
            dataBaseConfig = SQLiteDataBaseConfig.getinstance(pcontext);
            instance = new SQLiteHelper(pcontext);
        } else ;
        return instance;
    }

    public ArrayList createtable() {
        return dataBaseConfig.gettable();
    }

    @Override
    public void onCreate(SQLiteDatabase db) //get
    {
        mreflection = new Reflection();
        ArrayList<String> _Arraylist = dataBaseConfig.gettable();
        for (int i = 0; i < _Arraylist.size(); i++) {
            try {
                ((SQLiteDataTable) mreflection.newInstance(_Arraylist.get(i), new Object[]{mcontext}, new Class[]{Context.class})).oncreate(db);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //to call back
    public interface SQLiteDataTable {
        public void oncreate(SQLiteDatabase qdatabase);

        public void upgrate(SQLiteDatabase qdatabase);
    }
}
