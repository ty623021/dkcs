package com.rt.zgloan.bean;

/**
 * Created by hjy on 2017/8/24.
 */

public class HomeListInfo {
    private String id;//贷款列表id
    private String name;//贷款名称
    private String money_big;//最高贷款金额
    private String fast_time;//最快放款时间
    private String deadline_big;//最大借款期限
    private String loan_tag1;//借款标签1
    private String loan_tag2;//借款标签2
    private String loan_tag3;//借款标签3
    private String smeta;//图片地址
    private String people;//借款人数
    private String loan_tag1_color;
    private String loan_tag2_color;
    private String loan_tag3_color;
    private String loan_type;//0无  1活动  2最新  3热门


    public String getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public String getLoan_tag1_color() {
        return loan_tag1_color;
    }

    public void setLoan_tag1_color(String loan_tag1_color) {
        this.loan_tag1_color = loan_tag1_color;
    }

    public String getLoan_tag3_color() {
        return loan_tag3_color;
    }

    public void setLoan_tag3_color(String loan_tag3_color) {
        this.loan_tag3_color = loan_tag3_color;
    }

    public String getLoan_tag2_color() {
        return loan_tag2_color;
    }

    public void setLoan_tag2_color(String loan_tag2_color) {
        this.loan_tag2_color = loan_tag2_color;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getSmeta() {
        return smeta;
    }

    public void setSmeta(String smeta) {
        this.smeta = smeta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoan_tag3() {
        return loan_tag3;
    }

    public void setLoan_tag3(String loan_tag3) {
        this.loan_tag3 = loan_tag3;
    }

    public String getLoan_tag2() {
        return loan_tag2;
    }

    public void setLoan_tag2(String loan_tag2) {
        this.loan_tag2 = loan_tag2;
    }

    public String getLoan_tag1() {
        return loan_tag1;
    }

    public void setLoan_tag1(String loan_tag1) {
        this.loan_tag1 = loan_tag1;
    }

    public String getDeadline_big() {
        return deadline_big;
    }

    public void setDeadline_big(String deadline_big) {
        this.deadline_big = deadline_big;
    }

    public String getFast_time() {
        return fast_time;
    }

    public void setFast_time(String fast_time) {
        this.fast_time = fast_time;
    }

    public String getMoney_big() {
        return money_big;
    }

    public void setMoney_big(String money_big) {
        this.money_big = money_big;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
