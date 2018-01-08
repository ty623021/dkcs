package com.rt.zgloan.activity;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.rt.zgloan.R;
import com.rt.zgloan.base.BaseActivity;
import com.rt.zgloan.bean.BaseResponse;
import com.rt.zgloan.bean.VersionBean;
import com.rt.zgloan.http.HttpManager;
import com.rt.zgloan.util.AbStringUtil;
import com.rt.zgloan.util.AppUtil;
import com.rt.zgloan.util.SpUtil;
import com.rt.zgloan.util.ToastUtil;
import com.rt.zgloan.util.ViewUtil;
import com.rt.zgloan.weight.AlertDialog;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

/**
 * 设置
 * Created by hjy on 2017/8/25.
 */

public class SetAtivity extends BaseActivity<VersionBean> {


    @BindView(R.id.rel_change_login_password)
    RelativeLayout rel_change_login_password;
    @BindView(R.id.rel_change_phone)
    RelativeLayout rel_change_phone;
    @BindView(R.id.btn_sign_out)
    Button btn_sign_out;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.layout_height_top)
    RelativeLayout mLayoutHeightTop;
    @BindView(R.id.Rl_check_version)
    RelativeLayout mRlCheckVersion;
    @BindView(R.id.iv_latest_version)
    ImageView mIvLastestVersion;

    int versionCode;//当前版本号
    String version;//线上版本号
    String url;//下载链接
    String content;//更新内容
    String versionName;//版本名称

    private NotificationManager mNotificationManager;
    Notification mNotification;
    private static final int NOTIFY_ID = 0x11;

    @Override
    public Observable<BaseResponse<VersionBean>> initObservable() {
        mapParams.put("type", "1");

        return HttpManager.getApi().checkVersion(mapParams);
    }

    @Override
    public boolean isFirstLoad() {
        return true;
    }


    @OnClick({R.id.rel_change_login_password, R.id.rel_change_phone, R.id.btn_sign_out, R.id.Rl_check_version})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_change_login_password:
                this.startActivity(ChangeLoginPasswordActivity.class);
                break;

            case R.id.rel_change_phone:
                this.startActivity(ChangePhoneActivity.class);
                break;
            //退出登录
            case R.id.btn_sign_out:
                SpUtil.remove(SpUtil.userId);
                SpUtil.remove(SpUtil.mobile);
                SpUtil.remove(SpUtil.isLogin);
                MainActivity.startMainActivity(mActivity, 3);
                break;
            //检查更新
            case R.id.Rl_check_version:
                if (AbStringUtil.isEmpty(version)) {
                    return;
                }
                if (versionCode < Integer.parseInt(version)) {
                    mIvLastestVersion.setVisibility(View.VISIBLE);
                    getDialog();
                } else {
                    ToastUtil.showToast("当前为最新版本");
                }
                break;

        }
    }


    public static void startActivity(Context context, String phone) {
        Intent intent = new Intent(context, SetAtivity.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initView() {
        if (AppUtil.isVersionKitkat()) {
            mLayoutHeightTop.setVisibility(View.VISIBLE);
        } else {
            mLayoutHeightTop.setVisibility(View.GONE);
        }
        mTitle.setTitle(true, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.startMainActivity(mActivity, 3);
            }
        }, "设置");
        versionCode = ViewUtil.getAppVersionCode(mContext);//当前的版本号

    }


    @Override
    public void showLoading(String content) {

    }

    @Override
    public void showErrorMsg(String msg, String type) {

    }

    @Override
    public void recordSuccess(VersionBean versionBean) {
        if (versionBean != null) {
            version = versionBean.getVersion();
            url = versionBean.getUrl();
            content = versionBean.getContent();
            versionName = versionBean.getVersionName();
        }
    }

    private void getDialog() {
        final AlertDialog dialog = new AlertDialog(mContext);
        if (dialog != null) {
            dialog.showDialog("版本更新", "最新版本：" + versionName + "\n" + content, new AlertDialog.DialogOnClickListener() {
                @Override
                public void onPositiveClick() {
                    dialog.removeDialog(mContext);
                    startDownLoad();
                }

                @Override
                public void onNegativeClick() {
                    dialog.removeDialog(mContext);
                }
            }, true);
        }
    }

    /**
     * 判断是否需要显示通知栏和dialog 默认显示
     */
    private boolean isShow = true;

    private void startDownLoad() {
        final ProgressDialog progress = new ProgressDialog(mContext);
        progress.setTitle("正在下载，请稍后...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        setUpNotification();
        if (isShow) {
            progress.show();
        }
        String path = url;
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
            Toast.makeText(mContext, "未检测到sd卡", Toast.LENGTH_SHORT).show();
            return;
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
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotification = new Notification(icon, tickerText, when);
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.download_notification_layout);
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
                String fileProvider = mContext.getResources().getString(R.string.fileprovider);
                Uri contentUri = FileProvider.getUriForFile(mContext, fileProvider, file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mContext.startActivity(intent);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            ToastUtil.showToast("安装失败,请手动选择安装");
        }
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
//        context.startActivity(i);
    }

}
