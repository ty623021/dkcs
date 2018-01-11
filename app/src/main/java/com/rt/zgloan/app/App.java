package com.rt.zgloan.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.rt.zgloan.R;
import com.rt.zgloan.util.ToastUtil;

/**
 * Created by Administrator on 2017/8/21.
 */

public class App extends Application {
    public static App mApp;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //突破Dex文件方法数不能超过最大值65536的上限
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        //toast初始化
        ToastUtil.register(getContext());
    }
    public static Context getContext() {
        return mApp.getApplicationContext();
    }
    public static String getAPPName() {
        return getContext().getResources().getString(R.string.app_name);
    }
}
