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
        Dialog dlg = new Dialog(getContext(),R.style.LoadingDialogStyle);

        dlg.setCancelable(false);
        dlg.setCanceledOnTouchOutside(false);

        return dlg;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setMessage("");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, null);
        TextView textView = view.findViewById(R.id.tv_message);
        Bundle bundle = getArguments();
        if(null != bundle){
            String message = bundle.getString("message","loading...");
            textView.setText(message);
        }
        return view;
    }

    public void setMessage(String message){
        Bundle bundle = new Bundle();
        bundle.putString("message",message);
        setArguments(bundle);
    }


}
