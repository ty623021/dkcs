package com.rt.zgloan.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.app.App;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 注册成功
 * Created by hjy on 2017/9/4.
 */

public class RegisterSuccessActivity extends BaseActivity {
    private String total_munber;

    @BindView(R.id.tv_user)
    TextView tv_user;

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @OnClick({R.id.btn_see})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_see:
                this.startActivity(LoginActivity.class);
                LoginActivity.startLoginActivity(mActivity, 1);
                break;

        }
    }


    public static void startActivity(Context context, String total_munber) {
        Intent intent = new Intent(context, RegisterSuccessActivity.class);
        intent.putExtra("total_munber", total_munber);
        context.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_success;
    }

    @Override
    public void initView() {
        total_munber = getIntent().getStringExtra("total_munber");
        tv_user.setText("您成为" + App.getAPPName() + "第" + total_munber + "位用户");
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
