package com.example.lxiao.aahelper.business;

import android.content.Context;
import android.database.Cursor;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.databaseDAL.SQLiteDALpayout;
import com.example.lxiao.aahelper.model.PayOut;

import java.util.List;

/**
 * Created by U3 on 2015/2/16.
 */
public class    businesspayout extends businessbase {
    private SQLiteDALpayout mdal;
    public businesspayout(Context pcontext) {
        super(pcontext);
        mdal = new SQLiteDALpayout(pcontext);
    }
    public boolean insertpayout(PayOut ppayout){
        boolean result = mdal.insertpayout(ppayout);
        return result;
    }
    public boolean deletepayoutbypayoutid(int pid)
    {
        String condition = " And PayOutId = "+pid;
        boolean result = mdal.deletepayout(condition);
        return result;
    }
    public boolean deletepayoutbybookid(int pbookid)
    {
        String condition = " And pBookId = "+pbookid;
        boolean result = mdal.deletepayout(condition);
        return result;
    }
    public boolean updatepayoutbypayoutid(PayOut ppayout)
    {
        String condition = " PayOutId = "+ppayout.getPayOutId();
        boolean result = mdal.updatepayout(condition,ppayout);
        return result;
    }
    public List<PayOut> getpayout(String pcondition)
    {
        return mdal.getpayout(pcondition);
    }
    public int getcount()
    {
        return  mdal.getcount("");
    }
    public List<PayOut> getpayoutbybookid(int pbookid)
    {
        String condition = " And pBookId = " +pbookid +" Order By PayDate DESC,PayOutId DESC ";
        return mdal.getpayout(condition);
    }
    public String[] getpayouttotal(String pcondition){
        //ifnull(Sum(Amount),0) As SumAmount,Count(Amount) As Count
        String test = " And PayDate = 2015-02-23";
        String sql = "Select ifnull(Sum(Amount),0) As SumAmount,Count(Amount) As Count From PayOut Where 1=1 "+pcondition;
        String _Total[] = new String[2];
        Cursor _cursor = mdal.Execsql(sql);
       // if(_cursor.getCount() > 0)
       // _Total[0] = _cursor.getString(_cursor.getColumnIndex("Amount"));
        if(_cursor.getCount() == 1)
        {
            while(_cursor.moveToNext())
            {
                 _Total[0] = _cursor.getString(_cursor.getColumnIndex("Count"));
                _Total[1] = _cursor.getString(_cursor.getColumnIndex("SumAmount"));
            }
        }
        return _Total;
    }
    public String[] getpayouttotalbypaydate(String ppaydate, int pbookid)
    {
        String condition = " And PayDate = '"+ppaydate+"' And pBookId = "+pbookid;
        String _total[] = getpayouttotal(condition);
        return _total;
    }
    public String getpayouttotalmessage(String ppaydate, int pbookid)
    {
        String _total[] = getpayouttotalbypaydate(ppaydate,pbookid);
        return getcontext().getString(R.string._spayouttotalmessage,new Object[]{_total[0],_total[1]});
    }
    public String[] gettotalbybookid(int pbookid)
    {
        String condition = " And pBookId = "+pbookid;
        return getpayouttotal(condition);
    }
    public List<PayOut> getpayoutoderbyuserid(String condition)
    {
        condition += " Order By PayUserId";
        List<PayOut> _payout = getpayout(condition);
        if(_payout.size()>0)
            return _payout;
        else
            return null;
    }
    public String[] getpayoutdateandamounttotal(String pcondition)
    {
        String _sql = "Select Min(PayOutDate) As MinPayoutDate,Max(PayOutDate) As MaxPayoutDate,Sum(Amount) As Amount From Payout Where 1=1 " + pcondition;
        String _payouttotal[] = new String[3];
        Cursor _cursor = mdal.Execsql(_sql);
        if(_cursor.getCount() == 1)
        {
            while(_cursor.moveToNext())
            {
                _payouttotal[0] = _cursor.getString(_cursor.getColumnIndex("MinPayoutDate"));
                _payouttotal[1] = _cursor.getString(_cursor.getColumnIndex("MaxPayoutDate"));
                _payouttotal[2] = _cursor.getString(_cursor.getColumnIndex("Amount"));
            }
        }
        return _payouttotal;
    }
}
