package com.rt.zgloan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.creditCardActivity.CreditCardListActivity;
import com.rt.zgloan.bean.BankBean;
import com.rt.zgloan.util.AbImageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27 0027.
 */

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.ViewHolder> {

    private List<BankBean.BankInfo> list;
    private LayoutInflater inflater;
    private Context mContext;

    public BankListAdapter(Context context, List<BankBean.BankInfo> list) {
        this.mContext = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_bank_card_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BankBean.BankInfo bankInfo = list.get(position);
        holder.tv_name.setText(bankInfo.getName() + "");
        holder.tv_des.setText(bankInfo.getSummary() + "");
        AbImageUtil.glideImageList(bankInfo.getLogo(), holder.iv_img, R.mipmap.all_bank_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCardListActivity.startActivity(mContext, bankInfo.getId(), CreditCardListActivity.TYPE_BANK);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView iv_img;
        private TextView tv_name;
        private TextView tv_des;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_des = (TextView) itemView.findViewById(R.id.tv_des);
        }
    }
}
