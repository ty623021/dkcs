package com.rt.zgloan.base;

import java.util.List;

/**
 * baseview
 */
public interface BaseView<T> {
    /*******内嵌加载*******/
    /**
     * 开始加载dialog
     * @param content dialog显示的内容
     */
    void showLoading(String content);

    /**
     * 停止加载dialog
     */
   // void stopLoading();

    /**
     * 请求失败
     * @param msg  请求异常信息
     * @param type  若有多个请求，用于区分不同请求（不同请求失败或有不同的处理）
     *              PS：无需区分则可传null
     */
    void showErrorMsg(String msg, String type);
    void recordSuccess(T t);
}
