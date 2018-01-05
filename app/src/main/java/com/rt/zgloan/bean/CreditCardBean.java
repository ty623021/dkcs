package com.rt.zgloan.bean;

/**
 * Created by zcy on 2017/11/6 0006.
 * 信用卡首页
 */

public class CreditCardBean {

    private int id;
    private String name;//淘宝网银联金卡
    private String summary;//剁手族必备  网购超划算
    private String applicants;//
    private String pointsOne;//百度钱包刷卡返现3%
    private String pointsTwo;//4小时1000元航班延误险
    private String labelsOne;//刷卡免年费
    private String labelsTwo;//刷卡免年费
    private String img;//http://192.168.8.44/data/upload/portal/20171225/5a40733ad61c8.png
    private String showType;
    private String linkUrl; //外链地址

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getApplicants() {
        return applicants;
    }

    public void setApplicants(String applicants) {
        this.applicants = applicants;
    }

    public String getPointsOne() {
        return pointsOne;
    }

    public void setPointsOne(String pointsOne) {
        this.pointsOne = pointsOne;
    }

    public String getPointsTwo() {
        return pointsTwo;
    }

    public void setPointsTwo(String pointsTwo) {
        this.pointsTwo = pointsTwo;
    }

    public String getLabelsOne() {
        return labelsOne;
    }

    public void setLabelsOne(String labelsOne) {
        this.labelsOne = labelsOne;
    }

    public String getLabelsTwo() {
        return labelsTwo;
    }

    public void setLabelsTwo(String labelsTwo) {
        this.labelsTwo = labelsTwo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
