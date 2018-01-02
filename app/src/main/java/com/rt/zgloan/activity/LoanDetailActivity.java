package com.rt.zgloan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.rt.zgloan.R;
import com.rt.zgloan.adapter.ActivityLoanDetailAdapter;
import com.rt.zgloan.adapter.ApplicationProcedureAdapter;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.ApplyMaterialsInfo;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.LoanDetailBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.pullView.AbPullToRefreshView;
import com.rt.zgloan.recyclerview.SpaceItemDecoration;
import com.rt.zgloan.util.AbMathUtil;
import com.rt.zgloan.util.AbRefreshUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.StringUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.LoadingFragment;
import com.rt.zgloan.weight.MyShareSdk;
import com.rt.zgloan.weight.RadiusView;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 贷款详情
 * Created by hjy on 2017/8/24.
 */

public class LoanDetailActivity extends BaseActivity<LoanDetailBean> implements AbPullToRefreshView.OnHeaderRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.pull)
    AbPullToRefreshView pull;
    @BindView(R.id.rel_choose_loan_amount)
    RelativeLayout rel_choose_loan_amount;
    @BindView(R.id.main)
    View main;
    @BindView(R.id.rel_loan_range)
    RelativeLayout rel_loan_range;
    @BindView(R.id.tv_fast_time)
    TextView tvFastTime;
    @BindView(R.id.tv_large_loan_time)
    TextView tvLargeLoanTime;
    @BindView(R.id.rel_loan_tag1)
    RelativeLayout relLoanTag1;
    @BindView(R.id.rel_loan_tag2)
    RelativeLayout relLoanTag2;
    @BindView(R.id.rel_loan_tag3)
    RelativeLayout relLoanTag3;
    @BindView(R.id.tv_loan)
    TextView tvLoan;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_everday_still)
    TextView tvEverdayStill;
    @BindView(R.id.tv_reference_day_rate)
    TextView tvReferenceDayRate;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_loan_tag1)
    TextView tvLoanTag1;
    @BindView(R.id.tv_loan_tag2)
    TextView tvLoanTag2;
    @BindView(R.id.tv_loan_tag3)
    TextView tvLoanTag3;
    @BindView(R.id.tv_loan_money_range)
    TextView tv_loan_money_range;
    @BindView(R.id.tv_loan_time_range)
    TextView tv_loan_time_range;
    @BindView(R.id.rating_mission)
    RatingBar rating_mission;
    @BindView(R.id.everday_still)
    TextView everday_still;
    @BindView(R.id.tv_reference_rate)
    TextView tv_reference_rate;
    @BindView(R.id.loan_img)
    ImageView loan_img;
    @BindView(R.id.money_range)
    TextView money_range;
    @BindView(R.id.radiusView1)
    RadiusView radiusView1;
    @BindView(R.id.radiusView2)
    RadiusView radiusView2;
    @BindView(R.id.radiusView3)
    RadiusView radiusView3;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.tv_title)
    TextView mTvTitle;//产品名称
    @BindView(R.id.tv_loan_number)
    TextView mTvLoanNumber;//借款人数
    @BindView(R.id.tv_deadline_sml)
    TextView mTvDeadlineSml;//最小借款期限
    @BindView(R.id.tv_deadline_big)
    TextView mTvDeadlineBig;//最大借款期限
    @BindView(R.id.tv_money_sml)
    TextView mTvMoneySml;//最小借款金额
    @BindView(R.id.tv_money_big)
    TextView mTvMoneyBig;//最大借款金额
    @BindView(R.id.Ll_application_procedure)
    LinearLayout mLlApplicationProcedure;
    @BindView(R.id.tv_application_procedure)
    TextView mTvApplicationProcedure;//申请条件
    @BindView(R.id.Ll_product_description)
    LinearLayout mLlProductDescription;
    @BindView(R.id.tv_product_description)
    TextView mTvProductDescription;
    @BindView(R.id.Ll_repayment_instructions)
    LinearLayout mLlRepaymentInstructions;
    @BindView(R.id.tv_repayment_instructions)
    TextView mTvRepaymentInstructions;
    @BindView(R.id.Ll_loan_procedure)
    LinearLayout mLlLoanProcedure;
    @BindView(R.id.rv_loan_procedure)
    RecyclerView mRvLoanProcedure;//申请流程
    @BindView(R.id.iv_product)
    ImageView mIvProduct;//产品图片
    @BindView(R.id.tv_loan_money)
    TextView mTvLoanMoney;//借款金额
    @BindView(R.id.tv_loan_day)
    TextView mTvLoanDay;//借款天数
    @BindView(R.id.Ll_loan_money)
    LinearLayout mLlLoanMoney;
    @BindView(R.id.tv_day_repayment)
    TextView mTvDayRepayment;
    @BindView(R.id.tv_day_rate)
    TextView mTvDayRate;
    @BindView(R.id.tv_gross_interest)
    TextView mTvGrossInterest;//总利息金额
    @BindView(R.id.tv_day_repayment_money)
    TextView mTvDayRepaymentMoney;//单位还款金额
    @BindView(R.id.tv_day_rate_value)
    TextView mTvDayRateValue;//利息
    @BindView(R.id.Ll_loan_day)
    LinearLayout mLlLoanDay;
    @BindView(R.id.tv_day)
    TextView mTvDay;

    private List<ApplyMaterialsInfo> applyMaterialsInfo = new ArrayList<>();
    private ActivityLoanDetailAdapter adapter;
    private PopupWindow window;
    private int id;//标的id
    private String money_sml;//最小金额
    private String money_big;//最大金额
    private String amountSelector;//选择金额的目标值
    private String timeSelector;//选择时间的目标值
    private double loan_interest;//计算的总利息

    private View view;
    private LinearLayout wechat_friends, wechat_circle, qq_friends, sinaweibo_friends, qq_zone;
    private MyShareSdk showShare;
    private String apply_immediately_Url;
    private String name;

    private ApplicationProcedureAdapter mAdapter;
    private String risingRange;//借款差额
    private String[] deadline;//关于期限的数组
    private String[] rate;//关于借款日利率的数组
    private String rateType;//1是天，2是月 ，3是年
    private String rateReality;//实际利率
    private String produceId;
    private String urlLoan;

    @Override
    public Observable<BaseResponse<LoanDetailBean>> initObservable() {
        mapParams2.put("id", id);
        return HttpManager.getApi().getLoansDetail(mapParams2);
    }


    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, LoanDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }


    @OnClick({R.id.rel_choose_loan_amount, R.id.rel_loan_range, R.id.btn_apply_immediately, R.id.iv_back,
            R.id.iv_share, R.id.Ll_loan_money, R.id.Ll_loan_day})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                showSharePopWindow();
                break;
            case R.id.rel_choose_loan_amount:
                showAmountRangePopWindows();
                break;

            case R.id.rel_loan_range:
                showTimeRangePopWindows();
                break;
            case R.id.btn_apply_immediately:
                if (SpUtil.getBoolean(SpUtil.isLogin)) {
                    appleHttp();
                } else {
                    this.startActivity(LoginActivity.class);
                }
                break;
            case R.id.Ll_loan_money:
                showAmountRangePopWindows();//金额选择器
                break;
            case R.id.Ll_loan_day:
                showTimeRangePopWindows();//期限选择器
                break;

        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_loan_detail;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        id = getIntent().getIntExtra("id", 0);
        Logger.e("id---" + id);
        adapter = new ActivityLoanDetailAdapter(applyMaterialsInfo);
        recyclerView.setAdapter(adapter);
        AbRefreshUtil.initRefresh(pull, this);

    }

    //设置数据
    private void setValue(LoanDetailBean loanDetailBean) {
        Glide.with(mContext)
                .load(loanDetailBean.getImage_url())
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.icon_default)
                .centerCrop()
                .into(mIvProduct);//设置图片
        mTvTitle.setText(loanDetailBean.getName() + "");
        mTvDeadlineSml.setText(loanDetailBean.getDeadline_sml() + "");
        mTvDeadlineBig.setText(loanDetailBean.getDeadline_big() + "");
        mTvMoneySml.setText(loanDetailBean.getMoney_sml() + "");
        mTvMoneyBig.setText(loanDetailBean.getMoney_big() + "");
        produceId = loanDetailBean.getId();
        urlLoan = loanDetailBean.getJump_url();

        deadline = loanDetailBean.getDeadline();
        risingRange = loanDetailBean.getRising_range();
        if (!StringUtil.isBlank(loanDetailBean.getLoan_number())) {//设置借款人数
            mTvLoanNumber.setText(loanDetailBean.getLoan_number() + "");
        }

        if (!StringUtil.isBlank(loanDetailBean.getMoney_big())) {//设置借款额度
            mTvLoanMoney.setText(loanDetailBean.getMoney_big() + "元");
        }

        money_sml = loanDetailBean.getMoney_sml();
        money_big = loanDetailBean.getMoney_big();

        amountSelector = loanDetailBean.getMoney_big();
        timeSelector = loanDetailBean.getDeadline_big();


        if (loanDetailBean.getRate_type() != null) {
            if (loanDetailBean.getRate_type().equals("1")) {
                mTvDayRepayment.setText("每日应还(元)");
                mTvDayRate.setText("日利率");
                mTvDay.setText("天");
                if (!StringUtil.isBlank(loanDetailBean.getDeadline_big())) {//设置借款期限
                    mTvLoanDay.setText(loanDetailBean.getDeadline_big() + "天");
                }
            } else if (loanDetailBean.getRate_type().equals("2")) {
                mTvDayRepayment.setText("每月应还(元)");
                mTvDayRate.setText("月利率");
                mTvDay.setText("月");
                if (!StringUtil.isBlank(loanDetailBean.getDeadline_big())) {//设置借款期限
                    mTvLoanDay.setText(loanDetailBean.getDeadline_big() + "个月");
                }
            } else {
                mTvDayRepayment.setText("每月应还(元)");
                mTvDayRate.setText("年利率");
                mTvDay.setText("年");
                if (!StringUtil.isBlank(loanDetailBean.getDeadline_big())) {//设置借款期限
                    mTvLoanDay.setText(loanDetailBean.getDeadline_big() + "年");
                }
            }
            rateType = loanDetailBean.getRate_type();
        }
        if (loanDetailBean.getRate() != null) {
            rate = loanDetailBean.getRate();
            mTvDayRateValue.setText(rate[0] + "%");
            rateReality = rate[0];
        }
        if (!StringUtil.isBlank(amountSelector) && !StringUtil.isBlank(timeSelector)) {
            if (rateType.equals("3")) {
                double amount = Double.valueOf(amountSelector);
                double time = Double.valueOf(deadline[0]);
                double ra = Double.valueOf(rateReality);
                loan_interest = amount * (ra / 100) * time;
                String repay_money = AbMathUtil.roundStr((amount + loan_interest) / time / 12, 2);
                String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                mTvGrossInterest.setText(repay_interest + "");
                mTvDayRepaymentMoney.setText(repay_money + "");
            } else {
                double amount = Double.valueOf(amountSelector);
                double time = Double.valueOf(deadline[0]);
                double ra = Double.valueOf(rateReality);
                loan_interest = amount * (ra / 100) * time;
                String repay_money = AbMathUtil.roundStr((amount + loan_interest) / time, 2);
                String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                mTvGrossInterest.setText(repay_interest + "");
                mTvDayRepaymentMoney.setText(repay_money + "");
            }
        }
        if (loanDetailBean.getApplication_procedure() != null) {
            mTvApplicationProcedure.setText(Html.fromHtml(loanDetailBean.getApplication_procedure()));
//            mTvApplicationProcedure.setText(loanDetailBean.getApplication_procedure().replace(" ", "\n"));
        } else {
            mLlApplicationProcedure.setVisibility(View.GONE);
        }
        if (loanDetailBean.getProduct_description() != null) {
            mTvProductDescription.setText(Html.fromHtml(loanDetailBean.getProduct_description()));
//            mTvProductDescription.setText(loanDetailBean.getProduct_description().replace(" ", "\n"));
        } else {
            mLlProductDescription.setVisibility(View.GONE);
        }
        if (loanDetailBean.getRepayment_instructions() != null) {
            mTvRepaymentInstructions.setText(Html.fromHtml(loanDetailBean.getRepayment_instructions()));
//            mTvRepaymentInstructions.setText(loanDetailBean.getRepayment_instructions().replace(" ", "\n"));
        } else {
            mLlRepaymentInstructions.setVisibility(View.GONE);
        }
        if (loanDetailBean.getMaterial() != null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
            mRvLoanProcedure.setLayoutManager(gridLayoutManager);
            mRvLoanProcedure.addItemDecoration(new SpaceItemDecoration(0, 10, 0, 0));
            mAdapter = new ApplicationProcedureAdapter(this);
            mAdapter.clearData();
            mAdapter.addData(loanDetailBean.getMaterial());
            mRvLoanProcedure.setAdapter(mAdapter);
        } else {
            mLlLoanProcedure.setVisibility(View.GONE);
        }


    }

    /**
     * 显示选择金额的pop
     */
    private void showAmountRangePopWindows() {
        View view = View.inflate(this, R.layout.choose_range_popwindows, null);
        RelativeLayout rel_btn_cancel = (RelativeLayout) view.findViewById(R.id.rel_btn_cancel);
        RelativeLayout rel_btn_sure = (RelativeLayout) view.findViewById(R.id.rel_btn_sure);
        final WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);

        wheelview.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        wheelview.setSkin(WheelView.Skin.Holo); // common皮肤
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#FFFFFF");
        style.holoBorderColor = Color.parseColor("#DCDBDA");   // holo样式边框颜色 红色
        style.textColor = Color.argb(255, 125, 125, 125); // 文本颜色 灰色
        style.selectedTextColor = Color.parseColor("#ff4a4a"); // 选中文本颜色 黑色
        style.textSize = 14;// 文本大小
        style.selectedTextSize = 16;   // 选中文本大小
        style.textAlpha = 0.5f;  // 文本透明度(0f ~ 1f)
        wheelview.setStyle(style);
        wheelview.setSelection(0);
        wheelview.setWheelSize(5);

        List<String> list = new ArrayList<>();
        int sml = Integer.parseInt(money_sml);
        int big = Integer.parseInt(money_big);
        int rise = Integer.parseInt(risingRange);
        for (int i = 0; i < (big / rise); i++) {
            if (i == 0) {
                list.add(big + "元");
            } else {
                list.add((big - rise * i) + "元");
            }

        }
        wheelview.setWheelData(list);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        rel_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
                amountSelector = AbStringUtil.getNumbers(wheelview.getSelectionItem().toString());
                mTvLoanMoney.setText(amountSelector + "");

                if (!StringUtil.isBlank(amountSelector) && StringUtil.isBlank(timeSelector)) {
                    if (rateType.equals("3")) {
                        double amount = Double.valueOf(amountSelector);
                        double time = Double.valueOf(deadline[0]);
                        double ra = Double.valueOf(rate[0]);
                        loan_interest = amount * (ra / 100) * time;
                        String repay_money = AbMathUtil.roundStr((amount + loan_interest) / time / 12, 2);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    } else {
                        double amount = Double.valueOf(amountSelector);
                        double time = Double.valueOf(deadline[0]);
                        double ra = Double.valueOf(rate[0]);
                        loan_interest = amount * (ra / 100) * time;
                        String repay_money = AbMathUtil.roundStr((amount + loan_interest) / time, 2);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    }

                } else if (!StringUtil.isBlank(amountSelector) && !StringUtil.isBlank(timeSelector)) {
                    if (rateType.equals("3")) {
                        double amount = Double.valueOf(amountSelector);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(rateReality);
                        loan_interest = amount * (ra / 100) * time;
                        String repay_money = AbMathUtil.roundStr((amount + loan_interest) / time / 12, 2);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    } else {
                        double amount = Double.valueOf(amountSelector);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(rateReality);
                        loan_interest = amount * (ra / 100) * time;
                        String repay_money = AbMathUtil.roundStr((amount + loan_interest) / time, 2);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    }

                } else if (StringUtil.isBlank(amountSelector) && !StringUtil.isBlank(timeSelector)) {
                    if (rateType.equals("3")) {
                        String reference_rate1 = tv_reference_rate.getText().toString().trim();
                        double amount = Double.valueOf(money_sml);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(reference_rate1);
                        loan_interest = amount * (ra / 100) * time;
                        String repay_money = AbMathUtil.roundStr((amount + loan_interest) / time / 12, 2);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    } else {
                        String reference_rate1 = tv_reference_rate.getText().toString().trim();
                        double amount = Double.valueOf(money_sml);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(reference_rate1);
                        loan_interest = amount * (ra / 100) * time;
                        String repay_money = AbMathUtil.roundStr((amount + loan_interest) / time, 2);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    }

                }


            }
        });
        //让pop覆盖在输入法上面
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //让pop自适应输入状态
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
//        //获取点击View的坐标
//        int[] location = new int[2];
//        mLlLoanMoney.getLocationOnScreen(location);
//        window.showAsDropDown(mLlLoanMoney);//在v的下面
//        //显示在下方
//        window.showAtLocation(mLlLoanMoney, Gravity.NO_GRAVITY, location[0] + mLlLoanMoney.getWidth(), location[1]);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);

        rel_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(LoanDetailActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(LoanDetailActivity.this, 0.5f);

    }

    /**
     * 显示选择时间的pop
     */
    private void showTimeRangePopWindows() {
        View view = View.inflate(this, R.layout.choose_range_popwindows, null);
        RelativeLayout rel_btn_cancel = (RelativeLayout) view.findViewById(R.id.rel_btn_cancel);
        RelativeLayout rel_btn_sure = (RelativeLayout) view.findViewById(R.id.rel_btn_sure);
        final WheelView wheelview = (WheelView) view.findViewById(R.id.wheelview);


        wheelview.setWheelAdapter(new ArrayWheelAdapter(this)); // 文本数据源
        wheelview.setSkin(WheelView.Skin.Holo); // common皮肤
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#FFFFFF");
        style.holoBorderColor = Color.parseColor("#DCDBDA");   // holo样式边框颜色 红色
        style.textColor = Color.argb(255, 125, 125, 125); // 文本颜色 灰色
        style.selectedTextColor = Color.parseColor("#ff4a4a"); // 选中文本颜色 黑色
        style.textSize = 14;// 文本大小
        style.selectedTextSize = 16;   // 选中文本大小
        style.textAlpha = 0.5f;  // 文本透明度(0f ~ 1f)
        wheelview.setStyle(style);
        wheelview.setSelection(0);
        wheelview.setWheelSize(5);

        List<String> list = new ArrayList<>();
        int sml = Integer.parseInt(deadline[0]);
//        int big = Integer.parseInt(deadline_big);
        if (rateType.equals("1")) {
            if (deadline.length == 1) {
                list.add(deadline[0] + "天");
            } else {
                for (int i = 0; i <= deadline.length - 1; i++) {
                    list.add(deadline[i] + "天");
                }
            }
        } else if (rateType.equals("2")) {
            if (deadline.length == 1) {
                list.add(deadline[0] + "个月");
            } else {
                for (int i = 0; i <= deadline.length - 1; i++) {
                    list.add(deadline[i] + "个月");
                }
            }
        } else if (rateType.equals("3")) {
            if (deadline.length == 1) {
                list.add(deadline[0] + "年");
            } else {
                for (int i = 0; i <= deadline.length - 1; i++) {
                    list.add(deadline[i] + "年");
                }
            }
        }

        wheelview.setWheelData(list);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        rel_btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
                timeSelector = AbStringUtil.getNumbers(wheelview.getSelectionItem().toString());
                if (!StringUtil.isBlank(timeSelector) && !StringUtil.isBlank(amountSelector)) {
                    if (rateType.equals("1")) {
                        mTvLoanDay.setText(timeSelector + "天");
                        mTvDayRateValue.setText(rate[wheelview.getCurrentPosition()] + "%");
                        rateReality = rate[wheelview.getCurrentPosition()];
                        double amount = Double.valueOf(amountSelector);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(rateReality);
                        loan_interest = amount * (ra / 100) * time;//利息
                        double finalMoney = (amount + loan_interest) / time;
                        DecimalFormat formater = new DecimalFormat();
                        formater.setMaximumFractionDigits(2);
                        formater.setGroupingSize(0);
                        formater.setRoundingMode(RoundingMode.FLOOR);
                        String repay_money = formater.format(finalMoney);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    } else if (rateType.equals("3")) {
                        mTvLoanDay.setText(timeSelector + "年");
                        mTvDayRateValue.setText(rate[wheelview.getCurrentPosition()] + "%");
                        rateReality = rate[wheelview.getCurrentPosition()];
                        double amount = Double.valueOf(amountSelector);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(rateReality);
                        loan_interest = amount * (ra / 100) * time;//利息
                        double finalMoney = (amount + loan_interest) / time / 12;
                        DecimalFormat formater = new DecimalFormat();
                        formater.setMaximumFractionDigits(2);
                        formater.setGroupingSize(0);
                        formater.setRoundingMode(RoundingMode.FLOOR);
                        String repay_money = formater.format(finalMoney);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    } else if (rateType.equals("2")) {
                        mTvLoanDay.setText(timeSelector + "个月");
                        mTvDayRateValue.setText(rate[wheelview.getCurrentPosition()] + "%");
                        rateReality = rate[wheelview.getCurrentPosition()];
                        double amount = Double.valueOf(amountSelector);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(rateReality);
                        loan_interest = amount * (ra / 100) * time;//利息
                        double finalMoney = (amount + loan_interest) / time;
                        DecimalFormat formater = new DecimalFormat();
                        formater.setMaximumFractionDigits(2);
                        formater.setGroupingSize(0);
                        formater.setRoundingMode(RoundingMode.FLOOR);
                        String repay_money = formater.format(finalMoney);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    }


                } else if (!StringUtil.isBlank(timeSelector) && StringUtil.isBlank(amountSelector)) {
                    if (rateType.equals("1")) {
                        mTvLoanDay.setText(timeSelector + "天");
                        mTvDayRateValue.setText(rate[wheelview.getCurrentPosition()] + "%");
                        rateReality = rate[wheelview.getCurrentPosition()];
                        double amount = Double.valueOf(money_sml);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(rateReality);
                        loan_interest = amount * (ra / 100) * time;//利息
                        double finalMoney = (amount + loan_interest) / time;
                        DecimalFormat formater = new DecimalFormat();
                        formater.setMaximumFractionDigits(2);
                        formater.setGroupingSize(0);
                        formater.setRoundingMode(RoundingMode.FLOOR);
                        String repay_money = formater.format(finalMoney);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    } else if (rateType.equals("3")) {
                        mTvLoanDay.setText(timeSelector + "年");
                        mTvDayRateValue.setText(rate[wheelview.getCurrentPosition()] + "%");
                        rateReality = rate[wheelview.getCurrentPosition()];
                        double amount = Double.valueOf(money_sml);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(rateReality);
                        loan_interest = amount * (ra / 100) * time;//利息
                        double finalMoney = (amount + loan_interest) / time / 12;
                        DecimalFormat formater = new DecimalFormat();
                        formater.setMaximumFractionDigits(2);
                        formater.setGroupingSize(0);
                        formater.setRoundingMode(RoundingMode.FLOOR);
                        String repay_money = formater.format(finalMoney);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    } else if (rateType.equals("2")) {
                        mTvLoanDay.setText(timeSelector + "个月");
                        mTvDayRateValue.setText(rate[wheelview.getCurrentPosition()] + "%");
                        rateReality = rate[wheelview.getCurrentPosition()];
                        double amount = Double.valueOf(money_sml);
                        double time = Double.valueOf(timeSelector);
                        double ra = Double.valueOf(rateReality);
                        loan_interest = amount * (ra / 100) * time;//利息
                        double finalMoney = (amount + loan_interest) / time;
                        DecimalFormat formater = new DecimalFormat();
                        formater.setMaximumFractionDigits(2);
                        formater.setGroupingSize(0);
                        formater.setRoundingMode(RoundingMode.FLOOR);
                        String repay_money = formater.format(finalMoney);
                        String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
                        mTvGrossInterest.setText(repay_interest + "");
                        mTvDayRepaymentMoney.setText(repay_money + "");
                    }


                }


            }
        });
        //让pop覆盖在输入法上面
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //让pop自适应输入状态
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);

        rel_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(LoanDetailActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(LoanDetailActivity.this, 0.5f);

    }

    /**
     * 分享的弹框
     */
    private void showSharePopWindow() {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(this).inflate(R.layout.share_dailog, null);
        wechat_friends = (LinearLayout) view.findViewById(R.id.wechat_friends);
        wechat_circle = (LinearLayout) view.findViewById(R.id.wechat_circle);
        qq_friends = (LinearLayout) view.findViewById(R.id.qq_friends);
        sinaweibo_friends = (LinearLayout) view.findViewById(R.id.sinaweibo_friends);
        qq_zone = (LinearLayout) view.findViewById(R.id.qq_zone);

        wechat_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("Wechat", getString(R.string.share_title_detail) + name, apply_immediately_Url, getString(R.string.share_content_detail));
                }
            }
        });

        wechat_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("WechatMoments", getString(R.string.share_title_detail) + name, apply_immediately_Url, getString(R.string.share_content_detail));
                }
            }
        });
        qq_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("QQ", getString(R.string.share_title_detail) + name, apply_immediately_Url, getString(R.string.share_content_detail));
                }
            }
        });
        sinaweibo_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("SinaWeibo", getString(R.string.share_title_detail) + name, apply_immediately_Url, getString(R.string.share_content_detail));
                }
            }
        });
        qq_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("QZone", getString(R.string.share_title_detail) + name, apply_immediately_Url, getString(R.string.share_content_detail));
                }
            }
        });

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(LoanDetailActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(LoanDetailActivity.this, 0.5f);

    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);

    }

    @Override
    public void recordSuccess(LoanDetailBean loanDetailBean) {
        if (loanDetailBean != null) {
            Logger.e("loanDetailBean=" + loanDetailBean.toString());
            setValue(loanDetailBean);
        }
    }


