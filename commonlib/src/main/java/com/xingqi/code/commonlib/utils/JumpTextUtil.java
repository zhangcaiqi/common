package com.xingqi.code.commonlib.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class JumpTextUtil implements TextWatcher {
    private EditText mThisView = null;
    private View mNextView = null;

    public JumpTextUtil(EditText mThisView, View mNextView) {
        super();
        this.mThisView = mThisView;
        if (mNextView!=null) {
            this.mNextView = mNextView;
        }
    }
    //beforeTextChanged：文本改变之前触发
    //onTextChanged：文本改变过程中触发
    //afterTextChanged：文本改变之后触发
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString();
        if (str.contains("\r")||str.contains("\n")){
            mThisView.setText(str.replace("\r","").replace("\n",""));
            if (mNextView!=null){
                mNextView.requestFocus();
                if (mNextView instanceof EditText){
                    // instanceof 是判断其左边对象是否为其右边类的实例
                    EditText et = (EditText) mNextView;
                    et.setSelection(et.getText().length());
                }
            }
        }

    }
}