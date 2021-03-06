package com.example.lxiao.aahelper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.SliderMenuItem;
import com.example.lxiao.aahelper.UI.SliderMenuView;
import com.example.lxiao.aahelper.adapter.adapteruser;
import com.example.lxiao.aahelper.baseactivity.ActivityFrame;
import com.example.lxiao.aahelper.business.businessuser;
import com.example.lxiao.aahelper.model.User;
import com.example.lxiao.aahelper.utility.RegexTools;

/**
 * Created by U3 on 2015/2/9.
 */
public class ActivityUser extends ActivityFrame implements SliderMenuView.OnSlideMenuListener {

    private ListView lvuserlist;
    private adapteruser madapter;
    private businessuser mbusiness;
    private User muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendmainbody(R.layout.userlayout);
        initvar();
        initview();
        initlistener();
        binddata();
        createslidemenu(R.array._ausermenu);
    }

    public void binddata() {
        madapter = new adapteruser(this);
        lvuserlist.setAdapter(madapter);
    }

    @Override
    public void initvar() {
        super.initvar();

        mbusiness = new businessuser(this);
    }

    @Override
    public void initview() {
        super.initview();
        lvuserlist = (ListView) findViewById(R.id.lv_userlistview);
        setTitle(getString(R.string._suser));
    }

    @Override
    public void initlistener() {
        super.initlistener();
        registerForContextMenu(lvuserlist);
    }
    //长按弹出菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        ListAdapter _listadapter = lvuserlist.getAdapter();
        muser = (User) _listadapter.getItem(info.position);
        CreateMenu(menu);
    }
    //弹出菜单点击
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                ShowUserAddorEditDialog(muser);
                break;
            case 2:
               deleteuser();
                break;
            default:break;
        }
       return true;
    }
    //删除提示框
    public void deleteuser()
    {
        String _msg = getString(R.string._sdeleteusertitle,new Object[]{muser.getMusername()});
        showalertdialog(getString(R.string._sdeleteuser),_msg, new ondeleteuserlistener());
    }
    //处理删除事件
    class ondeleteuserlistener implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            boolean isdeleted = mbusiness.hideuserbyid(muser.getMuserid());
            if(isdeleted)
            {
                binddata();
                return;
            }
            else
                Toast.makeText(ActivityUser.this,getString(R.string._deletefail),Toast.LENGTH_LONG).show();
        }
    }
    //根据点击项来选择是否弹出对话框
    @Override
    public void onSlideMenuItemClick(View pview, SliderMenuItem pslideMenuItem) {
       // Toast.makeText(this, pslideMenuItem.getTitle(), Toast.LENGTH_LONG).show();
        if(pslideMenuItem.getId() == 0) {
            closeslidemenu();
            ShowUserAddorEditDialog(null);
        }
    }
    //显示修改或者新增用户的对话框
    public void ShowUserAddorEditDialog(User puser) {
        String Title;
        View _view = getlayoutinflater().inflate(R.layout.edit_or_add_user_layout, null);
        EditText _edittext = (EditText) _view.findViewById(R.id.et_addoredituser);
        if (puser != null) {
            _edittext.setText(puser.getMusername());
            Title = getString(R.string._susertype, new Object[]{getResources().getString(R.string._sedituser)});
        } else {
            Title = getString(R.string._susertype, new Object[]{getResources().getString(R.string._sadduser)});
            puser = new User();
        }

        AlertDialog.Builder _builder = new AlertDialog.Builder(this);
        _builder.setTitle(Title)

                .setView(_view)
                .setNeutralButton(getString(R.string._sbuttonsavetext), new OnAddorEdituserListener(puser, _edittext, true))
                .setNegativeButton(getString(R.string._sbuttoncanceltext), new OnAddorEdituserListener(null, null, false))
                .show();


    }
    //修改和添加用户的点击事件
    private class OnAddorEdituserListener implements DialogInterface.OnClickListener {
        private User muser;
        private EditText medittext;
        private boolean missave;

        public OnAddorEdituserListener(User puser, EditText pedittext, boolean pissave) {
            muser = puser;
            medittext = pedittext;
            missave = pissave;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            //点击取消
            if (!missave) {
                SetDialogIsClose(dialog, true);
                //return;
            }
            //点击保存
            else {
                SetDialogIsClose(dialog, false);
                //为空
                if (medittext.getText() == null) {
                    Toast.makeText(ActivityUser.this, getString(R.string._snullusername), Toast.LENGTH_LONG).show();
                    return;
                } else
                   //非空
                if (missave && medittext.getText() != null) {
                    boolean isllegle = RegexTools.IsChineseEnglishNum(medittext.getText().toString());
                    //判断合法性
                    if (!isllegle) {
                     Toast.makeText(ActivityUser.this, getString(R.string._sillegleusername), Toast.LENGTH_LONG).show();
                        return;
                    }
                    //判重
                    if(mbusiness.isuserexist(medittext.getText().toString(),muser.getMuserid()))
                    {
                        Toast.makeText(ActivityUser.this, getString(R.string._suserisexist), Toast.LENGTH_LONG).show();
                        return;
                    }
                    //修改
                    String _string = medittext.getText().toString().trim();
                    muser.setMusername(medittext.getText().toString());
                    if (muser.getMuserid() != 0) {
                        mbusiness.update(muser);
                        SetDialogIsClose(dialog, true);
                        Toast.makeText(ActivityUser.this,getString(R.string._seditsuccess),Toast.LENGTH_LONG).show();
                        binddata();
                        return;
                    }
                    //新增
                    else if (muser.getMuserid() == 0) {
                        mbusiness.insert(muser);
                        SetDialogIsClose(dialog, true);
                        Toast.makeText(ActivityUser.this,getString(R.string._sinsertsuccess),Toast.LENGTH_LONG).show();
                        binddata();
                        return;
                    }
                }
            }
        }


    }
}


