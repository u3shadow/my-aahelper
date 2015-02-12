package com.example.lxiao.aahelper.business;

import android.content.ContentValues;
import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.databaseDAL.SQLiteDALtype;
import com.example.lxiao.aahelper.model.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by U3 on 2015/2/9.
 * to use dal more convenience
 */
public class businesstype extends businessbase {
    private SQLiteDALtype mdal;
    private Context mcontext;
    private final String FlAG = " And Flag = '"+getstring(R.string._sflag)+"'";
    public businesstype(Context pcontext) {
        super(pcontext);
        mdal = new SQLiteDALtype(pcontext);
        // mcontext = pcontext;
    }

    //Insert type object by SQLiteDALtype inserttype(type) method
    public boolean updatebytypeid(Type ptype) {
        mdal.begaintransanction();
        try {
            String _condition = " And TypeId = "+ptype.getTypeId();
            boolean _resulta = mdal.updatetype(_condition,ptype);
            boolean _resultb = true;
            String _path;
            Type _parenttype = gettypebyid(ptype.getParentId());
            if(_parenttype != null)
            {
             _path = _parenttype.getPath()+ptype.getTypeId()+".";
            }
            else
            {
                _path = ptype.getTypeId()+".";
            }
            ptype.setPath(_path);
            _resultb = update(ptype);
            if(_resulta&&_resultb)
            {
                mdal.settransectionsucces();
                return true;
            }
            else
                return false;

        } catch (Exception e) {
            return false;
        } finally {
            mdal.endtransection();
        }
    }

    //delete type  by id use SQLiteDALtype deletetype(condition)
    public boolean deletetypebyid(int pid) {
        String mcondition = "And TypeId = " + pid;
        return mdal.deletetype(mcondition);
    }

    //delete type  by id use SQLiteDALtype updatetype(condition,type)
    public boolean update(Type ptype) {
        String mcondition = " TypeId = " + ptype.getTypeId();
       ContentValues _contentvalue = mdal.getcontentvalue(ptype);
       boolean _isupdate =  mdal.updatetype(mcondition, _contentvalue);
        return _isupdate;

    }

    //Get type list by SQLiteDALtype gettype(condition) method
    public List<Type> gettypes(String pcondition) {
        return mdal.gettype(pcondition);
    }

    //Get type  by id use SQLiteDALtype gettype(condition) method
    public Type gettypebyid(int id) {
        List<Type> mlist = mdal.gettype(FlAG+" And TypeId = " + id);
        if (mlist.size() > 0)
            return mlist.get(0);
        else
            return null;
    }

    //Get type  by id array use SQLiteDALtype gettypebyid(condition) method
    public List<Type> gettypelistbyid(String[] id) {
        List<Type> mlist = new ArrayList<Type>();
        for (int i = 0; i < id.length; i++) {
            Type mtype = gettypebyid(Integer.valueOf(id[i]));
            mlist.add(mtype);
        }
        return mlist;
    }

    //use gettype method to get not deleted type list
    public List<Type> getnothidetype() {
        return mdal.gettype(FlAG+" And DeleteState = -1");
    }

    //if this type is in database
    public boolean istypeexist(String ptypename, int ptypeid) {
        String condition = " And TypeName = '" + ptypename + "'";
        condition += " And TypeId <> " + ptypeid;
        condition += " And DeleteState = -1";
        List<Type> _list = gettypes(condition);
        if (_list.size() > 0)
            return true;
        else
            return false;
    }

    public boolean hidetypebyid(int pid) {
        String condition = " TypeId = " + pid;
        ContentValues _value = new ContentValues();
        _value.put("DeleteState", "0");
        boolean isupdate = mdal.updatetype(condition, _value);
        return isupdate;
    }

    public boolean setasdefault(int pid) {
        String condition = " TypeId = " + pid;
        ContentValues _value = new ContentValues();
        _value.put("IsDefault", "0");
        boolean isupdate = mdal.updatetype(condition, _value);
        return isupdate;
    }
    public boolean hidetypebypath(String ppath)
    {
        String _condition = "Path Like '"+ppath+"%'";
        ContentValues _contentvalue = new ContentValues();
        _contentvalue.put("DeleteState",0);
        return mdal.updatetype(_condition,_contentvalue);
    }
    public int getnothidecount()
    {
        return mdal.getcount(FlAG+" And DeleteState = -1");
    }
    public int getnothidecountbyparentid(int pparentid)
    {
        return mdal.getcount(FlAG+" And ParentId = "+pparentid+" And DeleteState = -1");
    }
    public List<Type> getnothideroottype()
    {
        return mdal.gettype(FlAG+" And ParentId = 0 And DeleteState = -1");
    }
    public List<Type> getnothidetypebyparentid(int pparentid)
    {
        return mdal.gettype(FlAG+"And ParentId = "+pparentid+" And DeleteState = -1");
    }
    public ArrayAdapter getroottypearrayadapter()
    {
        List _list = getnothideroottype();
        _list.add(0,"-请选择-");
        ArrayAdapter _arrayadapter = new ArrayAdapter(mcontext,android.R.layout.simple_spinner_dropdown_item,_list);
        _arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return _arrayadapter;
    }
    public boolean InsertType(Type ptype)
    {
        mdal.begaintransanction();
        try{
            boolean _resulta = mdal.inserttype(ptype);
            boolean _resultb = true;

            Type _parenttype = gettypebyid(ptype.getParentId());
            String _path;
            if(_parenttype != null)
            {
                _path = _parenttype.getPath()+ptype.getTypeId()+".";
            }
            else
            {
                _path = ptype.getTypeId()+".";
            }
            ptype.setPath(_path);
            _resultb = update(ptype);
            if(_resultb&&_resulta)
            {
                mdal.settransectionsucces();
                return true;
            }
            else
                return false;

        }catch(Exception e)
        {
            return false;
        }
        finally {
            mdal.endtransection();
        }
    }
}
