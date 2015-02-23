package com.example.lxiao.aahelper.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.business.businesspayout;
import com.example.lxiao.aahelper.business.businessuser;
import com.example.lxiao.aahelper.model.PayOut;
import com.example.lxiao.aahelper.utility.DateTools;

import java.util.List;

/**
 * Created by U3 on 2015/2/22.
 */
public class adapterpayout extends AdapterBase {
    private class holder{
        ImageView image;
        TextView name;
        TextView total;
        TextView payoutuserandtype;
        View relativelayoutdate;

    }
    public adapterpayout(Context c, List plist) {
        super(c, plist);
    }
    private businesspayout mbusinesspayout;
    private int mbookid;
    public adapterpayout(Context pcontext,int pbookid)
    {
        this(pcontext,null);
        mbusinesspayout = new businesspayout(pcontext);
        mbookid = pbookid;
        List _list = mbusinesspayout.getpayoutbybookid(mbookid);
        setlist(_list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder _holder;
        if(convertView == null)
        {
            _holder = new holder();
            convertView = getinflater().inflate(R.layout.payoutitemlayout,null);
            _holder.image = (ImageView)convertView.findViewById(R.id.iv_payoutimage);
            _holder.name = (TextView)convertView.findViewById(R.id.tv_payoutname);
            _holder.total =(TextView)convertView.findViewById(R.id.tv_total);
            _holder.relativelayoutdate = (RelativeLayout)convertView.findViewById(R.id.rl_payouttitle);
            _holder.payoutuserandtype = (TextView)convertView.findViewById(R.id.tv_payuserandpaytype);
            convertView.setTag(_holder);
        }
        else
        {
            _holder = (holder) convertView.getTag();
        }
        _holder.relativelayoutdate.setVisibility(View.GONE);
        PayOut _payout = (PayOut)getItem(position);
        String _payoutdate = DateTools.getFormatShortTime(_payout.getPayDate());
        boolean _show = false;
        if(position > 0)
        {
            PayOut _lastpayout = (PayOut)getItem(position - 1);
            String _lastpayoutdate = DateTools.getFormatShortTime(_lastpayout.getPayDate());
           _show = !_payoutdate.equals(_lastpayoutdate);
        }
        if(position == 0 || _show)
        {
            _holder.relativelayoutdate.setVisibility(View.VISIBLE);
            String _message = mbusinesspayout.getpayouttotalmessage(_payoutdate,mbookid);
            ((TextView)_holder.relativelayoutdate.findViewById(R.id.tv_payoutdate)).setText(_payoutdate);
            ((TextView)_holder.relativelayoutdate.findViewById(R.id.tv_payouttotal)).setText(_message);
        }
        _holder.image.setImageResource(R.drawable.bookicon);
        _holder.name.setText(_payout.getpTypeName());
        _holder.total.setText(_payout.getAmount().toString());

        businessuser _businessuser = new businessuser(getcontext());
        String _username = _businessuser.getusernamebyuserid(_payout.getPayUserId());
        _holder.payoutuserandtype.setText(_username+" "+_payout.getPayMean());

        return convertView;
    }
}
