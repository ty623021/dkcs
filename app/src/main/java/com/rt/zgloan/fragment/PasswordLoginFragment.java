package com.rt.zgloan.fragment;

import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.ForgotPasswordActivity;
import com.rt.zgloan.activity.MainActivity;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.LoginByPaswdBean;
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

public class PasswordLoginFragment extends BaseFragment {
    private static final String TAG = "PasswordLoginFragment";

    @BindView(R.id.edit_input_phone)
    EditTextWithDel mEtInputPhone;//手机号码
    @BindView(R.id.edit_input_login_password)
    EditTextWithDel mEtInputPsw;

    private String phoneNum;
    private String password;

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
        return R.layout.fragment_password_login;
    }

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @OnClick({R.id.iv_forgot_password, R.id.btn_login})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_forgot_password:
                this.startActivity(ForgotPasswordActivity.class);
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
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.showToast("请输入密码");
                    return;
                }
                if (password.length() < 6 || password.length() > 16) {
                    ToastUtil.showToast("密码长度应在6-16位之间");
                    return;
                }
                login(phoneNum, password);
                break;
        }
    }

    private void login(String phoneNum, String password) {
        mapParams.put("mobile", phoneNum);
        mapParams.put("password", password);
        mPresenter.toSubscribe(
                HttpManager.getApi().loginByPaswd(mapParams), new HttpSubscriber<LoginByPaswdBean>() {
                    @Override
                    protected void _onStart() {
                        //  Log.e("tag", "_onStart");
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在登录...");
                    }

                    @Override
                    protected void _onNext(LoginByPaswdBean loginByPaswdBean) {
                        // Log.e("tag", "_onNext");
                        ToastUtil.showToast("登录成功");
                        SpUtil.putBoolean(SpUtil.isLogin, true);
                        SpUtil.putString(SpUtil.userId, loginByPaswdBean.getId());//useId
                        SpUtil.putString(SpUtil.mobile, loginByPaswdBean.getMobile());//手机号码
                        SpUtil.putString(SpUtil.invite_code, loginByPaswdBean.getInvite_code());//邀请码
                        SpUtil.putString(SpUtil.is_state, loginByPaswdBean.getIs_state());//是否完善资料

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

    //获取手机号码和密码
    private void getEditContent() {
        phoneNum = mEtInputPhone.getText().toString().trim();
        password = mEtInputPsw.getText().toString().trim();
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @Override
    public void initView() {

    }
}
