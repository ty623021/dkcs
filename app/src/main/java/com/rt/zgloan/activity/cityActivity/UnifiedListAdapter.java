package com.rt.zgloan.activity.cityActivity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rt.zgloan.R;

import java.util.ArrayList;
import java.util.List;

public class UnifiedListAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_COUNT = 3;
    private Context mContext;
    private UnifiedClickListener listener;
    private LayoutInflater inflater;
    private boolean isShowHot;
    private List<UnifiedBase> hot_show = new ArrayList<>();
    private List<UnifiedBase> hot_all = new ArrayList<>();
    private List<UnifiedBase> history = new ArrayList<>();
    private List<List<UnifiedBase>> all = new ArrayList<>();
    private List<String> litter = new ArrayList<>();

    public UnifiedListAdapter(Context mContext, UnifiedClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        setHot(new ArrayList<UnifiedBase>(), false);
        this.inflater = LayoutInflater.from(mContext);
    }

    public void setHot(List<UnifiedBase> hot, boolean isShow) {
        this.hot_all.clear();
        if (hot != null && hot.size() > 0) {
            hot_all.addAll(hot);
        }
        setHot(isShow);
    }

    public void setHot(boolean isShow) {
        this.isShowHot = isShow;
        this.hot_show.clear();
        if (isShow) {
            hot_show.addAll(hot_all);
        } else {
            if (hot_all.size() > 5) {
                for (int i = 0; i < 5; i++) {
                    hot_show.add(hot_all.get(i));
                }
            } else {
                hot_show.addAll(hot_all);
            }
        }
        if (hot_show.size() >= 5) {
            hot_show.add(new UnifiedBase(""));
        }
        notifyDataSetChanged();
    }

    public void setHistory(List<UnifiedBase> history) {
        this.history.clear();
        if (history != null && history.size() > 0) {
            this.history.addAll(history);
        }
        notifyDataSetChanged();
    }

    public void setAll(List<List<UnifiedBase>> all) {
        this.all.clear();
        if (all != null && all.size() > 0) {
            this.all.addAll(all);
        }
        notifyDataSetChanged();
    }

    public void setLetter(List<String> litter) {
        this.litter.clear();
        if (litter != null && litter.size() > 0) {
            this.litter.addAll(litter);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return litter.size();
    }

    @Override
    public Object getItem(int position) {
        return litter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position > 1 ? 2 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == 0 || viewType == 1) {
            ViewHolder1 holder;
            if (convertView == null) {
                if (viewType == 0) {
                    convertView = inflater.inflate(R.layout.item_lv_unified_hot, parent, false);
                } else {
                    convertView = inflater.inflate(R.layout.item_lv_unified_history, parent, false);
                }
                holder = new ViewHolder1(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder1) convertView.getTag();
            }
            if (viewType == 0) {
                holder.type.setText("当前城市");
                UnifiedHotAdapter hotAdapter = new UnifiedHotAdapter(mContext, listener, isShowHot, history);
                holder.gridView.setAdapter(hotAdapter);
            } else {
                holder.type.setText("热门城市");
                UnifiedHotAdapter hotAdapter = new UnifiedHotAdapter(mContext, listener, isShowHot, hot_show);
                holder.gridView.setAdapter(hotAdapter);
            }
            holder.gridView.setSelector(new ColorDrawable(mContext.getResources().getColor(R.color.transparent)));//去除默认的条目点击效果
        } else {
            ViewHolder2 holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_lv_unified_all, parent, false);
                holder = new ViewHolder2(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder2) convertView.getTag();
            }
            if (all.size() > 0 && position >= 2) {
                UnifiedAllAdapter allAdapter = new UnifiedAllAdapter(mContext, listener, all.get(position - 2));
                holder.lv.setAdapter(allAdapter);
            }
        }
        return convertView;
    }

    public class ViewHolder1 {

        private TextView type;
        private MyGridView gridView;

        public ViewHolder1(View v) {
            type = (TextView) v.findViewById(R.id.tv_type);
            gridView = (MyGridView) v.findViewById(R.id.gv);
        }

    }

    public class ViewHolder2 {

        public ListView lv;

        public ViewHolder2(View v) {
            lv = (ListView) v.findViewById(R.id.unified_all_lv);
        }

    }

}
