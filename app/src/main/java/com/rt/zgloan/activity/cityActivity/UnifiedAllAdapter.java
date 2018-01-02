package com.rt.zgloan.activity.cityActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rt.zgloan.R;

import java.util.ArrayList;
import java.util.List;

public class UnifiedAllAdapter extends BaseAdapter {

    private Context context;
    private UnifiedClickListener listener;
    private LayoutInflater inflater;
    private List<UnifiedBase> lists = new ArrayList<>();

    public UnifiedAllAdapter(Context context, UnifiedClickListener listener, List<UnifiedBase> lists) {
        this.context = context;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
        setList(lists);
    }

    public void setList(List<UnifiedBase> station) {
        this.lists.clear();
        if (station != null) {
            this.lists.addAll(station);
        }
        notifyDataSetChanged();
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
            convertView = inflater.inflate(R.layout.item_lv_unified_all_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            holder.unified_all_tv_flag.setVisibility(View.VISIBLE);
            holder.unified_all_tv_flag.setText(lists.get(position).pinyinFirst);
        } else {
            holder.unified_all_tv_flag.setVisibility(View.GONE);
        }
        if (position == lists.size() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        holder.unified_all_tv_name.setText(lists.get(position).name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.choice(lists.get(position));
            }
        });
        return convertView;
    }

    public class ViewHolder {

        public TextView unified_all_tv_flag;
        public TextView unified_all_tv_name;
        public View line;

        public ViewHolder(View v) {
            unified_all_tv_flag = (TextView) v.findViewById(R.id.unified_all_tv_flag);
            unified_all_tv_name = (TextView) v.findViewById(R.id.unified_all_tv_name);
            line = v.findViewById(R.id.line);
        }

    }

}
