package com.rt.zgloan.dbentity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */
public class UserActionList implements Serializable {
    private List<UserAction> data;

    public List<UserAction> getData() {
        return data;
    }

    public void setData(List<UserAction> data) {
        this.data = data;
    }
}
