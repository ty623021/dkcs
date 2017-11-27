package com.rt.zgloan.adapter;

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

public class ProductCategoryAdapter extends BaseRecyclerAdapter<ProductCategoryAdapter.ViewHolder, LoanClassListBean.LoanClassBean> {
    private Fragment fragment;

    public ProductCategoryAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public ViewHolder mOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_product_category, parent, false));
    }

    @Override
    public void mOnBindViewHolder(ViewHolder holder, int position) {
        holder.mTvProductName.setText(data.get(position).getName() + "");
        holder.mTvProductIntroduce.setText(data.get(position).getDescribe() + "");
        Glide.with(fragment)
                .load(data.get(position).getPic())
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .centerCrop()
                .into(holder.mIvProduct);//设置图片

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product)
        ImageView mIvProduct;
        @BindView(R.id.tv_product_name)
        TextView mTvProductName;
        @BindView(R.id.tv_product_introduce)
        TextView mTvProductIntroduce;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }


    }
}
