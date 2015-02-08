package com.example.lxiao.aahelper.databaseDAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lxiao.aahelper.model.User;
import com.example.lxiao.aahelper.utility.DateTools;

/**
 * Created by lxiao on 2015/2/8.
 */
public class SQLiteDALuser extends SQLiteDALbase{

    public SQLiteDALuser(Context pcontext) {
        super(pcontext);
    }

    @Override
    protected Object findmodel(Cursor pcursor) {
        return null;
    }

    @Override
    protected String[] GetTableNameAndPk() {
        return new String[]{"User","UserID"};
    }

    @Override
    public void oncreate(SQLiteDatabase qdatabase) {

    }

    @Override
    public void upgrate(SQLiteDatabase qdatabase) {

    }
    public boolean insertuser(User puser)
    {
      ContentValues mcontentvalue = getcontentvalue(puser);
       long mid = getdatabase().insert(GetTableNameAndPk()[0],null,mcontentvalue);
        puser.setMuserid((int)mid);
        return mid >= 0;
    }
    public ContentValues getcontentvalue(User puser)
    {
        ContentValues mcontentvalue = new ContentValues();
        mcontentvalue.put("USERNAME",puser.getMusername());
        mcontentvalue.put("USERSTATE",puser.getMdeletestate());
        mcontentvalue.put("USERCREATEDATE", DateTools.getFormatDateTime(puser.getMcreatedate(),"yyyy-MM-dd HH:mm:ss"));
        return mcontentvalue;
    }
}

