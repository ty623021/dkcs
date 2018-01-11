package com.rt.zgloan.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.adapter.LoanProductAdapter;
import com.rt.zgloan.adapter.LoanProductClassAdapter;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.LoanClassListBean;
import com.rt.zgloan.bean.LoansListByLoanTypeBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.LoadingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

/**
 * 贷款列表页面
 * Created by Administrator on 2017/8/24.
 */

public class FragmentLoan extends BaseFragment<LoanClassListBean> implements AbPullToRefreshView.OnHeaderRefreshListener {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.pull)
    AbPullToRefreshView pull;
    @BindView(R.id.no_record)
    LinearLayout noRecord;
    @BindView(R.id.linear_no_net)
    LinearLayout linearNoNet;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.rv_loan_product_class)
    RecyclerView mRvLoanProductClass;
    private int loanType;//当前显示的产品分类的类型id
    private LoanProductClassAdapter mAdapter;//产品分类
    private LoanProductAdapter mLoanProductAdapter;//产品
    private List<LoanClassListBean.LoanClassBean> loanClassBeanList = new ArrayList<>();//产品分类列表
    private List<LoansListByLoanTypeBean.LoansListBean> loansListBeen = new ArrayList<>();//产品列表

    @Override
    public int getLayoutId() {
        return R.layout.fragment_loan;
    }

    @Override
    public Observable<BaseResponse<LoanClassListBean>> initObservable() {
        return HttpManager.getApi().getLoanClassList(mapParams);//请求产品分类数据
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle(false, "借款");
        AbRefreshUtil.initRefresh(pull, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        mRvLoanProductClass.setLayoutManager(gridLayoutManager);
        mAdapter = new LoanProductClassAdapter(this);
        mRvLoanProductClass.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setNestedScrollingEnabled(false);
        recycleView.setFocusable(false);
        mLoanProductAdapter = new LoanProductAdapter(this);
        recycleView.setAdapter(mLoanProductAdapter);
    }

    @Override
    public void showLoading(String content) {


    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);
        AbRefreshUtil.hintView(pull, mLoanProductAdapter, true, linearNoNet, noRecord);
    }

    @Override
    public void recordSuccess(final LoanClassListBean loanClassListBean) {
        if (loanClassListBean != null) {
            setLoanClassData(loanClassListBean);
        }
    }

    //设置分类及产品
    private void setLoanClassData(final LoanClassListBean loanClassListBean) {
        if (loanClassBeanList.size() > 0) {
            loanClassBeanList.clear();
            mAdapter.clearData();
        }
        loanClassBeanList.addAll(loanClassListBean.getClassify());
        mAdapter.addData(loanClassBeanList);
        mAdapter.notifyDataSetChanged();
        //默认加载第一个
        loanType = loanClassListBean.getClassify().get(0).getId();
        loanClassListBean.getClassify().get(0).setIsSelected(true);
        //获取默认的分类产品对应的列表数据
        getLoansListByLoanType(loanType,false);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < loanClassListBean.getClassify().size(); i++) {
                    loanClassListBean.getClassify().get(i).setIsSelected(i == position);
                }
                mAdapter.notifyDataSetChanged();
                loanType = loanClassListBean.getClassify().get(position).getId();
                getLoansListByLoanType(loanType,true);
            }
        });
    }

    /**
     * 下拉刷新
     * 获取默认的分类产品对应的列表数据
     *
     * @param loanType 产品类型
     */
    private void getLoansListByLoanType(int loanType, final boolean isShowLoading) {
        mapParams2.put("loanType", loanType);
        mPresenter.toSubscribe(HttpManager.getApi().getLoansListByLoanType(mapParams2), new HttpSubscriber<LoansListByLoanTypeBean>() {
            @Override
            protected void _onStart() {
                if (isShowLoading) {
                    LoadingFragment.getInstance().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在加载...");
                }
            }

            @Override
            protected void _onNext(LoansListByLoanTypeBean loansListByLoanTypeBean) {
                if (loansListByLoanTypeBean != null) {
                    setProductData(loansListByLoanTypeBean);
                    AbRefreshUtil.hintView(pull, mLoanProductAdapter, false, linearNoNet, noRecord);
                }
            }


            @Override
            protected void _onError(String message) {
                AbRefreshUtil.hintView(pull, mLoanProductAdapter, true, linearNoNet, noRecord);
                ToastUtil.showToast(message);
            }

            @Override
            protected void _onCompleted() {
                if (isShowLoading) {
                    LoadingFragment.getInstance().dismiss();
                }
            }
        });
    }

    //设置产品列表
    private void setProductData(LoansListByLoanTypeBean loansListByLoanTypeBean) {
        if (loansListByLoanTypeBean != null) {
            if (loansListBeen.size() > 0) {
                loansListBeen.clear();
                mLoanProductAdapter.clearData();
            }
            loansListBeen.addAll(loansListByLoanTypeBean.getClassify());
            mLoanProductAdapter.addData(loansListBeen);
            mLoanProductAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        getLoansListByLoanType(loanType,false);
    }

}
