package com.rt.zgloan.activity;

import android.view.View;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.util.AppUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 修改手机号码成功
 * Created by hjy on 2017/8/25.
 */

public class ChangePhoneSuccessActivity extends BaseActivity {
    private static final String TAG = "ChangePhoneSuccessActiv";
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;

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
        return R.layout.activity_change_phone_success;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle(false, "修改手机号码");
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
