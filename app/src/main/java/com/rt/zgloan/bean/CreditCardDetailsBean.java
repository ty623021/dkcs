package com.rt.zgloan.bean;

/**
 * Created by zcy on 2017/11/6 0006.
 * 信用卡详情
 */

public class CreditCardDetailsBean {

    private CardDetailsBean creditCard;

    public CardDetailsBean getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CardDetailsBean creditCard) {
        this.creditCard = creditCard;
    }

    public static class CardDetailsBean {
        private String id;
        private String name;
        private String bankId;
        private String level;
        private String purposeId;
        private String subjectId;
        private boolean recom;
        private String summary;
        private String pointsOne;
        private String pointsTwo;
        private String labelsOne;
        private String labelsTwo;
        private String applicants;
        private String currency;
        private String annualFee;
        private String img;
        private String baseInfo;//基本信息
        private String relateExpense;//费用
        private String specialPrivilege;//特权
        private String useCity;
        private String linkUrl;

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

        public String getBankId() {
            return bankId;
        }

        public void setBankId(String bankId) {
            this.bankId = bankId;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPurposeId() {
            return purposeId;
        }

        public void setPurposeId(String purposeId) {
            this.purposeId = purposeId;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public boolean isRecom() {
            return recom;
        }

        public void setRecom(boolean recom) {
            this.recom = recom;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
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

        public String getApplicants() {
            return applicants;
        }

        public void setApplicants(String applicants) {
            this.applicants = applicants;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getAnnualFee() {
            return annualFee;
        }

        public void setAnnualFee(String annualFee) {
            this.annualFee = annualFee;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getBaseInfo() {
            return baseInfo;
        }

        public void setBaseInfo(String baseInfo) {
            this.baseInfo = baseInfo;
        }

        public String getRelateExpense() {
            return relateExpense;
        }

        public void setRelateExpense(String relateExpense) {
            this.relateExpense = relateExpense;
        }

        public String getSpecialPrivilege() {
            return specialPrivilege;
        }

        public void setSpecialPrivilege(String specialPrivilege) {
            this.specialPrivilege = specialPrivilege;
        }

        public String getUseCity() {
            return useCity;
        }

        public void setUseCity(String useCity) {
            this.useCity = useCity;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }


}