//    private void setValue(LoanDetailInfo info) {
//        tv_name.setText(info.getName());
//        tvFastTime.setText(info.getFast_time() + "放款");
//        tvLargeLoanTime.setText("最长可贷款" + info.getDeadline_big());
//        if (!StringUtil.isBlank(info.getLoan_tag1()) && !StringUtil.isBlank(info.getLoan_tag1_color())) {
//            tvLoanTag1.setText(info.getLoan_tag1());
//            relLoanTag1.setVisibility(View.VISIBLE);
//            radiusView1.setBackgroundColor(info.getLoan_tag1_color());
//
//        }
//
//        if (!StringUtil.isBlank(info.getLoan_tag2()) && !StringUtil.isBlank(info.getLoan_tag2_color())) {
//            tvLoanTag2.setText(info.getLoan_tag2());
//            relLoanTag2.setVisibility(View.VISIBLE);
//            radiusView2.setBackgroundColor(info.getLoan_tag2_color());
//        } else {
//
//            relLoanTag2.setVisibility(View.GONE);
//
//        }
//        if (!StringUtil.isBlank(info.getLoan_tag3()) && !StringUtil.isBlank(info.getLoan_tag3_color())) {
//            tvLoanTag3.setText(info.getLoan_tag3());
//            relLoanTag3.setVisibility(View.VISIBLE);
//            radiusView3.setBackgroundColor(info.getLoan_tag3_color());
//        } else {
//            relLoanTag3.setVisibility(View.GONE);
//        }
//
//        if (!StringUtil.isBlank(info.getMoney_sml()) && !StringUtil.isBlank(info.getMoney_big())) {
//            tv_loan_money_range.setText("（" + info.getMoney_sml() + "-" + info.getMoney_big() + "元）");
//        }
//        if (!StringUtil.isBlank(info.getMoney_sml()) && !StringUtil.isBlank(info.getMoney_big())) {
//            if (info.getRate_type().equals("1")) {
//                tv_loan_time_range.setText(deadline[0] + "天");
//            } else if (info.getRate_type().equals("2")) {
//                tv_loan_time_range.setText(deadline[0] + "个月");
//            } else if (info.getRate_type().equals("3")) {
//                tv_loan_time_range.setText(deadline[0] + "年");
//            }
//
//        }
//
//        money_sml = info.getMoney_sml();
//        money_big = info.getMoney_big();
//        money_range.setText("(" + money_sml + "-" + money_big + "元)");
//        tv_loan_money_range.setText(money_sml + "元");
//
//        if (info.getRate_type().equals("1") || info.getRate_type().equals("2")) {
//            double amount = Double.valueOf(money_sml);
//            double time = Double.valueOf(deadline[0]);
//            double rate1 = Double.valueOf(rate[0]);
//            loan_interest = amount * (rate1 / 100) * time;//利息
//            double finalMoney = (amount + loan_interest) / time;
//            DecimalFormat formater = new DecimalFormat();
//            formater.setMaximumFractionDigits(2);
//            formater.setGroupingSize(0);
//            formater.setRoundingMode(RoundingMode.FLOOR);
//            String repay_money = formater.format(finalMoney);
//            String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
//            tvEverdayStill.setText(AbMathUtil.roundStr(repay_money, 2) + "");
//            tvRate.setText(AbMathUtil.roundStr(repay_interest, 2) + "");
//            tv_reference_rate.setText(AbMathUtil.roundStr(rate1, 2));
//        } else {
//            double amount = Double.valueOf(money_sml);
//            double time = Double.valueOf(deadline[0]);
//            double rate1 = Double.valueOf(rate[0]);
//            loan_interest = amount * (rate1 / 100) * time;//利息
//            double finalMoney = (amount + loan_interest) / time / 12;
//            DecimalFormat formater = new DecimalFormat();
//            formater.setMaximumFractionDigits(2);
//            formater.setGroupingSize(0);
//            formater.setRoundingMode(RoundingMode.FLOOR);
//            String repay_money = formater.format(finalMoney);
//            String repay_interest = AbMathUtil.roundStr(loan_interest, 2);
//            tvEverdayStill.setText(AbMathUtil.roundStr(repay_money, 2) + "");
//            tvRate.setText(AbMathUtil.roundStr(repay_interest, 2) + "");
//            tv_reference_rate.setText(AbMathUtil.roundStr(rate1, 2));
//        }
//
//
//        rating_mission.setRating(Float.parseFloat(info.getMission()));
//
//        if (info.getRate_type().equals("1")) {
//            everday_still.setText("每日应还（元）");
//            tvReferenceDayRate.setText("参考日利率");
//        } else if (info.getRate_type().equals("2")) {
//            everday_still.setText("每月应还（元）");
//            tvReferenceDayRate.setText("参考月利率");
//        } else if (info.getRate_type().equals("3")) {
//            everday_still.setText("每月应还（元）");
//            tvReferenceDayRate.setText("参考年利率");
//        }
//        glideImgManager.glideLoader(info.getSmeta(), R.mipmap.error_img, R.mipmap.error_img, loan_img, 0);
//
//    }


    private void setHttp() {
        mapParams2.put("id", id);
        mPresenter.toSubscribe(
                HttpManager.getApi().getLoansDetail(mapParams2), new HttpSubscriber<LoanDetailBean>() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在刷新...");
                    }

                    @Override
                    protected void _onNext(LoanDetailBean loanDetailBean) {

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

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        setHttp();
    }

    /**
     * 立即申请的请求
     */
    private void appleHttp() {
        mapParams.put("u_id", SpUtil.getString(SpUtil.userId));
        mapParams.put("p_id", produceId);
//        mapParams.put("l_id", id);

        mPresenter.toSubscribe(
                HttpManager.getApi().saveJump(mapParams), new HttpSubscriber() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在申请...");
                    }

                    @Override
                    protected void _onNext(Object o) {
                        if (!StringUtil.isBlank(urlLoan)) {
                            WebViewActivity.startActivity(mContext,urlLoan);
                        }
                    }


                    @Override
                    protected void _onError(String message) {
                        Log.e("tag", "_onError" + message);
                        ToastUtil.showToast(message);
                    }

                    @Override
                    protected void _onCompleted() {

                        LoadingFragment.getInstends().dismiss();
                    }
                }
        );

    }
}
