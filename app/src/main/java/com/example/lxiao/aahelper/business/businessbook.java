package com.example.lxiao.aahelper.business;

import android.content.ContentValues;
import android.content.Context;

import com.example.lxiao.aahelper.databaseDAL.SQLiteDALbook;
import com.example.lxiao.aahelper.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U3 on 2015/2/9.
 * to use dal more convenience
 */
public class businessbook extends businessbase {
    private SQLiteDALbook mdal;
    private Context mcontext;

    public businessbook(Context pcontext) {
        super(pcontext);
        mdal = new SQLiteDALbook(pcontext);
        // mcontext = pcontext;
    }

    //Insert book object by SQLiteDALbook insertbook(book) method
    public boolean insert(Book pbook) {
        mdal.begaintransanction();
        try {
            boolean _isinsert = mdal.insertbook(pbook);
            if (!_isinsert) return false;
            if (pbook.getIsDefault() == 1) {
                boolean _issetdefault = SetDefault(pbook.getBookId());
                if (!_issetdefault) return false;
                {
                    mdal.settransectionsucces();
                    return true;
                }
            }
            else
            {
                mdal.settransectionsucces();
                return true;
            }

        } catch (Exception e) {
            return false;
        } finally {
            mdal.endtransection();
        }

    }

    private boolean SetDefault(int pid) {
        ContentValues _resetdefault = new ContentValues();
        _resetdefault.put("IsDefault", -1);
        String _condition = " IsDefault = 1";
        boolean _isreset = mdal.updatebook(_condition, _resetdefault);
        if (!_isreset) return false;
        ContentValues _setdefault = new ContentValues();
        _setdefault.put("IsDefault", 1);
        String _setcondition = " BookId = " + pid;
        boolean _isset = mdal.updatebook(_setcondition, _setdefault);
        if (!_isset) return false;
        return true;

    }

    //delete book  by id use SQLiteDALbook deletebook(condition)
    public boolean deletebookbyid(int pid) {
        String mcondition = "And BookId = " + pid;
        return mdal.deletebook(mcondition);
    }

    //delete book  by id use SQLiteDALbook updatebook(condition,book)
    public void update(Book pbook) {
        String mcondition = " BookId = " + pbook.getBookId();
        if(pbook.getIsDefault() == 1)
            SetDefault(pbook.getBookId());
        mdal.updatebook(mcondition, pbook);

    }

    //Get book list by SQLiteDALbook getbook(condition) method
    public List<Book> getbooks(String pcondition) {
        return mdal.getbook(pcondition);
    }

    //Get book  by id use SQLiteDALbook getbook(condition) method
    public Book getbookbyid(int id) {
        List<Book> mlist = mdal.getbook(" And BookId = " + id);
        if (mlist.size() > 0)
            return mlist.get(0);
        else
            return null;
    }

    //Get book  by id array use SQLiteDALbook getbookbyid(condition) method
    public List<Book> getbooklistbyid(String[] id) {
        List<Book> mlist = new ArrayList<Book>();
        for (int i = 0; i < id.length; i++) {
            Book mbook = getbookbyid(Integer.valueOf(id[i]));
            mlist.add(mbook);
        }
        return mlist;
    }

    //use getbook method to get not deleted book list
    public List<Book> getnothidebook() {
        return mdal.getbook(" And DeleteState = -1");
    }

    //if this book is in database
    public boolean isbookexist(String pbookname, int pbookid) {
        String condition = " And BookName = '" + pbookname + "'";
        condition += " And BookId <> " + pbookid;
        condition += " And DeleteState = -1";
        List<Book> _list = getbooks(condition);
        if (_list.size() > 0)
            return true;
        else
            return false;
    }

    public boolean hidebookbyid(int pid) {
        String condition = " BookId = " + pid;
        ContentValues _value = new ContentValues();
        _value.put("DeleteState", "0");
        boolean isupdate = mdal.updatebook(condition, _value);
        return isupdate;
    }

    public boolean setasdefault(int pid) {
        String condition = " BookId = " + pid;
        ContentValues _value = new ContentValues();
        _value.put("IsDefault", "0");
        boolean isupdate = mdal.updatebook(condition, _value);
        return isupdate;
    }
}
