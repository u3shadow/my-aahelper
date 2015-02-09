package com.example.lxiao.aahelper.databaseDAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.model.User;
import com.example.lxiao.aahelper.utility.DateTools;

import java.util.List;

/**
 * Created by lxiao on 2015/2/8.
 * implement action on user
 */
public class SQLiteDALuser extends SQLiteDALbase {

    public SQLiteDALuser(Context pcontext) {
        super(pcontext);
    }

    @Override
    protected Object findmodel(Cursor pcursor) {
        User muser = new User();
        muser.setMusername(pcursor.getString(pcursor.getColumnIndex("UserName")));
        muser.setMuserid(pcursor.getInt(pcursor.getColumnIndex("UserID")));
        muser.setMcreatedate(DateTools.getDate(pcursor.getString(pcursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss"));
        muser.setMdeletestate(pcursor.getInt(pcursor.getColumnIndex("DeleteState")));
        return muser;
    }

    @Override
    protected String[] GetTableNameAndPk() {
        return new String[]{"User", "UserID"};
    }
    //create table by implement interface method
    @Override
    public void oncreate(SQLiteDatabase qdatabase) {
        StringBuilder s_CreateTableScript = new StringBuilder();

        s_CreateTableScript.append("		Create  TABLE User(");
        s_CreateTableScript.append("				[UserID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
        s_CreateTableScript.append("				,[UserName] varchar(10) NOT NULL");
        s_CreateTableScript.append("				,[CreateDate] datetime NOT NULL");
        s_CreateTableScript.append("				,[DeleteState] integer NOT NULL");
        s_CreateTableScript.append("				)");

        qdatabase.execSQL(s_CreateTableScript.toString());
        initdefaultdata(qdatabase);
    }

    @Override
    public void upgrate(SQLiteDatabase qdatabase) {

    }
    //insert
    public boolean insertuser(User puser) {
        ContentValues mcontentvalue = getcontentvalue(puser);
        long mid = getdatabase().insert(GetTableNameAndPk()[0], null, mcontentvalue);
        puser.setMuserid((int) mid);
        return mid >= 0;
    }

    public ContentValues getcontentvalue(User puser) {
        ContentValues mcontentvalue = new ContentValues();
        mcontentvalue.put("UserName", puser.getMusername());
        mcontentvalue.put("DeleteState", puser.getMdeletestate());
        mcontentvalue.put("CreateDate", DateTools.getFormatDateTime(puser.getMcreatedate(), "yyyy-MM-dd HH:mm:ss"));
        return mcontentvalue;
    }

    public boolean deleteuser(String pcondition) {
        return Delete(GetTableNameAndPk()[0], pcondition);
    }

    public void updateuser(String pcondition, User puser) {
        getdatabase().update(GetTableNameAndPk()[0], getcontentvalue(puser), pcondition, null);
    }

    public List<User> getuser(String pcondition) {
        String msql = "Select * From User Where 1=1 " + pcondition;
        return getlist(msql);
    }

    protected void initdefaultdata(SQLiteDatabase pdatabase) {
        User muser = new User();
        String[] mstring = getcontext().getResources().getStringArray(R.array._ainituser);
        for (int i = 0; i < mstring.length; i++) {
            muser.setMusername(mstring[i]);
            ContentValues mcontentvalue = getcontentvalue(muser);
            getdatabase().insert(GetTableNameAndPk()[0], null, mcontentvalue);
        }
    }
}

