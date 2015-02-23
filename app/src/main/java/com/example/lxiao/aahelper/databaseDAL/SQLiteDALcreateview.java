package com.example.lxiao.aahelper.databaseDAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lxiao.aahelper.database.SQLiteHelper;

/**
 * Created by U3 on 2015/2/18.
 */
public class SQLiteDALcreateview implements SQLiteHelper.SQLiteDataTable{
    private Context mcontext;
    public SQLiteDALcreateview(Context pcontext)
    {
        mcontext = pcontext;
    }
    @Override
    public void oncreate(SQLiteDatabase qdatabase) {
        StringBuilder s_CreateTableScript = new StringBuilder();
        Log.v("sk","createview");
        s_CreateTableScript.append("		Create View vpayout As ");
        s_CreateTableScript.append("		select a.*,b.ParentId,b.TypeName,b.Path,b.Flag,c.BookName from PayOut a LEFT JOIN Type b ON a.pTypeId = b.TypeId  LEFT JOIN Book c ON a.pBookId = c.BookId");

        qdatabase.execSQL(s_CreateTableScript.toString());
    }

    @Override
    public void upgrate(SQLiteDatabase qdatabase) {

    }
}
