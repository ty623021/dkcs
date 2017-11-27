package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/3 0003.
 */

public class BannerListBean {
    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        banner = banner;
    }

    List<BannerBean> banner;

    public static class BannerBean {
        /**
         * slide_id : 1
         * slide_pic : http://chaoshi.rohao.cn/data/upload/admin/20170901/59a8cb9827855.jpg
         * slide_url : https://www.baidu.com/
         */

        private String slide_id;
        private String slide_pic;
        private String slide_url;

        public String getSlide_id() {
            return slide_id;
        }

        public void setSlide_id(String slide_id) {
            this.slide_id = slide_id;
        }

        public String getSlide_pic() {
            return slide_pic;
        }

        public void setSlide_pic(String slide_pic) {
            this.slide_pic = slide_pic;
        }

        public String getSlide_url() {
            return slide_url;
        }

        public void setSlide_url(String slide_url) {
            this.slide_url = slide_url;
        }
    }

}
