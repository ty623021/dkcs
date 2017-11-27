package com.rt.zgloan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 * 活期列表
 */
public class TestBean {
//    private String name;//名称
//
//    public double getInvestMin() {
//        return investMin;
//    }
//
//    public void setInvestMin(double investMin) {
//        this.investMin = investMin;
//    }
//
//    public double getRewardInterest() {
//        return rewardInterest;
//    }
//
//    public void setRewardInterest(double rewardInterest) {
//        this.rewardInterest = rewardInterest;
//    }
//
//    private double investMin;//起投金额
//    private double rewardInterest;//加息收益率
    private String userName;
    private List<ItemBean> arr;
    private int pwd;

    public List<ItemBean> getArr() {
        return arr;
    }

    public void setArr(List<ItemBean> arr) {
        this.arr = arr;
    }

    public int getPwd() {
        return pwd;
    }

    public void setPwd(int pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
