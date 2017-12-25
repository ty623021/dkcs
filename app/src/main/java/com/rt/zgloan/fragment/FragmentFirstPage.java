package com.rt.zgloan.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.rt.zgloan.R;
import com.rt.zgloan.activity.MainActivity;
import com.rt.zgloan.activity.WebViewActivity;
import com.rt.zgloan.adapter.FragmentHomeListAdapter;
import com.rt.zgloan.adapter.LabelLoansAdapter;
import com.rt.zgloan.adapter.ProductCategoryAdapter;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.ArticleInfo;
import com.rt.zgloan.bean.BannerItemInfo;
import com.rt.zgloan.bean.BannerListBean;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.CommentListBean;
import com.rt.zgloan.bean.HomeListInfo;
import com.rt.zgloan.bean.LabelListBean;
import com.rt.zgloan.bean.LabelLoansListBean;
import com.rt.zgloan.bean.LoanClassListBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;
import com.rt.zgloan.recyclerview.DividerItemDecoration;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.viewPager.CycleViewPager;
import com.rt.zgloan.weight.LoadingFragment;
import com.rt.zgloan.weight.RollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
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

    private List<BannerItemInfo> banner_list;
    private List<ArticleInfo> article_list;
    private CycleViewPager cycleViewPager;
    // 轮播圆点
    private List<ImageView> views = new ArrayList<>();
    private FragmentActivity activity;
    //公告当前位置
    private int position = 1;
    private Timer timer;
    private boolean isTimer = true;
    //公告轮播间隔时间
    private static final int LONGTIME = 5000;
    //公告轮播条数
    private static final int NOTICE_NUM = 3;
    private FragmentHomeListAdapter adapter;
    private List<HomeListInfo> home_list = new ArrayList<>();
    private List<BannerListBean.BannerBean> mActivityListBean = new ArrayList<>();
    private List<LoanClassListBean.LoanClassBean> mListLoanClass;
    private List<LabelListBean.LabelBean> mListLabel = new ArrayList<>();
    private List<LabelLoansListBean> mListLabelLoansList;

    private ProductCategoryAdapter mProductCategoryAdapter;//产品分类
    private LabelLoansAdapter mLabelLoansAdapter;//标签和对应产品


    @Override
    public int getLayoutId() {
        return R.layout.fragment_first_page;
    }

    @Override
    public Observable<BaseResponse<BannerListBean>> initObservable() {

        return HttpManager.getApi().banner(new HashMap<String, String>());
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle(false, "汇生财");
        activity = getActivity();
        pull.clearFooter();


        AbRefreshUtil.initRefresh(pull, this);


        getMessageList();
        getLoanClassList();
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
        Logger.e("bannerListBean.getBanner().size()=" + bannerListBean.getBanner().size());
        if (bannerListBean.getBanner() != null && bannerListBean.getBanner().size() > 0) {
//            setHomeBanner(bannerListBean.getBanner());

            bannerGuideContent.setVisibility(View.VISIBLE);
            Logger.e("bannerSuccess--->>>");

            mActivityListBean = bannerListBean.getBanner();


            //设置banner样式
            bannerGuideContent.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置不显示指示器   BannerConfig.CIRCLE_INDICATOR//圆点指示器
            //设置图片加载器
            bannerGuideContent.setImageLoader(new GlideImageLoader());
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

//                    HomeIndexResponseBean.IndexImagesBean dto = mActivityListBean.get(position);
//                    //jumpType 1 不跳转 2跳转成分类 3跳转页面 productType 分类ID pageLink页面链接
//                    Intent intent = new Intent(mActivity, WebViewActivity.class);
//                    intent.putExtra("url", dto.getReUrl());
//                    startActivity(intent);
//                    Intent intent = new Intent(mActivity, WebViewActivity.class);
//                    intent.putExtra("url", bannerListBean.getBanner().get(position).getSlide_url());
//                    startActivity(intent);

                }
            });
            //banner设置方法全部调用完毕时最后调用
            bannerGuideContent.start();
        } else {
            bannerGuideContent.setVisibility(View.GONE);
            Logger.e("banner--->>>");
        }
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            BannerListBean.BannerBean dto = (BannerListBean.BannerBean) path;
            Glide.with(mContext).load(dto.getSlide_pic())
                    .placeholder(R.mipmap.banner)
                    .error(R.mipmap.banner)
                    .centerCrop()
                    .into(imageView); //设置图片
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            ImageView simpleDraweeView = new ImageView(context);

            return simpleDraweeView;
        }
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(BannerItemInfo info, int position, View imageView) {
            if (cycleViewPager != null && cycleViewPager.isCycle()) {
                if (!AbStringUtil.isEmpty(info.getUrl())) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", info.getUrl());
                    mActivity.startActivity(WebViewActivity.class, bundle);
                }
            }
        }

    };


    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        mPresenter.toSubscribe(
                HttpManager.getApi().banner(mapParams), new HttpSubscriber<BannerListBean>() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在刷新...");
                    }

                    @Override
                    protected void _onNext(BannerListBean bannerListBean) {
                        getMessageList();
                        getLoanClassList();
                        getLabelList();
                        pull.onHeaderRefreshFinish();

                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(message);
                        pull.onHeaderRefreshFinish();

                    }

                    @Override
                    protected void _onCompleted() {
                        LoadingFragment.getInstends().dismiss();
                        pull.onHeaderRefreshFinish();

                    }
                }
        );
    }

    //获取消息内容
    private void getMessageList() {
        Logger.e("SUCCESS");
        mPresenter.toSubscribe(
                HttpManager.getApi().getMessageList(mapParams), new HttpSubscriber<CommentListBean>() {
                    @Override
                    protected void _onStart() {
                    }

                    @Override
                    protected void _onNext(CommentListBean commentListBean) {
                        Logger.e("commentListBean.getCommentBeans().size()=" + commentListBean.getCommentBeans().size());
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
                        ToastUtil.showToast(message);

                    }

                    @Override
                    protected void _onCompleted() {

                    }
                }
        );
    }

    //获取产品分类
    private void getLoanClassList() {
        Logger.e("SUCCESS");
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
                        ToastUtil.showToast(message);

                    }

                    @Override
                    protected void _onCompleted() {

                    }
                }
        );
    }

    //获取产品标签及产品
    private void getLabelList() {
        mPresenter.toSubscribe(
                HttpManager.getApi().getLabelList(mapParams), new HttpSubscriber<LabelListBean>() {
                    @Override
                    protected void _onStart() {

                    }

                    @Override
                    protected void _onNext(LabelListBean labelListBean) {
                        mListLabel = labelListBean.getLabel();
                        setProduct(mListLabel);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(message);
                    }

                    @Override
                    protected void _onCompleted() {

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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }
}

