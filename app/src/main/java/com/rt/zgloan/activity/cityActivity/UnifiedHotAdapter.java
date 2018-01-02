package com.rt.zgloan.activity.cityActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rt.zgloan.R;

import java.util.ArrayList;
import java.util.List;

public class UnifiedHotAdapter extends BaseAdapter {

    private Context context;
    private UnifiedClickListener listener;
    private LayoutInflater inflater;
    public boolean isShowHot;
    private List<UnifiedBase> lists = new ArrayList<>();

    public UnifiedHotAdapter(Context context, UnifiedClickListener listener, boolean isShowHot, List<UnifiedBase> lists) {
        this.context = context;
        this.listener = listener;
        this.isShowHot = isShowHot;
        this.inflater = LayoutInflater.from(context);
        if (lists != null) {
            this.lists.addAll(lists);
        }
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gv_unified_hot, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.unified_hot_tv_name.setText(lists.get(position).name);
        if (lists.size() == 1) {
            holder.unified_hot_iv_local.setVisibility(View.VISIBLE);
        } else {
            holder.unified_hot_iv_local.setVisibility(View.GONE);
        }
        if (position != 0 && position == lists.size() - 1 && StrUtils.isNull(lists.get(position).name)) {
            holder.unified_hot_iv_arrow.setVisibility(View.VISIBLE);
            if (position == 5) {
                holder.unified_hot_iv_arrow.setImageResource(R.mipmap.icon_arrow_down_gray);
            } else {
                holder.unified_hot_iv_arrow.setImageResource(R.mipmap.icon_arrow_up_gray);
            }
        } else {
            holder.unified_hot_iv_arrow.setVisibility(View.GONE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 5 && StrUtils.isNull(lists.get(position).name)) {
                    listener.showHot();
                } else if (position == lists.size() - 1 && StrUtils.isNull(lists.get(position).name)) {
                    listener.hideHot();
                } else {
                    listener.choice(lists.get(position));
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {

        private TextView unified_hot_tv_name;
        private ImageView unified_hot_iv_local;
        private ImageView unified_hot_iv_arrow;

        public ViewHolder(View v) {
            unified_hot_iv_arrow = (ImageView) v.findViewById(R.id.unified_hot_iv_arrow);
            unified_hot_iv_local = (ImageView) v.findViewById(R.id.unified_hot_iv_local);
            unified_hot_tv_name = (TextView) v.findViewById(R.id.unified_hot_tv_name);
        }

    }

}
