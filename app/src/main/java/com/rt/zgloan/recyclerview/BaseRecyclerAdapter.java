package com.rt.zgloan.recyclerview;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现点击事件、HeaderView、FooterView的RecyclerView 适配器
 * @param <VH>  内容Item的ViewHolder类型
 * @param <T>   数据的类型
 *
 *  author:xiejingwen
 */
public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder,T> extends RecyclerView.Adapter<ViewHolder> {
    private OnItemClick onItemClick; //Item点击事件接口
    private OnLongItemClick onLongItemClick; //item长按事件接口
    protected List<T> data=new ArrayList<>();  //数据源
    protected T item;
    protected static final int TYPE_HEADER = 10000; //head类型
    protected static final int TYPE_FOOT = 20000;   //foot类型
    protected static final int TYPE_CONTENT = 0;   //内容类型
    protected Context mContext;
    protected LayoutInflater mInflater;
    private SparseArrayCompat<View> mHeaderViews=new SparseArrayCompat<>(); //header集合

    private SparseArrayCompat<View> mFooterViews=new SparseArrayCompat<>(); //foot 集合

    public abstract VH mOnCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void mOnBindViewHolder(VH holder,int position);

    /**
     * 添加数据
     * @param data
     */
    public void addData(List<T> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clearData(){
        data.clear();
        notifyDataSetChanged();
    }

    /**
     * 获取当前数据集
     * @return
     */
    public List<T> getData(){
        return data;
    }

    /**
     * 添加头部
     * PS:若头部偏左显示，加上headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
     * @param headerView
     * @return
     */
    public void addHeaderView(View headerView){
        headerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mHeaderViews.put(TYPE_HEADER+mHeaderViews.size(),headerView);
        notifyItemChanged(mHeaderViews.size()-1);
    }

    /**
     * 添加尾部
     * @param footView
     * @return
     */
    public void addFooterView(View footView){
        footView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFooterViews.put(TYPE_FOOT+mFooterViews.size(),footView);
        notifyItemChanged(getItemCount()-1);
    }
    /**
     * 添加尾部
     * @param footView
     * @return
     */
    public void addFooterView(View footView,int height){
        footView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        mFooterViews.put(TYPE_FOOT+mFooterViews.size(),footView);
        notifyItemChanged(getItemCount()-1);
    }
    /**
     * 删除所有尾部
     * @return
     */
    public void clearFooterView(){
        mFooterViews.clear();
        notifyDataSetChanged();
    }
    /**
     * 头部总数
     * @return
     */
    public int getHeadersCount()
    {
        return mHeaderViews.size();
    }

    /**
     * 尾部总数
     * @return
     */
    public int getFootersCount()
    {
        return mFooterViews.size();
    }

    /**
     * 当前item是否是头布局
     * @param position
     * @return
     */
    private boolean isHeaderViewPos(int position)
    {
        return position < getHeadersCount();
    }

    /**
     * 当前item是否是尾布局
     * @param position
     * @return
     */
    private boolean isFooterViewPos(int position)
    {
        return position >= getHeadersCount() + getRealItemCount();
    }

    /**
     * 内容长度
     * @return
     */
    private int getRealItemCount(){

        return data.size();

    }

    public void setOnItemClickListener(OnItemClick onItemClick){
        this.onItemClick=onItemClick;
    }

    public void setonLongItemClickListener(OnLongItemClick onLongItemClick){
        this.onLongItemClick=onLongItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        mInflater=LayoutInflater.from(mContext);
        if (mHeaderViews.get(viewType)!=null){
            return new HeaderViewHolder(mHeaderViews.get(viewType));
        }else if (mFooterViews.get(viewType)!=null){
            return new FooterViewHolder(mFooterViews.get(viewType));
        }
        return mOnCreateViewHolder(parent,viewType);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (isHeaderViewPos(position)||isFooterViewPos(position)){
            return;
        }
        if (onItemClick!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mPosition=holder.getLayoutPosition()-mHeaderViews.size();
                    onItemClick.onItemClick(v,mPosition);
                }
            });
        }
        if (onLongItemClick!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int mPosition=holder.getLayoutPosition()-mHeaderViews.size();
                    onLongItemClick.onLongItemClick(v,mPosition);
                    return true;
                }
            });
        }
        position-=mHeaderViews.size();
        item =data.get(position);
        mOnBindViewHolder((VH) holder,position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)){
            return mHeaderViews.keyAt(position);
        }else if (isFooterViewPos(position)){
            return mFooterViews.keyAt(position-getHeadersCount()-getRealItemCount());
        }else{
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return getHeadersCount()+getRealItemCount()+getFootersCount();
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
    static class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
    //设置HeaderView与FooterView兼容GridLayoutManager
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager= (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType=getItemViewType(position);
                    if (mHeaderViews.get(viewType)!=null||mFooterViews.get(viewType)!=null){
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }
    //设置HeaderView与FooterView兼容StaggeredGridLayoutManager
    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position=holder.getLayoutPosition();
        if (isHeaderViewPos(position)||isFooterViewPos(position)){
           ViewGroup.LayoutParams lp=holder.itemView.getLayoutParams();
            if (lp!=null&&lp instanceof StaggeredGridLayoutManager.LayoutParams){
                StaggeredGridLayoutManager.LayoutParams p= (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * recycleView Item点击事件
     * xiejingwen
     */
    public interface OnItemClick {
        void onItemClick(View view, int position);
    }

    /**
     * recycleView Item长按事件
     * xiejingwen
     */
    public interface OnLongItemClick {
        void onLongItemClick(View view, int position);
    }
}

