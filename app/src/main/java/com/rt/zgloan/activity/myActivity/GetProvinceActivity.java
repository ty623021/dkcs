package com.rt.zgloan.activity.myActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.adapter.GetProvinceAdapter;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.ProvinciesBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import rx.Observable;

/**
 * 选择省
 * Created by hjy on 2017/8/31.
 */

public class GetProvinceActivity extends BaseActivity<ProvinciesBean> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;

    private GetProvinceAdapter adapter;

    @Override
    public Observable<BaseResponse<ProvinciesBean>> initObservable() {

        return HttpManager.getApi().getProvincies(new HashMap<String, String>());
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_get_province;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("选择地区");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void recordSuccess(ProvinciesBean provinciesBean) {
        if (provinciesBean != null) {
            adapter = new GetProvinceAdapter(this);
            adapter.clearData();
            adapter.addData(provinciesBean.getProvince());
            recyclerView.setAdapter(adapter);
        }

    }

}
