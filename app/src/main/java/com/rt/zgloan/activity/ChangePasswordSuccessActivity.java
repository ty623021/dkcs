package com.rt.zgloan.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;

import butterknife.OnClick;
import rx.Observable;

/**
 * 修改登录密码成功
 * Created by hjy on 2017/8/25.
 */

public class ChangePasswordSuccessActivity extends BaseActivity {
    private static final String TAG = "ChangePasswordSuccessAc";


    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @OnClick({R.id.btn_restart_login})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_restart_login:
                this.startActivity(LoginActivity.class);
                break;
        }

    }


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChangePasswordSuccessActivity.class);
        context.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password_success;
    }

    @Override
    public void initView() {


    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {

    }

    @Override
    public void recordSuccess(Object o) {

    }
}
