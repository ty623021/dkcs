package com.rt.zgloan.activity.myActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.adapter.GetCityAdapter;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.CitiesBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.util.AppUtil;

import butterknife.BindView;
import rx.Observable;

/**
 * 选择城市
 * Created by hjy on 2017/8/31.
 */

public class GetCityActivity extends BaseActivity<CitiesBean> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;

    private GetCityAdapter adapter;
    private String id;

    @Override
    public Observable<BaseResponse<CitiesBean>> initObservable() {

        mapParams.put("id", id);
        return HttpManager.getApi().getRegionById(mapParams);
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, GetCityActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_get_city;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("选择城市");
        id = getIntent().getStringExtra("id");
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

    }

    @Override
    public void recordSuccess(CitiesBean citiesBean) {
        if (citiesBean != null) {
            adapter = new GetCityAdapter(this);
            adapter.clearData();
            adapter.addData(citiesBean.getCity());
            recyclerView.setAdapter(adapter);
        }
    }

//    @Override
//    public void recordSuccess(GetCityInfo info) {
//        infos = info.getRelute();
//        adapter = new GetCityAdapter(infos, provinceInfo);
//        recyclerView.setAdapter(adapter);
//    }
}
