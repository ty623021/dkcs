package com.rt.zgloan.base;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.rt.zgloan.app.App;
import com.rt.zgloan.app.AppManager;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.db.UserActionDao;
import com.rt.zgloan.dbentity.UserAction;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.presenter.BasePresenter;
import com.rt.zgloan.util.AbDateUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.StatusBarUtil;
import com.rt.zgloan.util.TitleUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.weight.AlertFragmentDialog;
import com.rt.zgloan.weight.LoadingFragment;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import rx.Observable;

/**
 * 基类..
 */
public abstract class BaseActivity<T> extends AppCompatActivity implements BaseView<T> {
    protected String TAG = this.getClass().getSimpleName();
    public BasePresenter mPresenter;
    public Context mContext;
    public BaseActivity mActivity;
    private PermissionsListener mListener;
    protected TitleUtil mTitle;
    // protected KeyboardNumberUtil input_controller;
    protected int type;
    public Map<String, String> mapParams = new HashMap<>();//请求参数
    public Map<String, Integer> mapParams2 = new HashMap<>();
    protected String progressTitle = "正在加载";
    private String className = this.getClass().getSimpleName();
    private UserAction userAction;
    private long s, e;
    private long startTime_WebPage = 0;//h5起始时间
    public String pageDescribe = "";//页面描述
    private UserAction userAction_WebPage;//h5操作对象
    private String stop_pageDescribe = "";//在webviewActivity中的最后一个页面的标题


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        AppManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        mContext = this;
        mActivity = this;
        // mPresenter = TUtil.getT(this,0);
        mPresenter = new BasePresenter();
        mTitle = new TitleUtil(this, getWindow().getDecorView());
        initStatusBar();
        initView();
        initPageDescrible();
        initPresenter();
    }

    private void initPageDescrible() {
        pageDescribe = mTitle.getTitle();
        if ("SplashActivity".equals(className)) {
            pageDescribe = "启动页";
        } else if ("GuideActivity".equals(className)) {
            pageDescribe = "引导页";
        } else if ("MainActivity".equals(className)) {
            pageDescribe = "首页";
        } else if ("WebViewActivity".equals(className)) {
            pageDescribe = "H5页";
        }
    }

    public abstract Observable<BaseResponse<T>> initObservable();

    public abstract boolean isFirstLoad();

    public abstract void onClick(View v);

    private void initPresenter() {
        //设置是否一进入Activity 就发送请求
        if (!isFirstLoad()) {
            return;
        }
        mPresenter.init(this);
        mPresenter.toSubscribe(initObservable(), new HttpSubscriber<T>() {
            @Override
            protected void _onStart() {
                mPresenter.mView.showLoading("");
                LoadingFragment.getInstance().show(BaseActivity.this.getSupportFragmentManager(), progressTitle);
            }

            @Override
            protected void _onNext(T t) {
                if (t != null) {
                    mPresenter.mView.recordSuccess(t);
                } else {
                    mPresenter.mView.showErrorMsg("数据获取失败，请重新获取", null);
                }

            }

            @Override
            protected void _onError(String message) {
                mPresenter.mView.showErrorMsg(message, null);
                ToastUtil.showToast(message);
            }

            @Override
            protected void _onCompleted() {
                LoadingFragment.getInstance().dismiss();
            }
        });

    }

    private void initStatusBar() {
        //层垫式状态栏
//        StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark);
        StatusBarUtil.transparencyBar(this);//透明状态栏

    }


    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    // public abstract void initPresenter();

    //加载、设置数据
    public abstract void initView();


//    protected boolean isKeyboardShow() {
//        if (input_controller != null) {
//            return input_controller.isKeyboardShow();
//        } else {
//            return false;
//        }
//    }
//    protected void showKeyboard(View wholeView, KeyboardNumberUtil.CUSTOMER_KEYBOARD_TYPE type, EditText view) {
//        if (getCurrentFocus() != null) {
//            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//        view.requestFocus();
//        input_controller = new KeyboardNumberUtil(this, wholeView, type, view);
//        input_controller.showKeyboard();
//        addInputListener(view);
//    }

    protected void hideKeyboard() {
//        if (input_controller != null) {
//            input_controller.hideKeyboard();
//        }
    }

    private void addInputListener(final EditText view) {
//        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus)
//                    hideKeyboard();
//            }
//        });
//        input_controller.setmKeyboardClickListenr(new KeyboardNumberUtil.KeyboardNumberClickListener() {
//
//            @Override
//            public void postHideEt() {
//            }
//
//            @Override
//            public void onAnimationEnd() {
//            }
//
//            @Override
//            public void handleHideEt(View relatedEt, int hideDistance) {
//            }
//
//            @Override
//            public void clickDelete() {
//                if (view.length() > 0) {
//                    view.setText(view.getText().subSequence(0, view.length() - 1));
//                    view.setSelection(view.getText().length());
//                }
//            }
//
//            @Override
//            public void click(String clickStr) {
//                view.append(clickStr);
//                view.setSelection(view.getText().length());
//            }
//
//            @Override
//            public void clear() {
//                view.setText("");
//                view.setSelection(view.getText().length());
//            }
//        });
    }

    /**
     * 滑到底部自动加载
     *
     * @param refresh
     * @param swipeTarget
     */
//    protected void autoLoading(final SwipeToLoadLayout refresh, RecyclerView swipeTarget) {
//        //自动上拉
//        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    if (!ViewCompat.canScrollVertically(recyclerView, 1)) {
//                        refresh.setLoadingMore(true);
//                    }
//                }
//            }
//        });
//    }

