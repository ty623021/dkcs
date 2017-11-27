package com.rt.zgloan.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.rt.zgloan.R;
import com.rt.zgloan.bean.LoanDetailBean;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zcy on 2017/11/8 0008.
 */

public class ApplicationProcedureAdapter extends BaseRecyclerAdapter<ApplicationProcedureAdapter.ViewHolder, LoanDetailBean.MaterialBean> {
    private Activity activity;

    public ApplicationProcedureAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public ApplicationProcedureAdapter.ViewHolder mOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplicationProcedureAdapter.ViewHolder(mInflater.inflate(R.layout.item_loan_step, parent, false));
    }

    @Override
    public void mOnBindViewHolder(ViewHolder holder, int position) {
        Logger.e("listSize" + data.size());
        holder.mTvProcedure.setText(data.get(position).getName() + "");
        Glide.with(activity)
                .load(data.get(position).getUrl())
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .centerCrop()
                .into(holder.mIvProcedure);//设置图片
        if (position < (data.size() - 1)) {
            holder.mIvArrow.setVisibility(View.VISIBLE);
        } else {
            holder.mIvArrow.setVisibility(View.INVISIBLE);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_procedure)
        TextView mTvProcedure;
        @BindView(R.id.iv_procedure)
        ImageView mIvProcedure;
        @BindView(R.id.iv_arrow)
        ImageView mIvArrow;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }
}
