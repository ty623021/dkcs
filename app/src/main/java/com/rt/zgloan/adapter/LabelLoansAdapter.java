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
import com.rt.zgloan.bean.LabelListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcy on 2017/11/7 0007.
 */

public class LabelLoansAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<LabelListBean.LabelBean> list;


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

        holder.item_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.mTvLabel.setText(list.get(position).getName() + "");
        Glide.with(mContext)
                .load(list.get(position).getImage_url())
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .centerCrop()
                .into(holder.mIvLabel);//设置图片
        holder.item_recyclerView.setAdapter(new ItemRecycleAdapter(list.get(position).getLoans()));
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
        private List<LabelListBean.LabelBean.LabelLoansBean> data;

        public ItemRecycleAdapter(List<LabelListBean.LabelBean.LabelLoansBean> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolderType(LayoutInflater.from(mContext).inflate(R.layout.item_product_introduce, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemHolderType) {
                bindType((ItemHolderType) holder, position);
            }
        }

        private void bindType(ItemHolderType holder, final int position) {
            if (data.get(position).getRate_type() == 1) {
                holder.mTvDayRate.setText("日利率");
                holder.mTvLoanDay.setText("借款期限(天):");
            } else if (data.get(position).getRate_type() == 2) {
                holder.mTvDayRate.setText("月利率");
                holder.mTvLoanDay.setText("借款期限(月):");
            } else {
                holder.mTvDayRate.setText("年利率");
                holder.mTvLoanDay.setText("借款期限(年):");
            }
            holder.mTvProductTitle.setText(data.get(position).getName() + "");
            holder.mTvMoneySml.setText(data.get(position).getMoney_sml() + "");
            holder.mTvMoneyBig.setText(data.get(position).getMoney_big() + "");
            holder.mTvDeadlineSml.setText(data.get(position).getDeadline_sml() + "");
            holder.mTvDeadlineBig.setText(data.get(position).getDeadline_big() + "");
            holder.mTvRate.setText(data.get(position).getRate() + "%");
            holder.mTvPropagandaLanguage.setText(Html.fromHtml(data.get(position).getPropaganda_language()));

            Glide.with(mContext)
                    .load(data.get(position).getImage_url())
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_default)
                    .centerCrop()
                    .into(holder.mIvProductIntroduce);//设置图片

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                     ToastUtil.showToast("clickSuccess");
                    LoanDetailActivity.startActivity(mContext, data.get(position).getId());
                }
            });
        }

        @Override
        public int getItemCount() {
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
    }
}
