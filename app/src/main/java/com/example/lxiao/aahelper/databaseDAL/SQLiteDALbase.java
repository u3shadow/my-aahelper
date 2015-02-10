package com.example.lxiao.aahelper.databaseDAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lxiao.aahelper.database.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxiao on 2015/2/8.
 * do some database method
 * use abstract method
 * not attach with business
 */
public abstract class SQLiteDALbase implements SQLiteHelper.SQLiteDataTable {
    private Context mcontext;
    private SQLiteDatabase mdatabase;

    public SQLiteDALbase(Context pcontext) {
        mcontext = pcontext;
    }
    //get database
    public SQLiteDatabase getdatabase() {
        if(mdatabase == null)
        {
            //get database through sqlopenhelper
            mdatabase = SQLiteHelper.getInstance(mcontext).getWritableDatabase();
        }

        return mdatabase;
    }
    //begain transanction
    public void begaintransanction() {
        mdatabase.beginTransaction();
    }
    //set transanction success
    public void settransectionsucces() {
        mdatabase.setTransactionSuccessful();
    }
    //end transanction
    public void endtransection() {
        mdatabase.endTransaction();
    }
    protected abstract String[] GetTableNameAndPk();//child class implement it to gettablenameandpk
    //get the num of instance have give id
    public int getcount(String pcondition) {
        String mstring[] = GetTableNameAndPk();
        Cursor mcursor = Execsql("Select " + mstring[1] + " From " + mstring[0] + " Where 1=1 " + pcondition);
        int count = mcursor.getCount();
        mcursor.close();
        return count;
    }
    //do raw query
    public Cursor Execsql(String psql) {
        return getdatabase().rawQuery(psql, null);//parm 2 is null beacuse we write it on psql
    }
    //delete by give condition
    protected boolean Delete(String ptablename, String pcondition) {
        return getdatabase().delete(ptablename, " 1=1 " + pcondition, null) >= 0;
    }
    //return the find instance by object
    protected abstract Object findmodel(Cursor pcursor);

    protected List cursortolist(Cursor pcursor) {
        List mlist = new ArrayList();
        while (pcursor.moveToNext()) {
            mlist.add(findmodel(pcursor));
        }
        pcursor.close();
        return mlist;
    }
    //change the cursor to a list(convelence to use)
    protected List getlist(String pcondition) {
        Cursor mcursor = Execsql(pcondition);
        return cursortolist(mcursor);
    }



    public Context getcontext() {
        return mcontext;
    }


}
