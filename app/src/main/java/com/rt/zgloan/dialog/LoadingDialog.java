package com.rt.zgloan.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.weight.LoadingFragment;


@SuppressLint("NewApi")
public class LoadingDialog extends DialogFragment {
    private TextView vLoading_text;
    private String mMsg = "正在加载···";
    private Dialog dialog;
    private static LoadingFragment loadingFragment;

    public static LoadingFragment getInstends() {
        if (loadingFragment == null) {
            synchronized (LoadingFragment.class) {
                loadingFragment = new LoadingFragment();
            }
        }
        return loadingFragment;
    }


    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_loading, null);
        vLoading_text = (TextView) view.findViewById(R.id.loading_text);
        vLoading_text.setText(mMsg);
        dialog = new Dialog(getActivity(), R.style.MyLoadDialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        return dialog;
    }


    public void setMsg(String msg) {
        if (msg != null) {
            this.mMsg = msg;
        }
    }

    @Override
    public void dismiss() {
//      dismissAllowingStateLoss();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
