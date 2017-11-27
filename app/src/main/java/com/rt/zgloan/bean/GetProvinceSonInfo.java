package com.rt.zgloan.bean;

import java.io.Serializable;

/**
 * Created by hjy on 2017/8/31.
 */

public class GetProvinceSonInfo implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
