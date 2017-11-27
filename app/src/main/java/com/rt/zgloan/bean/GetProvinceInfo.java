package com.rt.zgloan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hjy on 2017/8/31.
 */

public class GetProvinceInfo implements Serializable {
    private List<GetProvinceSonInfo> relute;

    public List<GetProvinceSonInfo> getRelute() {
        return relute;
    }

    public void setRelute(List<GetProvinceSonInfo> relute) {
        this.relute = relute;
    }
}
