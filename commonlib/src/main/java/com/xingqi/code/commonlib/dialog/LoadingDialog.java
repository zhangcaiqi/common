package com.xingqi.code.commonlib.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.xingqi.code.commonlib.R;


public class LoadingDialog extends DialogFragment {
    private LoadingDialog(){}

    public static LoadingDialog newInstance(){
        return newInstance("loading...");
    }
    public static LoadingDialog newInstance(String message){
        LoadingDialog loadingDialog = new LoadingDialog();
        Bundle bundle = new Bundle();
        bundle.putString("message",message);
        loadingDialog.setArguments(bundle);
        return loadingDialog;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dlg = new Dialog(getContext(),R.style.CustomProgressDialog);
        dlg.setCancelable(true);
        dlg.setCanceledOnTouchOutside(true);
        return dlg;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_custom_progress, null);
        TextView textView = view.findViewById(R.id.tv_message);
        Bundle bundle = getArguments();
        if(null != bundle){
            String message = bundle.getString("message","loading...");
            textView.setText(message);
        }
        return view;
    }


}
