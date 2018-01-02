package com.rt.zgloan.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.RegisterInfo;
import com.rt.zgloan.bean.RegisterSonInfo;
import com.rt.zgloan.bean.UserBean;
import com.rt.zgloan.globe.Constant;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.util.AbImageUtil;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.DialogUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.StringUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.EditTextWithDel;
import com.rt.zgloan.weight.LoadingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;


/**
 * 注册
 * Created by hjy on 2017/8/25.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_my_invite_code)
    TextView tv_my_invite_code;
    @BindView(R.id.rel_invite_code)
    LinearLayout rel_invite_code;
    @BindView(R.id.edit_input_phone)
    EditTextWithDel editInputPhone;
    @BindView(R.id.edit_verification_code)
    EditText editVerificationCode;
    @BindView(R.id.edit_login_password)
    EditTextWithDel editLoginPassword;
    @BindView(R.id.edit_invite_code)
    EditTextWithDel editInviteCode;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_get_graphical_code)
    TextView tv_get_graphical_code;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.ck_agreement)
    CheckBox mCkAgreement;
    private EditTextWithDel edit_graphic_verification_code;
    private MyCountDownTimer myCountDownTimer;//倒数计时器

    private String str_InputPhone, str_VerificationCode, str_LoginPassword, str_InviteCode;
    private List<RegisterSonInfo> info;
    private String str_graphic_verification_code;

    private static final int INTERVAL = 1;
    private int curTime;

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @OnClick({R.id.tv_my_invite_code, R.id.btn_sure, R.id.tv_get_graphical_code, R.id.Ll_register_agreement})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_my_invite_code:

                tv_my_invite_code.setVisibility(View.GONE);
                rel_invite_code.setVisibility(View.VISIBLE);

                break;

            case R.id.btn_sure:
                getEditContent();
                if (StringUtil.isBlank(str_InputPhone)) {
                    ToastUtil.showToast("请输入手机号码");
                    return;
                }

                if (!AbStringUtil.isMobileNo(str_InputPhone)) {
                    ToastUtil.showToast("请输入正确格式的手机号码");
                    return;
                }

                if (StringUtil.isBlank(str_VerificationCode)) {
                    ToastUtil.showToast("请输入验证码");
                    return;
                }

                if (StringUtil.isBlank(str_LoginPassword)) {
                    ToastUtil.showToast("请输入登录密码");
                    return;
                }

                if (str_LoginPassword.length() < 6 || str_LoginPassword.length() > 16) {
                    ToastUtil.showToast("密码长度应在6-16位之间");
                    return;
                }
                if (mCkAgreement.isChecked()) {
                    sign(str_InputPhone, str_LoginPassword, str_VerificationCode, str_InviteCode, "Android");
                } else {
                    ToastUtil.showToast("请同意用户服务协议");
                    return;
                }


                break;

            case R.id.tv_get_graphical_code:
                getEditContent();
                if (StringUtil.isBlank(str_InputPhone)) {
                    ToastUtil.showToast("请输入手机号码");
                    return;
                }
                if (!AbStringUtil.isMobileNo(str_InputPhone)) {
                    ToastUtil.showToast("请输入正确格式的手机号码");
                    return;
                }

                setSendCode(true);
                getMobileCode(str_InputPhone);
//                showGraphicalCodeDialog();
                break;
            case R.id.Ll_register_agreement:
                WebViewActivity.startActivity(mContext, Constant.REGISTER_AGREEMENT);
                break;
        }
    }

    //请求注册接口
    private void sign(String phoneNum, String psw, String verificationCode, String inviteCode, String source) {

        mapParams.put("mobile", phoneNum);
        mapParams.put("password", psw);
        mapParams.put("mobileCode", verificationCode);
        mapParams.put("invite_code", inviteCode);
        mapParams.put("source", source);
        mPresenter.toSubscribe(HttpManager.getApi().sign(mapParams), new HttpSubscriber<UserBean>() {

            @Override
            protected void _onStart() {

            }

            @Override
            protected void _onNext(UserBean userBean) {
                ToastUtil.showToast("注册成功");
//                SpUtil.putBoolean(SpUtil.isLogin, false);
//                SpUtil.putString(SpUtil.userId, userBean.getId());//userId
//                SpUtil.putString(SpUtil.mobile, userBean.getMobile());//手机号码
//                SpUtil.putString(SpUtil.invite_code, userBean.getInvite_code());//邀请码
//                SpUtil.putString(SpUtil.is_state, userBean.getIs_state());//是否完善资料
                RegisterSuccessActivity.startActivity(mContext, userBean.getId());
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showToast(message);
            }

            @Override
            protected void _onCompleted() {

            }
        });
    }

    //获取验证码接口
    private void getMobileCode(String phoneNum) {
        mapParams.put("mobile", phoneNum);
        mPresenter.toSubscribe(HttpManager.getApi().getMobileCode(mapParams), new HttpSubscriber() {
            @Override
            protected void _onStart() {
                LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "");
            }

            @Override
            protected void _onNext(Object o) {
                ToastUtil.showToast("发送验证码成功");
            }

            @Override
            protected void _onError(String message) {
                ToastUtil.showToast(message);
            }

            @Override
            protected void _onCompleted() {
                LoadingFragment.getInstends().dismiss();
            }
        });

    }

    private void getEditContent() {
        str_InputPhone = editInputPhone.getText().toString().trim();
        str_VerificationCode = editVerificationCode.getText().toString().trim();
        str_LoginPassword = editLoginPassword.getText().toString().trim();
        str_InviteCode = editInviteCode.getText().toString().trim();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("注册");
        mTitle.setRightTitle("登录", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.class);
            }
        });
//        myCountDownTimer = new MyCountDownTimer(60000, 1000);
    }


    private void showGraphicalCodeDialog() {
        View view = View.inflate(this, R.layout.dialog_graphical_code, null);
        ImageView clear_dialog_imgs = (ImageView) view.findViewById(R.id.clear_dialog_imgs);
        edit_graphic_verification_code = (EditTextWithDel) view.findViewById(R.id.edit_graphic_verification_code);
        final ImageView graphic_verification_code_img = (ImageView) view.findViewById(R.id.graphic_verification_code_img);
        AbImageUtil.glideImage(Constant.Url + str_InputPhone, graphic_verification_code_img, R.mipmap.error, R.mipmap.preload);
        Button btn_sure = (Button) view.findViewById(R.id.btn_sure);

        graphic_verification_code_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                LogUtils.loge("Url",+Constant.Url + str_InputPhone);
                AbImageUtil.glideImage(Constant.Url + str_InputPhone, graphic_verification_code_img, R.mipmap.error, R.mipmap.preload);
            }
        });

        clear_dialog_imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.dismiss();
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_graphic_verification_code = edit_graphic_verification_code.getText().toString().trim();
                if (StringUtil.isBlank(str_graphic_verification_code)) {
                    ToastUtil.showToast("请输入图形验证码");
                    return;
                }
                getCodeHttp();
            }
        });

        DialogUtil.showAlertDialog(view);
    }


    /**
     * 注册
     */

    private void registerHttp() {

        mapParams.put("mobile", str_InputPhone);
        mapParams.put("password", str_LoginPassword);
        mapParams.put("mobileCode", str_VerificationCode);
        mapParams.put("invite_code", str_InviteCode);
        mPresenter.toSubscribe(
                HttpManager.getApi().register(mapParams), new HttpSubscriber<RegisterInfo>() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在注册...");
                    }

                    @Override
                    protected void _onNext(RegisterInfo registerInfo) {
//                        Log.e("tag", "_onNext");
                        SpUtil.putBoolean(SpUtil.isLogin, true);
                        SpUtil.putString(SpUtil.userId, registerInfo.getRelute().getId());
                        SpUtil.putString(SpUtil.mobile, registerInfo.getRelute().getMobile());
                        SpUtil.putString(SpUtil.invite_code, registerInfo.getRelute().getInvite_code());
                        SpUtil.putString(SpUtil.is_state, registerInfo.getRelute().getIs_state());
                        RegisterSuccessActivity.startActivity(mContext, registerInfo.getTotal_munber());

                    }


                    @Override
                    protected void _onError(String message) {
                        Log.e("tag", "_onError" + message);
                        ToastUtil.showToast(message);


                    }

                    @Override
                    protected void _onCompleted() {

                        LoadingFragment.getInstends().dismiss();
                    }
                }
        );

    }


    /**
     * 获取手机验证码
     */
    private void getCodeHttp() {
        mapParams.put("mobile", str_InputPhone);
        mapParams.put("code", str_graphic_verification_code);
        mPresenter.toSubscribe(
                HttpManager.getApi().getCode(mapParams), new HttpSubscriber() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "");
                    }

                    @Override
                    protected void _onNext(Object o) {
                        ToastUtil.showToast("发送验证码成功");
                        myCountDownTimer.start();
                        DialogUtil.dismiss();
                    }


                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(message);


                    }

                    @Override
                    protected void _onCompleted() {
                        LoadingFragment.getInstends().dismiss();

                    }
                }
        );
    }

    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {

    }

    @Override
    public void recordSuccess(Object o) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            tv_get_graphical_code.setClickable(false);
            tv_get_graphical_code.setText(l / 1000 + "s");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tv_get_graphical_code.setText("重新获取");
            //设置可点击
            tv_get_graphical_code.setClickable(true);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INTERVAL:
                    if (curTime > 0) {
                        tv_get_graphical_code.setText("" + curTime + "秒");
                        mHandler.sendEmptyMessageDelayed(INTERVAL, 1000);
                        curTime--;
                    } else {
                        setSendCode(false);
                    }
                    break;

                default:
                    setSendCode(false);
                    break;
            }
        }

    };

    private void setSendCode(boolean send) {
        curTime = 60;
        if (send == true) {
            mHandler.sendEmptyMessage(INTERVAL);
            tv_get_graphical_code.setEnabled(false);
        } else {
            tv_get_graphical_code.setText("重新发送");
            tv_get_graphical_code.setEnabled(true);
        }
    }
}
