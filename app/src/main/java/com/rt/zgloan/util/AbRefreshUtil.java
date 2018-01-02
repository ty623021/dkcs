package com.rt.zgloan.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.rt.zgloan.pullView.AbPullToRefreshView;

/**
 * Created by geek on 2016/7/22.
 * 处理刷新操作类
 */
public class AbRefreshUtil {

    /**
     * 初始化下拉刷新
     *
     * @param pull     下拉刷新控件
     * @param listener 下拉刷新监听
     */
    public static void initRefresh(AbPullToRefreshView pull, AbPullToRefreshView.OnHeaderRefreshListener listener) {
        pull.setLoadMoreEnable(false);
        pull.clearFooter();
        pull.setOnHeaderRefreshListener(listener);
        //是否直接下拉刷新
//        pull.headerRefreshing();
    }

    /**
     * 初始化下拉刷新和上拉加载
     *
     * @param pull            拉刷新控件
     * @param refreshListener 下拉刷新监听
     * @param loadListener    上拉加载监听
     */
    public static void initRefresh(AbPullToRefreshView pull, AbPullToRefreshView.OnHeaderRefreshListener refreshListener, AbPullToRefreshView.OnFooterLoadListener loadListener) {
//        pull.setLoadMoreEnable(false);
//        pull.clearFooter();
        pull.setOnHeaderRefreshListener(refreshListener);
        pull.setOnFooterLoadListener(loadListener);
        //是否直接下拉刷新
//        pull.headerRefreshing();
    }

    /**
     * 初始化上拉加载
     *
     * @param pull         拉刷新控件
     * @param loadListener 上拉加载监听
     */
    public static void initRefresh(AbPullToRefreshView pull, AbPullToRefreshView.OnFooterLoadListener loadListener) {
        pull.setOnFooterLoadListener(loadListener);
        pull.setPullRefreshEnable(false);
    }


    /**
     * 用listView列表页面的网络请求
     *
     * @param adapter   适配器
     * @param isNetwork 是否显示网络链接失败
     * @param network   包裹网络加载失败和无数据的布局
     * @param nodata    没有请求到数据
     */
    public static void hintView(Adapter adapter, boolean isNetwork, View network, View nodata) {
        if (adapter.getCount() == 0) {
            network.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            network.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    /**
     * 用recycleView列表页面的网络请求
     *
     * @param adapter   适配器
     * @param isNetwork 是否显示网络链接失败
     * @param network   包裹网络加载失败和无数据的布局
     * @param nodata    没有请求到数据
     */
    public static void hintView(AbPullToRefreshView pull, RecyclerView.Adapter adapter, boolean isNetwork, LinearLayout network, LinearLayout nodata) {
        pull.onHeaderRefreshFinish();
        pull.onFooterLoadFinish();
        if (adapter.getItemCount() == 0) {
            network.setVisibility(View.VISIBLE);
            if (isNetwork) {
                network.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
            } else {
                network.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
            }
        } else {
            network.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }


    /**
     * 判断是否需要加载更多
     *
     * @param isRefresh 刷新状态
     * @param count     列表当前数量
     * @param countAll  列表的总数
     * @param pull      下拉刷新控件
     */
    public static void isLoading(boolean isRefresh, int count, int countAll, AbPullToRefreshView pull) {
        if (isRefresh) {
            pull.onHeaderRefreshFinish();
        } else {
            pull.onFooterLoadFinish();
        }
        //当偏移量大于等于请求到的总数的时候，关闭上拉加载的功能
        if (count >= 0 && count >= countAll) {
            pull.clearFooter();
            pull.setLoadMoreEnable(false);
            if (!isRefresh) {
                ToastUtil.showToast("已加载全部");
            }
        } else {
            pull.addFooter();
            pull.setLoadMoreEnable(true);
        }
    }

    /**
     * 判断是否需要加载更多
     *
     * @param nextPage  下一页
     * @param totalPage 列表的总数
     * @param pull      下拉刷新控件
     */
    public static boolean isLoad(int nextPage, int totalPage, AbPullToRefreshView pull) {
        //当偏移量大于等于请求到的总数的时候，关闭上拉加载的功能
        if (nextPage >= totalPage) {
            pull.onFooterLoadFinish();
        } else {
            return true;
        }
        return false;
    }

    /**
     * 判断是否需要加载更多
     *
     * @param nextPage  下一页
     * @param totalPage 列表的总数
     */
    public static boolean isLoad(int nextPage, int totalPage) {
        //当偏移量大于请求到的总数的时候，关闭上拉加载的功能
        if (nextPage > totalPage) {
            return false;
        } else {
            return true;
        }
    }
}
