package com.rt.zgloan.fragment;

import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BaseResponse;

import butterknife.BindView;
import rx.Observable;

/**
 * 信用卡模块
 * Created by Administrator on 2017/8/24.
 */

public class FragmentCreditCard extends BaseFragment {
    private static final String TAG = "FragmentCreditCard";
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_credit_card;
    }

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle(false, "信用卡");
    }

    @Override
    public void showLoading(String content) {

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showErrorMsg(String msg, String type) {

    }

    @Override
    public void recordSuccess(Object o) {

    }

}
