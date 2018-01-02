package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28 0028.
 * 信用卡定位的城市
 */

public class CreditCardAddressBean {

    private List<AddressBean> addrList;

    public List<AddressBean> getAddrList() {
        return addrList;
    }

    public void setAddrList(List<AddressBean> addrList) {
        this.addrList = addrList;
    }

    public static class AddressBean {
        private String name;//具体地址
        private String admName;//省市区
        private String admCode;//城市代码

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAdmName() {
            return admName;
        }

        public void setAdmName(String admName) {
            this.admName = admName;
        }

        public String getAdmCode() {
            return admCode;
        }

        public void setAdmCode(String admCode) {
            this.admCode = admCode;
        }
    }
}
