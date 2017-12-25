package com.rt.zgloan.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.activity.InvitingFriendsActivity;
import com.rt.zgloan.activity.LoginActivity;
import com.rt.zgloan.activity.PersonalDataActivity;
import com.rt.zgloan.activity.RegisterActivity;
import com.rt.zgloan.activity.SetAtivity;
import com.rt.zgloan.activity.myActivity.AboutOurActivity;
import com.rt.zgloan.base.BaseFragment;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.globe.Constant;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.weight.MyShareSdk;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 个人中心页面
 * Created by Administrator on 2017/8/24.
 */

public class FragmentMy extends BaseFragment {
    private static final String TAG = "FragmentMy";
    @BindView(R.id.Ll_main)
    LinearLayout main;
    @BindView(R.id.rel_personal_data)
    RelativeLayout rel_personal_data;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.rel_inviting_friends)
    RelativeLayout rel_inviting_friends;
    @BindView(R.id.rel_about_us)
    RelativeLayout rel_about_us;
    @BindView(R.id.line_aready_login)
    LinearLayout line_aready_login;
    @BindView(R.id.rel_no_login)
    RelativeLayout rel_no_login;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.red_circel)
    ImageView red_circel;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.tv_login_phone)
    TextView mTvLoginPhone;
    @BindView(R.id.line_login)
    View mLineLogin;

    private View view;

    private LinearLayout wechat_friends, wechat_circle, qq_friends, sinaweibo_friends, qq_zone;
    private MyShareSdk showShare;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @OnClick({R.id.rel_personal_data, R.id.btn_register, R.id.btn_login, R.id.rel_inviting_friends, R.id.rel_about_us,
            R.id.Rl_personage_center, R.id.Rl_my_share
    })
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_personal_data:
                if (SpUtil.getBoolean(SpUtil.isLogin)) {
                    this.startActivity(PersonalDataActivity.class);
                } else {
                    this.startActivity(LoginActivity.class);
                }
//                this.startActivity(PersonalDataActivity.class);
                break;
            case R.id.btn_register:
                this.startActivity(RegisterActivity.class);
                break;
            case R.id.btn_login:
                this.startActivity(LoginActivity.class);
                break;
            case R.id.rel_inviting_friends:
                if (SpUtil.getBoolean(SpUtil.isLogin)) {
                    this.startActivity(InvitingFriendsActivity.class);
                } else {
                    this.startActivity(LoginActivity.class);
                }
//                this.startActivity(InvitingFriendsActivity.class);
                break;
            case R.id.rel_about_us:
                this.startActivity(AboutOurActivity.class);
                break;
            case R.id.Rl_personage_center:
                if (SpUtil.getBoolean(SpUtil.isLogin)) {
                    this.startActivity(SetAtivity.class);
                } else {
                    this.startActivity(LoginActivity.class);
                }
//                this.startActivity(SetAtivity.class);
                break;
            case R.id.Rl_my_share:
                if (SpUtil.getBoolean(SpUtil.isLogin)) {
                    showSharePopWindow();
                } else {
                    this.startActivity(LoginActivity.class);
                }
//                showSharePopWindow();
                break;

        }
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }

        if (SpUtil.getBoolean(SpUtil.isLogin)) {
            mTvLoginPhone.setText(SpUtil.getString(SpUtil.mobile));
            mLineLogin.setVisibility(View.GONE);
//            mTvLoginPhone.setText(SpUtil.getString(SpUtil.mobile));//登录显示手机号
//            mLineLogin.setVisibility(View.GONE);
//            line_aready_login.setVisibility(View.GONE);
//            rel_no_login.setVisibility(View.GONE);
//            String phone = SpUtil.getString(SpUtil.mobile).replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
//            tv_phone.setText(phone);
            if (SpUtil.getString(SpUtil.is_state).equals("0")) {
                red_circel.setVisibility(View.INVISIBLE);
            } else if (SpUtil.getString(SpUtil.is_state).equals("1")) {
                red_circel.setVisibility(View.INVISIBLE);
            }

        } else {
            mTvLoginPhone.setText("立即登录");
            mLineLogin.setVisibility(View.INVISIBLE);
//            line_aready_login.setVisibility(View.GONE);
//            rel_no_login.setVisibility(View.GONE);
            red_circel.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public void showLoading(String content) {

    }


    @Override
    public void onResume() {
        super.onResume();
        if (SpUtil.getBoolean(SpUtil.isLogin)) {
            mTvLoginPhone.setText(SpUtil.getString(SpUtil.mobile));
            mLineLogin.setVisibility(View.GONE);
//            line_aready_login.setVisibility(View.VISIBLE);
//            rel_no_login.setVisibility(View.GONE);
//            String phone = SpUtil.getString(SpUtil.mobile).replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
//            tv_phone.setText(phone);
            if (SpUtil.getString(SpUtil.is_state).equals("0")) {
                red_circel.setVisibility(View.INVISIBLE);
            } else if (SpUtil.getString(SpUtil.is_state).equals("1")) {
                red_circel.setVisibility(View.INVISIBLE);
            }
        } else {
            mTvLoginPhone.setText("立即登录");
            mLineLogin.setVisibility(View.VISIBLE);
//            line_aready_login.setVisibility(View.GONE);
//            rel_no_login.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showErrorMsg(String msg, String type) {

    }

    @Override
    public void recordSuccess(Object o) {

    }

    /**
     * 分享的弹框
     */
    private void showSharePopWindow() {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(getActivity()).inflate(R.layout.share_dailog, null);

        wechat_friends = (LinearLayout) view.findViewById(R.id.wechat_friends);
        wechat_circle = (LinearLayout) view.findViewById(R.id.wechat_circle);
        qq_friends = (LinearLayout) view.findViewById(R.id.qq_friends);
        sinaweibo_friends = (LinearLayout) view.findViewById(R.id.sinaweibo_friends);
        qq_zone = (LinearLayout) view.findViewById(R.id.qq_zone);

        wechat_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("Wechat", getString(R.string.share_title_detail), Constant.INVITEUrl + SpUtil.getString(SpUtil.invite_code), getString(R.string.share_content_detail));
                }
            }
        });

        wechat_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("WechatMoments", getString(R.string.share_title_detail), Constant.INVITEUrl + SpUtil.getString(SpUtil.invite_code), getString(R.string.share_content_detail));
                }
            }
        });
        qq_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("QQ", getString(R.string.share_title_detail), Constant.INVITEUrl + SpUtil.getString(SpUtil.invite_code), getString(R.string.share_content_detail));
                }
            }
        });
        sinaweibo_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("SinaWeibo", getString(R.string.share_title_detail), Constant.INVITEUrl + SpUtil.getString(SpUtil.invite_code), getString(R.string.share_content_detail));
                }
            }
        });
        qq_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showShare == null) {
                    showShare = new MyShareSdk(mContext);
                } else {
                    showShare.showShare("QZone", getString(R.string.share_title_detail), Constant.INVITEUrl + SpUtil.getString(SpUtil.invite_code), getString(R.string.share_content_detail));
                }
            }
        });

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00808080);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.my_popshow_anim_style);
        //设置在底部显示
        window.showAtLocation(main, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                AppUtil.backgroundAlpha(getActivity(), 1f);
            }
        });
        AppUtil.backgroundAlpha(getActivity(), 0.5f);

    }
}
