package com.rt.zgloan.weight;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.util.AbStringUtil;


@SuppressLint("NewApi")
public class LoadingFragment extends DialogFragment {
    private TextView vLoading_text;
    private String mMsg = "正在加载···";
    private Dialog dialog;
    private static LoadingFragment loadingFragment;

    public static LoadingFragment getInstance() {
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
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        this.mMsg = tag;
        if (vLoading_text != null && !AbStringUtil.isEmpty(mMsg)) {
            vLoading_text.setText(mMsg);
        }
    }

    @Override
    public void dismiss() {
        try {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
