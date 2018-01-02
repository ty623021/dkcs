package com.rt.zgloan.activity.cityActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/8/4.
 */
public class StrUtils {

    //禁止实例化
    private StrUtils(){}

    /**
     * 判断字符串
     * 字符串为空，返回true
     *
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if ("".equals(str) || str == null || "null".equals(str) || "(null)".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串
     * 字符串为空，返回true
     *
     * @param str
     * @return
     */
    public static boolean isNotNull(String str) {
        if ("".equals(str) || str == null || "null".equals(str) || "(null)".equals(str)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否为中文
     * @param str
     * @return boolean
     */
    public static boolean isChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");;
        Matcher matcher = p.matcher(str);
        return matcher.matches();
    }

}
