package com.example.lxiao.aahelper.databaseDAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.model.Type;
import com.example.lxiao.aahelper.utility.DateTools;

import java.util.List;

/**
 * Created by lxiao on 2015/2/12.
 */
public class SQLiteDALtype extends SQLiteDALbase{
    public SQLiteDALtype(Context pcontext) {
        super(pcontext);
    }

    //call back method witch SQLiteDALbase class can use it to get type object
    @Override
    protected Object findmodel(Cursor pcursor) {
        Type mtype = new Type();
        mtype.setTypeName(pcursor.getString(pcursor.getColumnIndex("TypeName")));
        mtype.setTypeId(pcursor.getInt(pcursor.getColumnIndex("TypeID")));
        //datatools is a tool class
        mtype.setCreateDate(DateTools.getDate(pcursor.getString(pcursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss"));
        mtype.setDeleteState(pcursor.getInt(pcursor.getColumnIndex("DeleteState")));
        mtype.setFlag(pcursor.getString(pcursor.getColumnIndex("Flag")));
        mtype.setParentId(pcursor.getInt(pcursor.getColumnIndex("ParentId")));
        mtype.setPath(pcursor.getString(pcursor.getColumnIndex("Path")));
        return mtype;
    }

    //get a string array 0 is table name 1 is Pk name
    @Override
    protected String[] GetTableNameAndPk() {
        return new String[]{"Type", "TypeID"};
    }

    //create table by implement interface method
    @Override
    public void oncreate(SQLiteDatabase qdatabase) {
        StringBuilder s_CreateTableScript = new StringBuilder();

        s_CreateTableScript.append("		Create  TABLE Type(");
        s_CreateTableScript.append("				[TypeID] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
        s_CreateTableScript.append("				,[TypeName] varchar(20) NOT NULL");
        s_CreateTableScript.append("				,[CreateDate] datetime NOT NULL");
        s_CreateTableScript.append("				,[DeleteState] integer NOT NULL");
        s_CreateTableScript.append("				,[Path] varchar(20) NOT NULL");
        s_CreateTableScript.append("				,[ParentId] integer NOT NULL");
        s_CreateTableScript.append("				,[Flag] integer NOT NULL");
        s_CreateTableScript.append("				)");
        //Log.v("sk","datatablecreate1");
        qdatabase.execSQL(s_CreateTableScript.toString());
        // Log.v("sk","datatablecreate2");
        initdefaultdata(qdatabase);
    }

    @Override
    public void upgrate(SQLiteDatabase qdatabase) {

    }

    //create a contentvalue by type object, use to save into database
    public ContentValues getcontentvalue(Type ptype) {
        ContentValues mcontentvalue = new ContentValues();
        mcontentvalue.put("TypeName", ptype.getTypeName());
        mcontentvalue.put("DeleteState", ptype.getDeleteState());
        mcontentvalue.put("CreateDate", DateTools.getFormatDateTime(ptype.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        mcontentvalue.put("Path", ptype.getPath());
        mcontentvalue.put("ParentId", ptype.getParentId());
        mcontentvalue.put("Flag", ptype.getFlag());
        return mcontentvalue;
    }

    //insert a type
    public boolean inserttype(Type ptype) {
        ContentValues mcontentvalue = getcontentvalue(ptype);
        long mid = getdatabase().insert(GetTableNameAndPk()[0], null, mcontentvalue);
        ptype.setTypeId((int) mid);
        return mid >= 0;
    }

    //Use the SQLiteDALbase delete(tablename,condition) method to delete type
    public boolean deletetype(String pcondition) {
        return Delete(GetTableNameAndPk()[0], pcondition);
    }

    // updatetype by condition use database object
    // update(tablename,value,condition,conditionparam) method
    public boolean updatetype(String pcondition, Type ptype) {
      return  getdatabase().update(GetTableNameAndPk()[0], getcontentvalue(ptype), pcondition, null) >0;
    }

    public boolean updatetype(String pcondition, ContentValues values) {
        return getdatabase().update(GetTableNameAndPk()[0], values, pcondition, null) > 0;
    }

    //get type list by conditon use SQLiteBase getlist(condition) method
    public List<Type> gettype(String pcondition) {
        //1 = 1 always true so always add And to next condition
        String msql = "Select * From Type Where 1=1 " + pcondition;
        return getlist(msql);
    }

    //init some data by get XML source and for call back
    protected void initdefaultdata(SQLiteDatabase pdatabase) {
        String[] mstring = getcontext().getResources().getStringArray(R.array._atype);
        String mflag = getcontext().getResources().getString(R.string._sflag);
        Type _type = new Type();
        _type.setFlag(mflag);
        _type.setParentId(0);
        _type.setPath("");
        for (int i = 0; i < mstring.length; i++) {

            _type.setTypeName(mstring[i]);
            ContentValues mcontentvalue = getcontentvalue(_type);
            Long _id = pdatabase.insert(GetTableNameAndPk()[0],null,mcontentvalue);
            _type.setPath(_id.intValue()+".");
            String _condition = " TypeId = "+_id;
            mcontentvalue = getcontentvalue(_type);
            pdatabase.update(GetTableNameAndPk()[0],mcontentvalue," TypeId = "+_id,null);
        }
    }
}
