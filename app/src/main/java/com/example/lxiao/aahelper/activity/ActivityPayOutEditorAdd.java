package com.example.lxiao.aahelper.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.internal.view.menu.ExpandedMenuView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.numberdialog;
import com.example.lxiao.aahelper.adapter.adapterbookselect;
import com.example.lxiao.aahelper.adapter.adaptertype;
import com.example.lxiao.aahelper.adapter.adapteruser;
import com.example.lxiao.aahelper.baseactivity.ActivityFrame;
import com.example.lxiao.aahelper.business.businessbook;
import com.example.lxiao.aahelper.business.businesspayout;
import com.example.lxiao.aahelper.business.businesstype;
import com.example.lxiao.aahelper.business.businessuser;
import com.example.lxiao.aahelper.model.Book;
import com.example.lxiao.aahelper.model.PayOut;
import com.example.lxiao.aahelper.model.Type;
import com.example.lxiao.aahelper.model.User;
import com.example.lxiao.aahelper.utility.DateTools;
import com.example.lxiao.aahelper.utility.RegexTools;

import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by U3 on 2015/2/19.
 */
public class ActivityPayOutEditorAdd extends ActivityFrame implements View.OnClickListener,numberdialog.OnNumberDialogListener {

    private PayOut mpayout;
    private Button mbtsave;
    private Button mbtcancel;
    private Button mbtselectbook;
    private Button mbtselecttype;
    private Button mbtselectuser;
    private Button mbtsetamount;
    private Button mbtsetpaydate;
    private Button mbtselectpaytype;
    private EditText metbookname;
    private EditText metpaydate;
    private EditText metpaytype;
    private EditText metamount;
    private EditText mettypename;
    private EditText metpayuser;
    private EditText metcomment;
    private AutoCompleteTextView actvtypename;
    private businesspayout mbusinesspayout;
    private businessbook mbusinessbook;
    private businesstype mbusinesstype;
    private Book mdefaultbook;
    private businessuser mbusinessuser;
    private int mbookid;
    private Book mbook;
    private String[] mpayouttypearray;
    private int mtypeid;
    private String mpayuserid;
    private List<User> mUserSelectedList;
    private List<RelativeLayout> mItemColor;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_savepayout:
                addoreditpayout();
                break;
            case R.id.bt_cancelpayout:
                finish();
                break;
            case R.id.bt_selectbook:
                showbookselect();
                break;
            case R.id.bt_selecttype:
                showtypeselect();
                break;
            case R.id.bt_selectpaydata:
                Calendar _calendar = Calendar.getInstance();
                showdataselect(_calendar.get(Calendar.YEAR),_calendar.get(Calendar.MONTH),_calendar.get(Calendar.DATE));
                break;
            case R.id.bt_selectuser:
                ShowUserSelectDialog(metpaytype.getText().toString());
                break;
            case R.id.bt_selectpaytype:
                showpayouttypeselect();
                break;
            case R.id.bt_setamount:
                (new numberdialog(this)).show();
                break;
            default:
                break;

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("sk", "payout");
        appendmainbody(R.layout.edit_or_add_payout_layout);
        removebottombox();
        initview();
        initvar();
      binddata();
        SetTitle();
        initlistener();
    }

    private void SetTitle() {
        String _title;

        if (mpayout == null) {
            _title = getString(R.string._saddpayout);
        } else {
            _title = getString(R.string._seditpayout);
        }
        setTitle(_title);
    }

    @Override
    public void initview() {
        mbtsave = (Button) findViewById(R.id.bt_savepayout);
        mbtcancel = (Button) findViewById(R.id.bt_cancelpayout);
        mbtselectbook = (Button) findViewById(R.id.bt_selectbook);
        mbtselecttype = (Button) findViewById(R.id.bt_selecttype);
        mbtselectuser = (Button) findViewById(R.id.bt_selectuser);
        mbtsetamount = (Button) findViewById(R.id.bt_setamount);
        mbtsetpaydate = (Button) findViewById(R.id.bt_selectpaydata);
        mbtselectpaytype = (Button) findViewById(R.id.bt_selectpaytype);
        metbookname = (EditText) findViewById(R.id.et_selectbook);
        metpaydate = (EditText) findViewById(R.id.et_selectpaydata);
        metpaytype = (EditText) findViewById(R.id.et_selectpaytype);
        metamount = (EditText) findViewById(R.id.et_amount);
        mettypename = (EditText) findViewById(R.id.et_selectpaytype);
        metpayuser = (EditText) findViewById(R.id.et_payuser);
        metcomment = (EditText) findViewById(R.id.et_comment);
        actvtypename = (AutoCompleteTextView)findViewById(R.id.at_selecttype);
        actvtypename.setEditableFactory(new Editable.Factory());
    }

    @Override
    public void initlistener() {
        mbtsave.setOnClickListener(this);
        mbtcancel.setOnClickListener(this);
        mbtsetamount.setOnClickListener(this);
        mbtselectbook.setOnClickListener(this);
        mbtsetpaydate.setOnClickListener(this);
        mbtselectpaytype.setOnClickListener(this);
        mbtselectuser.setOnClickListener(this);
        actvtypename.setOnItemClickListener(new onautocompletetextviewitemclicklistener());
        mbtselecttype.setOnClickListener(this);
    }//need fix

    @Override
    public void initvar() {
        mbusinesspayout = new businesspayout(this);
        mpayout = (PayOut) getIntent().getSerializableExtra("mpayout");
        mbusinessbook = new businessbook(this);
        mbusinesstype = new businesstype(this);
        mbook = mbusinessbook.getdefault();
        mbusinessuser = new businessuser(this);
    }

    private void binddata() {
        mbookid = mbook.getBookId();
         metbookname.setText(mbook.getBookName());
        actvtypename.setAdapter(mbusinessbook.getallbookarrayadapter()); //need fix
        metpaydate.setText(DateTools.getFormatDateTime(new Date(), "yyyy-MM-dd"));
        mpayouttypearray = getResources().getStringArray(R.array.payouttype);
        metpaytype.setText(mpayouttypearray[0]);
    }

    private void initdata(PayOut ppayout) {
        metbookname.setText(ppayout.getpBookName());
        mbookid = ppayout.getpBookId();
        metamount.setText(ppayout.getAmount().toString());
        actvtypename.setText(ppayout.getpBookName());
        mtypeid = ppayout.getpBookId();
        metpaydate.setText(DateTools.getFormatDateTime(ppayout.getPayDate(), "yyyy-MM-dd"));
        metpaytype.setText(ppayout.getPayWay());

        businessuser _businessuser = new businessuser(this);
        String _username = _businessuser.getusernamebyuserid(ppayout.getPayUserId());

        metpayuser.setText(_username);
        mpayuserid = ppayout.getPayUserId();

        metcomment.setText(ppayout.getPayComment());
    }

    private boolean checkdata() {
        boolean _result = RegexTools.IsMoney(metamount.getText().toString().trim());
        if (!_result) {
            metamount.requestFocus();
            Toast.makeText(this, getString(R.string._serroramount), Toast.LENGTH_LONG).show();
            return _result;
        }

        _result = RegexTools.IsNull(mtypeid);
        if (!_result) {
            mbtselecttype.setFocusable(true);
            mbtselecttype.setFocusableInTouchMode(true);
            mbtselecttype.requestFocus();
            Toast.makeText(this, getString(R.string._snulltype), Toast.LENGTH_LONG).show();
            return _result;
        }
        if (mpayuserid == null) {
            mbtselectuser.setFocusable(true);
            mbtselectuser.setFocusableInTouchMode(true);
            mbtselectuser.requestFocus();
            Toast.makeText(this, getString(R.string._snulluser), Toast.LENGTH_LONG).show();
            return false;
        }
        String _paytype = metpaytype.getText().toString();
        if (_paytype.equals(mpayouttypearray[0]) || _paytype.equals(mpayouttypearray[1])) {
            if (mpayuserid.split(",").length <= 1) {
                mbtselectuser.setFocusable(true);
                mbtselectuser.setFocusableInTouchMode(true);
                mbtselectuser.requestFocus();
                Toast.makeText(this, getString(R.string._slessonuser), Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            if (mpayuserid.equals("")) {
                mbtselectuser.setFocusable(true);
                mbtselectuser.setFocusableInTouchMode(true);
                mbtselectuser.requestFocus();
                Toast.makeText(this, getString(R.string._snopayuser), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;

    }

    private void addoreditpayout() {
        boolean _result = checkdata();
        if (!_result) {
            return;
        }
        if (mpayout == null) {
            mpayout = new PayOut();
        }
        mpayout.setpBookId(mbookid);
        mpayout.setpTypeId(mtypeid);
        mpayout.setAmount(new BigDecimal(metamount.getText().toString().trim()));
        mpayout.setPayDate(DateTools.getDate(metpaydate.getText().toString().trim(), "yyyy-MM-dd"));
        //mpayout.set(metpaytype.getText().toString().trim());
        mpayout.setPayUserId(mpayuserid);
        mpayout.setPayComment(metcomment.getText().toString().trim());

        boolean _resultb = false;
        if (mpayout.getPayOutId() == 0) {
            _resultb = mbusinesspayout.insertpayout(mpayout);
        } else {
            _resultb = mbusinesspayout.updatepayoutbypayoutid(mpayout);
        }
        if (_resultb) {
            Toast.makeText(getApplicationContext(), getString(R.string._ssuccess), Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string._sfaile), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void SetNumberFinish(BigDecimal p_Number) {
        ((EditText) findViewById(R.id.et_amount)).setText(p_Number.toString());
    }

    private class onautocompletetextviewitemclicklistener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Type _type = (Type) parent.getAdapter().getItem(position);
            mtypeid = _type.getTypeId();
        }
    }

    private void showbookselect()//need fix
    {
        AlertDialog.Builder _builder = new AlertDialog.Builder(this);
        View _view = LayoutInflater.from(this).inflate(R.layout.dialog_list, null);
        ListView _listview = (ListView) _view.findViewById(R.id.ListViewSelect);
        adapterbookselect _adapterbookselect = new adapterbookselect(this);
        _listview.setAdapter(_adapterbookselect);

        _builder.setTitle(getString(R.string._sselectbook))
                .setNegativeButton(R.string._sback, null)
                .setView(_view);
        AlertDialog _alertdialog = _builder.create();
        _alertdialog.show();
        _listview.setOnItemClickListener(new onbookitemclicklistener(_alertdialog));
    }

    private class onbookitemclicklistener implements AdapterView.OnItemClickListener {
        private AlertDialog _dialog;

        public onbookitemclicklistener(AlertDialog pdialog) {
            _dialog = pdialog;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Book _book = (Book) ((Adapter) parent.getAdapter()).getItem(position);
            metbookname.setText(_book.getBookName());
            mbookid = _book.getBookId();
            _dialog.dismiss();
        }
    }

    private void showtypeselect()//need fix
    {
        AlertDialog.Builder _builder = new AlertDialog.Builder(this);
        View _view = LayoutInflater.from(this).inflate(R.layout.typelayout, null);
        ExpandableListView _expandablelistview = (ExpandableListView) _view.findViewById(R.id.el_typeview);
        adaptertype _typeadapter = new adaptertype(this);
        _expandablelistview.setAdapter(_typeadapter);

        _builder.setIcon(R.drawable.type)
                .setTitle(getString(R.string._sselecttype))
                .setNegativeButton(getString(R.string._sback), null)
                .setView(_view);
        AlertDialog _alertdialog = _builder.create();
        _alertdialog.show();
        _expandablelistview.setOnGroupClickListener(new ontypegroupclicklistener(_alertdialog, _typeadapter));
        _expandablelistview.setOnChildClickListener(new ontypechildclicklistener(_alertdialog, _typeadapter));
    }

    private class ontypegroupclicklistener implements ExpandableListView.OnGroupClickListener {
        private AlertDialog malertdialog;
        private adaptertype mtypeadapter;

        public ontypegroupclicklistener(AlertDialog palert, adaptertype padapter) {
            malertdialog = palert;
            mtypeadapter = padapter;
        }

        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            int _count = mtypeadapter.getChildrenCount(groupPosition);
            if (_count == 0) {
                Type _type = (Type) mtypeadapter.getGroup(groupPosition);
                actvtypename.setText(_type.getTypeName());
                mtypeid = _type.getTypeId();
                malertdialog.dismiss();
            }
            return false;
        }
    }
        private class ontypechildclicklistener implements ExpandableListView.OnChildClickListener {
            private AlertDialog malertdialog;
            private adaptertype mtypeadapter;

            public ontypechildclicklistener(AlertDialog palert, adaptertype padapter) {
                malertdialog = palert;
                mtypeadapter = padapter;
            }

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Type _type = (Type) mtypeadapter.getChild(groupPosition, childPosition);
                actvtypename.setText(_type.getTypeName());
                mtypeid = _type.getTypeId();
                malertdialog.dismiss();
                return false;
            }
        }

        private void showdataselect(int pyear, int pmonth, int pday) {
            (new DatePickerDialog(ActivityPayOutEditorAdd.this, new OnDateSelectListener(), pyear, pmonth, pday)).show();
        }

        private class OnDateSelectListener implements DatePickerDialog.OnDateSetListener {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date _date = new Date(year - 1900, monthOfYear, dayOfMonth);
                metpaydate.setText(DateTools.getFormatDateTime(_date, "yyyy-MM-dd"));
            }
        }
        private void showpayouttypeselect()//need fix
        {
            AlertDialog.Builder _builder = new AlertDialog.Builder(this);
            View _view = LayoutInflater.from(this).inflate(R.layout.paytypelayout,null);
            ListView _listview = (ListView)_view.findViewById(R.id.ListViewPayoutType);

            _builder.setTitle(R.string._sselectpaytype);
            _builder.setNegativeButton(R.string._sback, null);
            _builder.setView(_view);
            AlertDialog _AlertDialog = _builder.create();
            _AlertDialog.show();
            _listview.setOnItemClickListener(new OnPayoutTypeItemClickListener(_AlertDialog));
        }
        private class OnPayoutTypeItemClickListener implements AdapterView.OnItemClickListener{
            private AlertDialog mAlertDialog;
            public OnPayoutTypeItemClickListener(AlertDialog pAlertDialog)
            {
                mAlertDialog = pAlertDialog;
            }
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String _payouttype = (String)parent.getAdapter().getItem(position);
                metpaytype.setText(_payouttype);
                metpayuser.setText("");
                mpayuserid = "";
                mAlertDialog.dismiss();
            }
        }
    private void ShowUserSelectDialog(String p_PayoutType)//need fix
    {
        AlertDialog.Builder _Builder = new AlertDialog.Builder(this);
        View _View = LayoutInflater.from(this).inflate(R.layout.userlayout, null);
        LinearLayout _LinearLayout = (LinearLayout)_View.findViewById(R.id.usermainlinearlayout);
        _LinearLayout.setBackgroundResource(R.drawable.mainbackground);
        ListView _ListView = (ListView)_View.findViewById(R.id.lv_userlistview);
        adapteruser _AdapterUser = new adapteruser(this);
        _ListView.setAdapter(_AdapterUser);

        _Builder.setIcon(R.drawable.user);
        _Builder.setTitle(R.string._sselectuser);
        _Builder.setNegativeButton(R.string._sback, new OnSelectUserBack());
        _Builder.setView(_View);
        AlertDialog _AlertDialog = _Builder.create();
        _AlertDialog.show();
        _ListView.setOnItemClickListener(new OnUserItemClickListener(_AlertDialog,p_PayoutType));
    }
    private class OnSelectUserBack implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            metpayuser.setText("");
            String _Name = "";
            mpayuserid = "";
            if(mUserSelectedList != null)
            {
                for(int i=0;i<mUserSelectedList.size();i++)
                {
                    _Name += mUserSelectedList.get(i).getMusername() + ",";
                    mpayuserid += mUserSelectedList.get(i).getMuserid() + ",";
                }
                metpayuser.setText(_Name);
            }

            mItemColor = null;
            mUserSelectedList = null;
        }
    }

    private class OnUserItemClickListener implements AdapterView.OnItemClickListener
    {
        private AlertDialog m_AlertDialog;
        private String m_PayoutType;

        public OnUserItemClickListener(AlertDialog p_AlertDialog,String p_PayoutType)
        {
            m_AlertDialog = p_AlertDialog;
            m_PayoutType = p_PayoutType;
        }
        @Override
        public void onItemClick(AdapterView p_AdapterView, View arg1, int p_Position,
                                long arg3) {
//			ModelUser _ModelUser = (ModelUser)((Adapter)p_AdapterView.getAdapter()).getItem(p_Position);
//			((OnListSelectListener)ActivityBase.this).OnSelected(_ModelUser,"User");
//			m_AlertDialog.dismiss();

            String _PayoutTypeArr[] = getResources().getStringArray(R.array.payouttype);
            User _ModelUser = (User)((Adapter)p_AdapterView.getAdapter()).getItem(p_Position);
            if(m_PayoutType.equals(_PayoutTypeArr[0]) || m_PayoutType.equals(_PayoutTypeArr[1]))
            {
                RelativeLayout _Main = (RelativeLayout)arg1.findViewById(R.id.userrelativelayout);


                if(mItemColor == null && mUserSelectedList == null)
                {
                    mItemColor = new ArrayList<RelativeLayout>();
                    mUserSelectedList = new ArrayList<User>();
                }

                if(mItemColor.contains(_Main))
                {
                    _Main.setBackgroundResource(R.drawable.blue);
                    mItemColor.remove(_Main);
                    mUserSelectedList.remove(_ModelUser);
                }
                else {
                    _Main.setBackgroundResource(R.drawable.red);
                    mItemColor.add(_Main);
                    mUserSelectedList.add(_ModelUser);
                }

//				if(m_PayoutType.equals(_PayoutTypeArr[1]))
//				{
//					if(m_UserSelectedList.size() == 2)
//					{
//						((OnListSelectListener)ActivityBase.this).OnSelected(m_UserSelectedList,"User");
//						m_AlertDialog.dismiss();
//					}
//				}
                return;
            }

            if(m_PayoutType.equals(_PayoutTypeArr[2]))
            {
                mUserSelectedList = new ArrayList<User>();
                mUserSelectedList.add(_ModelUser);
                metpayuser.setText("");
                String _Name = "";
                mpayuserid = "";
                for(int i=0;i<mUserSelectedList.size();i++)
                {
                    _Name += mUserSelectedList.get(i).getMusername() + ",";
                    mpayuserid += mUserSelectedList.get(i).getMuserid() + ",";
                }
                metpayuser.setText(_Name);

                mItemColor = null;
                mUserSelectedList = null;
                m_AlertDialog.dismiss();
            }
        }
    }
}
