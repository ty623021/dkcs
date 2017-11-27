package com.rt.zgloan.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.myActivity.GetCityActivity;
import com.rt.zgloan.bean.ProvinciesBean;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;
import com.rt.zgloan.util.SpUtil;

/**
 * Created by hjy on 2017/8/31.
 */

public class GetProvinceAdapter extends BaseRecyclerAdapter<GetProvinceAdapter.ViewHolder, ProvinciesBean.ProvinceBean> {
    private Activity activity;

    public GetProvinceAdapter(Activity activity) {
        this.activity = activity;
    }

//    @Override
//    public RecordList mCreateViewHolder(ViewGroup parent, int viewType) {
//        View item = mInflater.from(mContext).inflate(R.layout.get_province_item, null);
//        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        return new GetProvinceAdapter.RecordList(item);
//    }

//    @Override
//    public void mBindViewHolder(RecordList holder, int position) {
//
//        final GetProvinceSonInfo info = list.get(position);
//
//
//        holder.tv_name.setText(info.getName());
//
//        holder.rel_choose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                GetCityActivity.startActivity(mContext,info);
//            }
//        });
//    }

    @Override
    public ViewHolder mOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.get_province_item, parent, false));
    }

    @Override
    public void mOnBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_name.setText(data.get(position).getName() + "");

        holder.rel_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetCityActivity.startActivity(mContext, data.get(position).getId());
                SpUtil.putString(SpUtil.PROVINCE, data.get(position).getName());
                SpUtil.putString(SpUtil.PROVINCE_ID, data.get(position).getId());
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tv_name;
        RelativeLayout rel_choose;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            rel_choose = (RelativeLayout) itemView.findViewById(R.id.rel_choose);

        }
    }
}
