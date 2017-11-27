/**
 *
 */
package com.rt.zgloan.util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.rt.zgloan.R;
import com.rt.zgloan.bean.Version;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.http.HttpSubscriber;
import com.rt.zgloan.presenter.BasePresenter;
import com.rt.zgloan.weight.AlertDialog;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
/*
* Error:A problem was found with the configuration of task ':app:packageZgloanRelease'.
> File 'D:\rt_workplace\ZgLoan\app\build\intermediates\res\resources-zgloan-release-stripped.ap_' specified for property 'resourceFile' does not exist.
* */


public class UpdateVersionUtil {
    private Context context;
    private int versionCode;
    private String versionName;
    //private int type = -1;// 0 自动检测更新 1 点击检测更新
    private NotificationManager mNotificationManager;
    Notification mNotification;
    private static final int NOTIFY_ID = 0x11;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int count = (Integer) msg.obj;
                    RemoteViews contentview = mNotification.contentView;
                    contentview.setTextViewText(R.id.tv_progress, count + "%");
                    contentview.setProgressBar(R.id.progressbar, 100, count, false);
                    mNotificationManager.notify(NOTIFY_ID, mNotification);
                    break;
                case 1:
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
                default:
                    break;
            }
        }
    };

    public UpdateVersionUtil(Context context) {
        this.context = context;
        //this.type = type;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void checkVersion(final boolean  isShowTip) {
        BasePresenter mPresenter = new BasePresenter();
        Map<String, String> parmas = new HashMap<String, String>();
        parmas.put("version_number", ViewUtil.getAppVersionCode(context) + "");
//        parmas.put("version_number","2");
        //...........
        parmas.put("canal_id", context.getResources().getString(R.string.channelId));
        mPresenter.toSubscribe(HttpManager.getApi().checkAppVersion(parmas), new HttpSubscriber<Version>() {
            @Override
            protected void _onStart() {

            }

            @Override
            protected void _onNext(Version version) {
                if (version.getIs_new().equals("0") && version.getIs_update().equals("0")) {//不强制更新
                    getDialog(version);
                } else if (version.getIs_new().equals("1")) {
                    //没有更新信息
                    if (isShowTip){
                        ToastUtil.showToast("没有更新信息");
                    }
                } else if (version.getIs_new().equals("0") && version.getIs_update().equals("1")) {//强制更新
                    if (NetUtil.isWifi(context)) {
                        isShow = false;
                        startDownLoad(version);
                    } else {
                        getDialog(version);
                    }

                }


            }

            @Override
            protected void _onError(String message) {

            }

            @Override
            protected void _onCompleted() {

            }
        });


//        IRequest http = new IRequest(context);
//        versionCode = AppUtil.getAppVersionCode(context);
//        versionName = AppUtil.getAppVersionName(context);
//        RequestParams params = new RequestParams();
//        params.put("version", versionName + "");
//        params.put("client", Constant.CLIENT_ID_NAME);
//
//        http.post(Config.URL_VERSIONON, params, new RequestListener() {
//            @Override
//            public void requestSuccess(String json) {
//                AbLogUtil.e("checkVersion", json.toString());
//
//                if (AbJsonUtil.isSuccess(json)) {
//                    final Version version = (Version) AbJsonUtil.fromJson(json, Version.class);
//                    if (version.getStatus().equals("maybe")) {
//                        getDialog(version);
//                    } else if (version.getStatus().equals("none")) {
//                        if (type == 0) {
//                            return;
//                        } else if (type == 1) {
//                            final AlertDialog dialog = new AlertDialog(context);
//                            if (dialog != null) {
//                                dialog.showDialog("版本更新", "已是最新版本V" + versionName, new AlertDialog.DialogOnClickListener() {
//
//                                    @Override
//                                    public void onPositiveClick() {
//                                        dialog.removeDialog(context);
//                                    }
//
//                                    @Override
//                                    public void onNegativeClick() {
//                                        dialog.removeDialog(context);
//                                    }
//                                });
//                            }
//                        }
//                    } else if (version.getStatus().equals("must")) {
//                        if (AbNetwork.getNetworkType() == AbNetwork.NETTYPE_WIFI) {
//                            isShow = false;
//                            startDownLoad(version);
//                        } else {
//                            getDialog(version);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void requestError(String message) {
//
//            }
//        });

    }

    private void getDialog(final Version version) {
        final AlertDialog dialog = new AlertDialog(context);
        String content = version.getDescription();
        if (dialog != null) {
            dialog.showDialog("版本更新", "最新版本：" + version.getVersion_name() + "\n" + content, new AlertDialog.DialogOnClickListener() {
                @Override
                public void onPositiveClick() {
                    dialog.removeDialog(context);
                    startDownLoad(version);
                }

                @Override
                public void onNegativeClick() {
                    dialog.removeDialog(context);
                }
            }, true);
        }
    }

    /**
     * 创建通知
     */
    @SuppressWarnings("deprecation")
    private void setUpNotification() {
        int icon = R.mipmap.icon_logo;
        CharSequence tickerText = "开始下载";
        long when = System.currentTimeMillis();
        mNotification = new Notification(icon, tickerText, when);
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.download_notification_layout);
        contentView.setTextViewText(R.id.name, "正在下载 ");
        contentView.setImageViewResource(R.id.image, R.raw.logo);
        // 指定个性化视图
        mNotification.contentView = contentView;
