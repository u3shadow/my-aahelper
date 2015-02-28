package com.example.lxiao.aahelper.databaseDAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    //call back method witch SQLiteDALbase class can use it to get user object
    @Override
    protected Object findmodel(Cursor pcursor) {
        User muser = new User();
        muser.setMusername(pcursor.getString(pcursor.getColumnIndex("UserName")));
        muser.setMuserid(pcursor.getInt(pcursor.getColumnIndex("UserID")));
        //datatools is a tool class
        muser.setMcreatedate(DateTools.getDate(pcursor.getString(pcursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss"));
        muser.setMdeletestate(pcursor.getInt(pcursor.getColumnIndex("DeleteState")));
        return muser;
    }

    //get a string array 0 is table name 1 is Pk name
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
        Log.v("sk", "datatablecreate1");
        qdatabase.execSQL(s_CreateTableScript.toString());
        Log.v("sk", "datatablecreate2");
        initdefaultdata(qdatabase);
    }

    @Override
    public void upgrate(SQLiteDatabase qdatabase) {

    }

    //create a contentvalue by user object, use to save into database
    public ContentValues getcontentvalue(User puser) {
        ContentValues mcontentvalue = new ContentValues();
        mcontentvalue.put("UserName", puser.getMusername());
        mcontentvalue.put("DeleteState", puser.getMdeletestate());
        mcontentvalue.put("CreateDate", DateTools.getFormatDateTime(puser.getMcreatedate(), "yyyy-MM-dd HH:mm:ss"));
        return mcontentvalue;
    }

    //insert a user
    public boolean insertuser(User puser) {
        ContentValues mcontentvalue = getcontentvalue(puser);
        long mid = getdatabase().insert(GetTableNameAndPk()[0], null, mcontentvalue);
        puser.setMuserid((int) mid);
        return mid >= 0;
    }

    //Use the SQLiteDALbase delete(tablename,condition) method to delete user
    public boolean deleteuser(String pcondition) {
        return Delete(GetTableNameAndPk()[0], pcondition);
    }

    // updateuser by condition use database object
    // update(tablename,value,condition,conditionparam) method
    public void updateuser(String pcondition, User puser) {
        getdatabase().update(GetTableNameAndPk()[0], getcontentvalue(puser), pcondition, null);
    }

    public boolean updateuser(String pcondition, ContentValues values) {
        return getdatabase().update(GetTableNameAndPk()[0], values, pcondition, null) > 0;
    }

    //get user list by conditon use SQLiteBase getlist(condition) method
    public List<User> getuser(String pcondition) {
        //1 = 1 always true so always add And to next condition
        String msql = "Select * From User Where 1=1 " + pcondition;
        return getlist(msql);
    }

    //init some data by get XML source and for call back
    protected void initdefaultdata(SQLiteDatabase pdatabase) {
        String[] mstring = getcontext().getResources().getStringArray(R.array._ainituser);
        for (int i = 0; i < mstring.length; i++) {
            User muser = new User();
            muser.setMusername(mstring[i]);
            ContentValues mcontentvalue = getcontentvalue(muser);
            pdatabase.insert(GetTableNameAndPk()[0], null, mcontentvalue);
        }
    }
}

