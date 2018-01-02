package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/6 0006.
 * 信用卡首页
 */

public class CreditCardHomeListBean {
    private int type;
    private List list;

    public CreditCardHomeListBean(int type, List list) {
        this.type = type;
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
