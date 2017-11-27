package com.rt.zgloan.bean;

/**
 * Created by yinsujun on 2016/1/25 0025  16.
 * 版本更新描述
 */
public class Version {
    private String id;
    private String version_number;
    private String canal_id;//渠道id
    private String is_update;//是否强制更新（0 否 1 是）
    private String description;
    private String url;//下载地址
    private String version_name;
    private String is_new;//版本是否是最新（0不是 1是）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCanal_id() {
        return canal_id;
    }

    public void setCanal_id(String canal_id) {
        this.canal_id = canal_id;
    }

    public String getVersion_number() {
        return version_number;
    }

    public void setVersion_number(String version_number) {
        this.version_number = version_number;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_update() {
        return is_update;
    }

    public void setIs_update(String is_update) {
        this.is_update = is_update;
    }

}
