package com.rt.zgloan.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;
import com.rt.zgloan.R;
import com.rt.zgloan.activity.MainActivity;
import com.rt.zgloan.activity.WebViewActivity;
import com.rt.zgloan.adapter.LabelLoansAdapter;
import com.rt.zgloan.adapter.ProductCategoryAdapter;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BannerListBean;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.CommentListBean;
import com.rt.zgloan.bean.LabelListBean;
import com.rt.zgloan.bean.LoanClassListBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;
import com.rt.zgloan.recyclerview.DividerItemDecoration;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.GlideImageLoader;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.LoadingFragment;
import com.rt.zgloan.weight.RollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 首页
 * Created by Administrator on 2017/8/24.
 */

public class FragmentFirstPage extends BaseFragment<BannerListBean> implements AbPullToRefreshView.OnHeaderRefreshListener {


    @BindView(R.id.pull)
    AbPullToRefreshView pull;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.rv_product_category)
    RecyclerView mRvProduct;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.rv_content)
    RollView mRvContent;
    @BindView(R.id.banner_guide_content)
    Banner bannerGuideContent;//banner图
    @BindView(R.id.loan_relative)
    RelativeLayout loan_relative;
    @BindView(R.id.credit_card_relative)
    RelativeLayout credit_card_relative;

    private FragmentActivity activity;
    private List<BannerListBean.BannerBean> mActivityListBean = new ArrayList<>();
    private List<LabelListBean.LabelBean> mListLabel = new ArrayList<>();
    private ProductCategoryAdapter mProductCategoryAdapter;//产品分类
    private LabelLoansAdapter mLabelLoansAdapter;//标签和对应产品


    @Override
    public int getLayoutId() {
        return R.layout.fragment_first_page;
    }

    @Override
    public Observable<BaseResponse<BannerListBean>> initObservable() {
        return null;
    }

    @OnClick({R.id.loan_relative, R.id.credit_card_relative})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_relative:
                ((MainActivity) activity).switchTab(1);
                break;
            case R.id.credit_card_relative:
                ((MainActivity) activity).switchTab(2);
                break;
            default:
                break;
        }
    }


    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle(false, "汇生财");
        activity = getActivity();

        AbRefreshUtil.initRefresh(pull, this);

        getBannerList();
        getMessageList();
//        getLoanClassList();//产品类别 隐藏
        getLabelList();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void recordSuccess(final BannerListBean bannerListBean) {

    }


    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        getBannerList();
        getMessageList();
