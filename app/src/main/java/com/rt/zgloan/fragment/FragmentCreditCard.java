package com.rt.zgloan.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.WebViewActivity;
import com.rt.zgloan.adapter.CreditCardAdapter;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BannerListBean;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.CreditCardBean;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;

/**
 * 信用卡模块
 * Created by Administrator on 2017/8/24.
 */

public class FragmentCreditCard extends BaseFragment implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private static final String TAG = "FragmentCreditCard";
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.banner)
    Banner bannerGuideContent;
    @BindView(R.id.pull)
    AbPullToRefreshView pull;
    private CreditCardAdapter adapter;
    private List<BannerListBean.BannerBean> mActivityListBean = new ArrayList<>();

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

    private List list = new ArrayList();

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle(false, "信用卡");
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        AbRefreshUtil.initRefresh(pull, this, this);
        for (int i = 1; i < 6; i++) {
            if (i == 1) {
                ArrayList data = new ArrayList();
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                this.list.add(new CreditCardBean(i, data));
            } else if (i == 2) {
                ArrayList data = new ArrayList();
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                this.list.add(new CreditCardBean(i, data));
            } else if (i == 3) {
                ArrayList data = new ArrayList();
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                this.list.add(new CreditCardBean(i, data));
            } else if (i == 4) {
                ArrayList data = new ArrayList();
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                this.list.add(new CreditCardBean(i, data));
            } else if (i == 5) {
                ArrayList data = new ArrayList();
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                data.add("i=" + i);
                this.list.add(new CreditCardBean(i, data));
            }
        }

        adapter = new CreditCardAdapter(mContext, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        initBanner();
    }

    private void initBanner() {
        BannerListBean.BannerBean bannerBean = new BannerListBean.BannerBean();
        bannerBean.setSlide_id("1");
        bannerBean.setSlide_pic("3000");
        bannerBean.setSlide_url("http://rtjt.udaihui.com/data/upload/admin/20171222/5a3cac6ca22a4.png");

        BannerListBean.BannerBean bannerBean1 = new BannerListBean.BannerBean();
        bannerBean1.setSlide_id("2");
        bannerBean1.setSlide_pic("3000");
        bannerBean1.setSlide_url("http://rtjt.udaihui.com/data/upload/admin/20171222/5a3cac6ca22a4.png");

        mActivityListBean.add(bannerBean);
        mActivityListBean.add(bannerBean1);
        if (!AbStringUtil.isListEmpty(mActivityListBean)) {
            return;
        }
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
                BannerListBean.BannerBean bannerBean = mActivityListBean.get(position);
                if (!AbStringUtil.isEmpty(bannerBean.getSlide_url())) {
                    Intent intent = new Intent(mActivity, WebViewActivity.class);
                    intent.putExtra("url", bannerBean.getSlide_url());
                    startActivity(intent);
                }
            }
        });
        //banner设置方法全部调用完毕时最后调用
        bannerGuideContent.start();
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

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        pull.onFooterLoadFinish();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        pull.onHeaderRefreshFinish();
    }
}
