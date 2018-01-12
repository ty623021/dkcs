package com.rt.zgloan.bean;

/**
 * Created by zcy on 2017/11/6 0006.
 * 信用卡首页
 */

public class LabelHomeListBean {
    private int type;
    private Object obj;

    public LabelHomeListBean(int type, Object obj) {
        this.type = type;
        this.obj = obj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
