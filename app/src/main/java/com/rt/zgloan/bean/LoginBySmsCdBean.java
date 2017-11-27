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
    private String invite_code;
    private String is_state;
    private String create_time;

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

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getIs_state() {
        return is_state;
    }

    public void setIs_state(String is_state) {
        this.is_state = is_state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
