package com.rt.zgloan.http;


import com.rt.zgloan.activity.cityActivity.CityBean;
import com.rt.zgloan.bean.AboutMeBean;
import com.rt.zgloan.bean.BankBean;
import com.rt.zgloan.bean.BannerListBean;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.CitiesBean;
import com.rt.zgloan.bean.CommentListBean;
import com.rt.zgloan.bean.CreditCardDetailsBean;
import com.rt.zgloan.bean.CreditCardHomeBean;
import com.rt.zgloan.bean.CreditCardListBean;
import com.rt.zgloan.bean.DataBean;
import com.rt.zgloan.bean.GetCityInfo;
import com.rt.zgloan.bean.GetProvinceInfo;
import com.rt.zgloan.bean.InviteFriendsRecordInfo;
import com.rt.zgloan.bean.InviteRecordBean;
import com.rt.zgloan.bean.LabelListBean;
import com.rt.zgloan.bean.LoanClassListBean;
import com.rt.zgloan.bean.LoanDetailBean;
import com.rt.zgloan.bean.LoanDetailInfo;
import com.rt.zgloan.bean.LoanListInfo;
import com.rt.zgloan.bean.LoansListByLoanTypeBean;
import com.rt.zgloan.bean.LoginByPaswdBean;
import com.rt.zgloan.bean.LoginBySmsCdBean;
import com.rt.zgloan.bean.PersonalDataInfo;
import com.rt.zgloan.bean.ProvinciesBean;
import com.rt.zgloan.bean.RegisterInfo;
import com.rt.zgloan.bean.TestBean;
import com.rt.zgloan.bean.UserBean;
import com.rt.zgloan.bean.UserDataBean;
import com.rt.zgloan.bean.Version;
import com.rt.zgloan.bean.VersionBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 *
 */
public interface HttpApi {

    //所有需要的泛型 添加：BaseResponse<UserInfo>
    // @FormUrlEncoded
    @GET("index.php?g=Api&m=Mobileverify&a=send")
    Observable<BaseResponse<TestBean>> request(@QueryMap Map<String, String> paramsMap);


    //首页banner图
    @FormUrlEncoded
    @POST("index/getBannerList")
    Observable<BaseResponse<BannerListBean>> banner(@FieldMap Map<String, String> paramsMap);

    //首页轮播
    @FormUrlEncoded
    @POST("index/getMessageList")
    Observable<BaseResponse<CommentListBean>> getMessageList(@FieldMap Map<String, String> paramsMap);

    //首页产品分类接口
    @FormUrlEncoded
    @POST("index/getloanClassList")
    Observable<BaseResponse<LoanClassListBean>> getLoanClassList(@FieldMap Map<String, String> paramsMap);

    //首页标签接口
    @FormUrlEncoded
    @POST("index/getLabelList")
    Observable<BaseResponse<LabelListBean>> getLabelList(@FieldMap Map<String, String> paramsMap);

    //上传用户的行为记录
    @FormUrlEncoded
    @POST("behavior/getBehaviorOperation")
    Observable<BaseResponse> getBehaviorOperation(@FieldMap Map<String, String> paramsMap);

    //    //首页标签产品列表接口
//    @FormUrlEncoded
//    @POST("index/getLabelLoansList")
//    Observable<BaseResponse<LabelLoansListBean>> getLabelLoansList(@FieldMap Map<String, Integer> paramsMap);
    //产品详情页
    @FormUrlEncoded
    @POST("loans/getLoansDetail")
    Observable<BaseResponse<LoanDetailBean>> getLoansDetail(@FieldMap Map<String, Integer> paramsMap);

    //贷款页产品信息
    @FormUrlEncoded
    @POST("loans/getLoansListByLoanType")
    Observable<BaseResponse<LoansListByLoanTypeBean>> getLoansListByLoanType(@FieldMap Map<String, Integer> paramsMap);

    //产品详情页提交数据接口
    @FormUrlEncoded
    @POST("loans/saveJump")
    Observable<BaseResponse> saveJump(@FieldMap Map<String, String> paramsMap);

