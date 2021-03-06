package com.rt.zgloan.activity.creditCardActivity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.LoginActivity;
import com.rt.zgloan.activity.WebViewActivity;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.CreditCardDetailsBean;
import com.rt.zgloan.globe.Constant;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.util.AbImageUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

import static com.rt.zgloan.R.id.tv_name;

/**
 * Created by Administrator on 2017/12/28 0028.
 * 信用卡详情页面
 */

public class CreditCardDetailsActivity extends BaseActivity<CreditCardDetailsBean> {

    @BindView(R.id.layout_height_top)
    RelativeLayout layoutHeightTop;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(tv_name)
    TextView tvName;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_loan_tag1)
    TextView tvLoanTag1;
    @BindView(R.id.tv_loan_tag2)
    TextView tvLoanTag2;
    @BindView(R.id.tv_loan_tag3)
    TextView tvLoanTag3;
    @BindView(R.id.tv_specialPrivilege)
    TextView tvSpecialPrivilege;
    @BindView(R.id.tv_baseInfo)
    TextView tvBaseInfo;
    @BindView(R.id.tv_relateExpense)
    TextView tvRelateExpense;
    @BindView(R.id.btn_apply)
    Button btnApply;

    private String id;
    private CreditCardDetailsBean.CardDetailsBean info;

    @Override
    public Observable<BaseResponse<CreditCardDetailsBean>> initObservable() {
        mapParams.put("id", id);
        return HttpManager.getApi().getCreditCardDetails(mapParams);
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }

    @OnClick(R.id.btn_apply)
    @Override
    public void onClick(View v) {
        if (info == null) {
            ToastUtil.showToast(Constant.GET_DATA_EXCEPTION);
            return;
        }
        if (v.getId() == R.id.btn_apply) {
            if (SpUtil.getBoolean(SpUtil.isLogin)) {
                if (!AbStringUtil.isEmpty(info.getLinkUrl())) {
                    WebViewActivity.startActivity(mContext, info.getLinkUrl());
                }
            } else {
                this.startActivity(LoginActivity.class);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_credit_card_details;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            layoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            layoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("详情");
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void recordSuccess(CreditCardDetailsBean cardDetailsBean) {
        info = cardDetailsBean.getCreditCard();
        AbImageUtil.glideImageList(info.getImg(), ivImg, R.mipmap.credit_card_details);
        tvName.setText(info.getName() + "");
        tvSummary.setText(info.getSummary() + "");
        if ("1".equals(info.getLevel())) {
            tvLoanTag1.setText("普卡");
        } else if ("2".equals(info.getLevel())) {
            tvLoanTag1.setText("金卡");
        } else if ("3".equals(info.getLevel())) {
            tvLoanTag1.setText("白金卡");
        } else {
            tvLoanTag1.setText("普卡");
        }
        if ("1".equals(info.getCurrency())) {
            tvLoanTag2.setText("人民币单币卡");
        } else if ("2".equals(info.getCurrency())) {
            tvLoanTag2.setText("多币卡");
        } else {
            tvLoanTag2.setText("人民币单币卡");
        }
        if ("1".equals(info.getAnnualFee())) {
            tvLoanTag3.setText("免首年");
        } else if ("2".equals(info.getAnnualFee())) {
            tvLoanTag3.setText("交易免年费");
        } else if ("3".equals(info.getAnnualFee())) {
            tvLoanTag3.setText("终生免年费");
        } else {
            tvLoanTag3.setText("免首年");
        }
        tvSpecialPrivilege.setText(Html.fromHtml(info.getSpecialPrivilege()) + "");
        tvBaseInfo.setText(Html.fromHtml(info.getBaseInfo()) + "");
        tvRelateExpense.setText(Html.fromHtml(info.getRelateExpense()) + "");
    }

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, CreditCardDetailsActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

}
