package com.rt.zgloan.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.bean.InviteRecordBean;
import com.rt.zgloan.recyclerview.BaseRecyclerAdapter;

/**
 * Created by hjy on 2017/8/25.
 */

public class InviteFriendsRecordAdapter extends BaseRecyclerAdapter<InviteFriendsRecordAdapter.ViewHolder, InviteRecordBean.InviteDataBean> {
    private Activity activity;

    public InviteFriendsRecordAdapter(Activity activity) {
        this.activity = activity;
    }

//    @Override
//    public RecordList mCreateViewHolder(ViewGroup parent, int viewType) {
//        View item = mInflater.from(mContext).inflate(R.layout.invite_record_item, null);
//        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        return new InviteFriendsRecordAdapter.RecordList(item);
//    }
//
//    @Override
//    public void mBindViewHolder(RecordList holder, int position) {
//        final InviteFriendsRecordSonInfo info = list.get(position);
//        String phone = info.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
//        holder.tv_phone.setText(phone);
//        holder.tv_create_time.setText("注册时间 " + info.getCreate_time());
//
//
//    }

    @Override
    public ViewHolder mOnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.invite_record_item, parent, false));
    }

    @Override
    public void mOnBindViewHolder(ViewHolder holder, int position) {
        holder.tv_phone.setText(data.get(position).getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2") + "");
        holder.tv_create_time.setText(data.get(position).getCreatTime() + "");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView tv_phone;
        TextView tv_create_time;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_create_time = (TextView) itemView.findViewById(R.id.tv_create_time);

        }
    }

}
