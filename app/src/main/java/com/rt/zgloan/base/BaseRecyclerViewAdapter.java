package com.rt.zgloan.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rt.zgloan.util.CollectionUtil;
import com.rt.zgloan.util.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/5/16...
 */

public abstract class BaseRecyclerViewAdapter<T, E extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<E> {
    public List<T> list;
    public LayoutInflater mInflater;
    public Context mContext;

    public BaseRecyclerViewAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public E onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        mInflater = LayoutInflater.from(mContext);
        return mCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(E holder, int position) {
        mBindViewHolder(holder, position);
    }

    public abstract E mCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void mBindViewHolder(E holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        if (!CollectionUtil.isEmpty(list)) {
            this.list = list;
            notifyDataSetChanged();
        } else {
            if (getItemCount() > 0) {
                this.list.clear();
                notifyDataSetChanged();
            }
        }
    }

    public void add(T t) {
        this.list.add(t);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        if (!CollectionUtil.isEmpty(list)) {
            this.list.addAll(list);
            notifyDataSetChanged();
        } else {
            ToastUtil.showToast("已加载全部");
        }
    }

    public void remove(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

}
