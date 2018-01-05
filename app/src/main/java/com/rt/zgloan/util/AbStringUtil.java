package com.rt.zgloan.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by geek on 2016/6/21.
 */
public class AbStringUtil {
    /**
     * 描述：将null转化为“”
     *
     * @param str 指定的字符串
     * @return 字符串的String类型
     */
    public static String parseEmpty(String str) {
        if (str == null || "null".equals(str.trim())) {
            str = "";
        }
        return str.trim();
    }

    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param str 指定的字符串
     * @return true or false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 || str.equals("") || str.equals("null") || str.equals("NULL");
    }

    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isListEmpty(List<?> list) {
        return list != null && list.size() > 0;
    }


    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param key
     * @param values
     * @return true or false
     */
    public static boolean isEmpty(String key, String values) {
        return key == null || key.trim().length() == 0 || values == null || values.trim().length() == 0;
    }


    /**
     * 获取字符串中文字符的长度（每个中文算2个字符）.
     *
     * @param str 指定的字符串
     * @return 中文字符的长度
     */
    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                /* 获取一个字符 */
                String temp = str.substring(i, i + 1);
                /* 判断是否为中文字符 */
                if (temp.matches(chinese)) {
                    valueLength += 2;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return 字符串的长度（中文字符计2个）
     */
    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    //中文字符长度为2
                    valueLength += 2;
                } else {
                    //其他字符长度为1
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取指定长度的字符所在位置.
     *
     * @param str  指定的字符串
     * @param maxL 要取到的长度（字符长度，中文字符计2个）
     * @return 字符的所在位置
     */
    public static int subStringLength(String str, int maxL) {
        int currentIndex = 0;
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            //获取一个字符
            String temp = str.substring(i, i + 1);
            //判断是否为中文字符
            if (temp.matches(chinese)) {
                //中文字符长度为2
                valueLength += 2;
            } else {
                //其他字符长度为1
                valueLength += 1;
            }
            if (valueLength >= maxL) {
                currentIndex = i;
                break;
            }
        }
        return currentIndex;
    }

    /**
     * 描述：手机号格式验证.
     *
     * @param str 指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
    public static Boolean isMobileNo(String str) {
        Boolean isMobileNo = false;
        try {
            Pattern p = Pattern.compile("^((12[0-9])|(13[0-9])|(14[0-9])|(17[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$");
//            Pattern p = Pattern.compile("(^(([0\\\\+]\\\\d{2,3}-)?(0\\\\d{2,3})-)(\\\\d{7,8})(-(\\\\d{3,}))?$)|(^(([0\\\\+]\\\\d{2,3})?(0\\\\d{2,3}))(\\\\d{7,8})((\\\\d{3,}))?$)|(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)");
            Matcher m = p.matcher(str);
            isMobileNo = m.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isMobileNo;
    }

    /**
     * 描述：是否只是字母和数字.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    /**
     * 描述：是否只是数字.
     *
     * @param str 指定的字符串
     * @return 是否只是数字:是为true，否则false
     */
    public static Boolean isNumber(String str) {
        Boolean isNumber = false;
        String expr = "^[0-9]+$";
        if (str.matches(expr)) {
            isNumber = true;
        }
        return isNumber;
    }

    /**
     * 描述：校验密码格式
     * 允许的特殊字符（'_'）
     *
     * @param str 指定的字符串
     * @return 密码格式是否正确:是为true，否则false
     */
    public static Boolean isPassword(String str) {
        Boolean isPassword = false;
        String expr = "^[A-Za-z0-9_]+$";
        if (str.matches(expr)) {
            isPassword = true;
        }
        return isPassword;
    }


    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 描述：是否是中文.
     *
     * @param str 指定的字符串
     * @return 是否是中文:是为true，否则false
     */
    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return 是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    isChinese = true;
                } else {

                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：从输入流中获得String.
     *
     * @param is 输入流
     * @return 获得的String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            //最后一个\n删除
            if (sb.indexOf("\n") != -1 && sb.lastIndexOf("\n") == sb.length() - 1) {
                sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 描述：标准化日期时间类型的数据，不足两位的补0.
     *
     * @param dateTime 预格式的时间字符串，如:2012-3-2 12:2:20
     * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
     */
    public static String dateTimeFormat(String dateTime) {
        StringBuilder sb = new StringBuilder();
        try {
            if (isEmpty(dateTime)) {
                return null;
            }
            String[] dateAndTime = dateTime.split(" ");
            if (dateAndTime.length > 0) {
                for (String str : dateAndTime) {
                    if (str.indexOf("-") != -1) {
                        String[] date = str.split("-");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append("-");
                            }
                        }
                    } else if (str.indexOf(":") != -1) {
                        sb.append(" ");
                        String[] date = str.split(":");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append(":");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    /**
     * 描述：不足2个字符的在前面补“0”.
     *
     * @param str 指定的字符串
     * @return 至少2个字符的字符串
     */
    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                str = "0" + str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str    the str
     * @param length 指定字节长度
     * @return 截取后的字符串
     */
    public static String cutString(String str, int length) {
        return cutString(str, length, "");
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str    文本
     * @param length 字节长度
     * @param dot    省略符号
     * @return 截取后的字符串
     */
    public static String cutString(String str, int length, String dot) {
        int strBLen = strlen(str, "GBK");
        if (strBLen <= length) {
            return str;
        }
        int temp = 0;
        StringBuffer sb = new StringBuffer(length);
        char[] ch = str.toCharArray();
        for (char c : ch) {
            sb.append(c);
            if (c > 256) {
                temp += 2;
            } else {
                temp += 1;
            }
            if (temp >= length) {
                if (dot != null) {
                    sb.append(dot);
                }
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 描述：截取字符串从第一个指定字符.
     *
     * @param str1   原文本
     * @param str2   指定字符
     * @param offset 偏移的索引
     * @return 截取后的字符串
     */
    public static String cutStringFromChar(String str1, String str2, int offset) {
        if (isEmpty(str1)) {
            return "";
        }
        int start = str1.indexOf(str2);
        if (start != -1) {
            if (str1.length() > start + offset) {
                return str1.substring(start + offset);
            }
        }
        return "";
    }

    /**
     * 描述：获取字节长度.
     *
     * @param str     文本
     * @param charset 字符集（GBK）
     * @return the int
     */
    public static int strlen(String str, String charset) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int length = 0;
        try {
            length = str.getBytes(charset).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 获取大小的描述.
     *
     * @param size 字节个数
     * @return 大小的描述
     */
    public static String getSizeDesc(long size) {
        String suffix = "B";
        if (size >= 1024) {
            suffix = "K";
            size = size >> 10;
            if (size >= 1024) {
                suffix = "M";
                //size /= 1024;
                size = size >> 10;
                if (size >= 1024) {
                    suffix = "G";
                    size = size >> 10;
                    //size /= 1024;
                }
            }
        }
        return size + suffix;
    }

    /**
     * 描述：ip地址转换为10进制数.
     *
     * @param ip the ip
     * @return the long
     */
    public static long ip2int(String ip) {
        ip = ip.replace(".", ",");
        String[] items = ip.split(",");
        return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.out.println(dateTimeFormat("2012-3-2 12:2:20"));
    }

    /**
     * @param chin
     * @return
     * @Title : filterChinese
     * @Type : FilterStr
     * @Description : 过滤出中文
     */
    public static String filterChinese(String chin) {
        if (chin != null)
            chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        return chin;
    }

    /**
     * @param number
     * @return
     * @Title : filterNumber
     * @Type : FilterStr
     * @date : 2014年3月12日 下午7:23:03
     * @Description : 过滤出数字
     */
    public static String filterNumber(String number) {
        if (number != null)
            number = number.replaceAll("[^(0-9)]", "");
        return number;
    }


    /**
     * 截取第一个字符
     *
     * @param str 被截取的字符
     * @return 截取后的字符
     */
    public static String cutString(String str) {
        if (str != null && str.length() > 1) {
            return str.substring(0, 1) + "*****";
        }
        return str;
    }

    /**
     * 隐藏身份信息
     *
     * @param str 被截取的字符
     * @return 截取后的字符
     */
    public static String setString(String str) {
        String num = "";
        if (str != null && str.length() > 1) {
            if (str.length() > 7) {
                num = str.substring(0, 2) + "****" + str.substring(7, str.length());
            } else {
                num = str.substring(0, 2) + "****";
            }
        } else {
            num = "--";
        }
        return num;
    }

    /**
     * 字符串全角化
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 获取以万元为单位
     *
     * @param money String类型
     * @return
     */
    public static double getMillionMoney(String money) {
        double million = 0;
        if (!isEmpty(money)) {
            double b = Double.parseDouble(money);
            if (b >= 10000) {
                million = b / 10000;
            } else {
                million = b;
            }
        }
        return million;
    }

    /**
     * 获取以万元为单位
     *
     * @param money double类型
     * @return
     */
    public static double getMillionMoney(double money) {
        if (money >= 10000) {
            money = money / 10000;
        }
        return money;
    }

    /**
     * 把高亮的文字添加在右边
     *
     * @param color     颜色
     * @param highlight 高亮字符串
     * @param str       普通字符串
     * @return html格式的字符串
     */
    public static String getRightHtml(String color, String highlight, String str) {
        String html = str + "<font color='" + color + "'>" + highlight + "</font>";
        return html;
    }

    /**
     * 把高亮的文字添加在左边
     *
     * @param color     颜色
     * @param highlight 高亮字符串
     * @param str       普通字符串
     * @return html格式的字符串
     */
    public static String getLeftHtml(String color, String highlight, String str) {
        String html = "<font color='" + color + "'>" + highlight + "</font>" + str;
        return html;
    }

    /**
     * 把高亮的文字添加在中间
     *
     * @param color     颜色
     * @param highlight 高亮字符串
     * @param startStr  左边字符串
     * @param endStr    右边边字符串
     * @return html格式的字符串
     */
    public static String getCenterHtml(String color, String highlight, String startStr, String endStr) {
        String html = startStr + "<font color='" + color + "'>" + highlight + "</font>" + endStr;
        return html;
    }


    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 设置银行限额
     */
    public static String getBankLimitMoney(double limitOnceMoney, double limitDayMoney) {
        double OnceMoney = getMillionMoney(limitOnceMoney);
        double DayMoney = getMillionMoney(limitDayMoney);
        String onceUnit = "万元";
        String dayUnit = "万元";
        if (limitOnceMoney < 10000) {
            onceUnit = "元";
        }
        if (limitDayMoney < 10000) {
            dayUnit = "元";
        }
        String once = "无限制";
        String day = "无限制";
        if (limitDayMoney > 0) {
            once = AbMathUtil.isPercentStr(OnceMoney) + onceUnit;
        }
        if (limitDayMoney > 0) {
            day = AbMathUtil.isPercentStr(DayMoney) + dayUnit;
        }
        return "限额：单笔" + once + "；单日" + day;
    }

    /**
     * 设置银行限额
     */
    public static String getBankLimitMoney(String limitOnceMoney, String limitDayMoney) {
        if (!isEmpty(limitOnceMoney) && !isEmpty(limitDayMoney)) {
            double limitOnce = 0;
            double limitDay = 0;
            try {
                limitOnce = Double.parseDouble(limitOnceMoney);
                limitDay = Double.parseDouble(limitDayMoney);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return getBankLimitMoney(limitOnce, limitDay);
        }
        return "";
    }

    /**
     * 隐藏身份信息
     *
     * @param idCard
     * @return
     */
    public static String hideIdCard(String idCard) {
        if (idCard == null) {
            return "";
        }
        String index1 = idCard.substring(0, 4);
        String index2 = idCard.substring(idCard.length() - 2, idCard.length());
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < idCard.length() - 6; i++) {
            buffer.append("*");
        }
        return index1 + buffer.toString() + index2;
    }

    /**
     * 隐藏银行卡
     *
     * @param bankCard
     * @return
     */
    public static String hideBankCard(String bankCard) {
        if (bankCard == null) {
            return "";
        }
        String index1 = bankCard.substring(0, 0);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bankCard.length() - 4; i++) {
            if (i % 4 == 0) {
                buffer.append(" ");
            } else if (i == bankCard.length() - 4) {
                buffer.append(" ");
            }
            buffer.append("*");
        }
        String index2 = bankCard.substring(bankCard.length() - 4, bankCard.length());
        return index1 + buffer.toString() + index2;
    }

    /**
     * 截取银行卡后四位
     *
     * @param bankCard
     * @return
     */
    public static String subBankCard(String bankCard) {
        if (bankCard == null) {
            return "";
        }
        return bankCard.substring(bankCard.length() - 4, bankCard.length());
    }


    /**
     * 隐藏用户真实姓名
     *
     * @param realName
     * @return
     */
    public static String hideRealName(String realName) {
        if (realName == null) {
            return "";
        }
        String index1 = realName.substring(0, 1);
        return index1 + "*";
    }

    /**
     * 隐藏用户手机号
     *
     * @param userPhone
     * @return
     */
    public static String hideUserPhone(String userPhone) {
        if (TextUtils.isEmpty(userPhone)) {
            return "";
        }
        String index1 = userPhone.substring(0, 3);
        String index2 = userPhone.substring(userPhone.length() - 4, userPhone.length());
        return index1 + "****" + index2;
    }

    /**
     * 隐藏用户帐号
     *
     * @param userName
     * @return
     */
    public static String hideUserName(String userName) {
        String index1 = userName.substring(0, 1);
        String index2 = userName.substring(userName.length() - 1, userName.length());
        return index1 + "****" + index2;
    }


    /**
     * 拼接收货人详细地址
     */
    public static String getAddress(String province, String city, String district, String address) {
        return province + " " + city + " " + district + " " + address;
    }

    /**
     * 拼接收货人省市区地址
     */
    public static String getAddress(String province, String city, String district) {
        return province + " " + city + " " + district + " ";
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i] + "")) {
                res = false;
                break;
            }
        }
        return res;
    }

    //判断是否含有数字.-1表示没有数字，有数字表示几
    public static int isDigitStr(String str) {
        int num = -1;//定义一个int值，用来表示是否包含数字

        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符

                String numStr = String.valueOf(str.charAt(i));
                num = Integer.parseInt(numStr);//如果有数字，那么拿出来，肯定不是－1

                break;
            }
        }
        return num;
    }

    //截取数字  
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }


}
