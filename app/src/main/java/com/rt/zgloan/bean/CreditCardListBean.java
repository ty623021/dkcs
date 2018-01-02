package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/6 0006.
 * 信用卡列表
 */

public class CreditCardListBean {
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private List<CreditCardBean> classify;

    public List<CreditCardBean> getClassify() {
        return classify;
    }

    public void setClassify(List<CreditCardBean> classify) {
        this.classify = classify;
    }
}
