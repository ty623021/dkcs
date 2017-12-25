package com.rt.zgloan.activity.myActivity;

import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.AboutMeBean;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.util.UpdateVersionUtil;
import com.rt.zgloan.util.ViewUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 关于我们///
 * Created by hjy on 2017/8/25.
 */

public class AboutOurActivity extends BaseActivity<AboutMeBean> {

    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.tv_latest_edition)
    TextView tv_latest_edition;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.tv_about_us)
    TextView mTvAboutUs;

    private String Version_number;


    @Override
    public Observable<BaseResponse<AboutMeBean>> initObservable() {
//        mapParams.put("version_number", ViewUtil.getAppVersionCode(mActivity) + "");
//        mapParams.put("canal_id", mActivity.getResources().getString(R.string.channelId));
        return HttpManager.getApi().aboutMe(mapParams);
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }

    @OnClick({R.id.rel_about_our, R.id.rel_check_change})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_about_our:
                this.startActivity(AbourOurSonActivity.class);
                break;
            case R.id.rel_check_change:
                UpdateVersionUtil updateVersionUtil = new UpdateVersionUtil(this);
                updateVersionUtil.checkVersion(true);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_our;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle("关于我们");
        tvRight.setText(ViewUtil.getAppVersion(mActivity));

    }


    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {
        ToastUtil.showToast(msg);

    }

    @Override
    public void recordSuccess(AboutMeBean aboutMeBean) {
        if (aboutMeBean != null) {
            mTvAboutUs.setText(Html.fromHtml(aboutMeBean.getContent()));
        }
    }

//    @Override
//    public void recordSuccess(Version version) {
//        if (Integer.parseInt(version.getVersion_number()) == ViewUtil.getAppVersionCode(mContext)) {
//            tv_latest_edition.setVisibility(View.VISIBLE);
//        } else {
//            tv_latest_edition.setVisibility(View.INVISIBLE);
//        }
//    }


}
