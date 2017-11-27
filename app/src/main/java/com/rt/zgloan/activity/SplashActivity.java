package com.rt.zgloan.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import com.rt.zgloan.R;
import com.rt.zgloan.app.App;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.base.PermissionsListener;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.weight.AlertFragmentDialog;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2017/8/25.
 */

public class SplashActivity extends BaseActivity {
    private boolean isRequesting;//为了避免在onResume中多次请求权限
    private String[] permissions = {Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onResume() {
        super.onResume();
        if (!isRequesting) {
            requestPermissions(permissions, mListener);
            isRequesting = true;
        }
    }

    private PermissionsListener mListener = new PermissionsListener() {
        @Override
        public void onGranted() {
            isRequesting = false;
            startActivity();

        }

        @Override
        public void onDenied(List<String> deniedPermissions, boolean isNeverAsk) {
            if (!isNeverAsk) {//请求权限没有全被勾选不再提示然后拒绝
                new AlertFragmentDialog.Builder(mActivity)
                        .setLeftBtnText("取消").setLeftCallBack(new AlertFragmentDialog.LeftClickCallBack() {
                    @Override
                    public void dialogLeftBtnClick() {
                        startActivity();
                    }
                }).setContent("为了能正常使用\"" + App.getAPPName() + "\"，请授予所需权限")
                        .setRightBtnText("授权").setRightCallBack(new AlertFragmentDialog.RightClickCallBack() {
                    @Override
                    public void dialogRightBtnClick() {
                        requestPermissions(permissions, mListener);
                    }
                }).build();
            } else {//全被勾选不再提示
                new AlertFragmentDialog.Builder(mActivity)
                        .setLeftBtnText("取消").setLeftCallBack(new AlertFragmentDialog.LeftClickCallBack() {
                    @Override
                    public void dialogLeftBtnClick() {
                        startActivity();
                    }
                }).setContent("\"" + App.getAPPName() + "\"缺少必要权限\n请手动授予\"" + App.getAPPName() + "\"访问您的权限")
                        .setRightBtnText("授权").setRightCallBack(new AlertFragmentDialog.RightClickCallBack() {
                    @Override
                    public void dialogRightBtnClick() {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                        isRequesting = false;
                    }
                }).build();
            }
        }
    };

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onClick(View v) {

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

    private void startActivity() {
        if (SpUtil.getInt(SpUtil.CACHE_IS_FIRST_LOGIN, SpUtil.HAS_ALREADY_LOGIN) == SpUtil.HAS_ALREADY_LOGIN) {
            startActivity(GuideActivity.class);
            finish();
        } else {
            startActivity(MainActivity.class);
            finish();
        }
    }
}
