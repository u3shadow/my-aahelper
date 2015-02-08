package com.example.lxiao.aahelper.databaseDAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lxiao.aahelper.database.SQLiteHelper;
import com.example.lxiao.aahelper.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxiao on 2015/2/8.
 */
public abstract class SQLiteDALbase implements SQLiteHelper.sqlitedatatable{
    private Context mcontext;
    private SQLiteDatabase mdatabase;

    public SQLiteDALbase(Context pcontext) {
        mcontext = pcontext;
    }

    public SQLiteDatabase getdatabase() {
        return mdatabase;
    }

    public void begaintransanction() {
        mdatabase.beginTransaction();
    }

    public void settransectionsucces() {
        mdatabase.setTransactionSuccessful();
    }

    public void endtransection() {
        mdatabase.endTransaction();
    }

    public int getcount(String pcondition) {
        String mstring[] = GetTableNameAndPk();
        Cursor mcursor = Execsql("Select " + mstring[1] + " From " + mstring[0] + " Where 1=1 " + pcondition);
        int count = mcursor.getCount();
        mcursor.close();
        return count;
    }
    protected boolean Delete(String ptablename, String pcondition)
    {
        return getdatabase().delete(ptablename, " 1=1 "+pcondition,null)>=0;
    }
    protected abstract Object findmodel(Cursor pcursor);
    protected List cursortolist(Cursor pcursor)
    {
        List mlist = new ArrayList();
        while(pcursor.moveToNext())
        {
            mlist.add(findmodel(pcursor));
        }
        pcursor.close();
        return mlist;
    }
    protected List getlist(String pcondition)
    {
        Cursor mcursor = Execsql(pcondition);
        return cursortolist(mcursor);
    }
    protected abstract String[] GetTableNameAndPk();
    public Context getcontext()
    {return mcontext;}
    public Cursor Execsql(String psql) {
        return getdatabase().rawQuery(psql, null);
    }
}
