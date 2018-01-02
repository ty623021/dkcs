package com.rt.zgloan.http;


import android.util.Log;

import com.rt.zgloan.app.App;
import com.rt.zgloan.util.NetUtil;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by xiejingwen on 2016/10/10 15:49.
 */
public abstract class HttpSubscriber<T> extends Subscriber<T> {
    public final int ERROR_DEFAULT = -1;    //默认异常
    public final int ERROR_NOT_LOGIN = -2;  //登录失效
    public final int ERROR_BUSY = -3;        //请求繁忙
    public final int ERROR_NETWORK = -4;     //无网络
    public final int ERROR_TIME_OUT = -5;     //请求超时

    public HttpSubscriber() {
    }

    @Override
    public void onStart() {
        super.onStart();
        _onStart();
    }

    @Override
    public void onCompleted() {
        _onCompleted();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        Log.e("tag", "HttpSubscriber onError.....................");
        e.printStackTrace();
        if (!NetUtil.isConnected(App.getContext())) {
            _onError("网络不可用", ERROR_NETWORK);
        } else if (e instanceof ApiException) {
            if (((ApiException) e).getCode() == ERROR_NOT_LOGIN) {
                //  EventBus.getDefault().post(new LogoutEvent(App.getContext(),0));
                _onError("请先登录", ((ApiException) e).getCode());
            } else if (((ApiException) e).getCode() == ERROR_BUSY) {
//                    if (null!=AppManager.getInstance().currentActivity()){
//                        RequestBusyFragmentDialog.newInstance(((ApiException) e).getTime()).show(AppManager.getInstance().currentActivity().getSupportFragmentManager(),RequestBusyFragmentDialog.TAG);
//                    }
            } else {
                _onError(e.getMessage(), ((ApiException) e).getCode());
            }
        } else if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            if (exception.code() == 401) {
                //清空
                // EventBus.getDefault().post(new LogoutEvent(App.getContext(),0));
                _onError("请先登录", ERROR_NOT_LOGIN);
            } else if (exception.code() == 500) {
                _onError("服务器异常，请稍后重试.", ERROR_DEFAULT);
            } else {
                _onError("连接服务器失败，请稍后再试..", ERROR_DEFAULT);
            }
        } else if (e instanceof SocketTimeoutException) {
            _onError("连接服务器超时，请稍后再试...", ERROR_TIME_OUT);
        } else if (e instanceof ClassCastException) {
            _onError("数据解析异常，请稍后再试...", ERROR_DEFAULT);
        } else {
            _onError("连接服务器失败，请稍后再试....", ERROR_DEFAULT);
        }
        _onCompleted();
    }

    protected abstract void _onStart();

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

    protected abstract void _onCompleted();

    protected void _onError(String message, int code) {
        _onError(message);
    }
}
