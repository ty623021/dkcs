package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by hjy on 2017/8/30.
 */

public class RegisterInfo {
    private String total_munber;

    private RegisterSonInfo relute;

    public String getTotal_munber() {
        return total_munber;
    }

    public void setTotal_munber(String total_munber) {
        this.total_munber = total_munber;
    }

    public RegisterSonInfo getRelute() {
        return relute;
    }

    public void setRelute(RegisterSonInfo relute) {
        this.relute = relute;
    }

    //    public List<RegisterSonInfo> getRelute() {
//        return relute;
//    }
//
//    public void setRelute(List<RegisterSonInfo> relute) {
//        this.relute = relute;
//    }
}
