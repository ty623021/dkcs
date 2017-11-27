package com.rt.zgloan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseRecyclerViewAdapter;
import com.rt.zgloan.bean.PersonalDataNewInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by hjy on 2017/8/27.
 */

public class PersonalDataNewAdapter extends BaseRecyclerViewAdapter<PersonalDataNewInfo, PersonalDataNewAdapter.NewDatadList> {

    public PersonalDataNewAdapter(List<PersonalDataNewInfo> list) {
        super(list);
    }

    @Override
    public PersonalDataNewAdapter.NewDatadList mCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mInflater.from(mContext).inflate(R.layout.personal_data_item, null);
        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new PersonalDataNewAdapter.NewDatadList(item);
    }

    @Override
    public void mBindViewHolder(final PersonalDataNewAdapter.NewDatadList holder, final int position) {
        final PersonalDataNewInfo info = list.get(position);
        holder.tv_item.setText(info.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (PersonalDataNewInfo info1 : list) {
                    if (info1.getName().equals(holder.tv_item.getText().toString())) {
                        info.setSelector(!info.isSelector());
                    } else {
                        info1.setSelector(false);
                    }
                }

                notifyDataSetChanged();
            }
        });
        if (info.isSelector()) {
            holder.rel_btn_item.setSelected(true);
            holder.tv_item.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.rel_btn_item.setSelected(false);
            holder.tv_item.setTextColor(mContext.getResources().getColor(R.color.black));
        }

    }


    class NewDatadList extends RecyclerView.ViewHolder {
        View itemView;
        @BindView(R.id.tv_item)
        TextView tv_item;
        @BindView(R.id.rel_btn_item)
        RelativeLayout rel_btn_item;

        public NewDatadList(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;


        }
    }

    public String getSelectString() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelector()) {
                return i + 1 + "";
            }
        }
//        for (PersonalDataNewInfo info1 : list) {
//            if (info1.isSelector()) {
//                return info1.getName();
//            }
//        }
        return "";
    }
}