//    protected void onComplete(SwipeToLoadLayout refresh){
//        if (refresh!=null){
//            if (refresh.isRefreshing()){
//                refresh.setRefreshing(false);
//            }else if (refresh.isLoadingMore()){
//                refresh.setLoadingMore(false);
//            }
//        }
//
//    }

    /**
     * 请求权限封装
     *
     * @param permissions
     * @param listener
     */
    public void requestPermissions(String[] permissions, PermissionsListener listener) {
        mListener = listener;
        List<String> requestPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                requestPermissions.add(permission);
            }
        }
        if (!requestPermissions.isEmpty() && Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, requestPermissions.toArray(new String[requestPermissions.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                List<String> deniedPermissions = new ArrayList<>();
                //当所有拒绝的权限都勾选不再询问，这个值为true,这个时候可以引导用户手动去授权。
                boolean isNeverAsk = true;
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        deniedPermissions.add(permissions[i]);
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) { // 点击拒绝但没有勾选不再询问
                            isNeverAsk = false;
                        }
                    }
                }
                if (deniedPermissions.isEmpty()) {
                    try {
                        mListener.onGranted();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        mListener.onDenied(Arrays.asList(permissions), true);
                    }
                } else {
                    //被拒绝权限
//                    for (String p:deniedPermissions){
//                        LogUtils.loge("Permissions  "+p);
//                    }
                    mListener.onDenied(deniedPermissions, isNeverAsk);
                }
                break;
            default:
                break;
        }
    }

    // 启动应用的设置弹窗
    public void toAppSettings(String message, final boolean isFinish) {
        if (TextUtils.isEmpty(message)) {
            message = "\"" + App.getAPPName() + "\"缺少必要权限";
        }
        AlertFragmentDialog.Builder builder = new AlertFragmentDialog.Builder(this);
        if (isFinish) {
            builder.setLeftBtnText("退出")
                    .setLeftCallBack(new AlertFragmentDialog.LeftClickCallBack() {
                        @Override
                        public void dialogLeftBtnClick() {
                            finish();
                        }
                    });
        } else {
            builder.setLeftBtnText("取消");
        }
        builder.setContent(message + "\n请手动授予\"" + App.getAPPName() + "\"访问您的权限")
                .setRightBtnText("去设置")
                .setRightCallBack(new AlertFragmentDialog.RightClickCallBack() {
                    @Override
                    public void dialogRightBtnClick() {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                }).build();
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    IWindowFocus iFocus;

    public void setOnIWindowFocus(IWindowFocus windowFocus) {
        iFocus = windowFocus;
    }

    public interface IWindowFocus {
        void focused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟统计
        MobclickAgent.onResume(this);
        //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onPageStart(TAG);
        Date date = new Date();
        s = date.getTime();
        userAction = new UserAction();
        userAction.setPageStartTime(getStringByFormat(date, "yyyy-MM-dd HH:mm:ss"));
        userAction.setDevice(AppUtil.getDevice());
        userAction.setPageName(className);
        userAction.setPageDescribe(pageDescribe);
        if ("WebViewActivity".equals(className)) {
            if (!TextUtils.isEmpty(stop_pageDescribe)) {
                userResume(stop_pageDescribe);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (userAction != null) {
            Date date = new Date();
            e = date.getTime();
            userAction.setPageEndTime(getStringByFormat(date, "yyyy-MM-dd HH:mm:ss"));
            userAction.setPageWaiteTime(AbDateUtil.getTimeDescription(e - s));
            UserActionDao.getInstance(this).add(userAction);
            userAction = null;
            if ("WebViewActivity".equals(className)) {
                if (!TextUtils.isEmpty(stop_pageDescribe)) {
                    userStop();
                }

            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟统计
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(TAG);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        AppManager.getInstance().finishActivity(this);
    }

    //    @Override
//    public void onBackPressed() {
//        if (isKeyboardShow()){
//            hideKeyboard();
//        }else{
//            super.onBackPressed();
//        }
//    }
    public static String getStringByFormat(Date date, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public void setPageDescribe(String pageDescribe) {
        //前页面开始
        if (startTime_WebPage == 0) {
            userResume(pageDescribe);
        } else {
            //当前页面结束,新页面开始 保存前一个页面
            userStop();
            //初始化新页面
            userResume(pageDescribe);
        }

    }

    public void userResume(String pageDescribe) {
        Date data = new Date();
        startTime_WebPage = data.getTime();
        userAction_WebPage = new UserAction();
        userAction_WebPage.setPageStartTime(getStringByFormat(data, "yyyy-MM-dd HH:mm:ss"));
        userAction_WebPage.setDevice(AppUtil.getDevice());
        userAction_WebPage.setPageName(className);
        stop_pageDescribe = pageDescribe;
        userAction_WebPage.setPageDescribe(pageDescribe);
    }

    public void userStop() {
        if (userAction_WebPage != null) {
            userAction_WebPage.setPageEndTime(getStringByFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
            userAction_WebPage.setPageWaiteTime(AbDateUtil.getTimeDescription(new Date().getTime() - startTime_WebPage));
            //记录最后一个页面的标题
            stop_pageDescribe = userAction_WebPage.getPageDescribe();
            UserActionDao.getInstance(this).add(userAction_WebPage);
            userAction_WebPage = null;
        }
    }
}
