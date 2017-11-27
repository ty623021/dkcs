package com.rt.zgloan.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseRecyclerViewAdapter;
import com.rt.zgloan.bean.PersonalDataInfo;
import com.rt.zgloan.bean.PersonalDataNewInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hjy on 2017/8/27.
 */

public class PersonalDataAdapter extends BaseRecyclerViewAdapter<PersonalDataNewInfo, PersonalDataAdapter.DatadList> {


    public PersonalDataAdapter(List<PersonalDataNewInfo> list) {
        super(list);
    }

    @Override
    public DatadList mCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.from(mContext).inflate(R.layout.personal_data_item, null);
        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new PersonalDataAdapter.DatadList(item);
    }

    @Override
    public void mBindViewHolder(final DatadList holder, int position) {
        final PersonalDataNewInfo personalDataNewInfo = list.get(holder.getAdapterPosition());
        holder.tv_item.setText(personalDataNewInfo.getName());
        if (personalDataNewInfo.isSelector()) {
            holder.rel_btn_item.setSelected(true);
            holder.tv_item.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.rel_btn_item.setSelected(false);
            holder.tv_item.setTextColor(mContext.getResources().getColor(R.color.black));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                personalDataNewInfo.setSelector(!personalDataNewInfo.isSelector());
                notifyDataSetChanged();
            }
        });


    }

    class DatadList extends RecyclerView.ViewHolder {

        View itemView;
        @BindView(R.id.tv_item)
        TextView tv_item;
        @BindView(R.id.rel_btn_item)
        RelativeLayout rel_btn_item;

        public DatadList(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;

        }
    }

    public String getSelectString() {
        StringBuffer stringBuffer = new StringBuffer("");

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelector()) {
                stringBuffer.append((i+1) + ",");
            }
        }
//        for (PersonalDataNewInfo info1 : list) {
//            if (info1.isSelector()) {
//                stringBuffer.append(info1.getName() + ",");
//            }
//        }
        if (stringBuffer.length() > 1) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }
}
