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
     * 标的类型
     * car 车标
     * house 车标
     * experience 体验
     * worth 净值标
     */
    public static final String INVEST_TYPE_CAR = "car";//车标
    public static final String INVEST_TYPE_HOUSE = "house";//房产
    public static final String INVEST_TYPE_HOME = "home";//老数据标类型
    public static final String INVEST_TYPE_EXPERIENCE = "experience";//体验
    public static final String INVEST_TYPE_PLEDGE = "pledge";//抵押
    public static final String INVEST_TYPE_WORTH = "worth";//(净值标)


    /**
     * 获取banner接口，不传递code参数时，默认code为'app'
     * code的参数类型有以下：
     * activity_banner : 移动端活动专区
     * projectAD : 移动端标内广告
     * invite : 移动端邀请好友
     * Register_banner : 移动端注册页面
     * MAU-banner  我的里面用户没有登录的时候的提示框背景
     * startup 启动页图片
     */
    public static final String ALL_BANNER = "all";
    public static final String ACTIVITY_BANNER = "activity_banner";
    public static final String PROJECTAD_BANNER = "projectAD";
    public static final String INVITE_BANNER = "invite_banner";
    public static final String REGISTER_BANNER = "Register_banner";
    public static final String MAU_BANNER = "MAU-banner";
    public static final String STARTUP = "startup";

    /**
     * 文章分类code，不传则查询所有分类下的文章，
     * 具体code在服务端后台可以查阅
     * notice(公告)，
     * help(帮助中心)，
     * medianews(媒体报道)
     */
    public static final String ARTICLE_CODE_NOTICE = "notice";
    public static final String ARTICLE_CODE_HELP = "help";
    public static final String ARTICLE_CODE_MEDIANEWS = "medianews";

    /**
     * 红包类型
     * cash 现金券
     * interest 加息券
     * coupon 体验金券
     */
    public static final String RED_PACKET_TYPE_CASH = "cash";
    public static final String RED_PACKET_TYPE_INTEREST = "interest";
    public static final String RED_PACKET_TYPE_COUPON = "coupon";
    /**
     * 红包使用状态
     * none 未使用
     * used 已使用
     * overdue 已过期
     * freeze 冻结
     */
    public static final String RED_PACKET_NONE = "none";
    public static final String RED_PACKET_USED = "used";
    public static final String RED_PACKET_OVERDUE = "overdue";
    public static final String RED_PACKET_FREEZE = "freeze";
    /**
     * 投资状态
     * NONE("未发布", "none"),
     * PROCESS("正在募集", "process"),
     * REPAYMENT("正在还款", "repayment"),
     * SUCCESS("募集完成", "success"),
     * DONE("回款完毕", "done"),
     * FAILURE("募集失败", "failure"),
     */
    public static final String INVEST_STATUS_NONE = "none";
    public static final String INVEST_STATUS_PROCESS = "process";
    public static final String INVEST_STATUS_SUCCESS = "success";
    public static final String INVEST_STATUS_REPAYMENT = "repayment";
    public static final String INVEST_STATUS_DONE = "done";
    public static final String INVEST_STATUS_FAILURE = "failure";


    /**
     * 充值入口的类型
     * rechargeActivity  正常充值
     * firstRecharge 第一次充值
     * withRecharge 在用户没有绑定银行卡的状态下提现
     * investBindBank 充值投资绑卡
     * invest 充值投资
     * realNameInvestRecharge 实名充值投资
     * integralBindBank 积分任务绑卡送积分
     */
    public static final String RECHARGE_TYPE_NORMAL = "rechargeActivity";
    public static final String RECHARGE_TYPE_FIRST = "firstRecharge";
    public static final String RECHARGE_TYPE_WITH = "withRecharge";
    public static final String RECHARGE_TYPE_INTEGRAL = "integralBindBank";
    public static final String RECHARGE_TYPE_INVEST_BIND_BANK = "investBindBank";
    public static final String RECHARGE_TYPE_REALNAME_INVEST = "realNameInvestRecharge";
    public static final String RECHARGE_TYPE_INVEST = "invest";


    /**
     * 修改用户信息入口的类型
     * updatePhone  修改手机号
     * forgetPass 忘记登录密码
     * forgetDealPass 忘记交易密码
     * updateName 修改用户名
     * updateLogin 修改登录密码
     * updateDealPass 修改交易密码
     */
    public static final String UPDATE_TYPE_UPDATEPHONE = "updatePhone";
    public static final String UPDATE_TYPE_FORGETPASS = "forgetPass";
    public static final String UPDATE_TYPE_FORGETDEALPASS = "forgetDealPass";
    public static final String UPDATE_TYPE_UPDATENAME = "updateName";
    public static final String UPDATE_TYPE_UPDATELOGIN = "updateLogin";
    public static final String UPDATE_TYPE_UPDATEDEALPASS = "updateDealPass";

    /**
     * 节日类型
     */
    //春节
    public static final String SPRINGFESTIVAL = "springFestival";
    //元宵
    public static final String LANTERNFESTIVAL = "lanternFestival";
    //情人节
    public static final String LOVERFESTIVAL = "valentinesDay";
    //51劳动节
    public static final String LABOURFESTIVAL = "labourDay";
    //提现
    public static final String WITHDRAW = "withdraw";
    //充值
    public static final String RECHARGE = "recharge";
    //绑卡
    public static final String BINDCARD = "bindCard";
    //转入页面 (如果是从转入页面进入绑卡页面需要传递，用来区分绑卡成功之后需要跳转的页面)
    public static final String TURNINTO = "TurnIntoActivity";
    //选择用户账户页面 (如果是从用户账户页面进入绑卡页面需要传递，用来区分绑卡成功之后需要跳转的页面)
    public static final String SELECT_ACCOUNT_MODE = "SelectAccountModeActivity";
    public static final String DATA_EXCEPTION = "服务器响应异常";
}
