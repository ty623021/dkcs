package com.rt.zgloan.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;

import com.rt.zgloan.R;


/**
 * Created by geek on 2016/6/28.
 */
public class DialogUtil {

    private static AlertDialog alertDialog;
    private static Dialog dialog;

    public static Dialog getDialog() {
        return dialog;
    }

    /**
     * 创建一个dialog  点击HOME键会关闭dialog
     *
     * @param view dialog 视图
     */
    public static void showAlertDialog(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//        dialog = builder.create();
//        dialog.show();
//        Window window = dialog.getWindow();
//        //清除关闭输入法的功能
//        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//        window.setContentView(view);
        createDialog(view, true);
    }

    /**
     * 创建一个dialog
     *
     * @param view         dialog 视图
     * @param isCancelable 是否点击HOME键会关闭dialog true 会 false 不会
     */
    public static void showAlertDialog(View view, boolean isCancelable) {
        createDialog(view, isCancelable);
    }

    private static void createDialog(View view, boolean isCancelable) {
        Activity activity = (Activity) view.getContext();
        if (activity == null && activity.isFinishing()) {
            //activity 不存在或者已关闭
            return;
        }
        dialog = new Dialog(activity, R.style.MyLoadDialog);
        dialog.setCancelable(isCancelable);// 点击HOME键不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        //当添加烟花特效的时候需要解除这行代码的注释
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogInterface) {
//                SoundPlay.destory();
//            }
//        });
    }

    public static void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
