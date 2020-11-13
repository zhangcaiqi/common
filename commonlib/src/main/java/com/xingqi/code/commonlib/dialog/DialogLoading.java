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


public class DialogLoading extends DialogFragment {

    public static final String TAG = "DialogLoading";

    public static DialogLoading newInstance(Bundle bundle) {
        DialogLoading dialog = new DialogLoading();
        if (null != bundle) {
            dialog.setArguments(bundle);
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading,container,false);
        TextView tvMessage = view.findViewById(R.id.tv_message);
        Bundle bundle = getArguments();
        String message = bundle.getString("message");
        tvMessage.setText(message);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dlg = new Dialog(getActivity(), R.style.LoadingDialogStyle);
        dlg.setCanceledOnTouchOutside(false);
        return dlg;
    }
}
