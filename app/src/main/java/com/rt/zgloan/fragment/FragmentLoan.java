package com.rt.zgloan.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;
import com.rt.zgloan.R;
import com.rt.zgloan.adapter.FragmentHomeListAdapter;
import com.rt.zgloan.adapter.LoanProductAdapter;
import com.rt.zgloan.adapter.LoanProductClassAdapter;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.HomeListInfo;
import com.rt.zgloan.bean.LoanClassListBean;
import com.rt.zgloan.bean.LoanListInfo;
import com.rt.zgloan.bean.LoansListByLoanTypeBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.SpUtil;
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

    private FragmentHomeListAdapter adapter;
    private List<HomeListInfo> homeListInfo = new ArrayList<>();
    private int type = 0;
    private int page = 1;
    private boolean isRefresh = true;

    private int countAll;

    private LoanProductClassAdapter mAdapter;//产品分类
    private LoanProductAdapter mLoanProductAdapter;//产品
    private int id;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_loan;
    }

    @Override
    public Observable<BaseResponse<LoanClassListBean>> initObservable() {
//        if (isRefresh) {
//            page = 1;
//        } else {
//            page++;
//        }
//        mapParams.put("type", "");
//        mapParams.put("p", page + "");
        return HttpManager.getApi().getLoanClassList(mapParams);
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

    }

    @Override
    public void showLoading(String content) {


    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);
        AbRefreshUtil.hintView(pull,mLoanProductAdapter, true, linearNoNet, noRecord);
    }

    @Override
    public void recordSuccess(final LoanClassListBean loanClassListBean) {

        if (loanClassListBean != null) {
            setLoanClassData(loanClassListBean);

        }

    }

    //设置分类及产品
    private void setLoanClassData(final LoanClassListBean loanClassListBean) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        mRvLoanProductClass.setLayoutManager(gridLayoutManager);
        mAdapter = new LoanProductClassAdapter(this);
        mAdapter.clearData();
        mAdapter.addData(loanClassListBean.getClassify());
        mRvLoanProductClass.setAdapter(mAdapter);


        if ("0".equals(String.valueOf(SpUtil.getInt(SpUtil.categoryId)))) {
            Logger.e("FRAGMENTID1" + SpUtil.getInt(SpUtil.categoryId));
            id = loanClassListBean.getClassify().get(0).getId();
            loanClassListBean.getClassify().get(0).setIsSelected(true);
        } else {
            Logger.e("FRAGMENTID2" + SpUtil.getInt(SpUtil.categoryId));
            id = SpUtil.getInt(SpUtil.categoryId);
            loanClassListBean.getClassify().get(SpUtil.getInt(SpUtil.categoryPosition)).setIsSelected(true);
        }

        getLoansListByLoanType(id);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < loanClassListBean.getClassify().size(); i++) {
                    loanClassListBean.getClassify().get(i).setIsSelected(i == position);
                }
                mAdapter.notifyDataSetChanged();
                id = loanClassListBean.getClassify().get(position).getId();
                Logger.e("id--->>>" + id);
                getLoansListByLoanType(id);
            }
        });
    }

    private void getLoansListByLoanType(int id) {
        mapParams2.put("loanType", id);
        mPresenter.toSubscribe(HttpManager.getApi().getLoansListByLoanType(mapParams2), new HttpSubscriber<LoansListByLoanTypeBean>() {
            @Override
            protected void _onStart() {
//                LoadingDialog.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在加载...");

            }

            @Override
            protected void _onNext(LoansListByLoanTypeBean loansListByLoanTypeBean) {
                if (loansListByLoanTypeBean != null) {
                    Logger.e("loansListByLoanTypeBeanSuccess");
                    setProductData(loansListByLoanTypeBean);
                    AbRefreshUtil.hintView(pull,mLoanProductAdapter, false, linearNoNet, noRecord);
                }
            }


            @Override
            protected void _onError(String message) {
                AbRefreshUtil.hintView(pull,mLoanProductAdapter, true, linearNoNet, noRecord);
                ToastUtil.showToast(message);
            }

            @Override
            protected void _onCompleted() {
//                LoadingDialog.getInstends().dismiss();
            }
        });
    }

    //设置产品
    private void setProductData(LoansListByLoanTypeBean loansListByLoanTypeBean) {
        if (loansListByLoanTypeBean != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recycleView.setLayoutManager(layoutManager);
            recycleView.setNestedScrollingEnabled(false);
            recycleView.setFocusable(false);
            mLoanProductAdapter = new LoanProductAdapter(this);
            mLoanProductAdapter.clearData();
            mLoanProductAdapter.addData(loansListByLoanTypeBean.getClassify());
            recycleView.setAdapter(mLoanProductAdapter);

        }


        SpUtil.remove(SpUtil.categoryId);
        SpUtil.remove(SpUtil.categoryPosition);

    }
//    @Override
//    public void recordSuccess(LoanListInfo loanListInfo) {
//        setHttpData(loanListInfo);
////        Log.e("loanListHttp", JSON.toJSONString(loanListInfo.getRelute()));
////        Log.e("totalPages", totalPages + "");
//        AbRefreshUtil.hintView(pull,adapter, false, linearNoNet, noRecord);
//
//
//    }

    private void setHttpData(LoanListInfo loanListInfo) {
        countAll = loanListInfo.getCount();
        homeListInfo = loanListInfo.getRelute();
        if (isRefresh) {
//            adapter.setList(homeListInfo);
        } else {
//            adapter.addAll(homeListInfo);
        }
        AbRefreshUtil.isLoading(isRefresh, adapter.getItemCount(), countAll, pull);
    }

    /**
     * 下拉刷新所调用的请求
     */
    private void setHttp() {
//        if (isRefresh) {
//            page = 1;
//        } else {
//            page++;
//        }
//        if (type > 0) {
//            mapParams.put("type", type + "");
//        } else {
//            mapParams.clear();
//        }
//        mapParams.put("p", page + "");
        mPresenter.toSubscribe(
                HttpManager.getApi().getLoanClassList(mapParams), new HttpSubscriber<LoanClassListBean>() {
                    @Override
                    protected void _onStart() {
//                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在刷新...");
                    }

                    @Override
                    protected void _onNext(LoanClassListBean loanClassListBean) {
//                        setHttpData(loanListInfo);
//                        Log.e("loanListHttp", JSON.toJSONString(loanListInfo.getRelute()));
                        if (loanClassListBean != null) {
                            setLoanClassData(loanClassListBean);
                        }
                        AbRefreshUtil.hintView(pull,mLoanProductAdapter, false, linearNoNet, noRecord);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(message);
                        AbRefreshUtil.hintView(pull,mLoanProductAdapter, true, linearNoNet, noRecord);
                    }

                    @Override
                    protected void _onCompleted() {
                        LoadingFragment.getInstends().dismiss();
                        pull.onHeaderRefreshFinish();
                    }
                }
        );

    }


    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        isRefresh = true;
        setHttp();
        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在刷新...");
    }

//    @Override
//    public void onFooterLoad(AbPullToRefreshView view) {
//        isRefresh = false;
//        setHttp();
//    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        setHttp();
    }
}
