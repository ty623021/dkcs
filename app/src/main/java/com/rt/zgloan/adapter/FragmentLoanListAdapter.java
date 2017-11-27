package com.rt.zgloan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseRecyclerViewAdapter;
import com.rt.zgloan.bean.HomeListInfo;
import com.rt.zgloan.glide.GlideImgManager;
import com.rt.zgloan.util.StringUtil;

import java.util.List;

/**
 * Created by hjy on 2017/8/24.
 */

public class FragmentLoanListAdapter extends BaseRecyclerViewAdapter<HomeListInfo, FragmentLoanListAdapter.LoanList> {

    private GlideImgManager glideImgManager;

    public FragmentLoanListAdapter(List<HomeListInfo> list) {
        super(list);
        glideImgManager = new GlideImgManager();
    }


    @Override
    public LoanList mCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.from(mContext).inflate(R.layout.fragment_loan_crash_market_item, null);
        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new FragmentLoanListAdapter.LoanList(item);
    }

    @Override
    public void mBindViewHolder(FragmentLoanListAdapter.LoanList holder, int position) {
        final HomeListInfo info = list.get(position);

        holder.linear_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LoanDetailActivity.startActivity(mContext, info.getId());
            }
        });
        if (!StringUtil.isBlank(info.getName())) {
            holder.tv_loan_name.setText(info.getName());
        }
        if (!StringUtil.isBlank(info.getMoney_big())) {
            holder.tv_money_big.setText(info.getMoney_big());
        }
        if (!StringUtil.isBlank(info.getFast_time())) {
            holder.tv_fast_time.setText(info.getFast_time() + "小时放款");
        }
        if (!StringUtil.isBlank(info.getDeadline_big())) {
            holder.tv_deadline_big.setText("最高可贷" + info.getDeadline_big() + "个月");
        }
        if (!StringUtil.isBlank(info.getPeople())) {
            holder.tv_loan_people.setText(info.getPeople() + "人借款成功");
        }

//        if (info.getIs_activity().equals("1")) {
//            holder.loan_activity_img.setVisibility(View.VISIBLE);
//        } else if (info.getIs_activity().equals("0")) {
//            holder.loan_activity_img.setVisibility(View.GONE);
//        }
        if (!StringUtil.isBlank(info.getLoan_tag1())) {
            holder.tv_loan_tag1.setText(info.getLoan_tag1());
            holder.rel_loan_tag1.setVisibility(View.VISIBLE);
        }

        if (!StringUtil.isBlank(info.getLoan_tag2())) {
            holder.tv_loan_tag2.setText(info.getLoan_tag2());
            holder.rel_loan_tag2.setVisibility(View.VISIBLE);
        } else {
            if (!StringUtil.isBlank(info.getLoan_tag3())) {
                holder.tv_loan_tag2.setText(info.getLoan_tag3());
            } else {
                holder.rel_loan_tag2.setVisibility(View.GONE);
            }
        }

        if (!StringUtil.isBlank(info.getLoan_tag3())) {
            holder.tv_loan_tag3.setText(info.getLoan_tag3());
            holder.rel_loan_tag3.setVisibility(View.VISIBLE);
        } else {
            holder.rel_loan_tag3.setVisibility(View.GONE);
        }
        glideImgManager.glideLoader(info.getSmeta(), R.mipmap.error_img, R.mipmap.error_img, holder.loan_img, 0);
    }

    class LoanList extends RecyclerView.ViewHolder {

        View itemView;
        LinearLayout linear_main;
        TextView tv_loan_name;
        TextView tv_money_big;
        TextView tv_fast_time;
        TextView tv_deadline_big;
        TextView tv_loan_tag1;
        TextView tv_loan_tag2;
        TextView tv_loan_tag3;
        ImageView loan_img;
        TextView tv_loan_people;
        ImageView loan_activity_img;
        RelativeLayout rel_loan_tag1, rel_loan_tag2, rel_loan_tag3;


        public LoanList(View itemView) {
            super(itemView);
            this.itemView = itemView;
            linear_main = (LinearLayout) itemView.findViewById(R.id.linear_main);
            tv_loan_name = (TextView) itemView.findViewById(R.id.tv_loan_name);
            tv_money_big = (TextView) itemView.findViewById(R.id.tv_money_big);
            tv_fast_time = (TextView) itemView.findViewById(R.id.tv_fast_time);
            tv_deadline_big = (TextView) itemView.findViewById(R.id.tv_deadline_big);
            tv_loan_tag1 = (TextView) itemView.findViewById(R.id.tv_loan_tag1);
            tv_loan_tag2 = (TextView) itemView.findViewById(R.id.tv_loan_tag2);
            tv_loan_tag3 = (TextView) itemView.findViewById(R.id.tv_loan_tag3);
            loan_img = (ImageView) itemView.findViewById(R.id.loan_img);
            tv_loan_people = (TextView) itemView.findViewById(R.id.tv_loan_people);
            loan_activity_img = (ImageView) itemView.findViewById(R.id.loan_activity_img);
            rel_loan_tag1 = (RelativeLayout) itemView.findViewById(R.id.rel_loan_tag1);
            rel_loan_tag2 = (RelativeLayout) itemView.findViewById(R.id.rel_loan_tag2);
            rel_loan_tag3 = (RelativeLayout) itemView.findViewById(R.id.rel_loan_tag3);
        }
    }
}
