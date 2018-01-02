package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/6 0006.
 * 首页列表
 */
public class LabelListBean {
    public List<LabelBean> getLabel() {
        return label;
    }

    public void setLabel(List<LabelBean> label) {
        this.label = label;
    }

    private List<LabelBean> label;

    public static class LabelBean {
        /**
         * id : 1
         * image_url : 标签图标
         * name : 极速借款
         */
        private int id;
        private String image_url;
        private String name;
        private List<LabelLoansBean> loans;//借款标的列表
        private List<CreditCardBean> cards;//信用卡推荐列表

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<LabelLoansBean> getLoans() {
            return loans;
        }

        public void setLoans(List<LabelLoansBean> loans) {
            this.loans = loans;
        }

        public List<CreditCardBean> getCards() {
            return cards;
        }

        public void setCards(List<CreditCardBean> cards) {
            this.cards = cards;
        }

        /**
         * id : 35
         * name : null
         * money_sml : null
         * money_big : null
         * deadline_sml : null
         * deadline_big : null
         * image_url : http://chaoshi.rohao.cn/data/upload/
         * rate : 10
         * propaganda_language : aaaaa
         * rate_type : 1
         */
        public static class LabelLoansBean {
            private int id;
            private String name;
            private int money_sml;
            private int money_big;
            private int deadline_sml;
            private int deadline_big;
            private String image_url;
            private String rate;
            private String propaganda_language;
            private int rate_type;

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

            public int getMoney_sml() {
                return money_sml;
            }

            public void setMoney_sml(int money_sml) {
                this.money_sml = money_sml;
            }

            public int getMoney_big() {
                return money_big;
            }

            public void setMoney_big(int money_big) {
                this.money_big = money_big;
            }

            public int getDeadline_sml() {
                return deadline_sml;
            }

            public void setDeadline_sml(int deadline_sml) {
                this.deadline_sml = deadline_sml;
            }

            public int getDeadline_big() {
                return deadline_big;
            }

            public void setDeadline_big(int deadline_big) {
                this.deadline_big = deadline_big;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getPropaganda_language() {
                return propaganda_language;
            }

            public void setPropaganda_language(String propaganda_language) {
                this.propaganda_language = propaganda_language;
            }

            public int getRate_type() {
                return rate_type;
            }

            public void setRate_type(int rate_type) {
                this.rate_type = rate_type;
            }
        }

    }
}