//        getLoanClassList();
        getLabelList();
    }

    private void getBannerList() {
        mPresenter.toSubscribe(
                HttpManager.getApi().banner(mapParams), new HttpSubscriber<BannerListBean>() {
                    @Override
                    protected void _onStart() {

                    }

                    @Override
                    protected void _onNext(BannerListBean bannerListBean) {
                        setBanner(bannerListBean);
                    }

                    @Override
                    protected void _onError(String message) {

                    }

                    @Override
                    protected void _onCompleted() {
                    }
                }
        );

    }

    //获取消息内容
    private void getMessageList() {
        mPresenter.toSubscribe(
                HttpManager.getApi().getMessageList(mapParams), new HttpSubscriber<CommentListBean>() {
                    @Override
                    protected void _onStart() {
                    }

                    @Override
                    protected void _onNext(CommentListBean commentListBean) {
                        if (commentListBean != null) {
                            List<String> strings = new ArrayList<>();
                            for (int i = 0; i < commentListBean.getCommentBeans().size(); i++) {
                                strings.add(commentListBean.getCommentBeans().get(i).getContent().toString());
                            }
                            mRvContent.setContent(strings);
                        }
                    }


                    @Override
                    protected void _onError(String message) {
                    }

                    @Override
                    protected void _onCompleted() {

                    }
                }
        );
    }

    //获取产品分类
    private void getLoanClassList() {
        mPresenter.toSubscribe(
                HttpManager.getApi().getLoanClassList(mapParams), new HttpSubscriber<LoanClassListBean>() {
                    @Override
                    protected void _onStart() {
                    }

                    @Override
                    protected void _onNext(LoanClassListBean loanClassListBean) {
                        setProductCategory(loanClassListBean);
                    }

                    @Override
                    protected void _onError(String message) {
                    }

                    @Override
                    protected void _onCompleted() {

                    }
                }
        );
    }

    //获取产品标签及产品
    private void getLabelList() {
        int cityId = SpUtil.getInt(SpUtil.CITY_ID);
        if (cityId > 0) {
            mapParams.put("cityId", cityId + "");
        }
        mPresenter.toSubscribe(
                HttpManager.getApi().getLabelList(mapParams), new HttpSubscriber<LabelListBean>() {
                    @Override
                    protected void _onStart() {
                        if (progressTitle != null)
                            LoadingFragment.getInstance().show(((FragmentActivity) mContext).getSupportFragmentManager(), progressTitle);
                    }

                    @Override
                    protected void _onNext(LabelListBean labelListBean) {
                        mListLabel = labelListBean.getLabel();
                        setProduct(mListLabel);
                        progressTitle = null;
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(message);
                    }

                    @Override
                    protected void _onCompleted() {
                        pull.onHeaderRefreshFinish();
                        LoadingFragment.getInstance().dismiss();
                    }
                }
        );
    }

    //获取产品分类
    private void setProductCategory(final LoanClassListBean loanClassListBean) {
        Logger.e("adapterSuccess");
        if (loanClassListBean != null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            mRvProduct.setLayoutManager(gridLayoutManager);
            mRvProduct.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
            mProductCategoryAdapter = new ProductCategoryAdapter(this);
            mProductCategoryAdapter.clearData();
            mProductCategoryAdapter.addData(loanClassListBean.getClassify());
            mRvProduct.setAdapter(mProductCategoryAdapter);
            mProductCategoryAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClick() {
                @Override
                public void onItemClick(View view, int position) {
                    SpUtil.putInt(SpUtil.categoryId, loanClassListBean.getClassify().get(position).getId());
                    SpUtil.putInt(SpUtil.categoryPosition, position);
                    Logger.e("SpUtil.categoryId" + loanClassListBean.getClassify().get(position).getId());
                    ((MainActivity) getActivity()).switchTab(1);

                }
            });
        }

    }

    //设置产品
    private void setProduct(List<LabelListBean.LabelBean> labelBeans) {
        if (labelBeans != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recycleView.setLayoutManager(layoutManager);
            recycleView.setNestedScrollingEnabled(false);
            recycleView.setFocusable(false);
            mLabelLoansAdapter = new LabelLoansAdapter(mContext, labelBeans);
            recycleView.setAdapter(mLabelLoansAdapter);
            mLabelLoansAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置轮播banner
     *
     * @param bannerListBean
     */
    private void setBanner(final BannerListBean bannerListBean) {
        if (AbStringUtil.isListEmpty(bannerListBean.getBanner())) {
            bannerGuideContent.setVisibility(View.VISIBLE);
            mActivityListBean = bannerListBean.getBanner();
            //设置banner样式
            bannerGuideContent.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置不显示指示器   BannerConfig.CIRCLE_INDICATOR//圆点指示器
            //设置图片加载器
            bannerGuideContent.setImageLoader(new GlideImageLoader(R.mipmap.banner));
            //设置图片集合
            bannerGuideContent.setImages(mActivityListBean);
            //设置banner动画效果
            bannerGuideContent.setBannerAnimation(Transformer.DepthPage);
            //设置自动轮播，默认为true
            bannerGuideContent.isAutoPlay(true);
            //设置轮播时间
            bannerGuideContent.setDelayTime(5000);
            //设置指示器位置（当banner模式中有指示器时）
            bannerGuideContent.setIndicatorGravity(BannerConfig.CENTER);//修改小圆点位置
            bannerGuideContent.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    BannerListBean.BannerBean bannerBean = mActivityListBean.get(position);
                    if (!AbStringUtil.isEmpty(bannerBean.getSlide_url())) {
                        WebViewActivity.startActivity(mActivity, bannerBean.getSlide_url());
                    }
                }
            });
            //banner设置方法全部调用完毕时最后调用
            bannerGuideContent.start();
        } else {
            bannerGuideContent.setVisibility(View.GONE);
            Logger.e("banner--->>>");
        }
    }

}

