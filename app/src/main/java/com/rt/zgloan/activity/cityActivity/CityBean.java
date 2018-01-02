package com.rt.zgloan.activity.cityActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28 0028.
 * 城市
 */

public class CityBean {

    private List<UnifiedBase> hotCityList;//热门城市
    private List<UnifiedBase> cityList;//城市列表

    public List<UnifiedBase> getHotCityList() {
        return hotCityList;
    }

    public void setHotCityList(List<UnifiedBase> hotCityList) {
        this.hotCityList = hotCityList;
    }

    public List<UnifiedBase> getCityList() {
        return cityList;
    }

    public void setCityList(List<UnifiedBase> cityList) {
        this.cityList = cityList;
    }
}
