package com.rt.zgloan.dbentity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/26.
 */
@DatabaseTable(tableName = "tb_userAction")
public class UserAction implements Serializable {
    @DatabaseField
    private String device="";
    @DatabaseField
    private String pageName="";
    @DatabaseField
    private String pageDescribe="";
    @DatabaseField
    private String pageStartTime="";
    @DatabaseField
    private String pageEndTime="";
    @DatabaseField
    private String pageWaiteTime="";

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageEndTime() {
        return pageEndTime;
    }

    public void setPageEndTime(String pageEndTime) {
        this.pageEndTime = pageEndTime;
    }

    public String getPageDescribe() {
        return pageDescribe;
    }

    public void setPageDescribe(String pageDescribe) {
        this.pageDescribe = pageDescribe;
    }

    public String getPageStartTime() {
        return pageStartTime;
    }

    public void setPageStartTime(String pageStartTime) {
        this.pageStartTime = pageStartTime;
    }

    public String getPageWaiteTime() {
        return pageWaiteTime;
    }

    public void setPageWaiteTime(String pageWaiteTime) {
        this.pageWaiteTime = pageWaiteTime;
    }

}