    //获取验证码接口
    @FormUrlEncoded
    @POST("mobileCode/getMobileCode")
    Observable<BaseResponse> getMobileCode(@FieldMap Map<String, String> paramsMap);

    //注册接口
    @FormUrlEncoded
    @POST("user/sign")
    Observable<BaseResponse<UserBean>> sign(@FieldMap Map<String, String> paramsMap);

    //短信登录接口
    @FormUrlEncoded
    @POST("userLogin/loginBySmsCd")
    Observable<BaseResponse<LoginBySmsCdBean>> loginBySmsCd(@FieldMap Map<String, String> paramsMap);

    //账号密码登录接口
    @FormUrlEncoded
    @POST("user/loginByPaswd")
    Observable<BaseResponse<LoginByPaswdBean>> loginByPaswd(@FieldMap Map<String, String> paramsMap);

    //忘记密码接口
    @FormUrlEncoded
    @POST("user/resetPwd")
    Observable<BaseResponse> resetPwd(@FieldMap Map<String, String> paramsMap);

    //修改密码接口
    @FormUrlEncoded
    @POST("user/changePwd")
    Observable<BaseResponse> changePwd(@FieldMap Map<String, String> paramsMap);

    //获取用户资料接口
    @FormUrlEncoded
    @POST("user/getUsersById")
    Observable<BaseResponse<UserDataBean>> getUsersById(@FieldMap Map<String, String> paramsMap);

    //保存用户资料
    @FormUrlEncoded
    @POST("user/UpdUses")
    Observable<BaseResponse> saveUserData(@FieldMap Map<String, String> paramsMap);

    //获取婚姻学历工作内容接口
    @FormUrlEncoded
    @POST("user/getCreditByType")
    Observable<BaseResponse<DataBean>> getCreditByType(@FieldMap Map<String, String> paramsMap);

    //获取省接口
    @FormUrlEncoded
    @POST("user/getProvincies")
    Observable<BaseResponse<ProvinciesBean>> getProvincies(@FieldMap Map<String, String> paramsMap);

    //获取市接口
    @FormUrlEncoded
    @POST("user/getRegionById")
    Observable<BaseResponse<CitiesBean>> getRegionById(@FieldMap Map<String, String> paramsMap);

    //邀请好友接口
    @FormUrlEncoded
    @POST("user/findInvitedUsers")
    Observable<BaseResponse<InviteRecordBean>> findInvitedUsers(@FieldMap Map<String, String> paramsMap);

    //检查更新接口
    @FormUrlEncoded
    @POST("index/checkVersion")
    Observable<BaseResponse<VersionBean>> checkVersion(@FieldMap Map<String, String> paramsMap);

    //贷款列表页面
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Index&a=loan_lists")
    Observable<BaseResponse<LoanListInfo>> loanList(@FieldMap Map<String, String> paramsMap);

    //贷款页面的详情
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Index&a=loan_dateil")
    Observable<BaseResponse<LoanDetailInfo>> loanDetail(@FieldMap Map<String, String> paramsMap);


    //获取验证码  注册
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Register&a=send")
    Observable<BaseResponse> getCode(@FieldMap Map<String, String> paramsMap);

    //获取验证码 忘记密码
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Register&a=send1")
    Observable<BaseResponse> getCodeForgotPassword(@FieldMap Map<String, String> paramsMap);

    //注册
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Register&a=register")
    Observable<BaseResponse<RegisterInfo>> register(@FieldMap Map<String, String> paramsMap);


    //登录
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Register&a=login")
    Observable<BaseResponse<RegisterInfo>> login(@FieldMap Map<String, String> paramsMap);

    //修改登录密码
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Register&a=edit_password")
    Observable<BaseResponse> changeLoginPassword(@FieldMap Map<String, String> paramsMap);


    //修改手机号码第一步
    @FormUrlEncoded
    @POST("index.php?g=Api&m=User&a=edit_phone")
    Observable<BaseResponse> changePhoneOne(@FieldMap Map<String, String> paramsMap);

