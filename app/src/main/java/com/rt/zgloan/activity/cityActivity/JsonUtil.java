package com.rt.zgloan.activity.cityActivity;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Json序列化工具包
 *
 * @author Sun
 * @modify 2012-5-12
 * @since 2012-1-19
 */
public class JsonUtil {

    private static final String CHARSET = "UTF-8";

    private JsonUtil() {

    }

    public static JsonUtil json;

    static {
        if (json == null) {
            synchronized (JsonUtil.class) {
                if (json == null) {
                    json = new JsonUtil();
                }
            }
        }
    }

    /**
     * 将json转换成为java对象
     *
     * @param str
     * @param classOfT
     * @return
     */
    public static <T> T Json2T(String str, Class<T> classOfT) {
        if (null == str) {
            return null;
        }
        try {
            return JSON.parseObject(str, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("JsonUtil.Json2T", "msg:" + str + "\nclazz:" + classOfT.getName());
            return null;
        }
    }

    /**
     * 将json转换成为java数组
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> List<T> Json2Lists(String json, Class<T> classOfT) {
        if (null == json) {
            return null;
        }
        try {
            return JSON.parseArray(json, classOfT);
        } catch (Exception e) {
            Log.e("JsonUtil.Json2T", "msg:" + json + "\r\nclazz:" + classOfT.getName());
            return null;
        }
    }


}
