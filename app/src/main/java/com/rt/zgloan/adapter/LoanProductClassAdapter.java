package com.rt.zgloan.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rt.zgloan.R;
import com.rt.zgloan.bean.LoanClassListBean;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcy on 2017/11/6 0006.
 */

public class LoanProductClassAdapter extends BaseRecyclerAdapter<LoanProductClassAdapter.ViewHolder, LoanClassListBean.LoanClassBean> {
    private Fragment fragment;

    public LoanProductClassAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public LoanProductClassAdapter.ViewHolder mOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new LoanProductClassAdapter.ViewHolder(mInflater.inflate(R.layout.item_loan_product_class, parent, false));
    }

    @Override
    public void mOnBindViewHolder(LoanProductClassAdapter.ViewHolder holder, int position) {
        holder.mTvLoanProductClass.setText(data.get(position).getName() + "");
        holder.mTvLoanProductClass.setTextColor(Color.parseColor(data.get(position).isSelected() ? "#ff4a4a" : "#575656"));
        Glide.with(fragment)
                .load(data.get(position).getPic())
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .centerCrop()
                .into(holder.mIvLoanProductClass);//设置图片

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_loan_product_class)
        ImageView mIvLoanProductClass;
        @BindView(R.id.tv_loan_product_class)
        TextView mTvLoanProductClass;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }
}
