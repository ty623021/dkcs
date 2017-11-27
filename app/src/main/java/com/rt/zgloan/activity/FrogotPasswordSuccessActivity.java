package com.rt.zgloan.activity;

import android.view.View;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;

import butterknife.OnClick;
import rx.Observable;

/**
 * 忘记密码成功页面
 * Created by hjy on 2017/9/7.
 */

public class FrogotPasswordSuccessActivity extends BaseActivity {
    private static final String TAG = "FrogotPasswordSuccessAc";


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

    @Override
    public int getLayoutId() {
        return R.layout.activity_frogot_password_success;
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
