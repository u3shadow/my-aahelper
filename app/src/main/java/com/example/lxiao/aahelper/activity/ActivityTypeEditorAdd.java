package com.example.lxiao.aahelper.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.SliderMenuView;
import com.example.lxiao.aahelper.adapter.adaptertype;
import com.example.lxiao.aahelper.baseactivity.ActivityFrame;
import com.example.lxiao.aahelper.business.businesstype;
import com.example.lxiao.aahelper.model.Type;
import com.example.lxiao.aahelper.utility.RegexTools;

import java.util.List;

/**
 * Created by lxiao on 2015/2/13.
 */
public class ActivityTypeEditorAdd extends ActivityFrame implements View.OnClickListener {

      private Button  mSaveButton;
      private Button mCancelButton;
      private EditText mEditText;
      private Spinner mSpinner;
      private businesstype mBusiness;
      private ArrayAdapter<Type> mAdapter;
      private Type mtype;
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_typesavebutton:
                AddOrEditType();
                break;
            case R.id.bt_typecancelbutton:
                finish();
                break;
            default:break;
         }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendmainbody(R.layout.edit_or_add_type_layout);
        RemoveBottomBox();
        initview();
        initvar();
        initlistener();
        binddata();
        SetTitle();
    }
    public void RemoveBottomBox()
    {
        SliderMenuView _slidemenuvie = new SliderMenuView(this);
        _slidemenuvie.RemoveBottomBox();
    }
    public void SetTitle(){
       if(mtype == null)
        setTitle(getString(R.string._saddtype));
        else {
           setTitle(getString(R.string._sedittype));
          InitData(mtype);
       }
    }
    public void InitData(Type ptype)
    {
        mEditText.setText(ptype.getTypeName());
        ArrayAdapter _arrayadapter = (ArrayAdapter)mSpinner.getAdapter();
        if(ptype.getTypeId() != 0)
        {
            int _posistion = 0;
            for(int i = 1;i < _arrayadapter.getCount();i++)//0 号位置为--请选择--字符串，不是TYPE类型 所以从1开始
            {
                Type _type = (Type) _arrayadapter.getItem(i);
                if(_type.getTypeId() == ptype.getParentId())
                {
                    _posistion = _arrayadapter.getPosition(_type);
                }
            }
            mSpinner.setSelection(_posistion);
        }
        else
        {
            int _count = mBusiness.getnothidecountbyparentid(ptype.getTypeId());
            if(_count != 0)
            {
                mSpinner.setEnabled(false);
            }
        }
    }
    @Override
    public void initvar() {
        super.initvar();
      Intent _intent =  this.getIntent();
        mtype = (Type) _intent.getSerializableExtra("mtype");
        mBusiness = new businesstype(this);
    }

    @Override
    public void initview() {
        super.initview();
        mSaveButton = (Button)findViewById(R.id.bt_typesavebutton);
        mCancelButton = (Button)findViewById(R.id.bt_typecancelbutton);
        mEditText = (EditText)findViewById(R.id.et_editoraddtypename);
        mSpinner = (Spinner)findViewById(R.id.sp_typeselectspinner);
    }

    @Override
    public void initlistener() {
        super.initlistener();
        mSaveButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
    }
    public void binddata()
    {
        mAdapter =mBusiness.getroottypearrayadapter();
        mSpinner.setAdapter(mAdapter);
    }
    public void AddOrEditType()
    {
        String _edittext = mEditText.getText().toString().trim();
        boolean _result = true;
        _result = RegexTools.IsChineseEnglishNum(_edittext);
        if(!_result)
        {
            Toast.makeText(this,getString(R.string._sillegletypename),Toast.LENGTH_LONG).show();
            return;
        }
        if(mtype == null)
        {
            mtype = new Type();
            mtype.setFlag(getString(R.string._sflag));
            mtype.setPath("");
        }
        mtype.setTypeName(_edittext);
        if(!mSpinner.getSelectedItem().toString().equals(getString(R.string._sdefaultselect)))
        {
            Type _parenttype = (Type)mSpinner.getSelectedItem();
            if(_parenttype != null)
            {
                mtype.setParentId(_parenttype.getTypeId());
            }
        }
        else
        {
           mtype.setParentId(0);
        }
        boolean _resultb = false;
        if(mtype.getTypeId() == 0)
        {
            _resultb = mBusiness.InsertType(mtype);
        }
        else
        {
            _resultb = mBusiness.updatebytypeid(mtype);
        }
        if(_resultb)
        {
            Toast.makeText(this,getString(R.string._ssuccess),Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(this,getString(R.string._sfaile),Toast.LENGTH_LONG).show();
        }


    }
}
