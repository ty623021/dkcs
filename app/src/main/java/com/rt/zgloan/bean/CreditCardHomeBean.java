package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/6 0006.
 * 信用卡首页
 */

public class CreditCardHomeBean {

    private List<CreditCardBean> hotCardList;
    private List<SubjectBean> subjectList;
    private List<BankBean> bankList;
    private List<CreditCardBean> recomList;
    private List<PurposeBean> purposeList;
    private List<CreditBannerBean> slideList;

    public List<CreditBannerBean> getSlideList() {
        return slideList;
    }
    public List<CreditCardBean> getHotCardList() {
        return hotCardList;
    }

    public List<CreditCardBean> getRecomList() {
        return recomList;
    }

    public List<SubjectBean> getSubjectList() {
        return subjectList;
    }

    public List<BankBean> getBankList() {
        return bankList;
    }

    public List<PurposeBean> getPurposeList() {
        return purposeList;
    }

    public static class CreditBannerBean {
        public String slideId;
        public String slideName;
        public String slidePic;
        public String slideUrl;
    }

    //按主题选卡
    public static class SubjectBean {
        public int id;
        public String name;//淘宝网银联金卡
        public String summary;//剁手族必备  网购超划算
        public String logo;//http://192.168.8.44/data/upload/portal/20171225/5a40733ad61c8.png
    }

    //按银行选卡
    public static class BankBean {
        public int id;
        public String name;//淘宝网银联金卡
        public String summary;//剁手族必备  网购超划算
        public String logo;//http://192.168.8.44/data/upload/portal/20171225/5a40733ad61c8.png
    }


    //按用途选卡
    public static class PurposeBean {
        public int id;
        public String name;//淘宝网银联金卡
        public String logo;//http://192.168.8.44/data/upload/portal/20171225/5a40733ad61c8.png
    }
}
