package com.rt.zgloan.util;


import com.rt.zgloan.app.App;

import net.grandcentrix.tray.AppPreferences;


/**
 * 跨进程存储工具类
 */
public class SpUtil {

    public static final String isLogin = "isLogin";
    public static final String userId = "userId";
    public static final String mobile = "mobile";
    public static final String invite_code = "invite_code";
    public static final String is_state = "is_state";
    //判断是不是第一次进app 是的话展示引导页
    public static final String CACHE_IS_FIRST_LOGIN = "FirstLogin";//key
    public static final int HAS_ALREADY_LOGIN = 1;//首次
    public static final int NOT_FIRST_LOGIN = -1;

    public static final String categoryId = "categoryId";//产品分类id
    public static final String categoryPosition = "categoryPosition";//产品分类位置

    public static final String PROVINCE = "province";//省份名称
    public static final String PROVINCE_ID = "province_id";//省份id

    public static final String CITY_NAME = "city_name";//城市名称
    public static final String CITY_ID = "city_id";//城市id


    private static AppPreferences appPreferences;

    public static String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static void putString(String key, String value) {
        getSharedPreferences().put(key, value);
    }


    public static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean value) {
        getSharedPreferences().put(key, value);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int value) {
        return getSharedPreferences().getInt(key, value);
    }

    public static void putInt(String key, int value) {

        getSharedPreferences().put(key, value);
    }

    public static long getLong(String key) {
        return getSharedPreferences().getLong(key, 0);
    }

    public static void putLong(String key, long value) {

        getSharedPreferences().put(key, value);
        throw new IllegalArgumentException("ds");
    }

    public static void remove(String key) {
        getSharedPreferences().remove(key);
    }

    public static AppPreferences getSharedPreferences() {
        if (appPreferences == null) {
            appPreferences = new AppPreferences(App.getContext());
        }
        return appPreferences;
    }

}
