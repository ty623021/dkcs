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

public class UnifiedFlagAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<String> lists = new ArrayList<>();

    public UnifiedFlagAdapter(Context context, List<String> lists ){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.lists = lists;
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
            convertView = inflater.inflate(R.layout.item_lv_unified_flag, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.unified_flag_tv_name.setText(lists.get(position));
        return convertView;
    }

    public class ViewHolder{

        private TextView unified_flag_tv_name;

        public ViewHolder(View v){
            unified_flag_tv_name = (TextView) v.findViewById(R.id.unified_flag_tv_name);
        }

    }

}
