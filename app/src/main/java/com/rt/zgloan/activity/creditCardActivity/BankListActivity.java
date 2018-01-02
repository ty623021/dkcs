package com.rt.zgloan.activity.creditCardActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.adapter.BankListAdapter;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BankBean;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.globe.Constant;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import rx.Observable;

/**
 * 信用卡列表
 * Created by hjy on 2017/8/25.
 */

public class BankListActivity extends BaseActivity<BankBean> implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    @BindView(R.id.pull)
    AbPullToRefreshView pull;
    @BindView(R.id.no_record)
    LinearLayout noRecord;
    @BindView(R.id.linear_no_net)
    LinearLayout linearNoNet;
    @BindView(R.id.layout_height_top)
    RelativeLayout layoutHeightTop;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BankListAdapter adapter;
    private String id;
    private ArrayList<BankBean.BankInfo> bankBeans = new ArrayList<>();
    private boolean isRefresh = true;
    private int pageNo = 1;
    private int totalPages;

    @Override
    public Observable<BaseResponse<BankBean>> initObservable() {
        id = getIntent().getStringExtra("id");
        mapParams.put("id", id);
        return HttpManager.getApi().getBankCardList(mapParams);
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
        return R.layout.activity_credit_card_list;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            layoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            layoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("信用卡办理");
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BankListAdapter(mContext, bankBeans);
        recyclerView.setAdapter(adapter);
        AbRefreshUtil.initRefresh(pull, this, this);
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void recordSuccess(BankBean bankBean) {
        if (AbStringUtil.isListEmpty(bankBean.getClassify())) {
            bankBeans.addAll(bankBean.getClassify());
            adapter.notifyDataSetChanged();
        }
        totalPages = bankBean.getTotalPages();
    }

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, BankListActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        isRefresh = false;
        if (AbRefreshUtil.isLoad(pageNo, totalPages, pull)) {
            sendHttp();
        } else {
            ToastUtil.showToast(Constant.LOADED);
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        isRefresh = true;
        sendHttp();
    }

    private void sendHttp() {
        if (isRefresh) {
            pageNo = 1;
        } else {
            pageNo++;
        }
        mapParams.put("pageNo", pageNo + "");
        mPresenter.toSubscribe(HttpManager.getApi().getBankCardList(mapParams), new HttpSubscriber<BankBean>() {
            @Override
            protected void _onStart() {

            }

            @Override
            protected void _onNext(BankBean bankBean) {
                if (AbStringUtil.isListEmpty(bankBean.getClassify())) {
                    if (AbStringUtil.isListEmpty(bankBeans) && isRefresh) {
                        bankBeans.clear();
                    }
                    totalPages = bankBean.getTotalPages();
                    bankBeans.addAll(bankBean.getClassify());
                    adapter.notifyDataSetChanged();
                }
                AbRefreshUtil.hintView(pull, adapter, false, linearNoNet, noRecord);
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showToast(message);
                AbRefreshUtil.hintView(pull, adapter, true, linearNoNet, noRecord);
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

}