//		Intent intent = new Intent(this, MainActivity.class);
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,
//				PendingIntent.FLAG_UPDATE_CURRENT);
        // 指定内容意图
//		mNotification.contentIntent = contentIntent;
        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    // 安装最新本版
    private void install(File file) {
//		// 调系统的隐式意图
        if (!file.exists()) {
            ToastUtil.showToast("安装包不存在");
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String fileProvider = context.getResources().getString(R.string.fileprovider);
                Uri contentUri = FileProvider.getUriForFile(context, fileProvider, file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            ToastUtil.showToast("安装失败,请手动选择安装");
        }
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
//        context.startActivity(i);
    }

    /**
     * 判断是否需要显示通知栏和dialog 默认显示
     */
    private boolean isShow = true;

    private void startDownLoad(Version version) {
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("正在下载，请稍后...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        setUpNotification();
        if (isShow) {
            progress.show();
        }
        String path = version.getUrl();
        FinalHttp fh = new FinalHttp();
        String newName;
        if (path.contains("/")) {
            newName = path.substring(path.lastIndexOf("/"), path.length());
        } else {
            newName = "/zgloan.apk";
        }
        // 判断有没有SD卡
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String target = Environment.getExternalStorageDirectory() + newName;
            fh.download(path, target, new AjaxCallBack<File>() {
                // 下载中
                @SuppressLint("NewApi")
                @Override
                public void onLoading(long count, long current) {
                    super.onLoading(count, current);
                    progress.setMax((int) (count));// 进度长总长
                    progress.setProgress((int) (current));// 当前进度
                    DecimalFormat df = new DecimalFormat(
                            "0.00");
                    String formatCount = df
                            .format((float) count / 1024 / 1024)
                            + "MB";
                    String formatCurrent = df
                            .format((float) current / 1024 / 1024)
                            + "MB";
                    progress.setProgressNumberFormat(formatCurrent
                            + "/"
                            + formatCount);
                    int loading = (int) (current / (float) count * 100);
                    Message.obtain(mHandler, 0, loading).sendToTarget();
                }

                @Override
                public void onSuccess(File t) {
                    super.onSuccess(t);
                    progress.dismiss();
                    Message.obtain(mHandler, 1).sendToTarget();
                    install(t);
                }
            });
        } else {
            // 没有SD卡
            Toast.makeText(context, "未检测到sd卡", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
