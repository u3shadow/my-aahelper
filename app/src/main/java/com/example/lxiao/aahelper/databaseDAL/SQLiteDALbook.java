package com.example.lxiao.aahelper.databaseDAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.model.Book;
import com.example.lxiao.aahelper.utility.DateTools;

import java.util.List;

/**
 * Created by lxiao on 2015/2/8.
 * implement action on book
 */
public class SQLiteDALbook extends SQLiteDALbase {

    public SQLiteDALbook(Context pcontext) {
        super(pcontext);
    }

    //call back method witch SQLiteDALbase class can use it to get book object
    @Override
    protected Object findmodel(Cursor pcursor) {
        Book mbook = new Book();
        mbook.setBookName(pcursor.getString(pcursor.getColumnIndex("BookName")));
        mbook.setBookId(pcursor.getInt(pcursor.getColumnIndex("BookID")));
        //datatools is a tool class
        mbook.setCreateDate(DateTools.getDate(pcursor.getString(pcursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss"));
        mbook.setDeleteState(pcursor.getInt(pcursor.getColumnIndex("DeleteState")));
        mbook.setIsDefault(pcursor.getInt(pcursor.getColumnIndex("IsDefault")));
        return mbook;
    }

    //get a string array 0 is table name 1 is Pk name
    @Override
    protected String[] GetTableNameAndPk() {
        return new String[]{"Book", "BookID"};
    }

    //create table by implement interface method
    @Override
    public void oncreate(SQLiteDatabase qdatabase) {
        StringBuilder s_CreateTableScript = new StringBuilder();

        s_CreateTableScript.append("		Create  TABLE Book(");
        s_CreateTableScript.append("				[BookID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
        s_CreateTableScript.append("				,[BookName] varchar(10) NOT NULL");
        s_CreateTableScript.append("				,[CreateDate] datetime NOT NULL");
        s_CreateTableScript.append("				,[DeleteState] integer NOT NULL");
        s_CreateTableScript.append("				,[IsDefault] integer NOT NULL");
        s_CreateTableScript.append("				)");
        //Log.v("sk","datatablecreate1");
        qdatabase.execSQL(s_CreateTableScript.toString());
        // Log.v("sk","datatablecreate2");
        initdefaultdata(qdatabase);
    }

    @Override
    public void upgrate(SQLiteDatabase qdatabase) {

    }

    //create a contentvalue by book object, use to save into database
    public ContentValues getcontentvalue(Book pbook) {
        ContentValues mcontentvalue = new ContentValues();
        mcontentvalue.put("BookName", pbook.getBookName());
        mcontentvalue.put("DeleteState", pbook.getDeleteState());
        mcontentvalue.put("CreateDate", DateTools.getFormatDateTime(pbook.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        mcontentvalue.put("IsDefault", pbook.getIsDefault());
        return mcontentvalue;
    }

    //insert a book
    public boolean insertbook(Book pbook) {
        ContentValues mcontentvalue = getcontentvalue(pbook);
        long mid = getdatabase().insert(GetTableNameAndPk()[0], null, mcontentvalue);
        pbook.setBookId((int) mid);
        return mid >= 0;
    }

    //Use the SQLiteDALbase delete(tablename,condition) method to delete book
    public boolean deletebook(String pcondition) {
        return Delete(GetTableNameAndPk()[0], pcondition);
    }

    // updatebook by condition use database object
    // update(tablename,value,condition,conditionparam) method
    public void updatebook(String pcondition, Book pbook) {
        getdatabase().update(GetTableNameAndPk()[0], getcontentvalue(pbook), pcondition, null);
    }

    public boolean updatebook(String pcondition, ContentValues values) {
        return getdatabase().update(GetTableNameAndPk()[0], values, pcondition, null) > 0;
    }

    //get book list by conditon use SQLiteBase getlist(condition) method
    public List<Book> getbook(String pcondition) {
        //1 = 1 always true so always add And to next condition
        String msql = "Select * From Book Where 1=1 " + pcondition;
        return getlist(msql);
    }

    //init some data by get XML source and for call back
    protected void initdefaultdata(SQLiteDatabase pdatabase) {
        String[] mstring = getcontext().getResources().getStringArray(R.array._abook);
        for (int i = 0; i < mstring.length; i++) {
            Book mbook = new Book();
            mbook.setBookName(mstring[i]);
            mbook.setIsDefault(1);
            ContentValues mcontentvalue = getcontentvalue(mbook);
            pdatabase.insert(GetTableNameAndPk()[0], null, mcontentvalue);
        }
    }
}

