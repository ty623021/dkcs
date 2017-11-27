package com.rt.zgloan.activity.myActivity;

import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;

import butterknife.BindView;
import rx.Observable;

/**
 * 关于我们的子页面
 * Created by hjy on 2017/9/3.
 */

public class AbourOurSonActivity extends BaseActivity {
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_our_son;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("关于我们");

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
