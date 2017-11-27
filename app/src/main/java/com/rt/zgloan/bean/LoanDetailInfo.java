package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by hjy on 2017/8/24.
 */

public class LoanDetailInfo {
    private String mission;
    private String money_sml;
    private String deadline_sml;
    private String id;
    private String name;
    private String smeta;
    private String post_parent;
    private String money_big;
    private String fast_time;
    private String deadline_big;
    private String loan_tag1;
    private String loan_tag2;
    private String loan_tag3;
    private String loan_tag1_color;
    private String loan_tag2_color;
    private String loan_tag3_color;

    private String rate_type;//1是天 2是月
//    private String rate;
    private String jump_url;
    private List<ApplyMaterialsInfo> material;
    private String[] deadline;
    private String[] rate;


    public String[] getRate() {
        return rate;
    }

    public void setRate(String[] rate) {
        this.rate = rate;
    }

    public String[] getDeadline() {
        return deadline;
    }

    public void setDeadline(String[] deadline) {
        this.deadline = deadline;
    }

    public String getLoan_tag1_color() {
        return loan_tag1_color;
    }

    public void setLoan_tag1_color(String loan_tag1_color) {
        this.loan_tag1_color = loan_tag1_color;
    }

    public String getLoan_tag2_color() {
        return loan_tag2_color;
    }

    public void setLoan_tag2_color(String loan_tag2_color) {
        this.loan_tag2_color = loan_tag2_color;
    }

    public String getLoan_tag3_color() {
        return loan_tag3_color;
    }

    public void setLoan_tag3_color(String loan_tag3_color) {
        this.loan_tag3_color = loan_tag3_color;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }


    public String getRate_type() {
        return rate_type;
    }

    public void setRate_type(String rate_type) {
        this.rate_type = rate_type;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getMoney_sml() {
        return money_sml;
    }

    public void setMoney_sml(String money_sml) {
        this.money_sml = money_sml;
    }

    public String getDeadline_sml() {
        return deadline_sml;
    }

    public void setDeadline_sml(String deadline_sml) {
        this.deadline_sml = deadline_sml;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoan_tag1() {
        return loan_tag1;
    }

    public void setLoan_tag1(String loan_tag1) {
        this.loan_tag1 = loan_tag1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmeta() {
        return smeta;
    }

    public void setSmeta(String smeta) {
        this.smeta = smeta;
    }

    public String getPost_parent() {
        return post_parent;
    }

    public void setPost_parent(String post_parent) {
        this.post_parent = post_parent;
    }

    public String getMoney_big() {
        return money_big;
    }

    public void setMoney_big(String money_big) {
        this.money_big = money_big;
    }

    public String getFast_time() {
        return fast_time;
    }

    public void setFast_time(String fast_time) {
        this.fast_time = fast_time;
    }

    public String getDeadline_big() {
        return deadline_big;
    }

    public void setDeadline_big(String deadline_big) {
        this.deadline_big = deadline_big;
    }

    public String getLoan_tag2() {
        return loan_tag2;
    }

    public void setLoan_tag2(String loan_tag2) {
        this.loan_tag2 = loan_tag2;
    }

    public String getLoan_tag3() {
        return loan_tag3;
    }

    public void setLoan_tag3(String loan_tag3) {
        this.loan_tag3 = loan_tag3;
    }

    public List<ApplyMaterialsInfo> getMaterial() {
        return material;
    }

    public void setMaterial(List<ApplyMaterialsInfo> material) {
        this.material = material;
    }
}
