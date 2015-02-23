package com.example.lxiao.aahelper.databaseDAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.model.PayOut;
import com.example.lxiao.aahelper.utility.DateTools;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by U3 on 2015/2/16.
 */
public class SQLiteDALpayout extends SQLiteDALbase {
    public SQLiteDALpayout(Context pcontext) {
        super(pcontext);
    }

    //call back method witch SQLiteDALbase class can use it to get payout object
    @Override
    protected Object findmodel(Cursor pcursor) {
        PayOut mpayout = new PayOut();
        mpayout.setPayOutId(pcursor.getInt(pcursor.getColumnIndex("PayOutId")));
        mpayout.setpBookId(pcursor.getInt((pcursor.getColumnIndex("pBookId"))));
        mpayout.setpBookName((pcursor.getString(pcursor.getColumnIndex("BookName"))));
        mpayout.setpTypeId(pcursor.getInt((pcursor.getColumnIndex("pTypeId"))));
        mpayout.setpTypeName((pcursor.getString(pcursor.getColumnIndex("TypeName"))));
        mpayout.setpPath((pcursor.getString(pcursor.getColumnIndex("Path"))));
        mpayout.setPayWay(pcursor.getInt((pcursor.getColumnIndex("PayWay"))));
        mpayout.setAmount(new BigDecimal(pcursor.getString(((pcursor.getColumnIndex("Amount"))))));
        Date _PayOutDate = DateTools .getDate(pcursor.getString(pcursor.getColumnIndex("PayDate")), "yyyy-MM-dd");
        mpayout.setPayDate(_PayOutDate);
        mpayout.setPayMean(pcursor.getString(pcursor.getColumnIndex("PayMean")));
        mpayout.setPayUserId((pcursor.getString(pcursor.getColumnIndex("PayUserId"))));
        mpayout.setPayComment((pcursor.getString(pcursor.getColumnIndex("PayComment"))));
        Date _CreateDate = DateTools.getDate(pcursor.getString(pcursor.getColumnIndex("CreateDate")), "yyyy-MM-dd HH:mm:ss");
        mpayout.setCreateDate(_CreateDate);
        mpayout.setDeleteState((pcursor.getInt(pcursor.getColumnIndex("DeleteState"))));

        return mpayout;
    }//1

    //get a string array 0 is table name 1 is Pk name
    @Override
    protected String[] GetTableNameAndPk() {
        return new String[]{"PayOut", "PayOutID"};
    }

    //create table by implement interface method
    @Override
    public void oncreate(SQLiteDatabase qdatabase) {
        StringBuilder s_CreateTableScript = new StringBuilder();

        s_CreateTableScript.append("		Create  TABLE PayOut(");
        s_CreateTableScript.append("				[PayOutId] integer PRIMARY KEY AUTOINCREMENT NOT NULL");
        s_CreateTableScript.append("				,[CreateDate] datetime NOT NULL");
        s_CreateTableScript.append("				,[DeleteState] integer NOT NULL");
        s_CreateTableScript.append("				,[pBookId] integer NOT NULL");
        s_CreateTableScript.append("				,[pTypeId] integer NOT NULL");
        s_CreateTableScript.append("				,[Amount] decimal");
        s_CreateTableScript.append("				,[PayWay] integer NOT NULL");
        s_CreateTableScript.append("				,[PayMean] text NOT NULL");
        s_CreateTableScript.append("				,[PayComment] text");
        s_CreateTableScript.append("				,[PayUserId] text NOT NULL");
        s_CreateTableScript.append("				,[PayDate] datetime NOT NULL");
        s_CreateTableScript.append("				)");
        qdatabase.execSQL(s_CreateTableScript.toString());

    }//1

    @Override
    public void upgrate(SQLiteDatabase qdatabase) {

    }

    //create a contentvalue by payout object, use to save into database
    public ContentValues getcontentvalue(PayOut ppayout) {
        ContentValues mcontentvalue = new ContentValues();
        mcontentvalue.put("DeleteState", ppayout.getDeleteState());
        mcontentvalue.put("CreateDate", DateTools.getFormatDateTime(ppayout.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        mcontentvalue.put("pBookId", ppayout.getpBookId());
        mcontentvalue.put("pTypeId", ppayout.getpTypeId());
        mcontentvalue.put("Amount", ppayout.getAmount().toString());
        mcontentvalue.put("PayWay", ppayout.getPayWay());
        mcontentvalue.put("PayMean", ppayout.getPayMean());
        mcontentvalue.put("PayComment", ppayout.getPayComment());
        mcontentvalue.put("PayUserId", ppayout.getPayUserId());
        mcontentvalue.put("PayDate", DateTools.getFormatDateTime(ppayout.getPayDate(), "yyyy-MM-dd HH:mm:ss"));
        return mcontentvalue;
    }//1

    //insert a payout
    public boolean insertpayout(PayOut ppayout) {
        ContentValues mcontentvalue = getcontentvalue(ppayout);
        long mid = getdatabase().insert(GetTableNameAndPk()[0], null, mcontentvalue);
        ppayout.setPayOutId((int) mid);
        return mid >= 0;
    }

    //Use the SQLiteDALbase delete(tablename,condition) method to delete payout
    public boolean deletepayout(String pcondition) {
        return Delete(GetTableNameAndPk()[0], pcondition);
    }

    // updatepayout by condition use database object
    // update(tablename,value,condition,conditionparam) method
    public boolean updatepayout(String pcondition, PayOut ppayout) {
        return  getdatabase().update(GetTableNameAndPk()[0], getcontentvalue(ppayout), pcondition, null) >0;
    }

    public boolean updatepayout(String pcondition, ContentValues values) {
        return getdatabase().update(GetTableNameAndPk()[0], values, pcondition, null) > 0;
    }

    //get payout list by conditon use SQLiteBase getlist(condition) method
    public List<PayOut> getpayout(String pcondition) {
        //1 = 1 always true so always add And to next condition
        String msql = "Select * From vpayout Where 1=1 " + pcondition;
        return getlist(msql);
    }
}
