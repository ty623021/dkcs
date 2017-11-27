package com.rt.zgloan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rt.zgloan.R;
import com.rt.zgloan.bean.LabelListBean;
import com.rt.zgloan.bean.LabelLoansListBean;
import com.rt.zgloan.bean.LoanClassListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcy on 2017/11/6 0006.
 */

public class FragmentFirstPageAdapter extends RecyclerView.Adapter {
    private static final int TYPE_PRODUCT_CATEGORY = 0;
    private static final int TYPE_PRODUCT_LIST = 1;
    private static final int TYPE_Label_HOT = 2;

    private List<LoanClassListBean.LoanClassBean> mListLoanClassBean;
    private List<LabelListBean.LabelBean> mListLabelBean;
    private List<LabelLoansListBean> mListLabelLoansListBean;

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public FragmentFirstPageAdapter(Context context, List<LoanClassListBean.LoanClassBean> listLoanClassBean, List<LabelListBean.LabelBean> listLabelBean, List<LabelLoansListBean> listLabelLoansListBean) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mListLoanClassBean = listLoanClassBean;
        this.mListLabelBean = listLabelBean;
        this.mListLabelLoansListBean = listLabelLoansListBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_PRODUCT_CATEGORY:
                return new ProductGategoryViewHolder(mContext, mLayoutInflater.inflate(R.layout.item_product_category, parent, false));
//            case TYPE_Label_HOT:
//            case TYPE_PRODUCT_LIST:
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(holder.getAdapterPosition());
        switch (itemViewType) {
            case TYPE_PRODUCT_CATEGORY:
                ((ProductGategoryViewHolder) holder).setData(mListLoanClassBean);
        }
    }

    @Override
    public int getItemCount() {
        return 2 + (mListLabelLoansListBean == null ? 0 : mListLabelLoansListBean.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //分类
            return TYPE_PRODUCT_CATEGORY;
        } else if (position == 2) {
            //热门标签
            return TYPE_Label_HOT;
        } else {
            //列表
            return TYPE_PRODUCT_LIST;
        }
    }

    public class ProductGategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product)
        ImageView mIvProduct;
        @BindView(R.id.tv_product_name)
        TextView mTvProductName;
        @BindView(R.id.tv_product_introduce)
        TextView mTvProductIntroduce;

        private Context mContext;

        public ProductGategoryViewHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            ButterKnife.bind(this, itemView);
        }

        public void setData(final List<LoanClassListBean.LoanClassBean> data) {
            if (data != null && !data.isEmpty()) {
                for (int i = 0; i < data.size(); i++) {
                    Glide.with(mContext)
                            .load(data.get(i).getPic())
                            .placeholder(R.drawable.image_default)
                            .error(R.drawable.image_default)
                            .into(mIvProduct);//设置图片
                    mTvProductName.setText(data.get(i).getName() + "");
                    mTvProductIntroduce.setText(data.get(i).getDescribe() + "");
                }

            }
        }
    }
}