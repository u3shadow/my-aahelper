package com.example.lxiao.aahelper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.UI.SliderMenuItem;
import com.example.lxiao.aahelper.UI.SliderMenuView;
import com.example.lxiao.aahelper.adapter.adapterbook;
import com.example.lxiao.aahelper.baseactivity.ActivityFrame;
import com.example.lxiao.aahelper.business.businessbook;
import com.example.lxiao.aahelper.model.Book;
import com.example.lxiao.aahelper.utility.RegexTools;

/**
 * Created by U3 on 2015/2/9.
 */
public class ActivityBooks extends ActivityFrame implements SliderMenuView.OnSlideMenuListener {

    private ListView lvbooklist;
    private adapterbook madapter;
    private businessbook mbusiness;
    private Book mbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendmainbody(R.layout.booklayout);
        initvar();
        initview();
        initlistener();
        binddata();
        createslidemenu(R.array._amainlistmenu);
    }

    public void binddata() {
        madapter = new adapterbook(this);
        lvbooklist.setAdapter(madapter);
    }

    @Override
    public void initvar() {
        super.initvar();

        mbusiness = new businessbook(this);
    }

    @Override
    public void initview() {
        super.initview();
        lvbooklist = (ListView) findViewById(R.id.lv_booklistview);
    }

    @Override
    public void initlistener() {
        super.initlistener();
        registerForContextMenu(lvbooklist);
    }
    //长按弹出菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        ListAdapter _listadapter = lvbooklist.getAdapter();
        mbook = (Book) _listadapter.getItem(info.position);
        CreateMenu(menu);
    }
    //弹出菜单点击
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                ShowBookAddorEditDialog(mbook);
                break;
            case 2:
               deletebook(); //need improve
                break;
            default:break;
        }
       return true;
    }
    //删除提示框
    public void deletebook()
    {
        String _msg = getString(R.string._sdeletebooktitle,new Object[]{mbook.getBookName()});
        showalertdialog(getString(R.string._sdeletebook),_msg, new ondeletebooklistener());
    }
    //处理删除事件
    class ondeletebooklistener implements DialogInterface.OnClickListener
    {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            boolean isdeleted = mbusiness.hidebookbyid(mbook.getBookId());
            if(isdeleted)
            {
                binddata();
                return;
            }
            else
                Toast.makeText(ActivityBooks.this,getString(R.string._deletefail),Toast.LENGTH_LONG).show();
        }
    }
    //根据点击项来选择是否弹出对话框
    @Override
    public void onSlideMenuItemClick(View pview, SliderMenuItem pslideMenuItem) {
       // Toast.makeText(this, pslideMenuItem.getTitle(), Toast.LENGTH_LONG).show();
        if(pslideMenuItem.getId() == 0) {
            closeslidemenu();
            ShowBookAddorEditDialog(null);
        }
    }
    //显示修改或者新增用户的对话框
    public void ShowBookAddorEditDialog(Book pbook) {
        String Title;
        View _view = getlayoutinflater().inflate(R.layout.edit_or_add_book_layout, null);
        EditText _edittext = (EditText) _view.findViewById(R.id.et_addoreditbook);
        CheckBox _checkbox = (CheckBox)_view.findViewById(R.id.cb_isdefaultbook);
        if (pbook != null) {
            _edittext.setText(pbook.getBookName());
            if(pbook.getIsDefault() == 1)
            _checkbox.setChecked(true);
            else
                _checkbox.setChecked(false);
            Title = getString(R.string._sbooktype, new Object[]{getResources().getString(R.string._seditbook)});
        } else {
            Title = getString(R.string._sbooktype, new Object[]{getResources().getString(R.string._saddbook)});
            pbook = new Book();
        }

        AlertDialog.Builder _builder = new AlertDialog.Builder(this);
        _builder.setTitle(Title)

                .setView(_view)
                .setNeutralButton(getString(R.string._sbuttonsavetext), new OnAddorEditbookListener(pbook, _edittext, true,_checkbox))
                .setNegativeButton(getString(R.string._sbuttoncanceltext), new OnAddorEditbookListener(null, null, false,null))
                .show();


    }
    //修改和添加用户的点击事件
    private class OnAddorEditbookListener implements DialogInterface.OnClickListener {
        private Book mbook;
        private EditText medittext;
        private boolean missave;
        private CheckBox mcheckbox;

        public OnAddorEditbookListener(Book pbook, EditText pedittext, boolean pissave,CheckBox pcheckbox) {
            mbook = pbook;
            medittext = pedittext;
            missave = pissave;
            mcheckbox = pcheckbox;

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
                    Toast.makeText(ActivityBooks.this, getString(R.string._snullbookname), Toast.LENGTH_LONG).show();
                    return;
                } else
                   //非空
                if (missave && medittext.getText() != null) {
                    boolean isllegle = RegexTools.IsChineseEnglishNum(medittext.getText().toString());
                    //判断合法性
                    if (!isllegle) {
                     Toast.makeText(ActivityBooks.this, getString(R.string._silleglebookname), Toast.LENGTH_LONG).show();
                        return;
                    }
                    //判重
                    if(mbusiness.isbookexist(medittext.getText().toString(),mbook.getBookId()))
                    {
                        Toast.makeText(ActivityBooks.this, getString(R.string._sbookisexist), Toast.LENGTH_LONG).show();
                        return;
                    }
                   if(mcheckbox.isChecked()) {
                            mbook.setIsDefault(1);
                    }

                    //修改
                    String _string = medittext.getText().toString().trim();
                    mbook.setBookName(medittext.getText().toString());
                    if (mbook.getBookId() != 0) {
                        mbusiness.update(mbook);
                        SetDialogIsClose(dialog, true);
                        if(!mcheckbox.isChecked()&&mbook.getIsDefault()==1)
                        {
                            Toast.makeText(ActivityBooks.this,getString(R.string._silligledefaultbook),Toast.LENGTH_LONG).show();
                        }
                        else
                        Toast.makeText(ActivityBooks.this,getString(R.string._seditbooksuccess),Toast.LENGTH_LONG).show();
                        binddata();
                        return;
                    }
                    //新增
                    else if (mbook.getBookId() == 0) {
                        mbusiness.insert(mbook);
                        SetDialogIsClose(dialog, true);
                        Toast.makeText(ActivityBooks.this,getString(R.string._sinsertbooksuccess),Toast.LENGTH_LONG).show();
                        binddata();
                        return;
                    }
                }
            }
        }


    }
}


