package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/6 0006.
 */

public class LoanClassListBean {
    public List<LoanClassBean> getClassify() {
        return classify;
    }

    public void setClassify(List<LoanClassBean> classify) {
        this.classify = classify;
    }

    List<LoanClassBean> classify;

    public static class LoanClassBean {

        /**
         * id : 1
         * name : 极速借款
         * pic : http://chaoshi.rohao.cn/data/upload/admin/20170901/59a8cb9827855.jpg
         * describe : 极速借款sssdasda
         */

        private int id;
        private String name;
        private String pic;
        private String describe;
        /**
         * 记录是否选中
         */
        private boolean isSelected;

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

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }
    }
}
