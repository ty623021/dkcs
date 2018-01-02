package com.rt.zgloan.activity.cityActivity;

public class UnifiedBase extends BaseBean {

    public int id;//城市ID
    public int parentId;//省ID
    public int level;//级别
    public String name;//城市名称
    public String pinyinFull;//拼音全拼
    public String pinyinAbbr;//拼音简拼
    public String pinyinFirst;//拼音首字母

    public UnifiedBase() {

    }

    public UnifiedBase(String city_name, String pinyin_full) {
        this.name = city_name;
        this.pinyinFull = pinyin_full;
    }

    public UnifiedBase(int city_id, String city_name) {
        this.id = city_id;
        this.name = city_name;
    }

    public UnifiedBase(int city_id, String city_name, String pinyin_full, String pinyin_abbr, String pinyin_first) {
        this.id = city_id;
        this.name = city_name;
        this.pinyinFull = pinyin_full;
        this.pinyinAbbr = pinyin_abbr;
        this.pinyinFirst = pinyin_first;
    }


    public UnifiedBase(String text) {
        this.name = text;
    }

}
