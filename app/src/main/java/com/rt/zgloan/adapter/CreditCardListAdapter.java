package com.rt.zgloan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.LoginActivity;
import com.rt.zgloan.activity.WebViewActivity;
import com.rt.zgloan.activity.creditCardActivity.CreditCardDetailsActivity;
import com.rt.zgloan.activity.creditCardActivity.CreditCardListActivity;
import com.rt.zgloan.bean.CreditCardBean;
import com.rt.zgloan.util.AbImageUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.SpUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class CreditCardListAdapter extends RecyclerView.Adapter<CreditCardListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<CreditCardBean> list;
    private Context mContext;

    public CreditCardListAdapter(Context context, List<CreditCardBean> list) {
        this.mContext = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_credit_card_item5, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CreditCardBean info = list.get(position);
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
        if (AbStringUtil.isEmpty(info.getLabelsTwo())) {
            holder.tv_labels2.setVisibility(View.GONE);
        } else {
            holder.tv_labels2.setVisibility(View.VISIBLE);
            holder.tv_labels2.setText(info.getLabelsTwo());
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
                        ((CreditCardListActivity) mContext).startActivity(LoginActivity.class);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_loan_number, tv_des, tv_pointsOne, tv_pointsTow, tv_labels1, tv_labels2;
        ImageView ivImg;
        View itemView;

        ViewHolder(View itemView) {
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
