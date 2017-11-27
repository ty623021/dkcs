package com.rt.zgloan.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.PersonalDataActivity;
import com.rt.zgloan.bean.CitiesBean;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;
import com.rt.zgloan.util.SpUtil;

/**
 * Created by hjy on 2017/8/31.
 */

public class GetCityAdapter extends BaseRecyclerAdapter<GetCityAdapter.ViewHolder, CitiesBean.CityBean> {
    private Activity activity;

    public GetCityAdapter(Activity activity) {
        this.activity = activity;
    }

//    public GetCityAdapter(List<GetCitySonInfo> list, GetProvinceSonInfo provinceSonInfo) {
//        super(list);
//        this.provinceSonInfo = provinceSonInfo;
//    }

//    @Override
//    public InviteFriendsRecordAdapter.RecordList mCreateViewHolder(ViewGroup parent, int viewType) {
//        View item = mInflater.from(mContext).inflate(R.layout.get_province_item, null);
//        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        return new GetCityAdapter.RecordList(item);
//    }
//
//    @Override
//    public void mBindViewHolder(InviteFriendsRecordAdapter.RecordList holder, int position) {
//
//        final GetCitySonInfo info = list.get(position);
//        holder.tv_name.setText(info.getName());
//
//        holder.rel_choose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, PersonalDataActivity.class);
//                intent.putExtra("province", provinceSonInfo);
//                intent.putExtra("city", info);
//                mContext.startActivity(intent);
//            }
//        });
//    }

    @Override
    public ViewHolder mOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.get_province_item, parent, false));
    }

    @Override
    public void mOnBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_name.setText(data.get(position).getName() + "");
        holder.rel_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersonalDataActivity.class);
                intent.putExtra("province", SpUtil.getString(SpUtil.PROVINCE));
                intent.putExtra("provinceId", SpUtil.getString(SpUtil.PROVINCE_ID));
                intent.putExtra("city", data.get(position).getName());
                intent.putExtra("cityId", data.get(position).getId());
//                intent.putExtra("province", provinceSonInfo);
//                intent.putExtra("city", info);
                mContext.startActivity(intent);
                SpUtil.remove(SpUtil.PROVINCE);
                SpUtil.remove(SpUtil.PROVINCE_ID);
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
