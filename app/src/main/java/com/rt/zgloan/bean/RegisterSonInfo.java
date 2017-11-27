package com.rt.zgloan.bean;

import com.rt.zgloan.util.SpUtil;

/**
 * Created by hjy on 2017/8/30.
 */

public class RegisterSonInfo {

    private String id;//用户唯一标识id
    private String create_time;//创建时间
    private String mobile;//用户手机号码
    private String invite_code;//邀请码
    private String is_state;//0未完善 1是完善

    public String getIs_state() {
        return is_state;
    }

    public void setIs_state(String is_state) {
        this.is_state = is_state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
