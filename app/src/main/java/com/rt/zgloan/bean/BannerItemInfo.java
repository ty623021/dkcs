package com.rt.zgloan.bean;


/**
 * banner 图对象
 */
public class BannerItemInfo {

    //banner图的实体类
    private String slide_pic;//图片地址
    private int slide_id;
    private String slide_url;

    public String getUrl() {
        return slide_url;
    }

    public void setUrl(String url) {
        this.slide_url = url;
    }

    public int getSlide_id() {
        return slide_id;
    }

    public void setSlide_id(int slide_id) {
        this.slide_id = slide_id;
    }

    public String getSlide_pic() {
        return slide_pic;
    }

    public void setSlide_pic(String slide_pic) {
        this.slide_pic = slide_pic;
    }

}
