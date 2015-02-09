package com.example.lxiao.aahelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lxiao.aahelper.utility.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by lxiao on 2015/2/8.
 * help open database
 */
public class SQLiteHelper extends SQLiteOpenHelper{
    private static SQLiteDataBaseConfig dataBaseConfig;//user to get database name and version
    private static SQLiteHelper instance;
    private Context mcontext;
    private Reflection mreflection = new Reflection();
    private SQLiteHelper(Context context)
    {
       super(context,dataBaseConfig.getdatabasename(),null,dataBaseConfig.getversion());
        mcontext = context;
    }
    public static SQLiteHelper getInstance(Context pcontext)//get the instance of SQLhelper
    {
        dataBaseConfig = SQLiteDataBaseConfig.getinstance(pcontext);
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
    public void onCreate(SQLiteDatabase db) //create table
    {
        ArrayList<String> _Arraylist = dataBaseConfig.gettable();
        for(int i = 0;i <_Arraylist.size();i++) {
            try {
                SQLiteDataTable _SQLiteDataTable = (SQLiteDataTable) mreflection.newInstance(_Arraylist.get(i),new Object[]{mcontext},new Class[]{Context.class});
                _SQLiteDataTable.oncreate(db);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
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