    //修改手机号码第一步
    @FormUrlEncoded
    @POST("index.php?g=Api&m=User&a=edit_phone_new")
    Observable<BaseResponse> changePhoneTwo(@FieldMap Map<String, String> paramsMap);


    //忘记密码
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Register&a=forget_password")
    Observable<BaseResponse> forgotPassword(@FieldMap Map<String, String> paramsMap);


    //邀请好友列表
    @FormUrlEncoded
    @POST("index.php?g=Api&m=User&a=invite_list")
    Observable<BaseResponse<InviteFriendsRecordInfo>> inviteFriendsList(@FieldMap Map<String, String> paramsMap);


    //保存用户资料
    @FormUrlEncoded
    @POST("index.php?g=Api&m=User&a=edit_info")
    Observable<BaseResponse> savePersonalData(@FieldMap Map<String, String> paramsMap);

    //获取用户资料
    @FormUrlEncoded
    @POST("index.php?g=Api&m=User&a=info")
    Observable<BaseResponse<PersonalDataInfo>> getPersonalData(@FieldMap Map<String, String> paramsMap);


    //获取省
    @FormUrlEncoded
    @POST("index.php?g=Api&m=User&a=sheng")
    Observable<BaseResponse<GetProvinceInfo>> getProvince(@FieldMap Map<String, String> paramsMap);

    //获取市
    @FormUrlEncoded
    @POST("index.php?g=Api&m=User&a=shi")
    Observable<BaseResponse<GetCityInfo>> getCity(@FieldMap Map<String, String> paramsMap);

    //获取市
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Index&a=jump")
    Observable<BaseResponse> apply(@FieldMap Map<String, String> paramsMap);

    //上传用户行为记录
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Index&a=behavior_operation")
    Observable<BaseResponse> sendUserAction(@FieldMap Map<String, String> paramsMap);

    //版本检查
    @FormUrlEncoded
    @POST("index.php?g=Api&m=Index&a=version")
    Observable<BaseResponse<Version>> checkAppVersion(@FieldMap Map<String, String> paramsMap);

    //关于我们
    @FormUrlEncoded
    @POST("index/aboutMe")
    Observable<BaseResponse<AboutMeBean>> aboutMe(@FieldMap Map<String, String> paramsMap);

    //获取信用卡首页数据
    @FormUrlEncoded
    @POST("creditcards/initData")
    Observable<BaseResponse<CreditCardHomeBean>> getCreditCardHome(@FieldMap Map<String, String> paramsMap);

    //获取银行列表
    @FormUrlEncoded
    @POST("bank/getBanks")
    Observable<BaseResponse<BankBean>> getBankCardList(@FieldMap Map<String, String> paramsMap);

    //获取城市列表
    @FormUrlEncoded
    @POST("city/getCity")
    Observable<BaseResponse<CityBean>> getCityList(@FieldMap Map<String, String> paramsMap);

    //获取信用卡详情
    @FormUrlEncoded
    @POST("creditcards/detail")
    Observable<BaseResponse<CreditCardDetailsBean>> getCreditCardDetails(@FieldMap Map<String, String> paramsMap);

    //按用途获取行用卡
    @FormUrlEncoded
    @POST("creditcards/getCreditCardsByPurpose")
    Observable<BaseResponse<CreditCardListBean>> getCreditCardsByPurpose(@FieldMap Map<String, String> paramsMap);

    //按银行获取行用卡
    @FormUrlEncoded
    @POST("creditcards/getCreditCardsByBank")
    Observable<BaseResponse<CreditCardListBean>> getCreditCardsByBank(@FieldMap Map<String, String> paramsMap);

    //按主题获取行用卡
    @FormUrlEncoded
    @POST("creditcards/getCreditCardsBySubject")
    Observable<BaseResponse<CreditCardListBean>> getCreditCardsBySubject(@FieldMap Map<String, String> paramsMap);

}
