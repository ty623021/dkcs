package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/7 0007.
 */

public class LoanDetailBean {
    /**
     * id : 5
     * name : 团贷网
     * image_url : http://chaoshi.rohao.cn/data/upload/
     * loan_type : 3
     * loan_number : 3
     * money_sml : 1000
     * money_big : 200000
     * deadline : ["111"]
     * rate : ["5.89"]
     * deadline_sml : 2
     * deadline_big : 12
     * material : [{"url":"http://chaoshi.rohao.cn/data/upload/portal/20170901/59a8b684be6ae.png","name":"基本信息","id":"3"},{"url":"http://chaoshi.rohao.cn/data/upload/portal/20170901/59a8b5cf35a2e.png","name":"芝麻信用","id":"4"},{"url":"http://chaoshi.rohao.cn/data/upload/portal/20170901/59a8b6d3bb577.png","name":"公积金","id":"8"},{"url":"http://chaoshi.rohao.cn/data/upload/portal/20170901/59a8b6efa4358.png","name":"保险授信","id":"9"},{"url":"http://chaoshi.rohao.cn/data/upload/portal/20170901/59a8b70f0d144.png","name":"中央征信","id":"10"}]
     * application_procedure : 2
     * product_description : 2
     * repayment_instructions : 2
     * rising_range : 2
     * jump_url : https://www.baidu.com/
     */

    private String id;
    private String name;
    private String image_url;
    private String loan_type;
    private String loan_number;
    private String money_sml;
    private String money_big;
    private String deadline_sml;
    private String deadline_big;
    private String application_procedure;
    private String product_description;
    private String repayment_instructions;
    private String rising_range;
    private String jump_url;
    private String[] deadline;
    private String[] rate;
    private String rate_type;
    private List<MaterialBean> material;

    public String getRate_type() {
        return rate_type;
    }

    public void setRate_type(String rate_type) {
        this.rate_type = rate_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLoan_type() {
        return loan_type;
    }

    public void setLoan_type(String loan_type) {
        this.loan_type = loan_type;
    }

    public String getLoan_number() {
        return loan_number;
    }

    public void setLoan_number(String loan_number) {
        this.loan_number = loan_number;
    }

    public String getMoney_sml() {
        return money_sml;
    }

    public void setMoney_sml(String money_sml) {
        this.money_sml = money_sml;
    }

    public String getMoney_big() {
        return money_big;
    }

    public void setMoney_big(String money_big) {
        this.money_big = money_big;
    }

    public String getDeadline_sml() {
        return deadline_sml;
    }

    public void setDeadline_sml(String deadline_sml) {
        this.deadline_sml = deadline_sml;
    }

    public String getDeadline_big() {
        return deadline_big;
    }

    public void setDeadline_big(String deadline_big) {
        this.deadline_big = deadline_big;
    }

    public String getApplication_procedure() {
        return application_procedure;
    }

    public void setApplication_procedure(String application_procedure) {
        this.application_procedure = application_procedure;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getRepayment_instructions() {
        return repayment_instructions;
    }

    public void setRepayment_instructions(String repayment_instructions) {
        this.repayment_instructions = repayment_instructions;
    }

    public String getRising_range() {
        return rising_range;
    }

    public void setRising_range(String rising_range) {
        this.rising_range = rising_range;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public String[] getDeadline() {
        return deadline;
    }

    public void setDeadline(String[] deadline) {
        this.deadline = deadline;
    }

    public String[] getRate() {
        return rate;
    }

    public void setRate(String[] rate) {
        this.rate = rate;
    }

    public List<MaterialBean> getMaterial() {
        return material;
    }

    public void setMaterial(List<MaterialBean> material) {
        this.material = material;
    }

    public static class MaterialBean {
        /**
         * url : http://chaoshi.rohao.cn/data/upload/portal/20170901/59a8b684be6ae.png
         * name : 基本信息
         * id : 3
         */

        private String url;
        private String name;
        private String id;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
