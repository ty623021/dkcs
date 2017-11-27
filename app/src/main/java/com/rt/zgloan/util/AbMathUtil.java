package com.rt.zgloan.util;

import java.math.BigDecimal;

/**
 * Created by geek on 2016/6/21.
 */
public class AbMathUtil {
    /**
     * 四舍五入.获取一个BigDecimal类型的数据
     *
     * @param number  原数
     * @param decimal 保留几位小数
     * @return 四舍五入后的值  数字
     */
    public static BigDecimal round(double number, int decimal) {
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 四舍五入. 获取一个字符串类型的数据
     *
     * @param number  原数
     * @param decimal 保留几位小数
     * @return 四舍五入后的值 字符串
     */
    public static String roundStr(double number, int decimal) {
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 四舍五入. 获取一个字符串类型的数据
     *
     * @param number  原数
     * @param decimal 保留几位小数
     * @return 四舍五入后的值 字符串
     */
    public static String roundStr(String number, int decimal) {
        if (number == null) {
            return "0.00";
        }
        return new BigDecimal(number).setScale(decimal, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 判断利率是否需要显示小数
     *
     * @return
     */
    public static String isPercentStr(double v) {
        String percent;
        if (v > (int) v) {
            BigDecimal round = round(v, 2);
            percent = round.toString();
        } else {
            BigDecimal round = round(v, 0);
            percent = round.toString();
        }
        return percent;
    }

    /**
     * 判断利率是否需要显示小数
     *
     * @return
     */
    public static String isPercentStr(String percent) {
        if (AbStringUtil.isEmpty(percent)) {
            double v = Double.parseDouble(percent);
            if (v > (int) v) {
                BigDecimal round = round(v, 1);
                percent = round.toString();
            } else {
                BigDecimal round = round(v, 0);
                percent = round.toString();
            }
        }
        return percent;
    }

    /**
     * 四舍五入. 获取一个字符串类型的数据
     *
     * @param number  原数
     * @param decimal 保留几位小数
     * @return 小数点后几位字符串，不四舍五入
     */
    public static double roundStrs(double number, int decimal) {
        if (AbStringUtil.isEmpty(number + "")) {
            return 0.00;
        }
        String s = String.valueOf(number);
        int start = s.indexOf(".");
        if (start != -1) {
            if (s.length() > start + decimal) {
                String substring = s.substring(0, start + decimal + 1);
                return Double.parseDouble(substring);
            }
        }
        return number;
    }


}
