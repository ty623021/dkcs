package com.rt.zgloan.activity.creditCardActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.adapter.CreditCardListAdapter;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.CreditCardBean;
import com.rt.zgloan.bean.CreditCardListBean;
import com.rt.zgloan.globe.Constant;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.recyclerview.SpaceItemDecoration;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

import static com.rt.zgloan.http.HttpManager.getApi;

/**
 * 信用卡列表
 * Created by hjy on 2017/8/25.
 */

public class CreditCardListActivity extends BaseActivity<CreditCardListBean> implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {

    public static final String TYPE_PURPOSE = "purpose";
    public static final String TYPE_SUBJECT = "subject";
    public static final String TYPE_BANK = "bank";
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
    private String id;
    private String type;
    private boolean isRefresh = true;
    private int pageNo = 1;
    private Observable observable;
    private CreditCardListAdapter adapter;
    private List<CreditCardBean> cardBeans = new ArrayList<>();
    private int totalPages;

    @Override
    public Observable<BaseResponse<CreditCardListBean>> initObservable() {
        mapParams.put("pageNo", pageNo + "");
        if (TYPE_PURPOSE.equals(type)) {
            mapParams.put("purposeId", id);
            return getApi().getCreditCardsByPurpose(mapParams);
        } else if (TYPE_SUBJECT.equals(type)) {
            mapParams.put("subjectId", id);
            return getApi().getCreditCardsBySubject(mapParams);
        } else {
            mapParams.put("bankId", id);
            return getApi().getCreditCardsByBank(mapParams);
        }
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
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");
        mTitle.setTitle("信用卡办理");
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new SpaceItemDecoration(0, 5, 0, 0));
        adapter = new CreditCardListAdapter(mContext, cardBeans);
        recyclerView.setAdapter(adapter);
        AbRefreshUtil.initRefresh(pull, this, this);
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);
        AbRefreshUtil.hintView(pull, adapter, true, linearNoNet, noRecord);
    }

    @Override
    public void recordSuccess(CreditCardListBean cardListBean) {
        if (AbStringUtil.isListEmpty(cardListBean.getClassify())) {
            cardBeans.addAll(cardListBean.getClassify());
            adapter.notifyDataSetChanged();
        }
        totalPages = cardListBean.getTotalPages();
        AbRefreshUtil.hintView(pull, adapter, false, linearNoNet, noRecord);
    }

    public static void startActivity(Context context, String id, String type) {
        Intent intent = new Intent(context, CreditCardListActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    private void sendHttp() {
        if (isRefresh) {
            pageNo = 1;
        } else {
            pageNo++;
        }
        if (TYPE_PURPOSE.equals(type)) {
            mapParams.put("purposeId", id);
            observable = getApi().getCreditCardsByPurpose(mapParams);
        } else if (TYPE_SUBJECT.equals(type)) {
            mapParams.put("subjectId", id);
            observable = HttpManager.getApi().getCreditCardsBySubject(mapParams);
        } else {
            mapParams.put("bankId", id);
            observable = getApi().getCreditCardsByBank(mapParams);
        }
        mapParams.put("pageNo", pageNo + "");
        ToastUtil.showToast(pageNo + "");
        mPresenter.toSubscribe(observable, new HttpSubscriber<CreditCardListBean>() {
            @Override
            protected void _onStart() {

            }

            @Override
            protected void _onNext(CreditCardListBean bankBean) {
                if (AbStringUtil.isListEmpty(bankBean.getClassify())) {
                    if (AbStringUtil.isListEmpty(cardBeans) && isRefresh) {
                        cardBeans.clear();
                    }
                    cardBeans.addAll(bankBean.getClassify());
                    adapter.notifyDataSetChanged();
                }
                totalPages = bankBean.getTotalPages();
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
}
