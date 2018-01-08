package com.rt.zgloan.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.DialogUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.StringUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.EditTextWithDel;
import com.rt.zgloan.weight.LoadingFragment;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 修改登录密码
 * Created by hjy on 2017/8/25.
 */

public class ChangeLoginPasswordActivity extends BaseActivity {

    @BindView(R.id.tv_get_graphical_code)
    TextView tv_get_graphical_code;
    @BindView(R.id.btn_sure)
    Button btn_sure;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.edit_set_old_password)
    EditTextWithDel mEtOldPsw;//旧密码
    @BindView(R.id.edit_set_new_password)
    EditTextWithDel mEtNewPsw;//新密码
    @BindView(R.id.edit_verification_code)
    EditText mEtVerificationCode;//短信验证码


    //    private MyCountDownTimer myCountDownTimer;
    private EditTextWithDel edit_graphic_verification_code;

    private String oldPsw, newPsw, phoneNum, verificationCode;
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

    @OnClick({R.id.tv_get_graphical_code, R.id.btn_sure})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_graphical_code:
//                showGraphicalCodeDialog();
//                if (StringUtil.isBlank(phoneNum)) {
//                    return;
//                }
                setSendCode(true);
                getMobileCode(phoneNum);
                break;
            case R.id.btn_sure:
                getEditContent();
                if (StringUtil.isBlank(oldPsw)) {
                    ToastUtil.showToast("请输入旧密码");
                    return;
                }
                if (StringUtil.isBlank(newPsw)) {
                    ToastUtil.showToast("请输入新密码");
                    return;
                }
                if ((newPsw.length() < 6 || newPsw.length() > 16) || (oldPsw.length() < 6 || oldPsw.length() > 16)) {
                    ToastUtil.showToast("密码长度应在6-16位之间");
                    return;
                }
                if (StringUtil.isBlank(verificationCode)) {
                    ToastUtil.showToast("请输入验证码");
                    return;
                }
                changeLoginPasswordHttp();

                break;

        }
    }

    private void getMobileCode(String phoneNum) {
        mapParams.put("mobile", phoneNum);
        mPresenter.toSubscribe(HttpManager.getApi().getMobileCode(mapParams), new HttpSubscriber() {
            @Override
            protected void _onStart() {
                LoadingFragment.getInstance().show(((FragmentActivity) mContext).getSupportFragmentManager(), "");
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
                LoadingFragment.getInstance().dismiss();
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_login_password;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("修改登录密码");
//        String phone = SpUtil.getString(SpUtil.mobile).replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        phoneNum = SpUtil.getString(SpUtil.mobile);
//        myCountDownTimer = new MyCountDownTimer(60000, 1000);

    }

//    private void showGraphicalCodeDialog() {
//        View view = View.inflate(this, R.layout.dialog_graphical_code, null);
//        ImageView clear_dialog_imgs = (ImageView) view.findViewById(R.id.clear_dialog_imgs);
//        edit_graphic_verification_code = (EditTextWithDel) view.findViewById(R.id.edit_graphic_verification_code);
//        final ImageView graphic_verification_code_img = (ImageView) view.findViewById(R.id.graphic_verification_code_img);
//        AbImageUtil.glideImage(Constant.Url + SpUtil.getString(SpUtil.mobile), graphic_verification_code_img, R.mipmap.error, R.mipmap.preload);
//        Button btn_sure = (Button) view.findViewById(R.id.btn_sure);
//
//        graphic_verification_code_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AbImageUtil.glideImage(Constant.Url + SpUtil.getString(SpUtil.mobile), graphic_verification_code_img, R.mipmap.error, R.mipmap.preload);
//            }
//        });
//
//        clear_dialog_imgs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogUtil.dismiss();
//            }
//        });
//        btn_sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                str_graphic_verification_code = edit_graphic_verification_code.getText().toString().trim();
//                if (StringUtil.isBlank(str_graphic_verification_code)) {
//                    ToastUtil.showToast("请输入图形验证码");
//                    return;
//                }
//                getCodeHttp();
//            }
//        });
//
//        DialogUtil.showAlertDialog(view);
//    }


    private void changeLoginPasswordHttp() {
        mapParams.put("id", SpUtil.getString(SpUtil.userId));
        mapParams.put("mobile", SpUtil.getString(SpUtil.mobile));
        mapParams.put("old_password", oldPsw);
        mapParams.put("new_password", newPsw);
        mapParams.put("mobileCode", verificationCode);

        mPresenter.toSubscribe(
                HttpManager.getApi().changePwd(mapParams), new HttpSubscriber() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstance().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在修改...");
                    }

                    @Override
                    protected void _onNext(Object o) {
                        SpUtil.remove(SpUtil.userId);
                        SpUtil.remove(SpUtil.mobile);
                        SpUtil.remove(SpUtil.isLogin);
                        ChangePasswordSuccessActivity.startActivity(mContext);
                    }


                    @Override
                    protected void _onError(String message) {
//                        Log.e("tag", "_onError" + message);
                        ToastUtil.showToast(message);


                    }

                    @Override
                    protected void _onCompleted() {

                        LoadingFragment.getInstance().dismiss();
                    }
                }
        );

    }

    /**
     * 获取手机验证码
     */
    private void getCodeHttp() {
        mapParams.put("mobile", SpUtil.getString(SpUtil.mobile));
        mapParams.put("code", verificationCode);
        mPresenter.toSubscribe(
                HttpManager.getApi().getCodeForgotPassword(mapParams), new HttpSubscriber() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstance().show(((FragmentActivity) mContext).getSupportFragmentManager(), "");
                    }

                    @Override
                    protected void _onNext(Object o) {
                        ToastUtil.showToast("发送验证码成功");
//                        myCountDownTimer.start();
                        DialogUtil.dismiss();

                    }


                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(message);


                    }

                    @Override
                    protected void _onCompleted() {
                        LoadingFragment.getInstance().dismiss();

                    }
                }
        );
    }

    private void getEditContent() {
        oldPsw = mEtOldPsw.getText().toString().trim();
        newPsw = mEtNewPsw.getText().toString().trim();
        verificationCode = mEtVerificationCode.getText().toString().trim();
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

//    //复写倒计时
//    private class MyCountDownTimer extends CountDownTimer {
//
//        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
//            super(millisInFuture, countDownInterval);
//        }
//
//        //计时过程
//        @Override
//        public void onTick(long l) {
//            //防止计时过程中重复点击
//            tv_get_graphical_code.setClickable(false);
//            tv_get_graphical_code.setText(l / 1000 + "s");
//
//        }
//
//        //计时完毕的方法
//        @Override
//        public void onFinish() {
//            //重新给Button设置文字
//            tv_get_graphical_code.setText("重新获取");
//            //设置可点击
//            tv_get_graphical_code.setClickable(true);
//        }
//    }

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
