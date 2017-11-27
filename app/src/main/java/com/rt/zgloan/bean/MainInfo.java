package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by hjy on 2017/8/24.
 */

public class MainInfo {


    private List<BannerItemInfo> banner;//banner图的列表
    private List<ArticleInfo> comment;//消息列表
    private List<HomeListInfo> relute;//首页贷款列表

    public List<HomeListInfo> getRelute() {
        return relute;
    }

    public void setRelute(List<HomeListInfo> relute) {
        this.relute = relute;
    }

    public List<ArticleInfo> getComment() {
        return comment;
    }

    public void setComment(List<ArticleInfo> comment) {
        this.comment = comment;
    }

    public List<BannerItemInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerItemInfo> banner) {
        this.banner = banner;
    }
}
