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

public class UnifiedHistoryAdapter extends BaseAdapter {

    private Context context;
    private UnifiedClickListener listener;
    private LayoutInflater inflater;
    private List<UnifiedBase> lists = new ArrayList<>();

    public UnifiedHistoryAdapter(Context context, UnifiedClickListener listener, List<UnifiedBase> lists){
        this.context = context;
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
        if(lists!=null){
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
            convertView = inflater.inflate(R.layout.item_gv_unified_history, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position==lists.size()-1){
            holder.unified_history_iv_clear.setVisibility(View.GONE);
            holder.unified_history_tv_name.setTextColor(context.getResources().getColor(R.color.button_color));
            holder.unified_history_tv_name.setText("清除全部");
        }else{
            holder.unified_history_iv_clear.setVisibility(View.VISIBLE);
            holder.unified_history_tv_name.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.unified_history_tv_name.setText(lists.get(position).name);
        }
        holder.unified_history_iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.remove(lists.get(position));
            }
        });
        holder.unified_history_tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==lists.size()-1){
                    listener.clear();
                }else{
                    listener.choice(lists.get(position));
                }
            }
        });
        return convertView;
    }

    public class ViewHolder{

        private TextView unified_history_tv_name;
        private ImageView unified_history_iv_clear;

        public ViewHolder(View v){
            unified_history_iv_clear = (ImageView) v.findViewById(R.id.unified_history_iv_clear);
            unified_history_tv_name = (TextView) v.findViewById(R.id.unified_history_tv_name);
        }

    }

}
