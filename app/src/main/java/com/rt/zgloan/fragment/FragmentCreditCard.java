package com.rt.zgloan.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.WebViewActivity;
import com.rt.zgloan.activity.cityActivity.UnifiedActivity;
import com.rt.zgloan.activity.cityActivity.UnifiedBase;
import com.rt.zgloan.adapter.CreditCardAdapter;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.CreditCardHomeBean;
import com.rt.zgloan.bean.CreditCardHomeListBean;
import com.rt.zgloan.bean.CreditCardListBean;
import com.rt.zgloan.globe.Constant;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.GlideImageLoader;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Observable;

import static android.app.Activity.RESULT_OK;


/**
 * 信用卡模块
 * Created by Administrator on 2017/8/24.
 */

public class FragmentCreditCard extends BaseFragment<CreditCardHomeBean> implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private static final String TAG = "FragmentCreditCard";

    @BindView(R.id.no_record)
    LinearLayout noRecord;
    @BindView(R.id.linear_no_net)
    LinearLayout linearNoNet;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.banner)
    Banner bannerGuideContent;
    @BindView(R.id.pull)
    AbPullToRefreshView pull;
    private CreditCardAdapter adapter;
    private List<CreditCardHomeBean.CreditBannerBean> mActivityListBean = new ArrayList<>();
    private List list = new ArrayList();
    private int cityId;//被选中的城市ID
    private int pageNo = 1;
    private int pageSize = 5;
    private int totalPages;
    private boolean isRefresh = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_credit_card;
    }

    @Override
    public Observable<BaseResponse<CreditCardHomeBean>> initObservable() {
        cityId = SpUtil.getInt(SpUtil.CITY_ID);
        if (cityId > 0) {
            mapParams.put("cityId", cityId + "");
        }
        return HttpManager.getApi().getCreditCardHome(mapParams);
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
        mTitle.setTitle("信用卡", R.mipmap.credit_icon_local, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(UnifiedActivity.class, 100);
            }
        });

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        AbRefreshUtil.initRefresh(pull, this, this);
        adapter = new CreditCardAdapter(mContext, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

    /**
     * 设置banner
     *
     * @param slideList
     */
    private void initBanner(List<CreditCardHomeBean.CreditBannerBean> slideList) {
        if (AbStringUtil.isListEmpty(slideList)) {
            if (mActivityListBean.size() > 0) {
                mActivityListBean.clear();
            }
            mActivityListBean.addAll(slideList);
            bannerGuideContent.setVisibility(View.VISIBLE);
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
                    CreditCardHomeBean.CreditBannerBean bannerBean = mActivityListBean.get(position);
                    if (!AbStringUtil.isEmpty(bannerBean.slideUrl)) {
                        WebViewActivity.startActivity(mContext, bannerBean.slideUrl);
                    }
                }
            });
            //banner设置方法全部调用完毕时最后调用
            bannerGuideContent.start();
        }
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            UnifiedBase base = (UnifiedBase) data.getExtras().get("UnifiedBase");
            cityId = base.id;
            getCreditCardList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showErrorMsg(String msg, String type) {
        AbRefreshUtil.hintView(adapter, pull, true, linearNoNet, noRecord);
        ToastUtil.showToast(msg);
    }

    @Override
    public void recordSuccess(CreditCardHomeBean cardBean) {
        setCreditCardBean(cardBean);
        AbRefreshUtil.hintView(adapter, pull, false, linearNoNet, noRecord);
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        isRefresh = true;
        pageNo = 1;
        getCreditCardList();
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        isRefresh = false;
        if (AbRefreshUtil.isLoad(pageNo, totalPages, pull)) {
            getHotCreditCardList();
        } else {
            ToastUtil.showToast(Constant.LOADED);
        }
    }

    //获取所有列表数据
    private void getCreditCardList() {
        if (cityId > 0) {
            mapParams.put("cityId", cityId + "");
        }
        mPresenter.toSubscribe(HttpManager.getApi().getCreditCardHome(mapParams), new HttpSubscriber<CreditCardHomeBean>() {
                    @Override
                    protected void _onStart() {
                    }

                    @Override
                    protected void _onNext(CreditCardHomeBean cardBean) {
                        setCreditCardBean(cardBean);
                        AbRefreshUtil.hintView(adapter, pull, false, linearNoNet, noRecord);
                    }

                    @Override
                    protected void _onError(String message) {
                        AbRefreshUtil.hintView(adapter, pull, true, linearNoNet, noRecord);
                        ToastUtil.showToast(message);
                    }

                    @Override
                    protected void _onCompleted() {

                    }
                }
        );
    }

    public void setCreditCardBean(CreditCardHomeBean creditCardBean) {
        if (list.size() > 0) {
            list.clear();
        }
        initBanner(creditCardBean.getSlideList());
        if (AbStringUtil.isListEmpty(creditCardBean.getRecomList())) {
            CreditCardHomeListBean cardBean = new CreditCardHomeListBean(CreditCardAdapter.TYPE_TYPE1, creditCardBean.getRecomList());
            list.add(cardBean);
        }
        if (AbStringUtil.isListEmpty(creditCardBean.getBankList())) {
            List<CreditCardHomeBean.BankBean> bankList = creditCardBean.getBankList();
            bankList.add(new CreditCardHomeBean.BankBean());
            CreditCardHomeListBean cardBean = new CreditCardHomeListBean(CreditCardAdapter.TYPE_TYPE2, creditCardBean.getBankList());
            list.add(cardBean);
        }
        if (AbStringUtil.isListEmpty(creditCardBean.getPurposeList())) {
            CreditCardHomeListBean cardBean = new CreditCardHomeListBean(CreditCardAdapter.TYPE_TYPE3, creditCardBean.getPurposeList());
            list.add(cardBean);
        }
        if (AbStringUtil.isListEmpty(creditCardBean.getSubjectList())) {
            CreditCardHomeListBean cardBean = new CreditCardHomeListBean(CreditCardAdapter.TYPE_TYPE4, creditCardBean.getSubjectList());
            list.add(cardBean);
        }
        if (AbStringUtil.isListEmpty(creditCardBean.getHotCardList())) {
            CreditCardHomeListBean cardBean = new CreditCardHomeListBean(CreditCardAdapter.TYPE_TYPE5, creditCardBean.getHotCardList());
            if (creditCardBean.getHotCardList().size() == pageSize) {
                totalPages = 2;
            }
            list.add(cardBean);
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * 获取热门信用卡的数据
     */
    private void getHotCreditCardList() {
        Map<String, String> mapParams = new HashMap<>();
        if (cityId > 0) {
            mapParams.put("cityId", cityId + "");
        }
        if (isRefresh) {
            pageNo = 1;
        } else {
            pageNo++;
        }
        mapParams.put("pageNo", pageNo + "");
        mapParams.put("pageSize", pageSize + "");
        mPresenter.toSubscribe(HttpManager.getApi().getHotCreditCardList(mapParams), new HttpSubscriber<CreditCardListBean>() {
                    @Override
                    protected void _onStart() {
                    }

                    @Override
                    protected void _onNext(CreditCardListBean cardBean) {
                        setHotCreditCardBean(cardBean);
                        totalPages = cardBean.getTotalPages();
                        pull.onFooterLoadFinish();
                    }

                    @Override
                    protected void _onError(String message) {
                        pageNo--;
                        pull.onFooterLoadFinish();
                        ToastUtil.showToast(message);
                    }

                    @Override
                    protected void _onCompleted() {

                    }
                }
        );
    }

    /**
     * 设置上拉加载的条目
     *
     * @param creditCardBean
     */
    public void setHotCreditCardBean(CreditCardListBean creditCardBean) {
        if (AbStringUtil.isListEmpty(creditCardBean.getClassify())) {
            CreditCardHomeListBean info = (CreditCardHomeListBean) list.get(list.size() - 1);
            if (info.getType() == CreditCardAdapter.TYPE_TYPE5) {
                //如果已经存在热门推荐列表直接添加到列表中
                info.getList().addAll(creditCardBean.getClassify());
            } else {
                CreditCardHomeListBean cardBean = new CreditCardHomeListBean(CreditCardAdapter.TYPE_TYPE5, creditCardBean.getClassify());
                list.add(cardBean);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
