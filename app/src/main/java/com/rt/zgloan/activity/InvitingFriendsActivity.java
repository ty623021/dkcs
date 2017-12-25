package com.rt.zgloan.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.rt.zgloan.R;
import com.rt.zgloan.adapter.InviteFriendsRecordAdapter;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.InviteRecordBean;
import com.rt.zgloan.globe.Constant;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.MyShareSdk;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 邀请好友页面
 * Created by hjy on 2017/8/25.
 */

public class InvitingFriendsActivity extends BaseActivity<InviteRecordBean> {
    //    @BindView(R.id.copy)
//    RelativeLayout copy;
    @BindView(R.id.tv_invite_code)
    TextView tv_invite_code;
    @BindView(R.id.main)
    LinearLayout main;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.rv_invite_record)
    RecyclerView mRvInviteRecord;
    @BindView(R.id.tv_inivte_count)
    TextView mTvInivteCount;


    private View view;
    private String useId;
    private InviteFriendsRecordAdapter mAdapter;
    private LinearLayout wechat_friends, wechat_circle, qq_friends, sinaweibo_friends, qq_zone;
    private MyShareSdk showShare;

    @Override
    public Observable<BaseResponse<InviteRecordBean>> initObservable() {
        mapParams.put("id", useId);
//        mapParams.put("id", "273");

        return HttpManager.getApi().findInvitedUsers(mapParams);
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }

    @OnClick({R.id.copy, R.id.btn_share,})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copy:
                AbStringUtil.copy(tv_invite_code.getText().toString(), this);
                ToastUtil.showToast("复制成功");
                break;
            case R.id.btn_share:
                showSharePopWindow();
                break;


        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_inviting_friends;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("邀请好友");
//        mTitle.setRightTitle(R.mipmap.icon_title_share, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        useId = SpUtil.getString(SpUtil.userId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvInviteRecord.setLayoutManager(layoutManager);
        mRvInviteRecord.setNestedScrollingEnabled(false);
        mRvInviteRecord.setFocusable(false);
    }


    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);
    }

    @Override
    public void recordSuccess(InviteRecordBean inviteRecordBean) {
        if (inviteRecordBean != null) {
            Logger.e("inviteRecordBean" + inviteRecordBean);
            if (inviteRecordBean.getCount() != null) {
                mTvInivteCount.setText(inviteRecordBean.getCount() + "人");
            }
            if (inviteRecordBean.getInviteData() != null) {
                mAdapter = new InviteFriendsRecordAdapter(this);
                mAdapter.clearData();
                mAdapter.addData(inviteRecordBean.getInviteData());
                mRvInviteRecord.setAdapter(mAdapter);
            }

            if (inviteRecordBean.getInviteCode() != null) {
                tv_invite_code.setText(inviteRecordBean.getInviteCode() + "");//邀请码
            }

        }
    }


    /**
     * 分享的弹框
     */
    private void showSharePopWindow() {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(this).inflate(R.layout.share_dailog, null);
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
                AppUtil.backgroundAlpha(InvitingFriendsActivity.this, 1f);
            }
        });
        AppUtil.backgroundAlpha(InvitingFriendsActivity.this, 0.5f);

    }


}
