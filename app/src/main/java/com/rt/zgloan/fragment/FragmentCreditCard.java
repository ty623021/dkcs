package com.rt.zgloan.fragment;

import android.view.View;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BaseResponse;

import rx.Observable;

/**
 * 信用卡模块
 * Created by Administrator on 2017/8/24.
 */

public class FragmentCreditCard extends BaseFragment {
    private static final String TAG = "FragmentCreditCard";


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
