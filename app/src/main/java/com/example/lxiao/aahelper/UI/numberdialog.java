package com.example.lxiao.aahelper.UI;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.lxiao.aahelper.R;

import java.math.BigDecimal;

import javax.crypto.CipherOutputStream;

/**
 * Created by U3 on 2015/2/20.
 */
public class numberdialog extends Dialog implements View.OnClickListener {
    private Context mcontext;
    public interface OnNumberDialogListener
    {
        public abstract void SetNumberFinish(BigDecimal p_Number);
    }
    public numberdialog(Context context)
    {
        super(context);
        mcontext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.number_dialog);
        findViewById(R.id.btnDot).setOnClickListener(this);
        findViewById(R.id.btnZero).setOnClickListener(this);
        findViewById(R.id.btnOne).setOnClickListener(this);
        findViewById(R.id.btnTwo).setOnClickListener(this);
        findViewById(R.id.btnThree).setOnClickListener(this);
        findViewById(R.id.btnFour).setOnClickListener(this);
        findViewById(R.id.btnFive).setOnClickListener(this);
        findViewById(R.id.btnSix).setOnClickListener(this);
        findViewById(R.id.btnSeven).setOnClickListener(this);
        findViewById(R.id.btnEight).setOnClickListener(this);
        findViewById(R.id.btnNine).setOnClickListener(this);
        findViewById(R.id.btnChange).setOnClickListener(this);
        findViewById(R.id.btnOk).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int _ID = v.getId();

        EditText _EditText = (EditText)findViewById(R.id.txtDisplay);
        String _Number = _EditText.getText().toString();

        switch (_ID) {
            case R.id.btnDot:
                if(_Number.indexOf(".") == -1)
                {
                    _Number += ".";
                }
                break;
            case R.id.btnOne:
                _Number += "1";
                break;
            case R.id.btnTwo:
                _Number += "2";
                break;
            case R.id.btnThree:
                _Number += "3";
                break;
            case R.id.btnFour:
                _Number += "4";
                break;
            case R.id.btnFive:
                _Number += "5";
                break;
            case R.id.btnSix:
                _Number += "6";
                break;
            case R.id.btnSeven:
                _Number += "7";
                break;
            case R.id.btnEight:
                _Number += "8";
                break;
            case R.id.btnNine:
                _Number += "9";
                break;
            case R.id.btnZero:
                _Number += "0";
                break;
            case R.id.btnChange:
                if(_Number.length() != 0)
                {
                    _Number = _Number.substring(0, _Number.length()-1);
                }
                break;
            case R.id.btnOk:
                BigDecimal _BigDecimal;
                if(!_Number.equals(".") && _Number.length() != 0)
                {
                    _BigDecimal = new BigDecimal(_Number);
                }
                else {
                    _BigDecimal = new BigDecimal(0);
                }
                ((OnNumberDialogListener)mcontext).SetNumberFinish(_BigDecimal);
                dismiss();
                break;
            default:
                break;
        }

        _EditText.setText(_Number);
    }
}
