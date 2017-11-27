package com.rt.zgloan.bean;

import java.util.List;

/**
 * Created by zcy on 2017/11/10 0010.
 */

public class CitiesBean {

    private List<CityBean> city;

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * id : 1
         * name : 北京市
         */

        private String id;
        private String name;

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
    }
}
