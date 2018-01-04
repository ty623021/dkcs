package com.rt.zgloan.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rt.zgloan.R;
import com.rt.zgloan.activity.LoanDetailActivity;
import com.rt.zgloan.activity.LoginActivity;
import com.rt.zgloan.activity.MainActivity;
import com.rt.zgloan.activity.WebViewActivity;
import com.rt.zgloan.activity.creditCardActivity.CreditCardDetailsActivity;
import com.rt.zgloan.bean.CreditCardBean;
import com.rt.zgloan.bean.LabelListBean;
import com.rt.zgloan.util.AbImageUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.SpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcy on 2017/11/7 0007.
 */

public class LabelLoansAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<LabelListBean.LabelBean> list;
    public static final int TYPE_TYPE1 = 2;//借款热门推荐
    public static final int TYPE_TYPE2 = 3;//信用卡热门推荐

    public LabelLoansAdapter(Context mContext, List<LabelListBean.LabelBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HolderType(LayoutInflater.from(mContext).inflate(R.layout.item_labels, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderType) {
            bindType((HolderType) holder, position);
        }
    }

    private void bindType(HolderType holder, int position) {
        LabelListBean.LabelBean labelBean = list.get(position);
        holder.item_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mTvLabel.setText(labelBean.getName() + "");
        AbImageUtil.glideImageList(labelBean.getImage_url(), holder.mIvLabel, R.drawable.image_default, R.drawable.image_default);
        if (TYPE_TYPE1 == labelBean.getId()) {
            holder.item_recyclerView.setAdapter(new ItemRecycleAdapter(labelBean.getId(), labelBean.getLoans()));
        } else if (TYPE_TYPE2 == labelBean.getId()) {
            holder.item_recyclerView.setAdapter(new ItemRecycleAdapter(labelBean.getId(), labelBean.getCards()));
        } else {
            holder.item_recyclerView.setAdapter(new ItemRecycleAdapter(labelBean.getId(), labelBean.getLoans()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderType extends RecyclerView.ViewHolder {
        private ImageView mIvLabel;
        private TextView mTvLabel;
        private RecyclerView item_recyclerView;

        public HolderType(View itemView) {
            super(itemView);
            item_recyclerView = (RecyclerView) itemView.findViewById(R.id.item_recyclerView);
            item_recyclerView.setNestedScrollingEnabled(false);
            item_recyclerView.setFocusable(false);
            mIvLabel = (ImageView) itemView.findViewById(R.id.iv_label);
            mTvLabel = (TextView) itemView.findViewById(R.id.tv_label);
        }
    }

    public class ItemRecycleAdapter extends RecyclerView.Adapter {
        private int type;
        private List data;

        public ItemRecycleAdapter(int type, List data) {
            this.data = data;
            this.type = type;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (TYPE_TYPE1 == type) {
                return new ItemHolderType(LayoutInflater.from(mContext).inflate(R.layout.item_product_introduce, parent, false));
            } else if (TYPE_TYPE2 == type) {
                return new ItemHolderType1(LayoutInflater.from(mContext).inflate(R.layout.item_credit_card_item5, parent, false));
            } else {
                return new ItemHolderType(LayoutInflater.from(mContext).inflate(R.layout.item_product_introduce, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemHolderType) {
                bindType((ItemHolderType) holder, position);
            } else if (holder instanceof ItemHolderType1) {
                bindType1((ItemHolderType1) holder, position);
            }
        }

        private void bindType(ItemHolderType holder, final int position) {
            final LabelListBean.LabelBean.LabelLoansBean info = (LabelListBean.LabelBean.LabelLoansBean) data.get(position);
            if (info.getRate_type() == 1) {
                holder.mTvDayRate.setText("日利率");
                holder.mTvLoanDay.setText("借款期限(天):");
            } else if (info.getRate_type() == 2) {
                holder.mTvDayRate.setText("月利率");
                holder.mTvLoanDay.setText("借款期限(月):");
            } else {
                holder.mTvDayRate.setText("年利率");
                holder.mTvLoanDay.setText("借款期限(年):");
            }
            holder.mTvProductTitle.setText(info.getName() + "");
            holder.mTvMoneySml.setText(info.getMoney_sml() + "");
            holder.mTvMoneyBig.setText(info.getMoney_big() + "");
            holder.mTvDeadlineSml.setText(info.getDeadline_sml() + "");
            holder.mTvDeadlineBig.setText(info.getDeadline_big() + "");
            if (info.getRate().contains("%")) {
                holder.mTvRate.setText(info.getRate());
            } else {
                holder.mTvRate.setText(info.getRate() + "%");
            }
            if (!AbStringUtil.isEmpty(info.getPropaganda_language())) {
                holder.mTvPropagandaLanguage.setText(Html.fromHtml(info.getPropaganda_language()));
            } else {
                holder.mTvPropagandaLanguage.setText("");
            }

            Glide.with(mContext)
                    .load(info.getImage_url())
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_default)
                    .centerCrop()
                    .into(holder.mIvProductIntroduce);//设置图片

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoanDetailActivity.startActivity(mContext, info.getId());
                }
            });
        }

        private void bindType1(ItemHolderType1 holder, final int position) {
            final CreditCardBean info = (CreditCardBean) data.get(position);
            holder.tv_title.setText(info.getName());
            holder.tv_loan_number.setText(info.getApplicants() + "人");
            holder.tv_des.setText(info.getSummary());
            holder.tv_pointsOne.setText(info.getPointsOne());
            holder.tv_pointsTow.setText(info.getPointsTwo());
            if (AbStringUtil.isEmpty(info.getLabelsOne())) {
                holder.tv_labels1.setVisibility(View.GONE);
            } else {
                holder.tv_labels1.setVisibility(View.VISIBLE);
                holder.tv_labels1.setText(info.getLabelsOne());
            }
            if (AbStringUtil.isEmpty(info.getLabelsTow())) {
                holder.tv_labels2.setVisibility(View.GONE);
            } else {
                holder.tv_labels1.setVisibility(View.VISIBLE);
                holder.tv_labels2.setText(info.getLabelsTow());
            }
            AbImageUtil.glideImageList(info.getImg(), holder.ivImg, R.mipmap.credit_card_default);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("0".equals(info.getShowType())) {
                        CreditCardDetailsActivity.startActivity(mContext, info.getId() + "");
                    } else if ("1".equals(info.getShowType())) {
                        if (SpUtil.getBoolean(SpUtil.isLogin)) {
                            if (!AbStringUtil.isEmpty(info.getLinkUrl())) {
                                WebViewActivity.startActivity(mContext, info.getLinkUrl());
                            }
                        } else {
                            ((MainActivity) mContext).startActivity(LoginActivity.class);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (data == null) {
                return 0;
            }
            return data.size();
        }


        class ItemHolderType extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_product_title)
            TextView mTvProductTitle;
            @BindView(R.id.iv_product_introduce)
            ImageView mIvProductIntroduce;
            @BindView(R.id.tv_money_sml)
            TextView mTvMoneySml;
            @BindView(R.id.tv_money_big)
            TextView mTvMoneyBig;
            @BindView(R.id.tv_deadline_sml)
            TextView mTvDeadlineSml;
            @BindView(R.id.tv_deadline_big)
            TextView mTvDeadlineBig;
            @BindView(R.id.tv_rate)
            TextView mTvRate;
            @BindView(R.id.tv_propaganda_language)
            TextView mTvPropagandaLanguage;
            @BindView(R.id.tv_day_rate)
            TextView mTvDayRate;
            @BindView(R.id.tv_loan_day)
            TextView mTvLoanDay;
            LinearLayout mLlProductIntroduce;
            View itemView;

            public ItemHolderType(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                this.itemView = itemView;
                this.mLlProductIntroduce = (LinearLayout) itemView.findViewById(R.id.Ll_product_introduce);
            }
        }

        class ItemHolderType1 extends RecyclerView.ViewHolder {
            TextView tv_title, tv_loan_number, tv_des, tv_pointsOne, tv_pointsTow, tv_labels1, tv_labels2;
            ImageView ivImg;
            View itemView;

            public ItemHolderType1(View itemView) {
                super(itemView);
                this.itemView = itemView;
                this.ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
                this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
                this.tv_loan_number = (TextView) itemView.findViewById(R.id.tv_loan_number);
                this.tv_des = (TextView) itemView.findViewById(R.id.tv_des);
                this.tv_pointsOne = (TextView) itemView.findViewById(R.id.tv_pointsOne);
                this.tv_pointsTow = (TextView) itemView.findViewById(R.id.tv_pointsTow);
                this.tv_labels1 = (TextView) itemView.findViewById(R.id.tv_labels1);
                this.tv_labels2 = (TextView) itemView.findViewById(R.id.tv_labels2);
            }
        }
    }


}
