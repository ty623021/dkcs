package com.rt.zgloan.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.MainActivity;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.LoginBySmsCdBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.EditTextWithDel;
import com.rt.zgloan.weight.LoadingFragment;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by zcy on 2017/11/2 0002.
 */

public class DynamicLoginFragment extends BaseFragment {
    private static final String TAG = "DynamicLoginFragment";
    @BindView(R.id.tv_verification)
    TextView mTvVerification;//获取验证码
    @BindView(R.id.edit_input_phone)
    EditTextWithDel mEtInputPhone;//手机号码
    @BindView(R.id.edit_input_verification_code)
    EditTextWithDel mEtInputVerCode;//短信验证码

    private static final int INTERVAL = 1;
    private int curTime;
    private String phoneNum;//手机号码
    private String verificationCode;//验证码


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
    public int getLayoutId() {
        return R.layout.fragment_dynamic_login;
    }

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @OnClick({R.id.tv_verification, R.id.btn_login})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_verification:
                getEditContent();
                if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtil.showToast("请输入手机号码");
                    return;
                }
                if (!AbStringUtil.isMobileNo(phoneNum)) {
                    ToastUtil.showToast("请输入正确格式的手机号码");
                    return;
                }
                setSendCode(true);
                getMobileCode(phoneNum);
                break;
            case R.id.btn_login:
                getEditContent();
                if (TextUtils.isEmpty(phoneNum)) {
                    ToastUtil.showToast("请输入手机号码");
                    return;
                }
                if (!AbStringUtil.isMobileNo(phoneNum)) {
                    ToastUtil.showToast("请输入正确格式的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(verificationCode)) {
                    ToastUtil.showToast("请输入验证码");
                    return;
                }
                loginBySmsCd(phoneNum, verificationCode, "Android");
                break;
        }
    }

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

    private void loginBySmsCd(String phoneNum, String verificationCode, String source) {
        mapParams.put("mobile", phoneNum);
        mapParams.put("mobileCode", verificationCode);
        mapParams.put("source", source);
        mPresenter.toSubscribe(
                HttpManager.getApi().loginBySmsCd(mapParams), new HttpSubscriber<LoginBySmsCdBean>() {
                    @Override
                    protected void _onStart() {
                        //  Log.e("tag", "_onStart");
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在登录...");
                    }

                    @Override
                    protected void _onNext(LoginBySmsCdBean loginBySmsCdBean) {
                        // Log.e("tag", "_onNext");
                        ToastUtil.showToast("登录成功");
                        SpUtil.putBoolean(SpUtil.isLogin, true);
                        SpUtil.putString(SpUtil.userId, loginBySmsCdBean.getId());//useID
                        SpUtil.putString(SpUtil.mobile, loginBySmsCdBean.getMobile());//手机号码
                        SpUtil.putString(SpUtil.invite_code, loginBySmsCdBean.getInvite_code());//邀请码
                        SpUtil.putString(SpUtil.is_state, loginBySmsCdBean.getIs_state());//是否完善资料

                        MainActivity.startMainActivity(mActivity, 2);
                    }


                    @Override
                    protected void _onError(String message) {
                        //  Log.e("tag", "_onError..." + message);
                        ToastUtil.showToast(message);


                    }

                    @Override
                    protected void _onCompleted() {

                        LoadingFragment.getInstends().dismiss();
                    }
                }
        );
    }

    //获取手机号码和验证码
    private void getEditContent() {
        phoneNum = mEtInputPhone.getText().toString().trim();
        verificationCode = mEtInputVerCode.getText().toString().trim();
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @Override
    public void initView() {

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INTERVAL:
                    if (curTime > 0) {
                        mTvVerification.setText("" + curTime + "秒");
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
            mTvVerification.setEnabled(false);
        } else {
            mTvVerification.setText("重新发送");
            mTvVerification.setEnabled(true);
        }
    }
}
