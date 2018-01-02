package com.rt.zgloan.globe;

import com.rt.zgloan.http.HttpManager;

/**
 * Created by geek on 2016/6/21.
 * 全局属性
 */
public class Constant {


    public static String Url = HttpManager.baseUrl + "index.php?g=Api&m=Register&a=verify&mobile=";////图形验证码的图片地址


    public static String INVITEUrl = HttpManager.baseUrl + "user/shareRegInfo?inviteCode=";////邀请好友的分享地址


    public static String REGISTER_AGREEMENT = HttpManager.baseUrl + "user/getAgreementUrl";//用户注册协议


    /**
     * 签名key
     */
    public static final String APPSECRET = "daikuanchaoshiwang";


    /**
     * UI的主色调
     * colorPrimary 蓝色
     * colorPrimary2 橘黄色
     */
    public static String colorPrimary = "#27A2FE";
    public static String colorPrimary2 = "#FF7F00";
    /**
     * UI设计的基准宽度.
     */
    public static final int UI_WIDTH = 720;

    /**
     * UI设计的基准高度.
     */
    public static final int UI_HEIGHT = 1280;

    /**
     * UI设计的密度.
     */
    public static final int UI_DENSITY = 2;

    /**
     * 客服电话
     */
    public static final String CUSTOMER_SERVICE_PHONE = "4006114589";
    /**
     * 所有的金额和百分比四舍五入后的位数
     */
    public static final int ROUND_DIGIT = 2;

    /**
     * 请求网络时显示的文字信息 正在加载...
     */
    public static final String LOADING = "正在加载...";
    /**
     * 客户端设备标识 "android"
     */
    public static final String CLIENT_ID = "2";
    /**
     * 客户端设备标识 "android"
     */
    public static final String CLIENT_ID_NAME = "android";


    /**
     * 签名key
     */
    public static final String LOANAPPSECRET = "dqA2UNYnfB6gpksWEaVGm+KunbAxOw8MFs3oAZQGJUwGZPfL6tufGeyP8nv/bVwq";
    /**
     * AppKey
     */
    public static final String APPKEY = "APP5f8e6bfa";
    /***
     * 充值通道  连连
     */
    public static final String APP_WAY = "lland";
    /**
     * 充值通道 银行托管
     */
    public static final String NEW_APP_WAY = "depositand";
    /***
     * 第一次登录
     */
    public static final String APP_CUR_VERSION = "appCurVersion";
    /**
     * The  CONNECTEXCEPTION.
     */
    public static final String CONNECT_EXCEPTION = "网络连接失败，请稍后再试";

    /**
     * The  SOCKETEXCEPTION.
     */
    public static final String SOCKET_EXCEPTION = "网络异常，请稍后再试";

    /**
     * The  SOCKETTIMEOUTEXCEPTION.
     */
    public static final String SOCKET_TIMEOUT_EXCEPTION = "网络连接超时，请稍后再试";

    /**
     * 资源未找到.
     */
    public static final String NOT_FOUND_EXCEPTION = "请求资源无效404";

    /**
     * 没有权限访问资源.
     */
    public static final String FORBIDDEN_EXCEPTION = "没有权限访问资源";

    /**
     * The  REMOTESERVICEEXCEPTION.
     */
    public static final String SERVICE_UNAVAILABLE = "服务器正在维护，请稍后再试";

    /**
     * The  UNKNOWNHOSTEXCEPTION.
     */
    public static final String UNKNOWN_HOST_EXCEPTION = "连接服务器失败，请稍后再试";

    /**
     * 其他异常.
     */
    public static final String UNTREATED_EXCEPTION = "未处理的异常";

    /**
     * 登陆已过期，请重新登录
     */
    public static final String LOGIN_EXCEPTION = "登陆已过期，请重新登录";

    /**
     * 已加载全部
     */
    public static final String LOADED = "已加载全部";

    /**
     * 银行信息获取失败提示
     */
    public static final String BANK_INFO = "银行信息获取失败，请重新获取";

    /**
     * The  GET_DATA_EXCEPTION.
     */
    public static final String GET_DATA_EXCEPTION = "数据获取失败，请稍后再试";


    public static final String DATA_EXCEPTION = "服务器响应异常";
}
