package com.rt.zgloan.bean;

/**
 * Created by zcy on 2017/11/10 0010.
 */

public class LoginBySmsCdBean {

    /**
     * id : 5
     * mobile : 13000000000
     * invite_code : SDFFF
     * is_state : 1
     * create_time : 2017-09-12 00:00:00
     */

    private String id;
    private String mobile;
    private String inviteCode;
    private String isState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getIsState() {
        return isState;
    }

    public void setIsState(String isState) {
        this.isState = isState;
    }
}
