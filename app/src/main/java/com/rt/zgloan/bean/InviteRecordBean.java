package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/10 0010.
 */

public class InviteRecordBean {
    List<InviteDataBean> inviteData;
    private String count;

    public List<InviteDataBean> getInviteData() {
        return inviteData;
    }

    public void setInviteData(List<InviteDataBean> inviteData) {
        this.inviteData = inviteData;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    private String totalPages;

    private String inviteCode;

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public class InviteDataBean {

        /**
         * id : 35
         * mobile : 13000000000
         * creatime : null
         */

        private String id;
        private String mobile;
        private String creatTime;

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

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }
    }
}
