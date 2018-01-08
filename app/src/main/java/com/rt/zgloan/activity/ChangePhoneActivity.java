package com.rt.zgloan.activity;

import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.myActivity.ChangePhoneNextActivity;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.globe.Constant;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.util.AbImageUtil;
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
 * 修改手机号码第一步
 * Created by hjy on 2017/8/25.
 */

public class ChangePhoneActivity extends BaseActivity {
    private static final String TAG = "ChangePhoneActivity";
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.edit_verification_code)
    EditText edit_verification_code;
    @BindView(R.id.tv_get_graphical_code)
    TextView tv_get_graphical_code;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    private String str_verification_code;
    private MyCountDownTimer myCountDownTimer;

    private EditTextWithDel edit_graphic_verification_code;
    private String str_graphic_verification_code;


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
                getEditContent();
                showGraphicalCodeDialog();
                break;

            case R.id.btn_sure:
                getEditContent();
                if (StringUtil.isBlank(str_verification_code)) {
                    ToastUtil.showToast("请输入手机验证码");
                    return;
                }
                changePhoneHttp();

                break;
        }


    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_change_phone;
    }


    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("修改手机号码");
        String phone = SpUtil.getString(SpUtil.mobile).replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        tv_phone.setText("当前手机号码:" + phone);
        myCountDownTimer = new MyCountDownTimer(60000, 1000);
    }

    private void showGraphicalCodeDialog() {
        View view = View.inflate(this, R.layout.dialog_graphical_code, null);
        ImageView clear_dialog_imgs = (ImageView) view.findViewById(R.id.clear_dialog_imgs);
        edit_graphic_verification_code = (EditTextWithDel) view.findViewById(R.id.edit_graphic_verification_code);
        final ImageView graphic_verification_code_img = (ImageView) view.findViewById(R.id.graphic_verification_code_img);
        AbImageUtil.glideImage(Constant.Url + SpUtil.getString(SpUtil.mobile), graphic_verification_code_img, R.mipmap.error, R.mipmap.preload);
        Button btn_sure = (Button) view.findViewById(R.id.btn_sure);

        graphic_verification_code_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbImageUtil.glideImage(Constant.Url + SpUtil.getString(SpUtil.mobile), graphic_verification_code_img, R.mipmap.error, R.mipmap.preload);
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


    private void changePhoneHttp() {

        mapParams.put("mobile", SpUtil.getString(SpUtil.mobile));
        mapParams.put("id", SpUtil.getString(SpUtil.userId));
        mapParams.put("code", str_verification_code);
        mPresenter.toSubscribe(
                HttpManager.getApi().changePhoneOne(mapParams), new HttpSubscriber() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstance().show(((FragmentActivity) mContext).getSupportFragmentManager(), "正在验证...");
                    }

                    @Override
                    protected void _onNext(Object o) {
//                        mActivity.startActivity(ChangePhoneNextActivity.class);
                        ChangePhoneNextActivity.startChangePhoneNextActivity(mContext, str_verification_code);
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


    /**
     * 获取手机验证码
     */
    private void getCodeHttp() {
        mapParams.put("mobile", SpUtil.getString(SpUtil.mobile));
        mapParams.put("code", str_graphic_verification_code);
        mPresenter.toSubscribe(
                HttpManager.getApi().getCodeForgotPassword(mapParams), new HttpSubscriber() {
                    @Override
                    protected void _onStart() {
                        LoadingFragment.getInstance().show(((FragmentActivity) mContext).getSupportFragmentManager(), "");
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
                        LoadingFragment.getInstance().dismiss();

                    }
                }
        );
    }

    private void getEditContent() {
        str_verification_code = edit_verification_code.getText().toString().trim();
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

}
