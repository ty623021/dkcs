package com.rt.zgloan.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rt.zgloan.R;
import com.rt.zgloan.activity.LoanDetailActivity;
import com.rt.zgloan.bean.LoansListByLoanTypeBean;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcy on 2017/11/8 0008.
 */

public class LoanProductAdapter extends BaseRecyclerAdapter<LoanProductAdapter.ViewHolder, LoansListByLoanTypeBean.LoansListBean> {
    private Fragment fragment;

    public LoanProductAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public LoanProductAdapter.ViewHolder mOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new LoanProductAdapter.ViewHolder(mInflater.inflate(R.layout.item_product_introduce, parent, false));
    }

    @Override
    public void mOnBindViewHolder(ViewHolder holder, final int position) {
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
    public long getItemId(int position) {
        return (long) position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
