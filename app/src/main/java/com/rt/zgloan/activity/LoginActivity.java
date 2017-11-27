package com.rt.zgloan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.RegisterInfo;
import com.rt.zgloan.fragment.DynamicLoginFragment;
import com.rt.zgloan.fragment.PasswordLoginFragment;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.StringUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.EditTextWithDel;
import com.rt.zgloan.weight.LoadingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * 登录
 * Created by hjy on 2017/8/25.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.edit_input_phone)
    EditTextWithDel editInputPhone;
    @BindView(R.id.edit_input_login_password)
    EditTextWithDel editInputLoginPassword;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.Ll_dynamic_login)
    LinearLayout mLlDynamicLogin;
    @BindView(R.id.Ll_psw_login)
    LinearLayout mLlPswLogin;
    @BindView(R.id.login_line1)
    View mLoginLine1;
    @BindView(R.id.login_line2)
    View mLoginLine2;
    @BindView(R.id.tv_dynamic_login)
    TextView mTvDynamicLogin;
    @BindView(R.id.tv_psw_login)
    TextView mTvPswLogin;

    private String str_phone, str_password;
    //两个fragment界面
    private DynamicLoginFragment mDynamicLoginFragment;
    private PasswordLoginFragment mPswLoginFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private void initTab() {
        mTvDynamicLogin.setSelected(false);
        mTvPswLogin.setSelected(false);

        mLoginLine1.setVisibility(View.INVISIBLE);
        mLoginLine2.setVisibility(View.INVISIBLE);


    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mDynamicLoginFragment != null) {
            transaction.hide(mDynamicLoginFragment);
        }
        if (mPswLoginFragment != null) {
            transaction.hide(mPswLoginFragment);
        }
    }

    public void switchTab(int index) {
        initTab();
        transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (mDynamicLoginFragment == null) {
                    mDynamicLoginFragment = new DynamicLoginFragment();
                    transaction.add(R.id.login_fragments, mDynamicLoginFragment);
                } else {
                    transaction.show(mDynamicLoginFragment);
                }
                mTvDynamicLogin.setSelected(true);
                mLoginLine1.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (mPswLoginFragment == null) {
                    mPswLoginFragment = new PasswordLoginFragment();

                    transaction.add(R.id.login_fragments, mPswLoginFragment);
                } else {
                    transaction.show(mPswLoginFragment);
                }
                mTvPswLogin.setSelected(true);
                mLoginLine2.setVisibility(View.VISIBLE);
                break;

        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @OnClick({R.id.tv_forgot_password, R.id.btn_login, R.id.Ll_dynamic_login, R.id.Ll_psw_login, R.id.iv_back, R.id.tv_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_forgot_password:
                this.startActivity(ForgotPasswordActivity.class);
                break;
            case R.id.btn_login:
                getEditContent();
                if (StringUtil.isBlank(str_phone)) {
                    ToastUtil.showToast("请输入手机号码");
                    return;
                }
                if (!AbStringUtil.isMobileNo(str_phone)) {
                    ToastUtil.showToast("请输入正确格式的手机号码");
                    return;
                }
                if (StringUtil.isBlank(str_password)) {
                    ToastUtil.showToast("请输入登录密码");
                    return;
                }
                loginHttp();
                break;
            case R.id.Ll_dynamic_login:
                switchTab(0);
                break;
            case R.id.Ll_psw_login:
                switchTab(1);
                break;

        }

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        fragmentManager = getSupportFragmentManager();
        switchTab(0);
    }

    /**
     * 登录
     */
    private void loginHttp() {
        mapParams.put("mobile", str_phone);
        mapParams.put("password", str_password);
        mPresenter.toSubscribe(
                HttpManager.getApi().login(mapParams), new HttpSubscriber<RegisterInfo>() {
                    @Override
                    protected void _onStart() {
                        //  Log.e("tag", "_onStart");
                        LoadingFragment.getInstends().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在登录...");
                    }

                    @Override
                    protected void _onNext(RegisterInfo registerInfo) {
                        // Log.e("tag", "_onNext");
                        ToastUtil.showToast("登录成功");
                        SpUtil.putBoolean(SpUtil.isLogin, true);
                        SpUtil.putString(SpUtil.userId, registerInfo.getRelute().getId());
                        SpUtil.putString(SpUtil.mobile, registerInfo.getRelute().getMobile());
                        SpUtil.putString(SpUtil.invite_code, registerInfo.getRelute().getInvite_code());
                        SpUtil.putString(SpUtil.is_state, registerInfo.getRelute().getIs_state());
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

    private void getEditContent() {
        str_phone = editInputPhone.getText().toString().trim();
        str_password = editInputLoginPassword.getText().toString().trim();
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

    /**
     * 跳转到 MainActivity
     *
     * @param context
     * @param currPager 选中的页面
     */
    public static void startLoginActivity(Activity context, int currPager) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("currPager", currPager);
        context.startActivity(intent);
        context.finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int currPager = intent.getIntExtra("currPager", 0);
            switchTab(currPager);
        }
    }
}
