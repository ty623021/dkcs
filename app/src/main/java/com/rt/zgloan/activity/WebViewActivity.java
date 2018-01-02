package com.rt.zgloan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.rt.zgloan.R;
import com.rt.zgloan.app.App;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.util.AppUtil;

import java.util.Map;

import butterknife.BindView;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/1.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    private String mUrl;
    private String upTitle;

    @Override
    public Observable<BaseResponse> initObservable() {
        return null;
    }

    @Override
    public boolean isFirstLoad() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }

        mTitle.setTitle(App.getAPPName());

        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString("url");
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        //WebView属性设置！！！
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDatabaseEnabled(true);

        //webview在安卓5.0之前默认允许其加载混合网络协议内容
        // 在安卓5.0之后，默认不允许加载http与https混合内容，需要设置webview允许其加载混合网络协议内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // mWebView.addJavascriptInterface(new JavaMethod(), "nativeMethod");
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.loadUrl(mUrl);
    }

    /**
     * 将cookie同步到WebView
     *
     * @param url WebView要加载的url
     * @param map 要同步的cookie
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public boolean syncCookie(String url, Map<String, String> map) {
        String newCookie;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.createInstance(mContext);
            }
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();//移除
            for (String key : map.keySet()) {
                String value = key + "=" + map.get(key);
                cookieManager.setCookie(url, value);
            }
            newCookie = cookieManager.getCookie(url);
        } catch (Exception e) {
            e.printStackTrace();
            newCookie = "";
        }
        return TextUtils.isEmpty(newCookie) ? false : true;
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }


    class MyWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();  // 接受所有网站的证书  解决https拦截问题
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(final WebView view, String url) {
            super.onPageFinished(view, url);
            mUrl = url;
            mProgressBar.setVisibility(View.GONE);
            if (view.canGoBack()) { //如果当前不是初始页面则显示关闭按钮
                mTitle.showClose(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            } else {
                mTitle.hintClose();
            }
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //记录操作行为  过滤重复回调
            if (!TextUtils.isEmpty(title) && !title.equals(upTitle)) {
                upTitle = title;
                setPageDescribe(title);
            }
            mTitle.setTitle(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }

                }
            }, title);
            mTitle.setRightTitle("", null);

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            closeActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void closeActivity() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }

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

    public static void startActivity(Context context, String mUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("url", mUrl);
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
