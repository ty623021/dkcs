package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/6 0006.
 * 银行信息
 */

public class BankBean {

    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    private List<BankInfo> classify;

    public List<BankInfo> getClassify() {
        return classify;
    }

    public void setClassify(List<BankInfo> classify) {
        this.classify = classify;
    }

    public static class BankInfo {
        private String id;
        private String logo;
        private String name;
        private String summary;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
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
    }


}
