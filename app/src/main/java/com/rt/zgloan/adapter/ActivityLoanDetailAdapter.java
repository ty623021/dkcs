package com.rt.zgloan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseRecyclerViewAdapter;
import com.rt.zgloan.bean.ApplyMaterialsInfo;
import com.rt.zgloan.bean.LoanDetailInfo;
import com.rt.zgloan.util.AbImageUtil;
import com.rt.zgloan.util.ToastUtil;

import java.util.List;

/**
 * Created by hjy on 2017/8/24.
 */

public class ActivityLoanDetailAdapter extends BaseRecyclerViewAdapter<ApplyMaterialsInfo, ActivityLoanDetailAdapter.LoanDetailList> {

    public ActivityLoanDetailAdapter(List<ApplyMaterialsInfo> list) {
        super(list);
    }

    @Override
    public LoanDetailList mCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.from(mContext).inflate(R.layout.loan_detail_item, null);
        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new ActivityLoanDetailAdapter.LoanDetailList(item);
    }

    @Override
    public void mBindViewHolder(LoanDetailList holder, int position) {
        final ApplyMaterialsInfo info = list.get(position);
        AbImageUtil.glideImageList(info.getUrl(), holder.imgs, R.mipmap.error_img, R.mipmap.error_img);
        holder.tv_name.setText(info.getName());
    }

    class LoanDetailList extends RecyclerView.ViewHolder {
        View itemView;
        ImageView imgs;
        TextView tv_name;

        public LoanDetailList(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imgs = (ImageView) itemView.findViewById(R.id.imgs);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
